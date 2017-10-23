package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by sojer on 22.10.2017.
 */
public class Server {

    private Socket socket;
    private ServerSocket serverSocket;
    private ArrayList<Session> sessionList;
    private static int clientNumber = 0;

    public Server() {
        this.sessionList = new ArrayList<Session>();
    }

    public void run() throws IOException
    {
        serverSocket = new ServerSocket(8080);
        while(true)
        {

            //serverSocket.setReuseAddress(true);
           /// serverSocket.bind(new InetSocketAddress(8080));
            socket = serverSocket.accept();
            System.out.println("New Client connected." + serverSocket);

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            Session newSession = new Session(socket,"client " + clientNumber, dataInputStream, dataOutputStream, this);

            // Create a new Thread with this object.
            Thread sessionThread = new Thread(newSession);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            sessionList.add(newSession);

            // start the thread.
            sessionThread.start();

            ++clientNumber;

        }
    }

    public Session getSessionByPosition(int position) {
        return sessionList.get(position);
    }

    public int getClientsNumber()
    {
        return sessionList.size();
    }
}
