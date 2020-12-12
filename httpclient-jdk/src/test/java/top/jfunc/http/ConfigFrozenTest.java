package top.jfunc.http;

import org.junit.Test;
import top.jfunc.http.SmartHttpClient;
import top.jfunc.http.base.Config;
import top.jfunc.http.smart.NativeSmartHttpClient;

/**
 * @author xiongshiyan at 2019/6/14 , contact me with email yanshixiong@126.com or phone 15208384257
 */
public class ConfigFrozenTest {
    @Test(expected = IllegalStateException.class)
    public void testFrozen(){
        Config config = Config.defaultConfig();
        config.setBaseUrl("http://....");
        config.setDefaultQueryCharset("UTF-8");

        config.freezeConfig();

        config.setBaseUrl("https://....");
    }
    @Test(expected = IllegalStateException.class)
    public void testFrozen2(){
        Config config = Config.defaultConfig();
        config.setBaseUrl("http://....");
        config.setDefaultQueryCharset("UTF-8");

        SmartHttpClient smartHttpClient = new NativeSmartHttpClient();
        smartHttpClient.setConfig(config);

        smartHttpClient.freezeConfig();

        //本身的Config不允许修改
        smartHttpClient.getConfig().setBaseUrl("https://....");
        //也不允许重新设置Config
        smartHttpClient.setConfig(Config.defaultConfig().setBaseUrl("https://...."));

    }
}
