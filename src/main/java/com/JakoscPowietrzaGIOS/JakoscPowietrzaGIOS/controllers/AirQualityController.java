package com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.controllers;

import com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.domain.dto.InstallationDTO;
import com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.domain.dto.StationDTO;
import com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.services.interfaces.AirQualityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AirQualityController {
    private final AirQualityService airQualityService;
    private final ObjectMapper objectMapper;

    public AirQualityController(AirQualityService airQualityService, ObjectMapper objectMapper) {
        this.airQualityService = airQualityService;
        this.objectMapper = objectMapper;
    }

    //https://api.gios.gov.pl/pjp-api/v1/rest/station/findAll?size=5&sort=+

    @GetMapping("/stations")
    public List<StationDTO> getStations(){
        String apiResult = airQualityService.getStations();

        List<StationDTO> result = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(apiResult);
            JsonNode stationList = jsonNode.get("Lista stacji pomiarowych");

            result = new ArrayList<>();

            if (stationList != null && stationList.isArray()) {
                for (JsonNode stationNode : stationList) {
                    Long stationId = stationNode.get("Identyfikator stacji").asLong();
                    String stationName = stationNode.get("Nazwa stacji").asText();

                    List<InstallationDTO> foundStationInstallations = getStationInstallations(stationId);

                    StationDTO stationDTO = StationDTO.builder()
                            .stationId(stationId)
                            .stationName(stationName)
                            .stationInstallations(foundStationInstallations)
                            .build();

                    result.add(stationDTO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<InstallationDTO> getStationInstallations(Long stationId){
        String apiResult = airQualityService.getInstallations(stationId);

        List<InstallationDTO> result = null;

        try{
            JsonNode jsonNode = objectMapper.readTree(apiResult);
            JsonNode installationList = jsonNode.get("Lista stanowisk pomiarowych dla podanej stacji");

            result = new ArrayList<>();

            if (installationList != null && installationList.isArray()) {
                for (JsonNode installationNode : installationList) {
                    Long installationId = installationNode.get("Identyfikator stanowiska").asLong();
                    String installationName = installationNode.get("Wskaźnik - kod").asText();

                    InstallationDTO installationDTO = InstallationDTO.builder()
                            .installationId(installationId)
                            .installationFormula(installationName)
                            .build();

                    result.add(installationDTO);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

}
