package com.kurtcan.zupuserservice.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateEmailDTO {
    private String emailSubject;
    private String emailTemplate;
    private String to;
    private Map<String, Object> properties;
}
