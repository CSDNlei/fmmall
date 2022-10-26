package com.fengmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com/fengmi/fmmall/dao")
@EnableScheduling
public class ControllerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ControllerApplication.class, args);
    }

}
