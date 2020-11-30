package top.jfunc.common.http.exe.jodd;

import jodd.http.HttpResponse;
import top.jfunc.common.http.component.HeaderExtractor;
import top.jfunc.common.http.component.StreamExtractor;
import top.jfunc.common.http.exe.BaseClientHttpResponse;
import top.jfunc.common.http.exe.ClientHttpResponse;
import top.jfunc.common.http.request.HttpRequest;
import top.jfunc.common.http.util.JoddUtil;
import top.jfunc.common.utils.IoUtil;

import java.io.IOException;

/**
 * {@link ClientHttpResponse} implementation that uses Jodd facilities.
 *
 * @author xiongshiyan
 */
public class JoddClientHttpResponse extends BaseClientHttpResponse<HttpResponse> {

	public JoddClientHttpResponse(HttpResponse httpResponse, HttpRequest httpRequest, StreamExtractor<HttpResponse> streamExtractor, HeaderExtractor<HttpResponse> headerExtractor) {
		super(httpResponse, httpRequest, streamExtractor, headerExtractor);
	}


	@Override
	public int getStatusCode() throws IOException {
		return response.statusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return response.statusPhrase();
	}

	@Override
	public void close() {
		IoUtil.close(responseStream);
		JoddUtil.closeQuietly(response);
	}
}
