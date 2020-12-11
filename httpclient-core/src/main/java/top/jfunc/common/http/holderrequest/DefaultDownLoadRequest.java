package top.jfunc.common.http.holderrequest;

import top.jfunc.common.http.holder.DefaultFileHolder;
import top.jfunc.common.http.holder.FileHolder;

import java.io.File;
import java.net.URL;

/**
 * 下载为文件的请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultDownLoadRequest extends BaseHttpRequest<DefaultDownLoadRequest> implements DownloadRequest {
    public DefaultDownLoadRequest(String url){
        super(url);
    }

    public DefaultDownLoadRequest(URL url){
        super(url);
    }
    public DefaultDownLoadRequest(){
    }

    public static DefaultDownLoadRequest of(){
        return new DefaultDownLoadRequest();
    }
    public static DefaultDownLoadRequest of(URL url){
        return new DefaultDownLoadRequest(url);
    }


    public static DefaultDownLoadRequest of(String url){
        return new DefaultDownLoadRequest(url);
    }
    public static DefaultDownLoadRequest of(String url , String filePath){
        DefaultDownLoadRequest downLoadFileRequest = new DefaultDownLoadRequest(url);
        downLoadFileRequest.fileHolder().setFile(filePath);
        return downLoadFileRequest;
    }
    public static DefaultDownLoadRequest of(String url , File file){
        DefaultDownLoadRequest downLoadFileRequest = new DefaultDownLoadRequest(url);
        downLoadFileRequest.fileHolder().setFile(file);
        return downLoadFileRequest;
    }

    private FileHolder fileHolder = new DefaultFileHolder();

    @Override
    public FileHolder fileHolder() {
        return this.fileHolder;
    }

    @Override
    public DefaultDownLoadRequest setFile(File file) {
        fileHolder().setFile(file);
        return myself();
    }

    public DefaultDownLoadRequest setFileHolder(FileHolder fileHolder) {
        this.fileHolder = fileHolder;
        return myself();
    }
}
