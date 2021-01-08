package com.cxxy.edu.service;

import com.cxxy.edu.utils.FFMpegUtil;
import com.cxxy.edu.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ThreadExecute{
    @Autowired
    private SpringUtil springUtil;
    private static final ThreadPoolExecutor JOB_EXECUTOR = new ThreadPoolExecutor(10,
            20,100, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100000),new ThreadPoolExecutor.CallerRunsPolicy());
    private static final Semaphore JOB_SEMAPHORE = new Semaphore(1);
    public void Execute(String inPath, String outPath)throws IOException {
        final String in =inPath;
        final String out =outPath;
//        final String in ="E:\\auxiliarytool_1-master\\auxiliarytool_1\\target\\classes\\static\\resource\\upload\\1\\1\\temp\\moive4.mp4";
//        final String out ="C:\\Users\\17343\\Desktop\\新建文件夹\\a\\moive4.mp4";

        JOB_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    //获得许可
                    JOB_SEMAPHORE.acquire();
                    FFMpegUtil ffMpegUtil= (FFMpegUtil) springUtil.getBean(FFMpegUtil.class);
                    FFMpegUtil.convetor(in,out);

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    //访问完，释放
                    JOB_SEMAPHORE.release();
                    System.out.println("修改完成！");
                }
            }
        });
    }
}
