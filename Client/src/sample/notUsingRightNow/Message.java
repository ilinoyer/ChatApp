package sample.notUsingRightNow;

import sample.User;

import java.util.Date;

/**
 * Created by sojer on 22.10.2017.
 */
public class Message {
    private String message;
    private Date date;
    private int lenght;
    private User user;


    public Message(String message, Date date, int lenght, User user) {
        this.message = message;
        this.date = date;
        this.lenght = lenght;
        this.user = user;
    }
}
