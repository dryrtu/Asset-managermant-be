package com.example.asset_management_idnes;


import org.springframework.boot.builder.SpringApplicationBuilder;

public class SpringBootServletInitializer  extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AssetManagementIdnesApplication.class);
    }


}
