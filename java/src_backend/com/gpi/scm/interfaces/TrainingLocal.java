package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.TrainingDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface TrainingLocal {

	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "TrainingBean" + "!" + TrainingLocal.class.getName();

	public boolean deleteTraining(long id) throws BusinessException;

	public TrainingDto editTraining(TrainingDto toSave) throws BusinessException;

	public TrainingDto newTraining(TrainingDto toSave) throws BusinessException;

	public List<TrainingDto> findTrainings(Long organizationId) throws BusinessException;

	public TrainingDto findTrainingsById(Long id) throws BusinessException;

}
