package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.TrainingDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.TrainingLocal;

public class TrainingDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(TrainingDelegate.class);

	private static TrainingLocal getBean() throws NamingException {
		return (TrainingLocal) getContext().lookup(TrainingLocal.JNDI_NAME);
	}

	public static boolean deleteTraining(long id) {
		try {
			return getBean().deleteTraining(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

	public static Object editTraining(TrainingDto toSave) {
		TrainingDto obj = null;
		try {
			obj = getBean().editTraining(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static Object saveTraining(TrainingDto toSave) {
		TrainingDto obj = null;
		try {
			obj = getBean().newTraining(toSave);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static List<TrainingDto> findTrainigns(Long organizationId) {
		List<TrainingDto> obj = null;
		try {
			obj = getBean().findTrainings(organizationId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static TrainingDto findTrainignsById(Long id) {
		TrainingDto obj = null;
		try {
			obj = getBean().findTrainingsById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return  obj;
	}
	
}
