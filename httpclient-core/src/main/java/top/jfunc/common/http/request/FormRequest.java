package top.jfunc.common.http.request;

import top.jfunc.common.http.base.MediaType;
import top.jfunc.common.http.util.ParamUtil;
import top.jfunc.common.utils.MultiValueMap;

import java.util.Map;

/**
 * Form表单请求application/x-www-form-urlencoded
 * @see MediaType#APPLICATION_FORM_DATA_STRING
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface FormRequest extends StringBodyRequest {
    /**
     * Form参数
     * @return Form参数
     */
    MultiValueMap<String, String> getFormParams();

    /**
     * 设置Form参数
     * @param params Form参数
     * @return this
     */
    FormRequest setFormParams(Map<String, String> params);
    /**
     * 设置Form参数
     * @param params Form参数
     * @return this
     */
    FormRequest setFormParams(MultiValueMap<String, String> params);

    /**
     * 新增form参数的便捷方法
     * @param key key
     * @param value value
     * @param values values
     * @return this
     */
    FormRequest addFormParam(String key, String value, String... values);

    /**
     * form参数生成body
     * @see ParamUtil#contactMap(MultiValueMap)
     * @return body
     */
    @Override
    default String getBody() {
        MultiValueMap<String, String> formParams = getFormParams();
        String bodyCharset = calculateBodyCharset();
        //没有显式设置就设置默认的
        if(null == getContentType()){
            setContentType(MediaType.APPLICATION_FORM_DATA.withCharset(bodyCharset));
        }
        return ParamUtil.contactMap(formParams, bodyCharset);
    }
}
