package com.br;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        try{
            ServerSocket server = new ServerSocket(4444);
            System.out.println("---------- CHAT BRAZUKA ----------");
            System.out.println("Esperando clientes se connectarem ...");

            Socket c1 = server.accept();
            Socket c2 = server.accept();

            System.out.println("Client 1 - Host: "+c1.getInetAddress().getHostAddress());
            System.out.println("Client 2 - Host: "+c2.getInetAddress().getHostAddress());

            BufferedReader bufr1 = new BufferedReader(new InputStreamReader(c1.getInputStream()));
            BufferedReader bufr2 = new BufferedReader(new InputStreamReader(c2.getInputStream()));

            PrintWriter printWriter1 = new PrintWriter(c1.getOutputStream(), true);
            PrintWriter printWriter2 = new PrintWriter(c2.getOutputStream(), true);

            while(c1.isConnected() && c2.isConnected()){
                new Thread(() -> {
                    try {
                        String message;
                        while((message = bufr1.readLine()) != null){
                            printWriter2.println("Client 1 - "+message);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                new Thread(() -> {
                    try {
                        String message;
                        while((message = bufr2.readLine()) != null){
                            printWriter1.println("Client 2 - "+message);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            c1.close();
            c2.close();
            server.close();
            System.out.println("---------- CHAT DESLIGADO ----------");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
