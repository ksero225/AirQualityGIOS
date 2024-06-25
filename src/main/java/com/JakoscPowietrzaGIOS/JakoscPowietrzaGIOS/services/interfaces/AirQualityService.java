package com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.services.interfaces;

import com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.domain.dto.StationDTO;

import java.util.List;

public interface AirQualityService {

    String getStations();

    String getInstallations(Long stationId);
}
