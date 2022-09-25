import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration_form extends JFrame{
    private JLabel getName;
    private JLabel getGender;
    private JLabel getDOB;
    private JPanel EmployeeRegForm;
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
    private JComboBox comboBox_Gender;
    private JTextField setDOB;
    private JTextField setStartDate;

    public Registration_form() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(save, setName.getText() + ", Hello");

            }
        });
    }
}
