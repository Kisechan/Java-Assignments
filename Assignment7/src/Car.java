public class Car implements Runnable {
    private int id;
    private TrafficLight trafficLight;

    public Car(int id, TrafficLight trafficLight) {
        this.id = id;
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {
        while (true) {
            if (trafficLight.getIsGreen()) {
                System.out.println("汽车" + id + "通过路口");
                break;
            } else {
                System.out.println("汽车" + id + "等候红灯");
                try {
                    Thread.sleep(trafficLight.getRemainTime());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
