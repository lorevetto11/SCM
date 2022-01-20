package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.ShapeDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface ShapeLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "ShapeBean" + "!" + ShapeLocal.class.getName();

	ShapeDto newShape(ShapeDto toSave) throws BusinessException;

	ShapeDto editShape(ShapeDto toSave) throws BusinessException;

	boolean deleteShape(long id) throws BusinessException;

	ShapeDto findShapesById(long id) throws BusinessException;

	List<ShapeDto> findShapes() throws BusinessException;

}
