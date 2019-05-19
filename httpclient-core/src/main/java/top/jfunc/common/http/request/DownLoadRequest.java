package top.jfunc.common.http.request;

import java.io.File;

/**
 * 文件下载请求
 * @author xiongshiyan
 */
public interface DownLoadRequest extends HttpRequest{
    /**
     * 下载到的文件
     * @return file
     */
    File getFile();
}