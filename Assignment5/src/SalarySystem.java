import java.util.ArrayList;
import java.util.List;

class SalarySystem {
    private final List<Employee> employees;

    public SalarySystem() {
        this.employees = new ArrayList<>();
        initializeEmployees();
    }

    private void initializeEmployees() {
        employees.add(new FixedSalaryEmployee("a001", "张三", 8500, 2));
        employees.add(new HourlySalaryEmployee("b001", "李四", 50, 150, 1));
        employees.add(new PerformanceSalaryEmployee("c001", "王五", 6000, 15000, 0));
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void querySalary(String employeeId) {
        for (Employee employee : employees) {
            if (employee.id.equals(employeeId)) {
                System.out.println("您本月工资明细如下");
                System.out.println(employee.getSalaryDetails());
                return;
            }
        }
        System.out.println("未找到该员工信息。");
    }
}
