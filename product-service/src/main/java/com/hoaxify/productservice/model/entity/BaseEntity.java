package com.hoaxify.productservice.model.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> extends Serializable {

    K getId();
}
