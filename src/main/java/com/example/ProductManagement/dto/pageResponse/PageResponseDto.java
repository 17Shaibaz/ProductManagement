package com.example.ProductManagement.dto.pageResponse;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class  PageResponseDto<T> {
    List<T> data;
    Integer totalPages;
    boolean isLast;
    Integer currentPage;
    Integer totalItems;

}
