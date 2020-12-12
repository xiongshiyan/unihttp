package top.jfunc.http.holderrequest;

import top.jfunc.http.holder.FileHolder;

import java.io.File;

/**
 * 文件下载请求
 * @author xiongshiyan
 */
public interface DownloadRequest extends HttpRequest, top.jfunc.http.request.DownloadRequest {
    /**
     * 下载到的文件
     * @return file
     */
    @Override
    default File getFile(){
        return fileHolder().getFile();
    }

    /**
     * 设置下载到哪个文件
     * @param file file
     * @return this
     */
    @Override
    default DownloadRequest setFile(File file){
        fileHolder().setFile(file);
        return this;
    }

    /**
     * 返回文件信息处理器
     * @return fileHolder must not be null
     */
    FileHolder fileHolder();
}
