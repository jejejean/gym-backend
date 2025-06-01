package com.gym.shared.interfaces;

public interface MapperConverter<R extends IHandleRequest, G extends IHandleResponse, T extends IHandleEntity> {
    G mapEntityToDto(T entity);
    T mapDtoToEntity(R request);
}
