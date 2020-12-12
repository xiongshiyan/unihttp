package top.jfunc.http.component.jodd;

import jodd.http.HttpResponse;
import top.jfunc.http.component.HeaderExtractor;
import top.jfunc.http.component.StreamExtractor;
import top.jfunc.http.response.BaseClientHttpResponse;
import top.jfunc.http.response.ClientHttpResponse;
import top.jfunc.http.request.HttpRequest;
import top.jfunc.http.util.JoddUtil;
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
