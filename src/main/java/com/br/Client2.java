package com.br;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args){
        try{
            Socket socket = new Socket("localhost", 4444);
            System.out.println("-------- Client 2 conectado ao CHAT BRAZUKA --------");

            Scanner scanner = new Scanner(System.in);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter printer = new PrintWriter(socket.getOutputStream(), true);

            while(socket.isConnected()){
                new Thread(() -> {
                    String messageReceived;
                    try{
                        if((messageReceived = reader.readLine()) != null){
                            System.out.println(messageReceived);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }).start();
                System.out.println("Digite aqui: ");
                String message = scanner.nextLine();
                printer.println(message);
            }
            socket.close();
            System.out.println("-------- Client 2 desconectado ao CHAT BRAZUKA --------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
