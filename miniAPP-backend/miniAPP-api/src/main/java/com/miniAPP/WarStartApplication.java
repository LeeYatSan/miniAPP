package com.miniAPP;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 * @Description: 继承SpringBootServletInitializer，等价于使用web.xml的形式去部署
 */
public class WarStartApplication extends SpringBootServletInitializer {

    /**
     * 重写配置：configure
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //使用web.xml运行应用程序，指向Application，最后启动Spring boot
        return builder.sources(Application.class);
    }
}
