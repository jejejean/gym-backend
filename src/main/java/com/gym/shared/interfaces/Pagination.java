package com.gym.shared.interfaces;

import com.gym.shared.pagination.PagedResponse;

public interface Pagination<G extends IHandlePaginationResponse> {
    PagedResponse<G> findAll(int page, int size, String search);

}
