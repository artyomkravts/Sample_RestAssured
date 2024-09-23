public class LoginSuccess { // Это POJO респонса успешного логина

    private Integer id;

    public LoginSuccess(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
