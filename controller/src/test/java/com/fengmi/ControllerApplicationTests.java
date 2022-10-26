package com.fengmi;

import com.fengmi.fmmall.dao.*;
import com.fengmi.fmmall.entity.CategoryVo;
import com.fengmi.fmmall.entity.Orders;
import com.fengmi.fmmall.entity.ProductVo;
import com.fengmi.fmmall.entity.ShoppingCartVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ControllerApplication.class)
class ControllerApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductCommentsMapper productCommentsMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Test
    void contextLoads() {
        List<CategoryVo> categoryVos = categoryMapper.selectAllCategories2(0);
        for (CategoryVo c1 : categoryVos) {
            System.err.println(c1);
            for (CategoryVo c2 : c1.getCategories()) {
                System.err.println("///" + c2);
                for (CategoryVo c3 : c2.getCategories()) {
                    System.err.println("//////" + c3);
                }
            }

        }

    }

    @Test
    public void test01() {
        List<ProductVo> productVos = productMapper.selectProductVo();
        for (ProductVo p : productVos) {
            System.err.println(p);
        }

    }

    @Test
    public void test02() {
        List<CategoryVo> categoryVos = categoryMapper.selectFirstCategories();
        for (CategoryVo c : categoryVos) {
            System.err.println(c);

        }
    }

    @Test
    public void test03() {
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectshopcartByUserId(49);
        System.out.println(shoppingCartVos);
    }

    @Test
    public void test04() {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(85);
        objects.add(88);
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectShopcartByCids(objects);
        System.out.println("shoppingCartVos = " + shoppingCartVos);
    }

    @Test
    public void test05() {
        String cids="120,121";
        String[] split = cids.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectShopcartByCids(list);
       for (ShoppingCartVo sc:shoppingCartVos){
           System.out.println("sc = " + sc);
       }
    }
    @Test
    public void test06(){
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Date date = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime",date);
        List<Orders> orders = ordersMapper.selectByExample(example);

        for (int i = 0; i <orders.size() ; i++) {
            System.out.println(orders.get(i).getOrderId()+"\t"+orders.get(i).getStatus()+"\t"+orders.get(i).getCreateTime());
        }
    }
}