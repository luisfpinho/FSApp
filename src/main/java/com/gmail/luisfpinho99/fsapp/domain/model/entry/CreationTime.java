package com.gmail.luisfpinho99.fsapp.domain.model.entry;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CreationTime implements Serializable {

    private final Date creationTimeValue;

    public CreationTime() {
        creationTimeValue = new Date();
    }

    public Date asDate() {
        return new Date(creationTimeValue.getTime());
    }
}
