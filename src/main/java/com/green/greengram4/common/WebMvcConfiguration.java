package com.green.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {  //새로고침 용 ?
    private final String imgFolder;

    public WebMvcConfiguration(@Value("${file.dir}") String imgFolder) {    //지금    은 절대경로 사용중
        this.imgFolder = imgFolder;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String imgFolderAbsolutePath = Paths.get(imgFolder).toFile().getAbsolutePath(); //마지막에 / 빠짐
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + imgFolder);   //마지막에 / 있어야함
        //addResourceHandler(경로) > 다음 경로로 요청 오면 controller 찾아보고 매치되는게 없으면  addResourceLocations(경로) 에 설정된 경로에서 찾게 설정. 그래도 없으면 static 확인
        //file: -> 외부 경로 사용하겠다 라는 설정. : 없으면 내부 경로로 인식됨.
        //기본은 요청 오면 controller(RestAPI) -> FE(static) 순서로 확인
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        // Anything else returns the index.
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}
