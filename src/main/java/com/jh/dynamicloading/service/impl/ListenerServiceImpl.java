package com.jh.dynamicloading.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.jh.dynamicloading.service.ListenerService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ListenerServiceImpl implements ListenerService {

    @Override
    public String registerBean(String beanName, Map<String,Object> proper) throws ClassNotFoundException {
        //获取context
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringUtil.getApplicationContext();
        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(Class.forName(beanName));
        for (Map.Entry<String, Object> entry : proper.entrySet()) {
            beanDefinitionBuilder.addPropertyValue(entry.getKey(), entry.getValue());
        }

        //判断Bean是否已经注册
        Object beanObject=null;
        try {
            beanObject = SpringUtil.getBean(beanName);
            System.out.println(String.format("Bean %s 已注册", beanName));
        } catch (NoSuchBeanDefinitionException e) {
            //动态注册bean.
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            //获取动态注册的bean.
            beanObject = SpringUtil.getBean(beanName);
            if (beanObject != null) {
                System.out.println(String.format("Bean %s 注册成功", beanName));
                return beanObject.toString();
            } else {
                return "register Bean Error";
            }
        }
        return "register Bean Success";
    }

}
