package com.gpi.scm.delegates;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.gpi.scm.generic.dtos.ParticipantDto;
import com.gpi.scm.generic.exceptions.BusinessException;
import com.gpi.scm.interfaces.ParticipantLocal;

public class ParticipantDelegate extends GenericDelegate {
	private static final Logger logger = Logger.getLogger(ParticipantDelegate.class);
	private static ParticipantLocal getBean() throws NamingException {
		return (ParticipantLocal) getContext().lookup(ParticipantLocal.JNDI_NAME);
	}
	
	public static List<ParticipantDto> findParticipants(Long roleId) {
		List<ParticipantDto> obj = null;
		try {
			obj = getBean().findParticipants(roleId);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	public static List<ParticipantDto> findParticipants() {
		List<ParticipantDto> obj = null;
		try {
			obj = getBean().findParticipants();
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ParticipantDto saveParticipant(ParticipantDto toSave, List<Long> organizations) {
		ParticipantDto obj = null;
		try {
			obj = getBean().newParticipant(toSave,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static ParticipantDto editParticipant(ParticipantDto toSave, List<Long> organizations) {
		ParticipantDto obj = null;
		try {
			obj = getBean().editParticipant(toSave,organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}

	public static boolean delete(long id, List<Long> organizations) {
		try {
			return getBean().deleteParticipant(id, organizations);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return false;
	}

	public static ParticipantDto findParticipantById(long id) {
		ParticipantDto obj = null;
		try {
			obj = getBean().findParticipantsById(id);
		} catch (BusinessException | NamingException e) {
			logger.error(e);
		}
		return obj;
	}
	

}