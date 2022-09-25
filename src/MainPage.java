import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame{
    private Boolean admin;
    private JButton buttonLogInUser;
    private JButton buttonLogInAdmin;
    private JPanel MainPage;
    LogInPage lp = new LogInPage();

    public MainPage() {
        buttonLogInUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                admin = Boolean.FALSE;
                LogIn();
            }
        });
        buttonLogInAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                admin = Boolean.TRUE;
                LogIn();
            }
        });
    }

    public void LogIn(){
        lp.setContentPane(lp.LogInPg);
        lp.setTitle("Log In");
        lp.setSize(800,600);
        lp.setVisible(true);
        lp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        MainPage mp = new MainPage();
        mp.setContentPane(mp.MainPage);
        mp.setTitle("Main App");
        mp.setSize(800,600);
        mp.setVisible(true);
        mp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}


