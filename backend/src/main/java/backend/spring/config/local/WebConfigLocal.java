package backend.spring.config.local;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("local")
@Configuration
public class WebConfigLocal implements WebMvcConfigurer {

    @Value("${react.url}")
    private String reactUrl;

    @Value("${photo.file.dir}")
    private String photoFileDir;

    @Value("${avatar.file.dir}")
    private String avatarFileDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(reactUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type", "Authorization")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/users/avatars/**", "/sns/photos/**")
                .addResourceLocations(
                        photoFileDir, avatarFileDir);
    }
}