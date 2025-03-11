interface PrintPic {
    void printPic(Camera c);
}

public class Print implements PrintPic{
    @Override
    public void printPic(Camera c) {
        c.getPic();
    }
}
