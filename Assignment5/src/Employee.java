abstract class Employee {
    String id;
    String name;
    double salary;
    double absenceHours;

    public Employee(String id, String name, double salary, double absenceHours) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.absenceHours = absenceHours;
    }

    abstract double calculateSalary();

    public void displaySalaryDetails() {
        double grossSalary = calculateSalary();
        double deduction = absenceHours * 50;
        double netSalary = grossSalary - deduction;
        System.out.println("Id：" + id + "，姓名：" + name + "，应发工资：" + String.format("%.1f", grossSalary) + "元，缺勤" + String.format("%.1f", absenceHours) + "小时，扣款：" + String.format("%.1f", deduction) + "元，实发工资：" + String.format("%.1f", netSalary) + "元");
    }
}