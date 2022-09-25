import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogInPage extends JFrame{
    public JPanel LogInPg;
    private JLabel Email;
    private JLabel Password;
    private JButton LogIn;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton ForgotPsswd;

    public LogInPage() {
        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        ForgotPsswd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }
}
