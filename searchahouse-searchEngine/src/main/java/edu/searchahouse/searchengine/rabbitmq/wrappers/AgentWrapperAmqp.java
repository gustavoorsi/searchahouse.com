package edu.searchahouse.searchengine.rabbitmq.wrappers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import edu.searchahouse.searchengine.model.Agent;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class AgentWrapperAmqp<E extends Agent> {

    private E entity;
    private QueueOperation crudOperation;

    public AgentWrapperAmqp() {
    }

    public AgentWrapperAmqp(E entity, QueueOperation crudOperation) {
        this.entity = entity;
        this.crudOperation = crudOperation;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

    public void setCrudOperation(QueueOperation crudOperation) {
        this.crudOperation = crudOperation;
    }

    public E getEntity() {
        return entity;
    }

    public QueueOperation getCrudOperation() {
        return crudOperation;
    }
}
