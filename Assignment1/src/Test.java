public class Test {
    public static void main(String[] args) {
        Camera s = CameraFactory.getCamera(2);
        Camera a = CameraFactory.getCamera(1);

        Print print = new Print();

        print.printPic(s);
        print.printPic(a);
    }
}