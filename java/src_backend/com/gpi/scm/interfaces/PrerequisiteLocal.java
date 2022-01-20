package com.gpi.scm.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.gpi.scm.generic.dtos.BasePrerequisiteDto;
import com.gpi.scm.generic.exceptions.BusinessException;

@Local
public interface PrerequisiteLocal {
		public BasePrerequisiteDto findByContextId(long id,List<Long> organizations)throws BusinessException;
}
