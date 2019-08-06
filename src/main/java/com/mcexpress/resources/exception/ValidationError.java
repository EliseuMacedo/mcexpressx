package com.mcexpress.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status,msg,timeStamp);
	}

	public List<FieldMessage> getErrors() { //No java é essa palavra get que vai ser convertida no JSON
		return errors;
	}

	public void addError(String fieldName, String msg) {
		errors.add(new FieldMessage(fieldName,msg));
	}
}