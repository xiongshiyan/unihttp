package top.jfunc.common.http;
/**

 参照以下配置方式配置SmartHttpClient和HttpService对接口的扫描

 @Bean
 public SmartHttpClient smartHttpClient(){
     Config config = Config.defaultConfig()
     .setBaseUrl("http://localhost:9999/http-server-test/")
     .addInterceptor(new CatMonitorInterceptor());
     return new SmartHttpClientImpl().setConfig(config);
 }
 @Bean
 public HttpServiceCreator httpServiceCreator(SmartHttpClient smartHttpClient){
    return new HttpServiceCreator().setSmartHttpClient(smartHttpClient);
 }
 @Bean
 public HttpServiceScanConfigure httpServiceScanConfigure(){
     HttpServiceScanConfigure httpServiceScanConfigure = new HttpServiceScanConfigure(httpServiceCreator(smartHttpClient()));
     httpServiceScanConfigure.setAnnotationClassScan(HttpService.class);
     httpServiceScanConfigure.setScanPackages("top.jfunc.network.controller.client");
     return httpServiceScanConfigure;
 }

 */