import java.util.Scanner;

public class LoginTest {
    public static void main(String[] args) {
        String correctUsername = "admin";
        String correctPassword = "123456";
        int loginAttempts = 0;
        final int MAX_ATTEMPTS = 3;
        Scanner scanner = new Scanner(System.in);

        while (loginAttempts < MAX_ATTEMPTS) {
            System.out.print("请输入用户名：");
            String username = scanner.nextLine();
            System.out.print("请输入密码：");
            String password = scanner.nextLine();

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("登录成功");
                scanner.close();
                return;
            } else {
                loginAttempts++;
                System.out.println("用户名或密码错误，您还有 " + (MAX_ATTEMPTS - loginAttempts) + " 次尝试机会。");
            }
        }

        try {
            throw new LoginException("登录失败次数达到上限，程序退出。");
        } catch (LoginException e) {
            System.err.println("登录失败异常：" + e.getContent());
        } finally {
            scanner.close();
        }
    }
}