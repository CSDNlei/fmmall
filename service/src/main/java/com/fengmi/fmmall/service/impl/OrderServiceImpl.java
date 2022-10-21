package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.OrderItemMapper;
import com.fengmi.fmmall.dao.OrdersMapper;
import com.fengmi.fmmall.dao.ProductSkuMapper;
import com.fengmi.fmmall.dao.ShoppingCartMapper;
import com.fengmi.fmmall.entity.OrderItem;
import com.fengmi.fmmall.entity.Orders;
import com.fengmi.fmmall.entity.ProductSku;
import com.fengmi.fmmall.entity.ShoppingCartVo;
import com.fengmi.fmmall.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;

    /**保存订单业务
     * @param cids   cartId   **120,121**
     * @param orders
     * @return
     */
    @Override
    @Transactional  //此注解将一下所有事务看做一个整体  单个执行失败则全部失败
    public ResultVo Orderaddr(String cids, Orders orders) throws SQLException {
//      1.   校验库存   根据cids查询当前订单中关联的购物车记录详情 包括库存
        String[] split = cids.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectShopcartByCids(list);
        boolean f = true;
//          untitled    产品名称（多个产品用,隔开）
        String untitled = "";
        for (ShoppingCartVo sc : shoppingCartVos) {
            if (Integer.parseInt(sc.getCartNum()) > (sc.getSkuStock())) {
                f = false;
//                获取商品名称，以，分割为字符串
            }
            untitled = untitled + sc.getProductName() + ",";
        }
        if (f) {
//          2.      表示库存充足 --保存订单
            orders.setUntitled(untitled);
            orders.setCreateTime(new Date());
//            订单初始状态 待支付 1
            orders.setStatus("1");
//            生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            orders.setOrderId(orderId);
//            保存订单
            ordersMapper.insert(orders);
//        3.         生成商品快照
            for (ShoppingCartVo sc : shoppingCartVos) {
                int cartNum = Integer.parseInt(sc.getCartNum());
                String itemId = System.currentTimeMillis() + "" + (new Random().nextInt(8999) + 1000);
                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(), sc.getProductName(), sc.getProductImg(), sc.getSkuId(), sc.getSkuName(), new BigDecimal(sc.getSellPrice()), cartNum, new BigDecimal(sc.getSellPrice() * cartNum), new Date(), new Date(), 0);
                orderItemMapper.insert(orderItem);
            }
//             4.   扣减库存
            for (ShoppingCartVo sc : shoppingCartVos) {
                String skuId = sc.getSkuId();
                int newStock = sc.getSkuStock() - Integer.parseInt(sc.getCartNum());
                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }
            // 5.删除购物车 当购物车购买成功之后 购物车中做对应删除操作
            for(int cid:list){
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }
            return new ResultVo(ResStauts.OK, "下单成功！", orderId);
        } else {
//               表示库存不足
            return new ResultVo(ResStauts.NO, "库存不足，下单失败!", null);
        }
    }
}
