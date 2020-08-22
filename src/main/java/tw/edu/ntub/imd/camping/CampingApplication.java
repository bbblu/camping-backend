package tw.edu.ntub.imd.camping;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import tw.edu.ntub.imd.camping.config.properties.FileProperties;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Gender;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;
import tw.edu.ntub.imd.camping.dto.file.directory.Directory;
import tw.edu.ntub.imd.camping.dto.file.directory.DirectoryImpl;
import tw.edu.ntub.imd.camping.dto.file.uploader.MultipartFileUploader;
import tw.edu.ntub.imd.camping.service.BaseViewService;
import tw.edu.ntub.imd.camping.util.json.ResponseUtils;

import java.io.IOException;
import java.nio.file.Paths;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class CampingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampingApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = ResponseUtils.createMapper();
        SimpleModule projectModule = new SimpleModule("projectModule",
                new Version(1, 0, 0, "", "tw.edu.ntub.imd", "camping")
        );
        projectModule.addSerializer(UserRoleEnum.class, new JsonSerializer<>() {
            @Override
            public void serialize(UserRoleEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(value.id);
            }
        });
        projectModule.addDeserializer(UserRoleEnum.class, new JsonDeserializer<>() {
            @Override
            public UserRoleEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return UserRoleEnum.getByRoleId(p.getValueAsInt());
            }
        });
        projectModule.addSerializer(Gender.class, new JsonSerializer<>() {
            @Override
            public void serialize(Gender value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.id);
            }
        });
        projectModule.addDeserializer(Gender.class, new JsonDeserializer<>() {
            @Override
            public Gender deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return Gender.getById(p.getValueAsString());
            }
        });
        projectModule.addSerializer(Experience.class, new JsonSerializer<>() {
            @Override
            public void serialize(Experience value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.id);
            }
        });
        projectModule.addDeserializer(Experience.class, new JsonDeserializer<>() {
            @Override
            public Experience deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return Experience.getById(p.getValueAsString());
            }
        });
        mapper.registerModule(projectModule);
        return mapper;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("transactionInterceptor") Advice advice) {
        AspectJExpressionPointcut expression = new AspectJExpressionPointcut();
        expression.setExpression("execution(* " + BaseViewService.class.getPackageName() + ".*.*(..))");
        return new DefaultPointcutAdvisor(expression, advice);
    }

    @Bean("fileDirectory")
    public Directory fileDirectory(FileProperties fileProperties) {
        return new DirectoryImpl(Paths.get(fileProperties.getPath()));
    }

    @Bean
    public MultipartFileUploader multipartFileUploader(Directory fileDirectory, FileProperties fileProperties) {
        return new MultipartFileUploader(fileDirectory, fileProperties.getUrl());
    }
}
