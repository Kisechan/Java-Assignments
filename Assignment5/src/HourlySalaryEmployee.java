class HourlySalaryEmployee extends Employee {
    double hourlyRate;
    double workHours;

    public HourlySalaryEmployee(String id, String name, double hourlyRate, double workHours, double absenceHours) {
        super(id, name, 0, absenceHours);
        this.hourlyRate = hourlyRate;
        this.workHours = workHours;
        this.salary = calculateSalary();
    }

    @Override
    double calculateSalary() {
        if (workHours < 120) {
            return hourlyRate * 120;
        }
        return hourlyRate * workHours;
    }

    public void displaySalaryDetails() {
        double grossSalary = calculateSalary();
        double deduction = absenceHours * 50;
        double netSalary = grossSalary - deduction;
        System.out.println("Id：" + id + "，姓名：" + name + "，应发工资：" + String.format("%.1f", grossSalary) + "元，缺勤" + String.format("%.1f", absenceHours) + "小时，扣款：" + String.format("%.1f", deduction) + "元，实发工资：" + String.format("%.1f", netSalary) + "元");
    }
}
