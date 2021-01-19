package top.jfunc.http.base;

import top.jfunc.common.utils.MultiValueMap;

import java.io.IOException;

/**
 * Represents the base interface for HTTP request and response messages.
 * Consists of {@link MultiValueMap}, retrievable via {@link #getHeaders()}.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public interface HttpMessage {

	/**
	 * Return the headers of this message.
	 * @return a corresponding HttpHeaders object (maybe {@code null})
	 * @throws IOException in case of I/O Errors
	 */
	MultiValueMap<String, String> getHeaders() throws IOException;

}