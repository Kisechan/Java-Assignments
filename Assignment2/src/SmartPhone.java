class SmartPhone extends Phone implements Net {
    public SmartPhone(String brand) {
        super(brand);
    }

    @Override
    public void call() {
        System.out.println("SmartPhone " + getBrand() + " is calling...");
    }

    @Override
    public void net() {
        System.out.println("SmartPhone " + getBrand() + " is surfing the internet...");
    }
}