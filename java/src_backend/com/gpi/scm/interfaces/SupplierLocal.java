package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.constants.JndiNamingConstants;
import com.gpi.scm.generic.dtos.SupplierDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface SupplierLocal {
	public static final String JNDI_NAME = JndiNamingConstants.JNDI_LOCAL_FIRST_PART + "SupplierBean" + "!" + SupplierLocal.class.getName();

	public List<SupplierDto> findSupplier(Long organizations) throws BusinessException;
	
	public boolean deleteSupplier(long id) throws BusinessException;

	public SupplierDto editSupplier(SupplierDto supplier) throws BusinessException;

	public SupplierDto newSupplier(SupplierDto supplier) throws BusinessException;


}
