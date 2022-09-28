import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Registration_form extends JFrame{
    private JLabel getName;
    private JLabel getGender;
    public JPanel EmployeeRegForm;
    private JLabel getStartDate;
    private JLabel getLevel;
    private JLabel getTeamInfo;
    private JLabel getPhone;
    private JLabel getEmail;
    private JButton save;
    private JButton button_clear;
    private JTextField setName;
    private JTextField setTeamInfo;
    private JTextField setPhone;
    private JTextField setEmail;
    private JTextField setLevel;
    private JTextField setDOB;
    private JTextField setStartDate;
    private JTextField setGender;
    private JLabel idLabel;
    private JTextField id;
    private JButton selectImageFileButton;

    private BufferedImage img;

    public Registration_form() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(save,  "User has been added successfully");

                DataStore storeInstance = DataStore.getDataStoreInstance();
                System.out.println(id.getText() + "fetched");
                storeInstance.addRecordIfNotExist(new Employee(setName.getText(), id.getText(), img));

                id.setText("");
                setName.setText("");
                setGender.setText("");
                setStartDate.setText("");
                setLevel.setText("");
                setTeamInfo.setText("");
                setPhone.setText("");
                setEmail.setText("");
                resetImage();
            }
        });
        button_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                id.setText("");
                setName.setText("");
                   setGender.setText("");
                setStartDate.setText("");
                setLevel.setText("");
                setTeamInfo.setText("");
                setPhone.setText("");
                setEmail.setText("");
                resetImage();
            }
        });
        selectImageFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser chooser = new JFileChooser();
                int ch = chooser.showOpenDialog(null);

                if(ch==JFileChooser.APPROVE_OPTION){
                    try {
                        File fl = chooser.getSelectedFile();
                        img = ImageIO.read(fl);
                        selectImageFileButton.setEnabled(false);
                        selectImageFileButton.setText(fl.getAbsolutePath());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Unable to load image");
                    }
                }
            }
        });
    }

    private void resetImage(){
        selectImageFileButton.setEnabled(true);
        selectImageFileButton.setText("Select Image File");
        img=null;
    }
}
