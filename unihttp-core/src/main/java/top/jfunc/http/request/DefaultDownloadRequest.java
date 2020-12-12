package top.jfunc.http.request;

import java.io.File;

/**
 * 下载为文件的请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultDownloadRequest extends BaseHttpRequest<DefaultDownloadRequest> implements DownloadRequest {
    public DefaultDownloadRequest(String url){
        super(url);
    }
    public DefaultDownloadRequest(){
    }
    public static DownloadRequest of(String url){
        return new DefaultDownloadRequest(url);
    }
    public static DownloadRequest of(){
        return new DefaultDownloadRequest();
    }
    public static DownloadRequest of(String url , File file){
        DefaultDownloadRequest downLoadFileRequest = new DefaultDownloadRequest(url);
        downLoadFileRequest.setFile(file);
        return downLoadFileRequest;
    }

    private File file;

    @Override
    public DownloadRequest setFile(File file) {
        this.file = file;
        return myself();
    }

    @Override
    public File getFile() {
        return file;
    }
}
