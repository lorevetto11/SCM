package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.MaterialCategoryDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface MaterialCategoryLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "MaterialCategoryBean" + "!"
			+ MaterialCategoryLocal.class.getName();

	public List<MaterialCategoryDto> findMaterialCategory() throws BusinessException;

	public boolean deleteMaterialCategory(long id) throws BusinessException;

	public MaterialCategoryDto editMaterialCategory(MaterialCategoryDto MaterialCategory) throws BusinessException;

	public MaterialCategoryDto newMaterialCategory(MaterialCategoryDto MaterialCategory) throws BusinessException;

}
