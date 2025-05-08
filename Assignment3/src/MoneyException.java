public class MoneyException extends Exception {
    private final String content;

    public MoneyException(String content) {
        super(content);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}