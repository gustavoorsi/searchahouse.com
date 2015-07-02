package edu.searchahouse.aop;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.searchahouse.configuration.RabbitMqProducerConfiguration;
import edu.searchahouse.model.BaseEntity;

@Service
public class QueueSender {

    private final RabbitMessagingTemplate rabbitMessagingTemplate;
    private final MappingJackson2MessageConverter mappingJackson2MessageConverter;

    @Autowired
    public QueueSender(RabbitMessagingTemplate rabbitMessagingTemplate, MappingJackson2MessageConverter mappingJackson2MessageConverter) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.mappingJackson2MessageConverter = mappingJackson2MessageConverter;
    }

    @Async
    public void convertAndSend(final EntityWrapperAmqp<? extends BaseEntity> entity, final String routingKey) {
        this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);
        this.rabbitMessagingTemplate.convertAndSend(RabbitMqProducerConfiguration.amqpTopicExchange, routingKey, entity);
    }

}
