package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ParticipantDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ParticipantLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ParticipantBean" + "!" + ParticipantLocal.class.getName();

	public List<ParticipantDto> findParticipants(Long roleId) throws BusinessException;

	public ParticipantDto newParticipant(ParticipantDto toSave, List<Long> organizations) throws BusinessException;

	public ParticipantDto editParticipant(ParticipantDto toSave, List<Long> organizations) throws BusinessException;

	public boolean deleteParticipant(long id, List<Long> organizations) throws BusinessException;

	public ParticipantDto findParticipantsById(long id) throws BusinessException;
	
	public List<ParticipantDto> findParticipants() throws BusinessException;


}
