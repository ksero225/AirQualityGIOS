package com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDTO {
    private Long stationId;
    private String stationName;
    private List<InstallationDTO> stationInstallations;
}
