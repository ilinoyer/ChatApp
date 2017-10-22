package com.company;
import java.net.Socket;

/**
 * Created by sojer on 22.10.2017.
 */
public class Session {
    private Socket socket;
    private String user;
    private Server server;

    public Session(Socket socket, String user, Server server) {
        this.socket = socket;
        this.user = user;
        this.server = server;
    }

    public void notify(String message)
    {

    }
}
