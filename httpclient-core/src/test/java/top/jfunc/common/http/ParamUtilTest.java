package top.jfunc.common.http;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class ParamUtilTest {
    @Test
    public void testReplaceRouteParamsIfNecessary(){
        String url = "http://httpbin.org/book/{id}/{ss}";
        Map<String , String> map = new HashMap<>(1);
        map.put("id" , "2");
        map.put("dd" , "dd");
        map.put("ss" , "ss");
        String s = ParamUtil.replaceRouteParamsIfNecessary(url, map);
        System.out.println(s);
        Assert.assertEquals("http://httpbin.org/book/2/ss" ,s);

        url = "https://httpbin.org/book/2";
        String necessary = ParamUtil.replaceRouteParamsIfNecessary(url, null);
        Assert.assertEquals(url , necessary);
    }
}
