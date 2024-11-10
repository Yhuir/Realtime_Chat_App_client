package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;
import com.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClientService {

    private User u = new User();
    private boolean b = false;


    public boolean checkUser(String id, String passwd) {

        u.setUserId(id);
        u.setPasswd(passwd);

        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) ois.readObject();

            if (msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {


                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                ManageClientConnectServerThread.addClientConnectServerThread(id, ccst);

                b = true;

            } else {

                socket.close();

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return b;

    }

    public void onlineFriendList() {
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_GET_ONLINE_FRIEND);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()
                    ).getSocket().getOutputStream());
            oos.writeObject(message);
            

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void logout() {
        Message message = new Message();
        message.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId()
                    ).getSocket().getOutputStream());
            oos.writeObject(message);

            System.out.println(u.getUserId() + " Exit system");

            System.exit(0);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }




}
