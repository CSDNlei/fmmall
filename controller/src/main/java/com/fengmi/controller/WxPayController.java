package com.fengmi.controller;
import com.fengmi.fmmall.service.OrderService;
import com.fengmi.websocket.WebSocketServer;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 回调接口  当用户支付成功之后 微信支付平台就会请求这个接口 将支付状态的数据传输过来
 */
@RestController
@RequestMapping("/pay")
@Slf4j
public class WxPayController {
    @Autowired
    private OrderService orderService;
    @RequestMapping("/callback")
    public String paysuccess(HttpServletRequest request) throws Exception {
        System.out.println("true = " + true);
//        1.接收微信平台传递的数据（使用request输入流接收）
        ServletInputStream stream = request.getInputStream();
        byte[] bs = new byte[1024];
        int length = -1;
        StringBuilder builder = new StringBuilder();
        while ((length=stream.read(bs))!=-1){
              builder.append(new String(bs,0,length));
        }
        String s = builder.toString();
        log.info("结果="+s);
        Map<String, String> map = WXPayUtil.xmlToMap(s);
         if (map!=null && "success".equalsIgnoreCase(map.get("result_code"))){
//             支付成功
//            2. 修改订单状态  待发货/已支付
             String s1 = map.get("out_trade_no");
             int i = orderService.UpdateOrderStauts(s1, "2");
             System.out.println("i = " + i);
//             3.通过websocket建立连接  向前端发送请求
             WebSocketServer.sendMsg(s1,"1");
//           4.  响应微信支付平台
             if (i>0){
                 HashMap<String, String> map1 = new HashMap<>();
                 map1.put("result_code","SUCCESS");
                 map1.put("return_msg","OK");
                 map1.put("appid",map1.get("appid"));
                 map1.put("return_code","SUCCESS");
                 return WXPayUtil.mapToXml(map1);
             }else {
                 return null;
             }
         }
         return null;
    }
}
