import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

class ChooseThread extends Thread {
    private boolean runFlag = true;
    private int times = 0;

    Vector<String> vector = new Vector();
    Random randomNumber = new Random();

    public ChooseThread() {}
    public void launch() {
        super.start();
    }
    public void changeflag_start() {
        runFlag = true;
    }
    public void changeflag_stop() {
        runFlag = false;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        while (runFlag) {
            System.out.println("抽奖开始……");
            long phoneNum;
            phoneNum = randomNumber.nextInt(10000000) + 13000000000L;
            times++;
            System.out.println("获奖号码：" + phoneNum + "\t" + "计数: " + times);
            vector.add(phoneNum + "");

            try {
                sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.print("输入任意字符继续，EXIT结束抽奖：");
            String answer = sc.next();
            if (answer.toLowerCase().equals("exit")) {
                System.out.println("获奖信息" + vector);
                this.changeflag_stop();
            }
        }
    }
}

