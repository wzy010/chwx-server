package cn.net.easyinfo.config;

import cn.net.easyinfo.common.DateConverter;
import cn.net.easyinfo.common.handler.GlobalExceptionHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Value("${virtualPath}")
	private String virtualPath;

	@Value("${filePath}")
	private String filePath;

	@Override
	public void addViewControllers( ViewControllerRegistry registry ) {
		registry.addViewController( "/" ).setViewName( "forward:/index.html" );
		registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
		super.addViewControllers( registry );
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {

		registry.addConverter(new DateConverter());
	}

	@Bean
	GlobalExceptionHandler getGlobalExceptionHandler() {
		return new GlobalExceptionHandler();
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//其中OTA表示访问的前缀。"file:D:/OTA/"是文件真实的存储路径
		registry.addResourceHandler("/" + virtualPath + "/**").addResourceLocations("file:" + filePath+virtualPath+ "/");

	}
}
