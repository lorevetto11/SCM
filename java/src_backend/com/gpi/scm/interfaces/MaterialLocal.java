package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.MaterialDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface MaterialLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "MaterialBean" + "!" + MaterialLocal.class.getName();

	public List<MaterialDto> findMaterial(Long organizations) throws BusinessException;
	
	public boolean deleteMaterial(long id) throws BusinessException;

	public MaterialDto editMaterial(MaterialDto Material) throws BusinessException;

	public MaterialDto newMaterial(MaterialDto Material) throws BusinessException;


}
