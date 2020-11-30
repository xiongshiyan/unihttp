package top.jfunc.common.http.exe.jdk;

import top.jfunc.common.http.component.HeaderExtractor;
import top.jfunc.common.http.component.StreamExtractor;
import top.jfunc.common.http.exe.BaseClientHttpResponse;
import top.jfunc.common.http.response.ClientHttpResponse;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.util.NativeUtil;
import top.jfunc.common.utils.IoUtil;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * {@link ClientHttpResponse} implementation that uses standard JDK facilities.
 * @author xiongshiyan
 */
public class JdkClientHttpResponse extends BaseClientHttpResponse<HttpURLConnection> {


	public JdkClientHttpResponse(HttpURLConnection connection, HttpRequest httpRequest, StreamExtractor<HttpURLConnection> streamExtractor, HeaderExtractor<HttpURLConnection> headerExtractor) {
		super(connection, httpRequest, streamExtractor, headerExtractor);
	}


	@Override
	public int getStatusCode() throws IOException {
		return response.getResponseCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return response.getResponseMessage();
	}

	@Override
	public void close() {
        NativeUtil.closeQuietly(response);
        IoUtil.close(responseStream);
	}
}
