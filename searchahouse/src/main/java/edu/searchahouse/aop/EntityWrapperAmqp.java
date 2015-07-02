package edu.searchahouse.aop;

import edu.searchahouse.model.BaseEntity;

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
