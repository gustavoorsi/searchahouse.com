package edu.searchahouse.leadrouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class LeadrouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeadrouterApplication.class, args);
    }

//    @Bean
//    public MappingJackson2HttpMessageConverter jackson2Converter() {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
////        converter.setObjectMapper(halObjectMapper());
//        return converter;
//    }

//    @Bean
//    public ObjectMapper halObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.registerModule(new Jackson2HalModule()); // support hal+json
//
//        return objectMapper;
//    }

}
