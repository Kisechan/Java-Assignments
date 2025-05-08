class FixedSalaryEmployee extends Employee {
    public FixedSalaryEmployee(String id, String name, double salary, double absenceHours) {
        super(id, name, salary, absenceHours);
    }

    @Override
    double calculateSalary() {
        return salary;
    }
}
