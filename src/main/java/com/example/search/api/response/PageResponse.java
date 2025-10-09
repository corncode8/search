package com.example.search.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> items;      // 페이지 데이터 리스트
    private long totalItems;    // 전체 아이템 개수
    private int totalPages;     // 전체 페이지 개수
    private int currentPage;    // 현재 페이지 번호
    private int itemsPerPage;   // 페이지당 아이템 개수
    private boolean hasPreviousPage; // 이전 페이지 존재 여부
    private boolean hasNextPage;     // 다음 페이지 존재 여부

}
