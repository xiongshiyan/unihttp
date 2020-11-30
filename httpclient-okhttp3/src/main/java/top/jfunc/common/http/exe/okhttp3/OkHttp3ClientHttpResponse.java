package top.jfunc.common.http.exe.okhttp3;

import okhttp3.Response;
import top.jfunc.common.http.component.HeaderExtractor;
import top.jfunc.common.http.component.StreamExtractor;
import top.jfunc.common.http.exe.BaseClientHttpResponse;
import top.jfunc.common.http.exe.ClientHttpResponse;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.util.OkHttp3Util;
import top.jfunc.common.utils.IoUtil;

import java.io.IOException;

/**
 * {@link ClientHttpResponse} implementation that uses OkHttp2 facilities.
 *
 * @author xiongshiyan
 */
public class OkHttp3ClientHttpResponse extends BaseClientHttpResponse<Response> {


	public OkHttp3ClientHttpResponse(Response response, HttpRequest httpRequest, StreamExtractor<Response> streamExtractor, HeaderExtractor<Response> headerExtractor) {
		super(response, httpRequest, streamExtractor, headerExtractor);
	}


	@Override
	public int getStatusCode() throws IOException {
		return response.code();
	}

	@Override
	public String getStatusText() throws IOException {
		return response.message();
	}


	@Override
	public void close() {
		IoUtil.close(responseStream);
		OkHttp3Util.closeQuietly(response);
	}
}
