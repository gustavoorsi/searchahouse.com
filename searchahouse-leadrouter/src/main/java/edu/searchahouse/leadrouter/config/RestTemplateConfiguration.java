package edu.searchahouse.leadrouter.config;

import java.util.Arrays;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter jackson2Converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2ConverterSupportHal() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(halObjectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper halObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule()); // support hal+json

        return objectMapper;
    }

    static class BLOCKING_REST_TEMPLATES {

        @Bean
        public RestTemplate restTemplateSupportHal(MappingJackson2HttpMessageConverter jackson2ConverterSupportHal) {
            RestTemplate restTemplate = new RestTemplate(Arrays.asList(jackson2ConverterSupportHal));

            return restTemplate;
        }

        @Bean
        public RestTemplate restTemplate(MappingJackson2HttpMessageConverter jackson2Converter) {
            RestTemplate restTemplate = new RestTemplate(Arrays.asList(jackson2Converter));

            return restTemplate;
        }
    }

    static class NON_BLOCKING_REST_TEMPLATES {

        private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
        private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
        private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);

        @Bean
        public CloseableHttpAsyncClient asyncHttpClient() throws Exception {
            PoolingNHttpClientConnectionManager connectionManager = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(
                    IOReactorConfig.DEFAULT));
            connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
            connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
            connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("localhost")), 20);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();

            CloseableHttpAsyncClient httpclient = HttpAsyncClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config)
                    .build();
            return httpclient;
        }
        
        
        @Bean
        public AsyncRestTemplate asyncRestTemplateSupportHal(MappingJackson2HttpMessageConverter jackson2ConverterSupportHal) throws Exception {
            // HttpComponentsAsyncClientHttpRequestFactory internally uses NIO
            AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory(asyncHttpClient()));
            asyncRestTemplate.setMessageConverters( Arrays.asList(jackson2ConverterSupportHal) );
            return asyncRestTemplate;
        }
        
        @Bean
        public AsyncRestTemplate asyncRestTemplate(MappingJackson2HttpMessageConverter jackson2Converter) throws Exception {
            // HttpComponentsAsyncClientHttpRequestFactory internally uses NIO
            AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory(asyncHttpClient()));
            asyncRestTemplate.setMessageConverters( Arrays.asList(jackson2Converter) );
            return asyncRestTemplate;
        }
    }

}
