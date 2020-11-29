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
        String s = smartHttpClient.get("https://dzgtest.palmte.cn/dzg/api/v2/h5/common/getIp");
        System.out.println(s);
        System.out.println(smartHttpClient.head(Request.of("https://dzgtest.palmte.cn/dzg/api/v2/h5/common/getIp")));
    }
}
