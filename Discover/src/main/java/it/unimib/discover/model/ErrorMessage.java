package it.unimib.discover.model;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = -8633340453989341526L;
	private String fieldName;
    private String errorMessage;
    private String formSection;

    public ErrorMessage(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public ErrorMessage(String fieldName, String errorMessage, String formSection) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
        this.formSection = formSection;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFormSection() {
        return formSection;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ErrorMessage [fieldName=");
        builder.append(fieldName);
        builder.append(", errorMessage=");
        builder.append(errorMessage);
        builder.append("]");
        return builder.toString();
    }

}