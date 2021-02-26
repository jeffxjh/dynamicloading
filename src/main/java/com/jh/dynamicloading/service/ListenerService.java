package com.jh.dynamicloading.service;

import java.util.Map;

public interface ListenerService {
    String registerBean(String beanName, Map<String,Object> proper) throws ClassNotFoundException;
}
