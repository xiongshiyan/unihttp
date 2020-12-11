package top.jfunc.common.http.holderrequest;

import top.jfunc.common.http.base.FormFile;
import top.jfunc.common.http.holder.DefaultFormFileHolder;
import top.jfunc.common.http.holder.DefaultParamHolder;
import top.jfunc.common.http.holder.FormFileHolder;
import top.jfunc.common.http.holder.ParamHolder;
import top.jfunc.common.utils.MultiValueMap;

import java.net.URL;
import java.util.Map;

/**
 * 多文件、参数同时支持的上传请求
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultUpLoadRequest extends BaseHttpRequest<DefaultUpLoadRequest> implements UploadRequest {
    public DefaultUpLoadRequest(String url){
        super(url);
    }
    public DefaultUpLoadRequest(URL url){
        super(url);
    }
    public DefaultUpLoadRequest(){
    }
    public static DefaultUpLoadRequest of(){
        return new DefaultUpLoadRequest();
    }
    public static DefaultUpLoadRequest of(URL url){
        return new DefaultUpLoadRequest(url);
    }
    public static DefaultUpLoadRequest of(String url){
        return new DefaultUpLoadRequest(url);
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
    public DefaultUpLoadRequest setParamCharset(String paramCharset) {
        formParamHolder().setParamCharset(paramCharset);
        return myself();
    }

    @Override
    public DefaultUpLoadRequest addFormParam(String key, String value, String... values) {
        formParamHolder().add(key, value, values);
        return myself();
    }

    @Override
    public top.jfunc.common.http.request.UploadRequest setFormParams(Map<String, String> formParams) {
        formParamHolder().set(formParams);
        return myself();
    }

    @Override
    public top.jfunc.common.http.request.UploadRequest setFormParams(MultiValueMap<String, String> formParams) {
        formParamHolder().set(formParams);
        return myself();
    }

    @Override
    public DefaultUpLoadRequest addFormFile(FormFile... formFiles) {
        formFileHolder().addFormFile(formFiles);
        return myself();
    }

    @Override
    public DefaultUpLoadRequest addFormFiles(Iterable<FormFile> formFiles) {
        formFileHolder().addFormFiles(formFiles);
        return myself();
    }

    public DefaultUpLoadRequest setFormParamHolder(ParamHolder formParamHolder) {
        this.formParamHolder = formParamHolder;
        return myself();
    }

    public DefaultUpLoadRequest setFormFileHolder(FormFileHolder formFileHolder) {
        this.formFileHolder = formFileHolder;
        return myself();
    }
}
