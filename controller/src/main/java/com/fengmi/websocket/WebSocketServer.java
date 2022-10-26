package com.fengmi.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {
     private static ConcurrentHashMap<String, Session> sessionmap =  new ConcurrentHashMap<>();
     /**前端发送socket请求建立连接 就会执行onopen方法**/
     @OnOpen
    public void open(@PathParam("oid") String orderId, Session session){
          sessionmap.put(orderId,session);
     }
     /**前端关闭页面或者主动关闭socket链接 都会执行close方法**/
     @OnClose
    public void close(@PathParam("oid") String orderId){
         sessionmap.remove(orderId);
     }
     public static void  sendMsg(String orderId,String msg){
         try {
             Session session = sessionmap.get(orderId);
             session.getBasicRemote().sendText(msg);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
