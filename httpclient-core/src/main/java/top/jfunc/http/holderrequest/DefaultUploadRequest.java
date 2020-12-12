package top.jfunc.http.holderrequest;

import top.jfunc.http.base.FormFile;
import top.jfunc.http.holder.DefaultFormFileHolder;
import top.jfunc.http.holder.DefaultParamHolder;
import top.jfunc.http.holder.FormFileHolder;
import top.jfunc.http.holder.ParamHolder;
import top.jfunc.common.utils.MultiValueMap;

import java.net.URL;
import java.util.Map;

/**
 * 多文件、参数同时支持的上传请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultUploadRequest extends BaseHttpRequest<DefaultUploadRequest> implements UploadRequest {
    public DefaultUploadRequest(String url){
        super(url);
    }
    public DefaultUploadRequest(URL url){
        super(url);
    }
    public DefaultUploadRequest(){
    }
    public static DefaultUploadRequest of(){
        return new DefaultUploadRequest();
    }
    public static DefaultUploadRequest of(URL url){
        return new DefaultUploadRequest(url);
    }
    public static DefaultUploadRequest of(String url){
        return new DefaultUploadRequest(url);
    }
    /**
     * form参数// private MultiValueMap<String,String> formParamHolder;
     */
    private ParamHolder formParamHolder = new DefaultParamHolder();
    /**
     * 2018-06-18为了文件上传增加的// private List<FormFile> formFiles = null;
     */
    private FormFileHolder formFileHolder = new DefaultFormFileHolder();

    @Override
    public ParamHolder formParamHolder() {
        return formParamHolder;
    }

    @Override
    public FormFileHolder formFileHolder() {
        return formFileHolder;
    }

    @Override
    public DefaultUploadRequest setParamCharset(String paramCharset) {
        formParamHolder().setParamCharset(paramCharset);
        return myself();
    }

    @Override
    public DefaultUploadRequest addFormParam(String key, String value, String... values) {
        formParamHolder().add(key, value, values);
        return myself();
    }

    @Override
    public top.jfunc.http.request.UploadRequest setFormParams(Map<String, String> formParams) {
        formParamHolder().set(formParams);
        return myself();
    }

    @Override
    public top.jfunc.http.request.UploadRequest setFormParams(MultiValueMap<String, String> formParams) {
        formParamHolder().set(formParams);
        return myself();
    }

    @Override
    public DefaultUploadRequest addFormFile(FormFile... formFiles) {
        formFileHolder().addFormFile(formFiles);
        return myself();
    }

    @Override
    public DefaultUploadRequest addFormFiles(Iterable<FormFile> formFiles) {
        formFileHolder().addFormFiles(formFiles);
        return myself();
    }

    public DefaultUploadRequest setFormParamHolder(ParamHolder formParamHolder) {
        this.formParamHolder = formParamHolder;
        return myself();
    }

    public DefaultUploadRequest setFormFileHolder(FormFileHolder formFileHolder) {
        this.formFileHolder = formFileHolder;
        return myself();
    }
}
