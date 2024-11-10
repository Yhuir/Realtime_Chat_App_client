package com.qqclient.view;

import com.qqclient.service.ManageClientConnectServerThread;
import com.qqclient.service.UserClientService;
import com.qqclient.utility.Utility;
import com.qqcommon.Message;
import com.qqcommon.MessageType;

import javax.swing.text.Utilities;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class QQView {
    //start

    private boolean loop = true;
    private String key = "";

    private UserClientService userClientService = new UserClientService();


    public static void main(String[] args) {
        QQView qqView = new QQView();
        qqView.mainMenu();
    }

    private void mainMenu() {
        while (loop) {
            System.out.println("-------Welcome to QQ Client-------");
            System.out.println("\t\t 1. Login");
            System.out.println("\t\t 9. Exit");
            System.out.print("Please enter: ");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("Please enter your QQ ID: ");
                    String qqID = Utility.readString(50);

                    System.out.print("Enter your password: ");
                    String password = Utility.readString(50);

                    if (userClientService.checkUser(qqID, password)) {
                        System.out.println("------Secondary menu (" + qqID + ")------");
                        while (loop) {
                            System.out.println("\t\t 1. View all online user");
                            System.out.println("\t\t 2. Group message");
                            System.out.println("\t\t 3. Private message");
                            System.out.println("\t\t 4. Send file");
                            System.out.println("\t\t 9. Exit");
                            System.out.print("Please select: ");

                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;

                                case "2":
                                    System.out.println("\t\t Group message");
                                    break;

                                case "3":
                                    System.out.println("\t\t Private message");
                                    break;

                                case "4":
                                    System.out.println("\t\t Send file");
                                    break;

                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;

                            }
                        }
                    } else {
                        System.out.println("Login failed");

                    }
                    break;

                case "9":
                    loop = false;
                    break;

            }
        }
    }

}



