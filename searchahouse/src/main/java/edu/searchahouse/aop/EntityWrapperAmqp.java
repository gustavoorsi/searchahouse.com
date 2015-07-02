package edu.searchahouse.aop;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import edu.searchahouse.model.BaseEntity;

@JsonAutoDetect( fieldVisibility = Visibility.ANY )
public class EntityWrapperAmqp<E extends BaseEntity> {

    private E entity;
    private CrudOperation crudOperation;

    public EntityWrapperAmqp(E entity, CrudOperation crudOperation) {
        this.entity = entity;
        this.crudOperation = crudOperation;
    }

    public enum CrudOperation {
        CREATE, UPDATE, DELETE;
    }

}
