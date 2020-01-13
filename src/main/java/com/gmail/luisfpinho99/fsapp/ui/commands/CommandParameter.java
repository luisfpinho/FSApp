package com.gmail.luisfpinho99.fsapp.ui.commands;

import java.util.Objects;

public class CommandParameter {

    public final String parameter;
    public final String value;

    public CommandParameter(String parameter, String value) {
        this.parameter = parameter;
        this.value = value;
    }

    public CommandParameter(String parameter) {
        this.parameter = parameter;
        value = null;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        CommandParameter otherParameter = (CommandParameter) otherObject;
        return Objects.equals(parameter, otherParameter.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter);
    }
}
