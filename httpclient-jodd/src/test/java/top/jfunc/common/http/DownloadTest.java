package top.jfunc.common.http;

import org.junit.Ignore;
import org.junit.Test;
import top.jfunc.common.http.download.Downloader;
import top.jfunc.common.http.download.InterruptBaseConfFileDownloader;
import top.jfunc.common.http.download.InterruptBaseDownloadFileDownloader;
import top.jfunc.common.http.download.MultiThreadDownloader;
import top.jfunc.common.http.request.RequestCreator;
import top.jfunc.common.http.request.DefaultDownLoadRequest;
import top.jfunc.common.http.smart.JoddSmartHttpClient;
import top.jfunc.common.http.smart.SmartHttpClient;
import top.jfunc.common.utils.CommonUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author xiongshiyan at 2020/2/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Ignore
public class DownloadTest {
    DefaultDownLoadRequest defaultDownLoadRequest = RequestCreator.download("http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.51/bin/apache-tomcat-8.5.51.tar.gz"
            , new File("C:\\Users\\xiongshiyan\\Desktop\\tomcat-" + CommonUtil.randomString(16) + ".tar.gz"));
    SmartHttpClient smartHttpClient = new JoddSmartHttpClient();

    @Test
    public void testCommonDownload() throws IOException{
        long l = System.currentTimeMillis();
        smartHttpClient.download(defaultDownLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void multiThreadDownload() throws IOException{
        long l = System.currentTimeMillis();
        MultiThreadDownloader downloader = new MultiThreadDownloader(smartHttpClient , 102400 , 10);
        downloader.download(defaultDownLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void interruptBaseConfFileDownloader() throws IOException{
        long l = System.currentTimeMillis();
        Downloader downloader = new InterruptBaseConfFileDownloader(smartHttpClient , 1024);
        DefaultDownLoadRequest defaultDownLoadRequest = RequestCreator.download("http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.51/bin/apache-tomcat-8.5.51.tar.gz"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\tomcat-jodd.tar.gz"));
        downloader.download(defaultDownLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void interruptBaseDownloadFileDownloader() throws IOException{
        long l = System.currentTimeMillis();
        Downloader downloader = new InterruptBaseDownloadFileDownloader(smartHttpClient , 1024);
        DefaultDownLoadRequest defaultDownLoadRequest = RequestCreator.download("http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.51/bin/apache-tomcat-8.5.51.tar.gz"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\tomcat-jodd.tar.gz"));
        downloader.download(defaultDownLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void getNetFileLength() throws IOException{
        Downloader downloader = new InterruptBaseDownloadFileDownloader(smartHttpClient , 1024);
        DefaultDownLoadRequest defaultDownLoadRequest = RequestCreator.download("http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.51/bin/apache-tomcat-8.5.51.tar.gz"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\tomcat.tar.gz"));
        long netFileLength = downloader.getNetFileLength(defaultDownLoadRequest);
        System.out.println(netFileLength);
    }
}
