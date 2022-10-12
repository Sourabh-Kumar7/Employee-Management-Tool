import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeDetailPage extends JFrame{
    private JTextField id;
    private JTextField name;
    private JButton saveButton;
    private JButton deleteButton;
    public JPanel employeeDetailPg;
    private JLabel image;
    private JTextField startDate;
    private JTextField level;
    private JTextField teamInfo;
    private JTextField email;
    private JTextField phone;
    private JTextField position;
    private JComboBox gender;
    private JComboBox age;
    private BufferedImage img;
    private Runnable callBack;

    public EmployeeDetailPage() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(name.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(saveButton, "Name is required");
                    return;
                }else {
//                    Name Validation
//                    Special Characters & digits Are Not Allowed.
//                    Spaces are only allowed between two words.
//                    Only one space is allowed between two words.
//                    Spaces at the start or at the end are consider to be invalid.
//                    Single word name is also valid : ^[a-zA-z]+([\s][a-zA-Z]+)*$
//                    Single word name is in-valid : ^[a-zA-z]+([\s][a-zA-Z]+)+$
//                    reference : https://stackoverflow.com/questions/9289451/regular-expression-for-alphabets-with-spaces
                    String nameRegex = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
                    Pattern namePattern = Pattern.compile(nameRegex);
                    Matcher nameMatcher = namePattern.matcher(name.getText());

                    if(!nameMatcher.matches()){
                        JOptionPane.showMessageDialog(saveButton, "Please enter valid name");
                        return;
                    }
                }
                //Phone Validation
                //reference : https://www.javatpoint.com/mobile-number-validation-in-java
                String phoneRegex = "(0/91)?[7-9][0-9]{9}";
                Pattern phonePattern = Pattern.compile(phoneRegex);
                Matcher phoneMatcher = phonePattern.matcher(phone.getText());

                if(!phoneMatcher.matches()){
                    JOptionPane.showMessageDialog(saveButton, "Please enter valid phone no.");
                    return;
                }

                //Email Validation
                //reference : https://www.javatpoint.com/java-email-validation
                String emailRegex =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
                Pattern emailPattern = Pattern.compile(emailRegex);
                Matcher emailMatcher = emailPattern.matcher(email.getText());

                if(!emailMatcher.matches()){
                    JOptionPane.showMessageDialog(saveButton, "Please enter valid email Id");
                    return;
                }

                DataStore ds  = DataStore.getDataStoreInstance();
                Employee e = new Employee(id.getText(), name.getText(), age.getSelectedItem().toString(), gender.getSelectedItem().toString(),
                        startDate.getText(), level.getText(), teamInfo.getText(), position.getText(),
                        phone.getText(), email.getText(), img);
                ds.updateRecord(e);
                callBack.run();
                JOptionPane.showMessageDialog(saveButton,  "User record has been updated successfully");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataStore ds  = DataStore.getDataStoreInstance();
                ds.deleteRecord(id.getText());
                callBack.run();
                JOptionPane.showMessageDialog(saveButton,  "User has been deleted successfully");
                Window wa = SwingUtilities.getWindowAncestor(employeeDetailPg);
                wa.dispose();
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

    private int selectAgeIndex(String age){

        int ageIndex = 0;

        if(age!="Please Select Your Age"){
            ageIndex = Integer.parseInt(age);
        }
        return ageIndex;
    }
    private int selectGenderIndex(String gender){
        Map<String, Integer> genderIndex = new HashMap<String, Integer>();
        genderIndex.put("Male", 1);
        genderIndex.put("Female", 2);
        genderIndex.put("Other", 3);
        genderIndex.put("Don't want to reveal", 4);

        if(gender!="Please Select Your Gender"){
            return genderIndex.get(gender);
        }

        return 0;
    }

    public void setData(Employee e) {
        id.setText(e.getId());
        name.setText(e.getName());
        age.setSelectedIndex(selectAgeIndex(e.getAge()));
        gender.setSelectedIndex(selectGenderIndex(e.getGender()));
        startDate.setText(e.getStartDate());
        level.setText(e.getLevel());
        teamInfo.setText(e.getTeamInfo());
        position.setText(e.getPosition());
        phone.setText(e.getPhone());
        email.setText(e.getEmail());
        img = e.getImage();
        showImage();
    }

    private void showImage() {
        if(Objects.nonNull(img)){
            image.setIcon(new ImageIcon(img));
        }
    }

    public void setCallBack(Runnable r) {
        this.callBack=r;
    }
}
