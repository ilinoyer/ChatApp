package com.company;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by sojer on 22.10.2017.
 */
public class Server {

    private Socket socket;
    private ArrayList<Session> sessionList;

    public Server(Socket socket) {
        this.socket = socket;
        this.sessionList = new ArrayList<Session>();
    }
}
