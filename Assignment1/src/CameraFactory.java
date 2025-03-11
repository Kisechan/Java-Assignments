class CameraFactory {
    public static Camera getCamera(int i) {
        if (i == 1) {
            return new Aigo("Default", "Default");
        } else if (i == 2) {
            return new Seagull("Default", "Default");
        } else {
            return null;
        }
    }
}