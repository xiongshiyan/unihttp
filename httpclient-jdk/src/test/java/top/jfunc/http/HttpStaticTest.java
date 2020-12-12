package top.jfunc.http;

import org.junit.Assert;
import org.junit.Test;
import top.jfunc.http.HttpDelegate;
import top.jfunc.http.HttpStatic;
import top.jfunc.http.SmartHttpClient;
import top.jfunc.http.smart.NativeSmartHttpClient;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class HttpStaticTest {
    @Test
    public void testLoad(){
        SmartHttpClient smartHttpClient = HttpStatic.getSmartHttpClient();
        Assert.assertTrue(smartHttpClient instanceof NativeSmartHttpClient);

        SmartHttpClient httpClient = HttpDelegate.delegate();
        Assert.assertTrue(httpClient instanceof NativeSmartHttpClient);
    }
}
