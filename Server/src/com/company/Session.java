package com.company;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by sojer on 22.10.2017.
 */
public class Session implements Runnable{

    // private String user;

    private Server server;
    private Socket socket;
    Scanner scanner = new Scanner(System.in);
    private String name;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    private boolean isloggedin;

    public Session(Socket socket, String name, DataInputStream dataInputStream, DataOutputStream dataOutputStream, Server server) {
        this.socket = socket;
        this.name = name;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.isloggedin = true;
        this.server = server;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = dataInputStream.readUTF();

                System.out.println(received);

                if(received.equals("logout")){
                    this.isloggedin=false;
                    this.socket.close();
                    break;
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (int i = 0 ; i < server.getClientsNumber() ; ++i)
                {
                    // if the recipient is found, write on its
                    // output stream
                    if (server.getSessionByPosition(i).getName().equals(recipient) && server.getSessionByPosition(i).isloggedin==true)
                    {
                        server.getSessionByPosition(i).dataOutputStream.writeUTF(this.name+" : "+MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
