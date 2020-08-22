package tw.edu.ntub.imd.camping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import tw.edu.ntub.imd.camping.config.properties.FileProperties;
import tw.edu.ntub.imd.camping.util.json.ResponseUtils;

@Import({FileProperties.class})
@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return ResponseUtils.createMapper();
    }
}
