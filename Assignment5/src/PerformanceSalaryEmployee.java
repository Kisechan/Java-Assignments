class PerformanceSalaryEmployee extends Employee {
    double salesAmount;

    public PerformanceSalaryEmployee(String id, String name, double baseSalary, double salesAmount, double absenceHours) {
        super(id, name, baseSalary, absenceHours);
        this.salesAmount = salesAmount;
        this.salary = calculateSalary();
    }

    @Override
    double calculateSalary() {
        double commission = 0;
        if (salesAmount <= 5000) {
            commission = salesAmount * 0.12;
        } else if (salesAmount <= 10000) {
            commission = 5000 * 0.12 + (salesAmount - 5000) * 0.15;
        } else {
            commission = 5000 * 0.12 + 5000 * 0.15 + (salesAmount - 10000) * 0.18;
        }
        return salary + commission;
    }
}