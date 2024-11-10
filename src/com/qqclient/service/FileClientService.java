package com.qqclient.service;

import com.qqcommon.Message;
import com.qqcommon.MessageType;

import java.io.*;

public class FileClientService {

    public void sendFileToOne(String src, String dest, String sendId, String receiverId) {

        Message message = new Message();

        message.setSrc(src);
        message.setDest(dest);
        message.setSender(sendId);
        message.setReceiver(receiverId);
        message.setMsgType(MessageType.MESSAGE_FILE_MESS);

        byte[] fileBytes = new byte[(int) new File(src).length()];
        FileInputStream fileInputStream = null;

        try {

            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);
            message.setFileByte(fileBytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        System.out.println("\n" + sendId + " send " + src + " to " + receiverId + " at " + dest);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(sendId).getSocket().getOutputStream());
            oos.writeObject(message);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
