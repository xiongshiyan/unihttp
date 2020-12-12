package top.jfunc.common.http.smart;

/**
 * 本包定义了了Request-Response的{@link top.jfunc.common.http.SmartHttpClient}接口，
 * 便于扩展功能，也更好使用。此包的类就是默认的实现，可以自己重写替换之
 * {@link top.jfunc.common.http.holderrequest.Request}代表一个请求，{@link top.jfunc.common.http.response.Response}代表面向用户的响应
 * {@link top.jfunc.common.http.holderrequest.Request}可以使用以下这些语义更加明确的更加具体的实现来代替
 * {@link top.jfunc.common.http.request.HttpRequest}、
 * {@link top.jfunc.common.http.request.StringBodyRequest}、
 * {@link top.jfunc.common.http.request.FormRequest}、
 * {@link top.jfunc.common.http.request.UploadRequest}
 *
 * 实现模板{@link top.jfunc.common.http.smart.SmartHttpTemplate}
 * 及模板的拦截器{@link top.jfunc.common.http.smart.TemplateInterceptor}在模板执行前后进行拦截

 * 模板方法{@link top.jfunc.common.http.smart.SmartHttpTemplate#template(top.jfunc.common.http.request.HttpRequest, top.jfunc.common.http.base.ContentCallback, top.jfunc.common.http.response.ClientHttpResponseConverter)}
 * 参数封装一个http请求一般遵循的步骤
 * 1. {@link top.jfunc.common.http.request.HttpRequest}代表请求，是所有请求的公共父类
 * 2. {@link top.jfunc.common.http.base.ContentCallback}封装对body的处理
 * 3. {@link top.jfunc.common.http.response.ClientHttpResponseConverter}封装对结果的处理
 */