package sample;

/**
 * Created by sojer on 22.10.2017.
 */
public class User {
    private String login;
    private String password;


    public User(String login)
    {
        this.login = login;
    }
    public User()
    {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
