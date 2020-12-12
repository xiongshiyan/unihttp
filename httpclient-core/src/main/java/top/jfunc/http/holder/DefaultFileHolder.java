package top.jfunc.http.holder;

import java.io.File;

/**
 * 默认的 FileHolder 实现
 * @author xiongshiyan
 */
public class DefaultFileHolder implements FileHolder {
    /**
     * 为文件下载确定信息
     */
    private File file = null;

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public FileHolder setFile(File file) {
        this.file = file;
        return this;
    }
}
