import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListingPage extends JFrame{


    private JButton AddNewEmp;
    private JTable table1;
    private JPanel ListPage;
    private JTextField idSearchQuery;
    private JTextField nameSearchQuery;
    private JButton search;

    Registration_form regForm = new Registration_form();
    EmployeeDetailPage empDetail = new EmployeeDetailPage();

    DataStore dStore;

    public ListingPage() {
        String[][] data = {
                { "Kundan Kumar Jha", "4031"},
                { "Anand Jha", "6014" }
        };

        // Column Names
        String[] columnNames = { "ID", "Name"};

        // Initializing the JTable
//        table1 = new JTable(data, columnNames);
//        table1.setBounds(30,40,200,300);

        dStore = DataStore.getDataStoreInstance();
        //updateList();
        AddNewEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                registerNewUser();
                updateList();
            }
        });
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                updateList();
            }
        });

        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(table1.getSelectedRow()<0)return;
                String idOfSelected = (String) table1.getValueAt(table1.getSelectedRow(),0);
                table1.clearSelection();
                Employee e = dStore.getRecord(idOfSelected);
                if(Objects.isNull(e)){
                    return;
                }

                empDetail.setContentPane(empDetail.employeeDetailPg);
                empDetail.setTitle("Employee Details");
                empDetail.setSize(800,600);
                empDetail.setVisible(true);
                empDetail.setData(e);
            }
        });
    }

    private void updateList(){

        String idSearchQry = idSearchQuery.getText();
        String nameSearchQry = nameSearchQuery.getText();

        String[] columnNames = { "ID", "Name"};
        List<Employee> empList = dStore.getRecords().stream().filter(employee ->
            employee.getId().contains(idSearchQry)
        ).filter(employee -> employee.getName().contains(nameSearchQry)).collect(Collectors.toList());
        System.out.println(empList.size());
        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        for(int i=0;i<empList.size();i++){
            Employee emp = empList.get(i);
            Object[] row = { emp.getId(), emp.getName()};
            model.addRow(row);
        }
        table1.setModel(model);
    }

    public void registerNewUser()
    {
        regForm.setContentPane(regForm.EmployeeRegForm);
        regForm.setTitle("Main App");
        regForm.setSize(600,400);
        regForm.setVisible(true);
    }

    public static void main(String[] args){
        ListingPage listPg = new ListingPage();
        listPg.setContentPane(listPg.ListPage);
        listPg.setTitle("Main App");
        listPg.setSize(800,600);
        listPg.setVisible(true);
        listPg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
