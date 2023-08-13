package ru.practicum.exploreWithMe.common.pagination;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.exploreWithMe.common.exception.RequestParametersException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Pagination {
    public static Pageable splitByPages(Integer from, Integer size) {
        if (from == null || size == null) {
            return null;
        }

        if (size <= 0 || from < 0) {
            throw new RequestParametersException("from/size");
        }

        return PageRequest.of(from / size, size);
    }
}
