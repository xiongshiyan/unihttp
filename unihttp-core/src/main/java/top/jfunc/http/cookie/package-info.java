package top.jfunc.http.cookie;

/**
 * 框架对{@link top.jfunc.http.cookie.Cookie}的支持
 * {@link top.jfunc.http.cookie.CookieAccessor}用于封装对cookie的通用处理，
 * 其内部可以通过{@link top.jfunc.http.cookie.CookieStore}实现cookie的真正持久化。
 * 通过{@link top.jfunc.http.cookie.CookieInterceptor}可以方便地集成
 */