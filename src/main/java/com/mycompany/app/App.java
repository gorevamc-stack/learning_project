package com.mycompany.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {

        int port = 8081;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/ping", (HttpExchange exchange) -> {

            String method = exchange.getRequestMethod();

            if ("GET".equals(method)) {
                sendResponse(exchange, 200, "pong");
            } else {
                sendResponse(exchange, 405, "Неверный метод");
            }
        });

        server.createContext("/", (HttpExchange exchange) ->
                sendResponse(exchange, 404, "Неизвестный запрос"));

        server.start();
        System.out.println("Сервер запущен на порту " + port);
    }

    private static void sendResponse(HttpExchange exchange, int status,
                                     String body) throws IOException {
        exchange.sendResponseHeaders(status, body.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(body.getBytes());
        os.close();

    }
}


