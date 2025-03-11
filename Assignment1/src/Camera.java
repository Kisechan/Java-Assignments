abstract public class Camera {
    private String name;
    private String color;

    public Camera(String name, String color) {
        this.name = name;
        this.color = color;
    }

    abstract public void getPic();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}