package edu.searchahouse.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.searchahouse.aop.EntityWrapperAmqp.CrudOperation;
import edu.searchahouse.configuration.RabbitMqProducerConfiguration;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.BaseEntity;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Property;

@Component
@Aspect
public class RabbitMqAspect {

    private final QueueSender queueSender;

    @Autowired
    public RabbitMqAspect(//
            final QueueSender queueSender //
    ) {
        this.queueSender = queueSender;
    }

    /**
     * 
     * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
     * this message to do what it wants.
     * 
     * @param entity
     *            the saved or updated entity.
     */
    @AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
    public void updateElasticsearch(final Property entity) {
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.CREATE), RabbitMqProducerConfiguration.amqpQueueProperty);
    }

    /**
     * 
     * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
     * this message to do what it wants.
     * 
     * @param entity
     *            the saved or updated entity.
     */
    @AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
    public void updateElasticsearch(final Agent entity) {
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.CREATE), RabbitMqProducerConfiguration.amqpQueueAgent);
    }

    /**
     * 
     * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
     * this message to do what it wants.
     * 
     * @param entity
     *            the saved or updated entity.
     */
    @AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
    public void updateElasticsearch(final Lead entity) {
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.CREATE), RabbitMqProducerConfiguration.amqpQueueLead);
    }

    @After(value = "execution(* org.springframework.data.repository.*.delete(..)) && args(property) )")
    public void delePropertyElasticsearch(final JoinPoint joinPoint, Property property) {

        BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.DELETE), RabbitMqProducerConfiguration.amqpQueueProperty);
    }

    @After(value = "execution(* org.springframework.data.repository.*.delete(..)) && args(agent) )")
    public void deleAgentElasticsearch(final JoinPoint joinPoint, Agent agent) {

        BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.DELETE), RabbitMqProducerConfiguration.amqpQueueAgent);
    }

    @After(value = "execution(* org.springframework.data.repository.*.delete(..)) && args(lead) )")
    public void deleLeadElasticsearch(final JoinPoint joinPoint, Lead lead) {

        BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];
        convertAndSend(new EntityWrapperAmqp<BaseEntity>(entity, CrudOperation.DELETE), RabbitMqProducerConfiguration.amqpQueueLead);
    }

    private void convertAndSend(final EntityWrapperAmqp<? extends BaseEntity> entity, final String routingKey) {
        this.queueSender.convertAndSend(entity, routingKey);
    }
}
