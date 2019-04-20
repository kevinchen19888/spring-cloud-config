package com.kevin.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@RefreshScope   // 动态刷新配置,使用时在需要的类上进行添加
public class ConfigClientApplication {

    /**
     * 使用post请求方式发送以下请求,config-client会重新读取配置文件
     * http://localhost:8881/actuator/bus-refresh  // TODO 注意8881为多实例部署时的端口号
     *
     * 当再次请求接口:http://localhost:8882/hi 时配置已经更新
     *
     * /actuator/bus-refresh接口可以指定服务，即使用"destination"参数，
     * 比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @Value(("${familyName}"))
    String familyName;

    @RequestMapping(value = "/hi")
    public String hi(){

        return "the foo is:"+foo+" and the familyName is:"+familyName;
    }
}
