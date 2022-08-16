package com.can.zupuserservice.core.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortParamsDTO {
    private Integer page;
    private Integer size;
    private String sort;
    private String order;
}
