class PerformanceSalaryEmployee extends Employee {
    double salesAmount;

    public PerformanceSalaryEmployee(String id, String name, double baseSalary, double salesAmount, double absenceHours) {
        super(id, name, baseSalary, absenceHours);
        this.salesAmount = salesAmount;
        this.salary = calculateSalary();
    }

    @Override
    double calculateSalary() {
        double commission;
        if (salesAmount <= 5000) {
            commission = salesAmount * 0.12;
        } else if (salesAmount <= 10000) {
            commission = 5000 * 0.12 + (salesAmount - 5000) * 0.15;
        } else {
            commission = 5000 * 0.12 + 5000 * 0.15 + (salesAmount - 10000) * 0.18;
        }
        return salary + commission;
    }

    public void displaySalaryDetails() {
        double grossSalary = calculateSalary();
        double deduction = absenceHours * 50;
        double netSalary = grossSalary - deduction;
        System.out.println("Id：" + id + "，姓名：" + name + "，应发工资：" + String.format("%.1f", grossSalary) + "元，缺勤" + String.format("%.1f", absenceHours) + "小时，扣款：" + String.format("%.1f", deduction) + "元，实发工资：" + String.format("%.1f", netSalary) + "元");
    }
}
