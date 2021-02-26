package com.jh.dynamicloading.listen;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileListenerRunner implements CommandLineRunner {

    @Resource
    private FileListenerFactory fileListenerFactory;

    @Override
    public void run(String... args)  {
        // 创建监听者
        FileAlterationMonitor fileAlterationMonitor = fileListenerFactory.getMonitor();
        try {
            // do not stop this thread
            fileAlterationMonitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}