package com.gmail.luisfpinho99.fsapp.domain.model.entry;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class LastModificationTime implements Serializable {

    private final Date lastModificationTimeValue;

    public LastModificationTime() {
        lastModificationTimeValue = new Date();
    }

    public Date asDate() {
        return new Date(lastModificationTimeValue.getTime());
    }
}
