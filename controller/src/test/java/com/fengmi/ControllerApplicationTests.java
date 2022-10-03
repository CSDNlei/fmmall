package com.fengmi;

import com.fengmi.fmmall.dao.CategoryMapper;
import com.fengmi.fmmall.dao.ProductCommentsMapper;
import com.fengmi.fmmall.dao.ProductMapper;
import com.fengmi.fmmall.entity.CategoryVo;
import com.fengmi.fmmall.entity.ProductCommentsVo;
import com.fengmi.fmmall.entity.ProductVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void test01(){
        List<ProductVo> productVos = productMapper.selectProductVo();
        for (ProductVo p : productVos){
            System.err.println(p);
        }

    }
    @Test
    public void test02(){
        List<CategoryVo> categoryVos = categoryMapper.selectFirstCategories();
         for (CategoryVo c:categoryVos){
             System.err.println(c);
         }
    }
    @Test
    public void test03(){
//        List<ProductCommentsVo> productCommentsVos = productCommentsMapper.selectCommentsById("3");
//        for (ProductCommentsVo niubi:productCommentsVos){
//            System.err.println("niubi = " + niubi);
//    }
    }

}
