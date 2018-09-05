package it.unimib.discover.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationResponse implements Serializable {

	private static final long serialVersionUID = -8609618102815610861L;
	private String status;
    private List<ErrorMessage> errorMessages;
    private List<FieldError> fieldErrors;

    public ValidationResponse() {}

    public ValidationResponse(String status) {
		this.status = status;
	}

	public ValidationResponse(String status, BindingResult errors) {
		this.status = status;
		this.errorMessages = new ArrayList<ErrorMessage>();
        for (FieldError fieldError : errors.getFieldErrors()) {
            String field = fieldError.getField();
            if (field.contains("[")) {
                field = field.replace("[", "\\[");
                field = field.replace("]", "\\]");
            }
            if(field.contains(".")) {
            	field = field.replace(".", "\\.");
            }
            this.errorMessages.add(new ErrorMessage(field, fieldError.getDefaultMessage()));
        }
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ValidationResponse [status=");
        builder.append(status);
        builder.append(", errorMessages=");
        builder.append(errorMessages);
        builder.append(", fieldErrors=");
        builder.append(fieldErrors);
        builder.append("]");
        return builder.toString();
    }
}
