package top.jfunc.http.holder;

import top.jfunc.http.base.FormFile;
import top.jfunc.common.utils.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiongshiyan at 2019/6/2 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultFormFileHolder implements FormFileHolder {
    /**
     * 2018-06-18为了文件上传增加的
     */
    private List<FormFile> formFiles = new ArrayList<>(2);

    @Override
    public Iterable<FormFile> getFormFiles() {
        return this.formFiles;
    }

    @Override
    public FormFileHolder addFormFile(FormFile... formFiles) {
        if(ArrayUtil.isNotEmpty(formFiles)){
            this.formFiles.addAll(Arrays.asList(formFiles));
        }
        return this;
    }

    @Override
    public FormFileHolder addFormFiles(Iterable<FormFile> formFiles) {
        if(null != formFiles){
            formFiles.forEach(this.formFiles::add);
        }
        return this;
    }
}
