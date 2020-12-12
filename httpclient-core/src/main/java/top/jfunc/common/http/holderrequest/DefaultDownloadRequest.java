package top.jfunc.common.http.holderrequest;

import top.jfunc.common.http.holder.DefaultFileHolder;
import top.jfunc.common.http.holder.FileHolder;

import java.io.File;
import java.net.URL;

/**
 * 下载为文件的请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultDownloadRequest extends BaseHttpRequest<DefaultDownloadRequest> implements DownloadRequest {
    public DefaultDownloadRequest(String url){
        super(url);
    }

    public DefaultDownloadRequest(URL url){
        super(url);
    }
    public DefaultDownloadRequest(){
    }

    public static DefaultDownloadRequest of(){
        return new DefaultDownloadRequest();
    }
    public static DefaultDownloadRequest of(URL url){
        return new DefaultDownloadRequest(url);
    }


    public static DefaultDownloadRequest of(String url){
        return new DefaultDownloadRequest(url);
    }
    public static DefaultDownloadRequest of(String url , String filePath){
        DefaultDownloadRequest downLoadFileRequest = new DefaultDownloadRequest(url);
        downLoadFileRequest.fileHolder().setFile(filePath);
        return downLoadFileRequest;
    }
    public static DefaultDownloadRequest of(String url , File file){
        DefaultDownloadRequest downLoadFileRequest = new DefaultDownloadRequest(url);
        downLoadFileRequest.fileHolder().setFile(file);
        return downLoadFileRequest;
    }

    private FileHolder fileHolder = new DefaultFileHolder();

    @Override
    public FileHolder fileHolder() {
        return this.fileHolder;
    }

    @Override
    public DefaultDownloadRequest setFile(File file) {
        fileHolder().setFile(file);
        return myself();
    }

    public DefaultDownloadRequest setFileHolder(FileHolder fileHolder) {
        this.fileHolder = fileHolder;
        return myself();
    }
}
