package com.jh.dynamicloading.api;

import cn.hutool.extra.spring.SpringUtil;
import com.jh.dynamicloading.TestService;
import com.jh.dynamicloading.service.ListenerService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class RegeistController {
    @Resource
    ListenerService listenerService;


    /**
     * 注册Bean
     *
     * @return
     */
    @GetMapping("/bean/register/{beanName}")
    public String registerBean(@PathVariable String beanName) throws ClassNotFoundException {
        return listenerService.registerBean("com.jh.dynamicloading.TestService",new HashMap<>());
    }
    @GetMapping("/bean/do")
    public String dos()  {
        return  SpringUtil.getBean(TestService.class).sout();
    }


}
