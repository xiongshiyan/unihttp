package top.jfunc.http.holder;

import top.jfunc.common.utils.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongshiyan at 2019/6/3 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class DefaultRouteParamHolder implements RouteParamHolder {
    private static final String SEPERATOR = ":";

    /**
     * 路径参数，形如这种URL http://httpbin.org/book/{id}，保存id和id的值
     * @since 1.0.4
     */
    private Map<String , String> routeParams;

    private String seperator = SEPERATOR;

    public DefaultRouteParamHolder(String seperator) {
        this.seperator = seperator;
    }

    public DefaultRouteParamHolder() {
    }

    @Override
    public Map<String, String> getMap() {
        return routeParams;
    }

    @Override
    public void setMap(Map<String, String> routeParams) {
        this.routeParams = routeParams;
    }

    @Override
    public String get(String key) {
        return null == routeParams ? null : routeParams.get(key);
    }

    @Override
    public RouteParamHolder put(String key, String value) {
        if(null == this.routeParams){
            this.routeParams = new HashMap<>(2);
        }
        this.routeParams.put(key, value);
        return this;
    }

    @Override
    public RouteParamHolder addRouteParams(String... kvs) {
        if(null == this.routeParams){
            this.routeParams = new HashMap<>(2);
        }
        for (String kv : kvs) {
            String[] split = kv.split(seperator);
            this.routeParams.put(split[0] , split[1]);
        }
        return null;
    }

    @Override
    public String remove(String key) {
        if(MapUtil.isEmpty(routeParams)){
            return null;
        }
        return routeParams.remove(key);
    }

    public String getSeperator() {
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }
}
