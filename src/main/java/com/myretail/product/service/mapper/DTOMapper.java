package com.myretail.product.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Sort of anti corruption layer such that API response can vary
 * independently of domain model
 * 
 * @author mrinmoy
 *
 * @param <D>
 * @param <E>
 */
public interface DTOMapper<D, E> {

	E toEntity(D dto);

    D toDto(E entity);

    default List <E> toEntity(List<D> dtoList) {
    	return dtoList
	    		.stream()
	    		.map(this::toEntity)
	    		.collect(Collectors.toList());
    			
    }

    default List <D> toDto(List<E> entityList) {
    	return entityList
    			.stream()
    			.map(this::toDto)
    			.collect(Collectors.toList());
    }
}
