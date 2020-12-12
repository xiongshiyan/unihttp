package top.jfunc.http.component.jdk;

import top.jfunc.http.component.HeaderExtractor;
import top.jfunc.http.component.StreamExtractor;
import top.jfunc.http.response.BaseClientHttpResponse;
import top.jfunc.http.response.ClientHttpResponse;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.NativeUtil;
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
