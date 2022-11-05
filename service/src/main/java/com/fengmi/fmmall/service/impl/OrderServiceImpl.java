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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * 保存订单业务
     *
     * @param cids   cartId   **120,121**
     * @param orders
     * @return
     */
    @Override
    @Transactional  //此注解将一下所有事务看做一个整体  单个执行失败则全部失败
    public Map<String, String> Orderaddr(String cids, Orders orders) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
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
            for (int cid : list) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }
            map.put("orderId", orderId);
            map.put("productNames", untitled);
            return map;
        } else {
//               表示库存不足
            return null;
        }
    }

    @Override
    public int UpdateOrderStauts(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        return ordersMapper.updateByPrimaryKeySelective(orders);
    }

    @Override
    public ResultVo getOrderById(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        return new ResultVo(ResStauts.OK, "success", orders.getStatus());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)  //事务锁
    public void closeOrder(String orderId) {
        synchronized (this) {  //jvm锁
            /*b.修改当前订单：  status=6  已关闭 close_type=1 超时未支付*/
            Orders orders1 = new Orders();
            orders1.setOrderId(orderId);
            orders1.setStatus("6");
            orders1.setCloseType(1);
            ordersMapper.updateByPrimaryKeySelective(orders1);
            /**还原库存
             * 先根据当前订单编号查询商品快照  ===>修改product_sku
             * **/
            Example example1 = new Example(OrderItem.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("orderId", orderId);
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
            /**还原库存**/
            for (int j = 0; j < orderItems.size(); j++) {
                OrderItem orderItem = orderItems.get(j);
//                        修改
                ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
                productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
                productSkuMapper.updateByPrimaryKey(productSku);
            }
        }
    }
}