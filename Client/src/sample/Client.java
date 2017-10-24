package sample;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


import static java.lang.System.exit;

/**
 * Created by sojer on 24.10.2017.
 */
public class Client {

    final static int serverPort = 8080;
    private User user;
    private String ipAddress;


    public Client(User user, String ip) {
        this.user = user;
        this.ipAddress = ip;
    }

    public void runClient() throws IOException, UnknownHostException
    {
        Scanner scanner = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName(ipAddress);

        // establish the connection
        Socket s = new Socket(ip, serverPort);

        // obtaining input and out streams
        DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());

        //sendMessage Thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                try {
                    dataOutputStream.writeUTF(user.getLogin());
                }catch(IOException e)
                {e.printStackTrace();}

                while (true) {

                    String msg = "";
                    // read the message to deliver.


                        try {
                            msg = Main.bufforToSend.get(0);
                            Main.bufforToSend.remove(0);
                        }
                        catch(Exception e)
                        {}

                        try {
                            // write on the output stream
                            if(msg != "")
                                dataOutputStream.writeUTF(msg);
                            if(msg.equals("logout"))
                                exit(1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                }

        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dataInputStream.readUTF();
                        System.out.println(msg);
                        Main.bufforToWrite.add(msg);

                    } catch (Exception e) {

                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();

    }

}
