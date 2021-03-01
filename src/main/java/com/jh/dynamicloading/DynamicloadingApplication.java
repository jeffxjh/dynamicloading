package com.jh.dynamicloading;

import cn.hutool.extra.spring.SpringUtil;
import com.jh.dynamicloading.aspect.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

@Import({/*cn.hutool.extra.spring.SpringUtil.class,*/DynamicDataSourceRegister.class})
@SpringBootApplication
public class DynamicloadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicloadingApplication.class, args);
    }

}
