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
    private JTextField levelSearchQuery;
    private JTextField positionSearchQuery;

    Registration_form regForm = new Registration_form();
    EmployeeDetailPage empDetail = new EmployeeDetailPage();

    DataStore dStore;

    public ListingPage() {
        dStore = DataStore.getDataStoreInstance();
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
                showEmpDetails(e);
            }
        });
    }

    private void showEmpDetails(Employee e) {
        empDetail.setContentPane(empDetail.employeeDetailPg);
        empDetail.setTitle("Employee Detail");
        empDetail.setSize(800,600);
        empDetail.setVisible(true);
        empDetail.setData(e);
        empDetail.setCallBack(this::updateList);
    }

    public void updateList(){

        String idSearchQry = idSearchQuery.getText();
        String nameSearchQry = nameSearchQuery.getText();
        String levelSearchQry = levelSearchQuery.getText();
        String positionSearchQry = positionSearchQuery.getText();

        String[] columnNames = { "ID", "Name", "Level", "Position"};
        List<Employee> empList = dStore.getRecords().stream().filter(employee ->
                employee.getId().contains(idSearchQry)).filter(employee ->
                employee.getName().contains(nameSearchQry)).filter(employee ->
                employee.getLevel().contains(levelSearchQry)).filter(employee ->
                employee.getPosition().contains(positionSearchQry)).collect(Collectors.toList());
        System.out.println(empList.size());
        DefaultTableModel model = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        for(int i=0;i<empList.size();i++){
            Employee emp = empList.get(i);
            Object[] row = { emp.getId(), emp.getName(), emp.getLevel(), emp.getPosition()};
            model.addRow(row);
        }
        table1.setModel(model);
    }

    public void registerNewUser()
    {
        regForm.setContentPane(regForm.EmployeeRegForm);
        regForm.setTitle("Registration Form");
        regForm.setSize(600,400);
        regForm.setVisible(true);
        regForm.setCallBack(this::updateList);
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
