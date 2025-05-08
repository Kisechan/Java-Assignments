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
}
