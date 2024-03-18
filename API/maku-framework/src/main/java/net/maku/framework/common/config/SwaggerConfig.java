package net.maku.framework.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @author 阿沐  babamu@126.com
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"net.maku"};
        return GroupedOpenApi.builder().group("管理后台模块")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }
    @Bean
    public GroupedOpenApi travelApi() {
        String[] paths = {"/travel/**"};
        String[] packagedToMatch = {"net.maku"};
        return GroupedOpenApi.builder().group("前端模块")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("travelTips");
        return new OpenAPI().info(new Info()
                .title("后台接口")
                .description("后台接口文档")
                .contact(contact)
                .version("3.0")
                .termsOfService("https://traveltips.com.cn")
                .license(new License().name("MIT")
                        .url("https://traveltips.com.cn")));
    }

}
