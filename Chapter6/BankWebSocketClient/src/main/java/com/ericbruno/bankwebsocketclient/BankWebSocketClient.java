/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ericbruno.bankwebsocketclient;

import java.io.IOException;
import java.net.URI;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import java.util.concurrent.TimeUnit;
import org.glassfish.tyrus.client.ClientManager;

/**
 *
 * @author ebruno
 */
@ClientEndpoint
public class BankWebSocketClient {
    static ClientManager client;
    static Session session;
    
    public static void main(String[] args) {
        try {
            client = ClientManager.createClient();
            URI uri = new URI("ws://localhost:8080/graphql");
            session = client.connectToServer(BankWebSocketClient.class, uri);
            client.getExecutorService().awaitTermination(1, TimeUnit.DAYS);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Websocket Connected");
        System.out.println("Session: " + session.getId());
        try {
            session.getBasicRemote().sendText(
                    "{\"operationName\":\"NewBalanceSub\"," +
                    "\"type\": \"start\",\"id\": \"123\", " +
                    "\"payload\": {\"query\":" +
                    "\"subscription NewBalanceSub { " +
                    "newBalance(accountId: 2) { balance } }\"}}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        try {
            System.out.println("Received: " + message);
            session.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
        //client.shutdown();
        return "";
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Session: " + session.getId() + " closing");
        System.out.println("Reason: " + reason);
    }

}
