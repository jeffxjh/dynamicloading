package com.jh.dynamicloading.listen;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jh.dynamicloading.service.ListenerService;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileListener extends FileAlterationListenerAdaptor {

    // 声明业务服务
    private ListenerService listenerService;

    // 采用构造函数注入服务
    public FileListener(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    // 文件创建执行
    @Override
    public void onFileCreate(File file) {
        System.out.println("创建执行");
    }

    // 文件创建修改
    @Override
    public void onFileChange(File file) {
        try {
            System.out.println("修改执行");
            // 触发业务
            String s = FileUtil.readString(file, StandardCharsets.UTF_8);
            System.out.println(s);
            JSONArray objects = JSONUtil.readJSONArray(file, StandardCharsets.UTF_8);
            for (Object object : objects) {
                JSONObject jsonObject = (JSONObject) object;
                if (jsonObject.containsKey("beanName")) {
                    String beanName = "";
                    Map<String, Object> map = new HashMap<>();
                    Iterator<Map.Entry<String, Object>> iterator =
                            jsonObject.entrySet().iterator();
                    while (true) {
                        if (iterator.hasNext()) {
                            Map.Entry<String, Object> next = iterator.next();
                            String key = next.getKey();
                            if (!"beanName".equals(key)) {
                                Object value = next.getValue();
                                if (value instanceof String) {
                                    map.put(key, StrUtil.toString(value));
                                }
                            } else {
                                beanName = StrUtil.toString(next.getValue());
                            }

                        } else {
                            break;
                        }
                    }
                    listenerService.registerBean(beanName, map);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    // 文件创建删除
    @Override
    public void onFileDelete(File file) {
        System.out.println("删除执行");
    }

    // 目录创建
    @Override
    public void onDirectoryCreate(File directory) {

    }

    // 目录修改
    @Override
    public void onDirectoryChange(File directory) {
    }

    // 目录删除
    @Override
    public void onDirectoryDelete(File directory) {
    }


    // 轮询开始
    @Override
    public void onStart(FileAlterationObserver observer) {
        System.out.println("轮询开始" + DateUtil.currentSeconds());
    }

    // 轮询结束
    @Override
    public void onStop(FileAlterationObserver observer) {
        System.out.println("轮询结束" + DateUtil.currentSeconds());
    }
}