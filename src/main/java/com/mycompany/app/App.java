package com.mycompany.app;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class App {
    public static void main(String[] args)  {

        int port = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                try  (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
                ) {
                    String requestLine = in.readLine();
                    System.out.println("Получен запрос: " + requestLine);
                    if (requestLine != null && requestLine.startsWith("GET /ping")) {
                        String responseLine = "pong";
                        responseServer(out,200,responseLine);
                        break;
                    } else {
                        responseServer(out,404,"Ошибка запроса");
                    } clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Ошибка" + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Cервер не запущен" + e);
        }
    }

    private static void responseServer (BufferedWriter out, int statusCode, String body) throws IOException {
        out.write("HTTP/1.1 " + statusCode + " OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: " + body.getBytes().length + "\r\n" +
                "\r\n" + body);
        out.flush();
    }
}
