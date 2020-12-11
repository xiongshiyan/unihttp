package top.jfunc.common.http.request;

import java.io.File;

/**
 * 下载为文件的请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultDownLoadRequest extends BaseHttpRequest<DefaultDownLoadRequest> implements DownloadRequest {
    public DefaultDownLoadRequest(String url){
        super(url);
    }
    public DefaultDownLoadRequest(){
    }
    public static DefaultDownLoadRequest of(String url){
        return new DefaultDownLoadRequest(url);
    }
    public static DefaultDownLoadRequest of(){
        return new DefaultDownLoadRequest();
    }
    public static DefaultDownLoadRequest of(String url , File file){
        DefaultDownLoadRequest downLoadFileRequest = new DefaultDownLoadRequest(url);
        downLoadFileRequest.setFile(file);
        return downLoadFileRequest;
    }

    private File file;

    @Override
    public DefaultDownLoadRequest setFile(File file) {
        this.file = file;
        return myself();
    }

    @Override
    public File getFile() {
        return file;
    }
}
