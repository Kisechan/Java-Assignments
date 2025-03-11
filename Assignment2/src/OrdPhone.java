class OrdPhone extends Phone {
    public OrdPhone(String brand) {
        super(brand);
    }

    @Override
    public void call() {
        System.out.println("OrdPhone " + getBrand() + " is calling...");
    }

    public void send() {
        System.out.println("OrdPhone " + getBrand() + " is sending a message...");
    }
}
