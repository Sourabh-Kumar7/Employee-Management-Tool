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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration_form extends JFrame{
    private JLabel nameLabel;
    private JLabel genderLabel;
    public JPanel EmployeeRegForm;
    private JLabel startDateLabel;
    private JLabel levelLabel;
    private JLabel getTeamInfo;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JButton saveButton;
    private JButton clearButton;
    private JTextField name;
    private JTextField teamInfo;
    private JTextField phone;
    private JTextField email;
    private JTextField level;
    private JTextField setDOB;
    private JTextField startDate;
    private JLabel idLabel;
    private JTextField id;
    private JButton selectImageFileButton;
    private JLabel ageLabel;
    private JTextField position;
    private JComboBox gender;
    private JComboBox age;

    private BufferedImage img;
    private Runnable callBack;

    public Registration_form() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                //ID Validation
                if(id.getText().isEmpty()){
                    JOptionPane.showMessageDialog(saveButton,  "Id is required");
                    return;
                } else if(!checkIdValidation(id.getText())){
                    JOptionPane.showMessageDialog(saveButton,  "Please enter the valid ID");
                    return;
                }

                //Name Validation
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

                DataStore storeInstance = DataStore.getDataStoreInstance();
                System.out.println(id.getText() + "fetched");
                boolean addRecord=storeInstance.addRecordIfNotExist(new Employee(id.getText(), name.getText(), age.getSelectedItem().toString(),
                        gender.getSelectedItem().toString(), startDate.getText(), level.getText(), teamInfo.getText(), position.getText(),
                        phone.getText(), email.getText(), img));
                if (addRecord) {
                    callBack.run();
                    clearAll();
                    JOptionPane.showMessageDialog(saveButton,  "User has been added successfully");
                    Window wa = SwingUtilities.getWindowAncestor(EmployeeRegForm);
                    wa.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(saveButton,  "User already exists");
                }

            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearAll();
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
        gender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        gender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private boolean checkIdValidation(String id){

        boolean idValidity = true;
        try {
            int intValue = Integer.parseInt(id);
            if(intValue<1){
                idValidity = false;
            }
        }catch (NumberFormatException e){
            idValidity = false;
        }
        return idValidity;
    }
    private void resetImage(){
        selectImageFileButton.setEnabled(true);
        selectImageFileButton.setText("Select Image File");
        img=null;
    }

    private void clearAll(){
        id.setText("");
        name.setText("");
        age.setSelectedIndex(0);
        gender.setSelectedIndex(0);
        startDate.setText("");
        level.setText("");
        teamInfo.setText("");
        position.setText("");
        phone.setText("");
        email.setText("");
        resetImage();
    }

    public void setCallBack(Runnable r) {
        this.callBack=r;
    }
}
