// (1) 创建自定义异常类 LoginException
public class LoginException extends Exception {
    private final String content;

    public LoginException(String content) {
        super(content);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}