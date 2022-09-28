import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EmployeeDetailPage extends JFrame{
    private JTextField id;
    private JTextField name;
    private JButton saveButton;
    private JButton deleteButton;
    public JPanel employeeDetailPg;
    private JLabel image;

    private BufferedImage img;

    public EmployeeDetailPage() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataStore ds  = DataStore.getDataStoreInstance();
                Employee e = new Employee(name.getText(),id.getText(), img);
                ds.updateRecord(e);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataStore ds  = DataStore.getDataStoreInstance();
                ds.deleteRecord(id.getText());
            }
        });
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                int ch = chooser.showOpenDialog(null);

                if(ch==JFileChooser.APPROVE_OPTION){
                    try {
                        img = ImageIO.read(chooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,  "Unable to load image");
                    }
                }
                showImage();
            }
        });
    }

    public void setData(Employee e) {
        name.setText(e.getName());
        id.setText(e.getId());
        img = e.getImage();
        showImage();
    }

    private void showImage() {
        if(Objects.nonNull(img)){
            image.setIcon(new ImageIcon(img));
        }
    }
}
