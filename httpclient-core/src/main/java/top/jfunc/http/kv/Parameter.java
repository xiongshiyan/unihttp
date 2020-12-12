package top.jfunc.http.kv;

/**
 * Http的Query或者Form参数模型(multiValue),key-value1,value2
 * @author xiongshiyan at 2019/5/17 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class Parameter extends MultiValueEntry {
    public Parameter(String key, String value, String... values) {
        super(key, value , values);
    }
    public Parameter(String key, Iterable<String> values) {
        super(key, values);
    }

    public static Parameter of(String name, String value, String... values){
        return new Parameter(name, value , values);
    }

    public static Parameter of(String name, Iterable<String> values){
        return new Parameter(name, values);
    }
}
