package top.jfunc.common.http;

import top.jfunc.common.http.response.Response;
import top.jfunc.common.utils.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * 1.HTTP请求异常，包括http组件内部错误-1，服务器返回错误（错误码和错误信息）
 * 2.超时异常由专门的IOException表达
 * @see Response
 * @author 熊诗言 2017/11/24
 */
public class HttpException extends RuntimeException{
    private int responseCode = -1;
    private MultiValueMap<String , String> headers;

    public HttpException(int responseCode,String errorMessage , MultiValueMap<String , String> headers){
        super(errorMessage);
        this.responseCode = responseCode;
        this.headers = headers;
    }
    public HttpException(int responseCode,String errorMessage){
        super(errorMessage);
        this.responseCode = responseCode;
    }
    public HttpException(String errorMessage){
        super(errorMessage);
    }
    public HttpException(Exception e){
        super(e);
    }
    public HttpException(){
    }


    public int getResponseCode() {
        return responseCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
