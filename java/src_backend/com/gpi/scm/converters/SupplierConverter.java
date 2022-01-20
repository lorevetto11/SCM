package com.gpi.scm.converters;

import java.util.ArrayList;
import java.util.List;

import com.gpi.scm.ejb.entities.Supplier;
import com.gpi.scm.generic.dtos.SupplierDto;

public class SupplierConverter extends GenericConverter {
	public static SupplierDto entityToDto(Supplier entity, boolean loadRelations) {
		if (entity == null) {
			return null;
		}
		SupplierDto result = new SupplierDto();
		result.setId(entity.getId());
		result.setName(entity.getName());
		result.setDescription(entity.getDescription());
		result.setVatNumber(entity.getVatNumber());
		result.setContact(entity.getContact());
		result.setAddress(entity.getAddress());
		result.setEmail(entity.getEmail());
		result.setPhone(entity.getPhone());
		GenericConverter.entityToDto(result, entity);
		if (loadRelations) {
			result.setContext(ContextConverter.entityToDto(entity.getContext(), false));
			result.setOrganization(OrganizationConverter.entityToDto(entity.getOrganization(), false));
		}
		return result;
	}

	public static Supplier dtoToEntity(SupplierDto dto) {
		if (dto == null) {
			return null;
		}
		Supplier result = new Supplier();
		result.setId(dto.getId());
		result.setName(dto.getName());
		result.setDescription(dto.getDescription());
		result.setVatNumber(dto.getVatNumber());
		result.setContact(dto.getContact());
		result.setAddress(dto.getAddress());
		result.setEmail(dto.getEmail());
		result.setPhone(dto.getPhone());
		GenericConverter.dtoToEntity(result, dto);
		return result;
	}
	public static List<SupplierDto> entityToDto(List<Supplier> entities, boolean loadRelations) {
		List<SupplierDto> suppliers = new ArrayList<>();
		for (Supplier supplier : entities) {
			suppliers.add(entityToDto(supplier, loadRelations));
		}
		return suppliers;
	}
	public static List<Supplier> dtoToEntity(List<SupplierDto> entities) {
		List<Supplier> suppliers = new ArrayList<>();
		for (SupplierDto supplier : entities) {
			suppliers.add(dtoToEntity(supplier));
		}
		return suppliers;
	}

}