package com.jh.dynamicloading.listen;

import com.jh.dynamicloading.service.ListenerService;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.TimeUnit;
@Component
public class FileListenerFactory {
    // 设置监听路径
    private final String monitorDir = "E:\\aa";

    // 设置轮询间隔
    private final long interval = TimeUnit.SECONDS.toMillis(1);

    // 自动注入业务服务
    @Resource
    private ListenerService listenerService;

    public FileAlterationMonitor getMonitor() {
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                FileFilterUtils.suffixFileFilter(".json"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        // 装配过滤器
        // FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir));
        FileAlterationObserver observer = new FileAlterationObserver(new File(monitorDir), filter);

        // 向监听者添加监听器，并注入业务服务
        observer.addListener(new FileListener(listenerService));

        // 返回监听者
        return new FileAlterationMonitor(interval, observer);
    }

}
