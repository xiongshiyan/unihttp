package top.jfunc.common.http;

import org.junit.Assert;
import org.junit.Test;
import top.jfunc.common.http.smart.ApacheSmartHttpClient;
import top.jfunc.common.http.smart.SmartHttpClient;

/**
 * @author xiongshiyan at 2019/5/16 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class HttpStaticTest {
    @Test
    public void testLoad(){
        SmartHttpClient smartHttpClient = HttpStatic.getSmartHttpClient();
        Assert.assertTrue(smartHttpClient instanceof ApacheSmartHttpClient);

        SmartHttpClient httpClient = HttpDelegate.delegate();
        Assert.assertTrue(httpClient instanceof ApacheSmartHttpClient);
    }
}
