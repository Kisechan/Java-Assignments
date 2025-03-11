public class Seagull extends Camera{
    public Seagull(String name,String color) {
        super(name,color);
    }

    @Override
    public void getPic() {
        System.out.println("Seagull Camera: " + getName() + " - " + getColor());
    }
}
