package com.engine.infrastructure.adapter.input.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class PagedResponse<T> {

    private List<T> data;
    private Pagination pagination;

    @Data
    @Builder
    public static class Pagination {
        private int page;
        private int limit;
        private long total;
        private int totalPages;
    }

    public static <T, R> PagedResponse<R> of(Page<T> page, Function<T, R> mapper) {
        return PagedResponse.<R>builder()
                .data(page.getContent().stream().map(mapper).collect(Collectors.toList()))
                .pagination(Pagination.builder()
                        .page(page.getNumber() + 1)
                        .limit(page.getSize())
                        .total(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .build())
                .build();
    }
}
