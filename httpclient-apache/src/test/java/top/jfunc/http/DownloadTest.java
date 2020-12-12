package top.jfunc.http;

import org.junit.Ignore;
import org.junit.Test;
import top.jfunc.http.SmartHttpClient;
import top.jfunc.http.download.Downloader;
import top.jfunc.http.download.InterruptBaseConfFileDownloader;
import top.jfunc.http.download.InterruptBaseDownloadFileDownloader;
import top.jfunc.http.download.MultiThreadDownloader;
import top.jfunc.http.request.DownloadRequest;
import top.jfunc.http.request.RequestCreator;
import top.jfunc.http.smart.ApacheSmartHttpClient;
import top.jfunc.common.utils.CommonUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author xiongshiyan at 2020/2/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Ignore
public class DownloadTest {
    DownloadRequest downLoadRequest = RequestCreator.download("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=974248807,3098128878&fm=26&gp=0.jpg"
            , new File("C:\\Users\\xiongshiyan\\Desktop\\" + CommonUtil.randomString(16) + ".jpg"));
    SmartHttpClient smartHttpClient = new ApacheSmartHttpClient();

    @Test
    public void testCommonDownload() throws IOException{
        long l = System.currentTimeMillis();
        smartHttpClient.download(downLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void multiThreadDownload() throws IOException{
        long l = System.currentTimeMillis();
        MultiThreadDownloader downloader = new MultiThreadDownloader(smartHttpClient , 102400 , 10);
        downloader.download(downLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void interruptBaseConfFileDownloader() throws IOException{
        long l = System.currentTimeMillis();
        Downloader downloader = new InterruptBaseConfFileDownloader(smartHttpClient , 1024);
        DownloadRequest downLoadRequest = RequestCreator.download("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=974248807,3098128878&fm=26&gp=0.jpg"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\1.jpg"));
        downloader.download(downLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void interruptBaseDownloadFileDownloader() throws IOException{
        long l = System.currentTimeMillis();
        Downloader downloader = new InterruptBaseDownloadFileDownloader(smartHttpClient , 1024);
        DownloadRequest downLoadRequest = RequestCreator.download("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=974248807,3098128878&fm=26&gp=0.jpg"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\2.jpg"));
        downloader.download(downLoadRequest);
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    public void getNetFileLength() throws IOException{
        Downloader downloader = new InterruptBaseDownloadFileDownloader(smartHttpClient , 1024);
        DownloadRequest defaultDownLoadRequest = RequestCreator.download("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=974248807,3098128878&fm=26&gp=0.jpg"
                , new File("C:\\Users\\xiongshiyan\\Desktop\\3.jpg"));
        long netFileLength = downloader.getNetFileLength(defaultDownLoadRequest);
        System.out.println(netFileLength);
    }
}
