public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        Thread trafficLightThread = new Thread(trafficLight);
        trafficLightThread.start();

        int id = 1;
        while (true) {
            Car car = new Car(id++, trafficLight);
            Thread carThread = new Thread(car);
            carThread.start();

            if ((id - 1) % 10 == 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (id > 50) {
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("模拟结束，创建了 " + (id - 1) + " 辆汽车。");
    }
}