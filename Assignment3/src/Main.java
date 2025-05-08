public class Main {
    public static void main(String[] args) {
        DebitCard card = new DebitCard("1234567890", 100.0);
        System.out.println();

        try {
            double newBalance = -50.0;
            if (newBalance < 0) {
                throw new MoneyException("设置余额失败：余额不能为负数，尝试设置的值为：" + newBalance);
            }
            card.setMoney(newBalance);
            System.out.println("设置后的余额：" + card.getMoney());

        } catch (MoneyException e) {
            System.err.println("捕获到 MoneyException 异常：" + e.getContent());
            System.err.println("异常的详细信息：" + e.getMessage());
        } finally {
            System.out.println("程序执行完毕。");
        }
    }
}