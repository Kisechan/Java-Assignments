class Aigo extends Camera {
    public Aigo(String name, String color) {
        super(name, color);
    }

    public void doSomething() {
        System.out.println("Aigo Camera is doing something special...");
    }

    @Override
    public void getPic() {
        doSomething();
        System.out.println("Aigo Camera: " + getName() + " - " + getColor());
    }
}