package com.gpi.scm.generic.dtos.validators;

import java.text.MessageFormat;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.gpi.scm.generic.dtos.FrequencyDto;
import com.gpi.scm.generic.dtos.NonComplianceDto;
import com.gpi.scm.generic.dtos.ProcessCheckOutcomeDto;
import com.gpi.scm.generic.dtos.SystemCheckOutcomeDto;

public class ValidationUtil {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	public ValidatorFactory getFactory() {
		return factory;
	}

	public void setFactory(ValidatorFactory factory) {
		this.factory = factory;
	}

	public String isValid(Object object) {
		Validator validator = factory.getValidator();
		String errorMsg = new String();

		Set<ConstraintViolation<Object>> violations = validator.validate(object);
		for (ConstraintViolation<Object> cv : violations) {

			errorMsg += MessageFormat.format(
					"The value of {0}.{1} was: {2} but must not be {3}" + System.lineSeparator(), cv.getRootBeanClass(),
					cv.getPropertyPath().toString(), cv.getInvalidValue(),
					cv.getConstraintDescriptor().getAttributes().get("value"));

		}

		return errorMsg;

	}

	public String isValid(FrequencyDto frequency) {
		Validator validator = factory.getValidator();
		String errorMsg = new String();

		Set<ConstraintViolation<FrequencyDto>> violations = validator.validate(frequency);
		for (ConstraintViolation<FrequencyDto> cv : violations) {

			if (cv.getPropertyPath().toString().equals("riskClass"))
				errorMsg += cv.getMessage();
			else {
				errorMsg += MessageFormat.format(
						" The value of {0}.{1} was: {2} but must not be {3}" + System.lineSeparator(),
						cv.getRootBeanClass(), cv.getPropertyPath().toString(), cv.getInvalidValue(),
						cv.getConstraintDescriptor().getAttributes().get("value"));
			}

		}

		return errorMsg;
	}
	public String isValid(NonComplianceDto compliance) {
		Validator validator = factory.getValidator();
		String errorMsg = new String();

		Set<ConstraintViolation<NonComplianceDto>> violations = validator.validate(compliance);
		for (ConstraintViolation<NonComplianceDto> cv : violations) {

			if (cv.getPropertyPath().toString().equals("association"))
				errorMsg += cv.getMessage();
			else {
				errorMsg += MessageFormat.format(
						" The value of {0}.{1} was: {2} but must not be {3}" + System.lineSeparator(),
						cv.getRootBeanClass(), cv.getPropertyPath().toString(), cv.getInvalidValue(),
						cv.getConstraintDescriptor().getAttributes().get("value"));
			}

		}

		return errorMsg;
	}
	public String isValid(ProcessCheckOutcomeDto outcome) {
		Validator validator = factory.getValidator();
		String errorMsg = new String();

		Set<ConstraintViolation<ProcessCheckOutcomeDto>> violations = validator.validate(outcome);
		for (ConstraintViolation<ProcessCheckOutcomeDto> cv : violations) {

			if (cv.getPropertyPath().toString().equals("association"))
				errorMsg += cv.getMessage();
			else {
				errorMsg += MessageFormat.format(
						" The value of {0}.{1} was: {2} but must not be {3}" + System.lineSeparator(),
						cv.getRootBeanClass(), cv.getPropertyPath().toString(), cv.getInvalidValue(),
						cv.getConstraintDescriptor().getAttributes().get("value"));
			}

		}

		return errorMsg;
	}
	public String isValid(SystemCheckOutcomeDto outcome) {
		Validator validator = factory.getValidator();
		String errorMsg = new String();

		Set<ConstraintViolation<SystemCheckOutcomeDto>> violations = validator.validate(outcome);
		for (ConstraintViolation<SystemCheckOutcomeDto> cv : violations) {

			if (cv.getPropertyPath().toString().equals("association"))
				errorMsg += cv.getMessage();
			else {
				errorMsg += MessageFormat.format(
						" The value of {0}.{1} was: {2} but must not be {3}" + System.lineSeparator(),
						cv.getRootBeanClass(), cv.getPropertyPath().toString(), cv.getInvalidValue(),
						cv.getConstraintDescriptor().getAttributes().get("value"));
			}

		}

		return errorMsg;
	}
}
