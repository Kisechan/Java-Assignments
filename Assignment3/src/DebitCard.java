public class DebitCard {
    private String accountNumber;
    private double money;

    public DebitCard(String accountNumber, double money) {
        this.accountNumber = accountNumber;
        this.money = money;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "accountNumber='" + accountNumber + '\'' +
                ", money=" + money +
                '}';
    }
}