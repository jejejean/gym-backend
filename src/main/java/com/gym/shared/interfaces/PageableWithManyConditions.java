package com.gym.shared.interfaces;


import com.gym.shared.Utils.SearchParams;
import com.gym.shared.pagination.PagedResponse;

public interface PageableWithManyConditions<T> {
    PagedResponse<T> findAllPageable(SearchParams searchParams);
}
