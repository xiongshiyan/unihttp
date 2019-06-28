package top.jfunc.common.http.holder;

import top.jfunc.common.http.ParamUtil;

/**
 * 完整路径holder
 * @author xiongshiyan at 2019/6/28 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultCompleteUrlHolder implements CompleteUrlHolder{
    private String finalUrl = null;
    /**
     * 路径参数，形如这种URL http://httpbin.org/book/{id}，保存id和id的值
     * @since 1.0.4 //private Map<String , String> routeParams;
     */
    private RouteParamHolder routeParamHolder = new DefaultRouteParamHolder();
    /**
     * 查询参数，拼装在URL后面 ?//private MultiValueMap<String,String> queryParamHolder;
     * @since 1.0.4
     */
    private ParamHolder queryParamHolder = new DefaultParamHolder();

    /**
     * 获取之后，最好就不要再更改参数了，或者在之前调用{@link UrlHolder#recalculate()}
     * @return 计算后的url
     */
    @Override
    public String getUrl() {
        //处理路径参数
        finalUrl = ParamUtil.replaceRouteParamsIfNecessary(finalUrl , routeParamHolder.getMap());
        //处理Query参数
        finalUrl = ParamUtil.contactUrlParams(finalUrl , queryParamHolder.getParams() , queryParamHolder.getParamCharset());
        return finalUrl;
    }

    @Override
    public CompleteUrlHolder setUrl(String destination) {
        this.finalUrl = destination;
        return this;
    }

    @Override
    public RouteParamHolder routeParamHolder() {
        return routeParamHolder;
    }

    @Override
    public ParamHolder queryParamHolder() {
        return queryParamHolder;
    }
}
