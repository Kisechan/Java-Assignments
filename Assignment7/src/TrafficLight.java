public class TrafficLight implements Runnable {
    private boolean isGreen = false;
    private int remainTime = 0;

    public void setGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }

    public boolean getIsGreen() {
        return isGreen;
    }

    public int getRemainTime() {
        return remainTime;
    }

    @Override
    public void run() {
        while (true) {
            try {
                setGreen(false);
                System.out.println("红灯亮起");
                remainTime = 3000;
                for (int i = 3; i > 0; i--) {
                    System.out.println("红灯剩余：" + i);
                    Thread.sleep(1000);
                    remainTime -= 1000;
                }

                setGreen(true);
                System.out.println("绿灯亮起");
                Thread.sleep(7000);
                System.out.println("绿灯剩余：3");
                Thread.sleep(1000);
                System.out.println("绿灯剩余：2");
                Thread.sleep(1000);
                System.out.println("绿灯剩余：1");
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
