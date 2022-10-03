package com.fengmi;

//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com/fengmi/fmmall/dao")
public class ControllerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ControllerApplication.class, args);
    }

}
