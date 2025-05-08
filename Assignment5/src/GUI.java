import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class GUI extends JFrame implements ActionListener {
    private List<Employee> employees;
    private JTextArea outputArea;
    private JPanel addEmployeePanel;
    private JPanel queryPanel;

    private JTextField addIdField;
    private JTextField addNameField;
    private JComboBox<String> salaryTypeComboBox;
    private JTextField addSalaryField;
    private JTextField addAbsenceField;
    private JTextField addWorkHoursField;
    private JTextField addSalesAmountField;
    private JButton addButton;

    private JTextField queryIdField;
    private JButton queryButton;
    private JPanel controlPanel;

    public GUI() {
        employees = new ArrayList<>();
        setTitle("薪酬系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

        createAddEmployeePanel();
        createQueryPanel();

        controlPanel.add(addEmployeePanel);
        controlPanel.add(queryPanel);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createAddEmployeePanel() {
        addEmployeePanel = new JPanel();
        addEmployeePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel;

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("员工ID："));
        addIdField = new JTextField(10);
        panel.add(addIdField);
        addEmployeePanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("员工姓名："));
        addNameField = new JTextField(10);
        panel.add(addNameField);
        addEmployeePanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("薪资类别："));
        salaryTypeComboBox = new JComboBox<>(new String[]{"固定工资制", "时薪制", "业绩制"});
        panel.add(salaryTypeComboBox);
        addEmployeePanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("固定薪资/时薪/底薪："));
        addSalaryField = new JTextField(10);
        panel.add(addSalaryField);
        addEmployeePanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("本月缺勤时长："));
        addAbsenceField = new JTextField(10);
        panel.add(addAbsenceField);
        addEmployeePanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("本月工作时长："));
        addWorkHoursField = new JTextField(10);
        panel.add(addWorkHoursField);
        addEmployeePanel.add(panel);
        addWorkHoursField.setVisible(false);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("本月销售额："));
        addSalesAmountField = new JTextField(10);
        panel.add(addSalesAmountField);
        addEmployeePanel.add(panel);
        addSalesAmountField.setVisible(false);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("录入员工信息");
        addButton.addActionListener(this);
        panel.add(addButton);
        addEmployeePanel.add(panel);

        salaryTypeComboBox.addActionListener(e -> {
            String selectedType = (String) salaryTypeComboBox.getSelectedItem();
            addWorkHoursField.setVisible("时薪制".equals(selectedType));
            addSalesAmountField.setVisible("业绩制".equals(selectedType));
        });
    }

    private void createQueryPanel() {
        queryPanel = new JPanel();
        queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JPanel panel;

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("请输入员工ID查询："));
        queryIdField = new JTextField(10);
        panel.add(queryIdField);
        queryPanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        queryButton = new JButton("查询");
        queryButton.addActionListener(this);
        panel.add(queryButton);
        queryPanel.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String id = addIdField.getText();
            String name = addNameField.getText();
            String selectedType = (String) salaryTypeComboBox.getSelectedItem();
            double salaryOrRate = 0;
            double absence = 0;

            try {
                salaryOrRate = Double.parseDouble(addSalaryField.getText());
                absence = Double.parseDouble(addAbsenceField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字格式！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (selectedType) {
                case "固定工资制":
                    employees.add(new FixedSalaryEmployee(id, name, salaryOrRate, absence));
                    outputArea.append("成功录入固定工资制员工：" + name + " (ID: " + id + ")\n");
                    break;
                case "时薪制":
                    double workHours = 0;
                    try {
                        workHours = Double.parseDouble(addWorkHoursField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "请输入有效的工作时长！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    employees.add(new HourlySalaryEmployee(id, name, salaryOrRate, workHours, absence));
                    outputArea.append("成功录入时薪制员工：" + name + " (ID: " + id + ")\n");
                    break;
                case "业绩制":
                    double salesAmount = 0;
                    try {
                        salesAmount = Double.parseDouble(addSalesAmountField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "请输入有效的销售额！", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    employees.add(new PerformanceSalaryEmployee(id, name, salaryOrRate, salesAmount, absence));
                    outputArea.append("成功录入业绩制员工：" + name + " (ID: " + id + ")\n");
                    break;
            }
            clearInputFields();
        } else if (e.getSource() == queryButton) {
            String queryId = queryIdField.getText();
            boolean found = false;
            for (Employee employee : employees) {
                if (employee.id.equals(queryId)) {
                    outputArea.append("\n查询结果：\n" + employee.getSalaryDetails() + "\n");
                    found = true;
                    break;
                }
            }
            if (!found) {
                outputArea.append("\n未找到ID为 " + queryId + " 的员工信息。\n");
            }
            queryIdField.setText("");
        }
    }

    private void clearInputFields() {
        addIdField.setText("");
        addNameField.setText("");
        addSalaryField.setText("");
        addAbsenceField.setText("");
        addWorkHoursField.setText("");
        addSalesAmountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}