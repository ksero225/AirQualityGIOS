package com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstallationDTO {
    private Long installationId; //installation number
    private String installationFormula;
}
