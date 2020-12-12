package top.jfunc.http.request;

import top.jfunc.http.base.FormFile;
import top.jfunc.common.utils.MultiValueMap;

import java.util.Map;

/**
 * 文件上传multipart/form-data
 * @see top.jfunc.http.base.MediaType#MULTIPART_FORM_DATA_STRING
 * @author xiongshiyan at 2019/5/18 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public interface UploadRequest extends HttpRequest {
    /**
     * Form参数
     * @return Form参数
     */
    MultiValueMap<String, String> getFormParams();

    /**
     * 新增form参数
     * @param key key
     * @param value value
     * @param values values
     * @return this
     */
    UploadRequest addFormParam(String key, String value, String... values);

    /**
     * 设置form参数
     * @param formParams formParams
     * @return this
     */
    UploadRequest setFormParams(MultiValueMap<String, String> formParams);

    /**
     * 设置form参数
     * @param formParams formParams
     * @return this
     */
    UploadRequest setFormParams(Map<String, String> formParams);

    /**
     * 获取charset
     * @return charset
     */
    String getParamCharset();

    /**
     * 提供便捷设置编码的方法
     * @param paramCharset 参数编码
     * @return this
     */
    UploadRequest setParamCharset(String paramCharset);

    /**
     * 上传文件信息
     * @return 上传文件信息
     */
    Iterable<FormFile> getFormFiles();

    /**
     * 新增文件上传信息
     * @param formFiles 上传的文件
     * @return this
     */
    UploadRequest addFormFile(FormFile... formFiles);

    /**
     * 新增文件上传信息
     * @param formFiles 上传的文件
     * @return this
     */
    UploadRequest addFormFiles(Iterable<FormFile> formFiles);
}
