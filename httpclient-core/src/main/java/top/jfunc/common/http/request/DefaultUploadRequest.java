package top.jfunc.common.http.request;

import top.jfunc.common.http.base.Config;
import top.jfunc.common.http.base.FormFile;
import top.jfunc.common.utils.ArrayListMultiValueMap;
import top.jfunc.common.utils.MapUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 多文件、参数同时支持的上传请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultUploadRequest extends BaseHttpRequest<DefaultUploadRequest> implements UploadRequest {
    public DefaultUploadRequest(String url){
        super(url);
    }
    public DefaultUploadRequest(){
    }
    public static DefaultUploadRequest of(String url){
        return new DefaultUploadRequest(url);
    }
    public static DefaultUploadRequest of(){
        return new DefaultUploadRequest();
    }

    private MultiValueMap<String , String> formParams = new ArrayListMultiValueMap<>(2);
    private String formParamCharset = Config.DEFAULT_CHARSET;
    private List<FormFile> formFiles = new ArrayList<>(2);

    @Override
    public Iterable<FormFile> getFormFiles() {
        return this.formFiles;
    }

    @Override
    public MultiValueMap<String, String> getFormParams() {
        return formParams;
    }

    @Override
    public DefaultUploadRequest addFormParam(String key, String value, String... values) {
        formParams.add(key, value, values);
        return myself();
    }

    @Override
    public DefaultUploadRequest setFormParams(MultiValueMap<String, String> formParams) {
        this.formParams = formParams;
        return myself();
    }

    @Override
    public DefaultUploadRequest setFormParams(Map<String, String> formParams) {
        if(MapUtil.notEmpty(formParams)){
            this.formParams = ArrayListMultiValueMap.fromMap(formParams);
        }
        return myself();
    }

    @Override
    public String getParamCharset() {
        return formParamCharset;
    }

    @Override
    public DefaultUploadRequest setParamCharset(String paramCharset) {
        this.formParamCharset = paramCharset;
        return myself();
    }

    @Override
    public DefaultUploadRequest addFormFile(FormFile... formFiles) {
        this.formFiles.addAll(Arrays.asList(formFiles));
        return myself();
    }

    @Override
    public UploadRequest addFormFiles(Iterable<FormFile> formFiles) {
        if(null != formFiles){
            formFiles.forEach(this.formFiles::add);
        }
        return myself();
    }
}
