package com.gym.shared.Utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ServiceUtil {

    public static Pageable createPageable(SearchParams searchParams) {
        return PageRequest.of(searchParams.getPage() - 1, searchParams.getSize());
    }
}
