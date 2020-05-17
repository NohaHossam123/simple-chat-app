/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    ServerSocket myserver;
    Socket s;

    public Server() {
        try {
            myserver = new ServerSocket(5000);
            while (true) {
                s = myserver.accept();
                new ChatHandler(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

class ChatHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    static Vector<ChatHandler> chats = new Vector<ChatHandler>();

    public ChatHandler(Socket s) throws IOException {
        dis = new DataInputStream(s.getInputStream());
        ps = new PrintStream(s.getOutputStream());
        chats.add(this);
        start();
    }

    @Override
    public void run() {
        String msg;
        try {
            msg = dis.readLine();
            SendMsgToAll(msg);
        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void SendMsgToAll(String s) {

        for (ChatHandler ch : chats) {
            ch.ps.println(s);
        }
    }
}
