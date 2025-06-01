package com.gym.shared.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudInterface<T extends IHandleRequest, G extends IHandleResponse> {
    //PagedResponse<? extends IHandleSimpleResponse> findAll(int page, int size, String search);
    List<G> findAll();
    Optional<G> findById(Long id);
    G create(T request);
    G update(Long id, T request);
    String delete(Long id);

}
