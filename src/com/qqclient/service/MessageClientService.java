package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {

    public void sendMsgToOne(String content, String sendId, String receiverId) {

        Message message = new Message();
        message.setSender(sendId);
        message.setContent(content);
        message.setReceiver(receiverId);
        message.setSendTime(new Date().toString());
        message.setMsgType(MessageType.MESSAGE_COMM_MESS);

        System.out.println(sendId+ " send message to " + receiverId +": "+ content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.getClientConnectServerThread(sendId).getSocket().getOutputStream());

            oos.writeObject(message);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsgToAll(String sendId, String content) {
        Message message = new Message();
        message.setSender(sendId);
        message.setMsgType(MessageType.MESSAGE_All_MESS);
        message.setContent(content);
        message.setSendTime(new Date().toString());

        System.out.println(sendId+ " send message to everyone: "+ content);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread.getClientConnectServerThread(sendId).getSocket().getOutputStream());

            oos.writeObject(message);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
