package com.gpi.scm.generic.dtos.validators;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.log4j.Logger;

public class AtLeastValidator implements ConstraintValidator<AtLeast, Object> {

	private static final Logger logger = Logger.getLogger(AtLeastValidator.class);

	private String[] required;
	private String message;

	static boolean propertyExists(Object bean, String property) {
		return PropertyUtils.isReadable(bean, property) && PropertyUtils.isWriteable(bean, property);
	}

	@Override
	public void initialize(AtLeast requiredIfChecked) {

		required = requiredIfChecked.required();
		message = requiredIfChecked.message();
	}

	@Override
	public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
		boolean valid = true;
		try {
			int atLeastOne = 0;
			Object requiredValue;
			String actualValue = BeanUtils.getProperty(objectToValidate, "id");
			if (actualValue.equals("0")) {
				for (String propName : required) {
					if (!propertyExists(objectToValidate, propName)) {
						requiredValue = null;
					} else {
						requiredValue = BeanUtils.getProperty(objectToValidate, propName);
						if (requiredValue == null) {
							atLeastOne++;
							message += propName + " ";
						}
					}
				}
			}
			valid = atLeastOne < required.length ? true : false;
			if (!valid) {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(message).addPropertyNode("association")
							.addConstraintViolation();
				
			}
		} catch (IllegalAccessException e) {
			logger.error("Accessor method is not available for class : {" + objectToValidate.getClass().getName()
					+ "}, exception : {" + e + "}");
			e.printStackTrace();
			return false;
		} catch (NoSuchMethodException e) {
			logger.error("Field or method is not available for class : {" + objectToValidate.getClass().getName()
					+ "}, exception : {" + e + "}");
			e.printStackTrace();
			return false;
		} catch (InvocationTargetException e) {
			logger.error("Exception occured accesing the class : {" + objectToValidate.getClass().getName()
					+ "}, exception : {" + e + "}");
			e.printStackTrace();
			return false;
		}
		return valid;
	}

}
