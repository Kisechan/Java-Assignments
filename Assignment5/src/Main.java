import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SalarySystem system = new SalarySystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n薪酬系统");
            System.out.println("1．录入员工本月薪酬信息");
            System.out.println("2．工资明细查询");
            System.out.println("3．退出系统");
            System.out.print("请输入操作编号：");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("输入员工薪资类别（1.固定工资制2.时薪制3.业绩制）：");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("请输入员工ID：");
                    String id = scanner.nextLine();
                    System.out.print("请输入员工姓名：");
                    String name = scanner.nextLine();
                    System.out.print("请输入员工薪资（时薪制输入时薪，其他输入固定薪资）：");
                    double salaryOrRate = scanner.nextDouble();
                    System.out.print("请输入本月缺勤时长（小时）：");
                    double absence = scanner.nextDouble();
                    scanner.nextLine();

                    switch (type) {
                        case 1:
                            system.addEmployee(new FixedSalaryEmployee(id, name, salaryOrRate, absence));
                            break;
                        case 2:
                            System.out.print("请输入本月工作时长（小时）：");
                            double workHours = scanner.nextDouble();
                            scanner.nextLine();
                            system.addEmployee(new HourlySalaryEmployee(id, name, salaryOrRate, workHours, absence));
                            break;
                        case 3:
                            System.out.print("请输入员工本月销售额：");
                            double sales = scanner.nextDouble();
                            scanner.nextLine();
                            system.addEmployee(new PerformanceSalaryEmployee(id, name, salaryOrRate, sales, absence));
                            break;
                        default:
                            System.out.println("无效的薪资类别。");
                            break;
                    }
                    System.out.print("是否继续录入员工信息（1.是2.否）：");
                    int continueInput = scanner.nextInt();
                    scanner.nextLine();
                    if (continueInput == 2) {
                        break;
                    }
                    break;
                case 2:
                    System.out.print("请输入员工ID：");
                    String queryId = scanner.nextLine();
                    system.querySalary(queryId);
                    break;
                case 3:
                    System.out.println("退出系统。");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效的操作编号，请重新输入。");
            }
        }
    }
}