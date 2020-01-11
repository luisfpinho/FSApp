package com.gmail.luisfpinho99.bysidefs.domain.model.entry;

import com.gmail.luisfpinho99.bysidefs.Preconditions;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FileContent implements Serializable {

    private final String content;

    public FileContent(String content) {
        Preconditions.ensureNotNull("The content of the file cannot be null.", content);
        this.content = content;
    }

    public FileContent() {
        content = "";
    }

    public int size() {
        return content.length();
    }

    public String getValue() {
        return content;
    }
}
