public class Test {
    public static void main(String[] args) {
        Phone ordPhone = Create.getOrdPhone();
        Phone smartPhone = Create.getSmartPhone();

        ordPhone.call();
        smartPhone.call();

        if (ordPhone instanceof OrdPhone) {
            OrdPhone ord = (OrdPhone) ordPhone;
            ord.send();
        }

        if (smartPhone instanceof SmartPhone) {
            SmartPhone smart = (SmartPhone) smartPhone;
            smart.net();
        }
    }
}