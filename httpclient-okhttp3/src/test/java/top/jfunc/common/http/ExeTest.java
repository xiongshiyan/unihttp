package top.jfunc.common.http;

import org.junit.Test;
import top.jfunc.common.http.exe.okhttp3.OkHttp3ExeSmartHttpClient;
import top.jfunc.common.http.smart.Request;
import top.jfunc.common.http.smart.SmartHttpClient;

import java.io.IOException;

public class ExeTest {
    @Test
    public void testExe() throws IOException {
        SmartHttpClient smartHttpClient = new OkHttp3ExeSmartHttpClient();
        String s = smartHttpClient.get("https://www.baidu.com");
        System.out.println(s);
        System.out.println(smartHttpClient.head(Request.of("https://www.baidu.com")));
    }
}
