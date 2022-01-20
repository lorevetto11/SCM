package com.gpi.scm.generic.dtos.validators;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.apache.commons.beanutils.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.log4j.Logger;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

	private static final Logger logger = Logger.getLogger(ConditionalValidator.class);

	private String selected;
	private String[] required;
	private String message;
	private String[] values;

	static boolean propertyExists(Object bean, String property) {
		return PropertyUtils.isReadable(bean, property) && PropertyUtils.isWriteable(bean, property);
	}

	@Override
	public void initialize(Conditional requiredIfChecked) {

		selected = requiredIfChecked.selected();
		required = requiredIfChecked.required();
		message = requiredIfChecked.message();
		values = requiredIfChecked.values();
	}

	@Override
	public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
		Boolean valid = true;
		try {
			Object requiredValue;
			Object actualValue = BeanUtils.getProperty(objectToValidate, selected);
			if (Arrays.asList(values).contains(actualValue)) {
				for (String propName : required) {
					if (!propertyExists(objectToValidate, propName)) {
						requiredValue = null;
					} else {
						requiredValue = BeanUtils.getProperty(objectToValidate, propName);
					}
					valid = requiredValue != null;
					System.out.println("value: " + requiredValue +" ");
					if (!valid) {
						context.disableDefaultConstraintViolation();
						context.buildConstraintViolationWithTemplate(message+ propName).addPropertyNode(propName)
								.addConstraintViolation();
					}
				}
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
