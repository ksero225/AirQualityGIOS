package com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.services.implementations;

import com.JakoscPowietrzaGIOS.JakoscPowietrzaGIOS.services.interfaces.AirQualityService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class AirQualityServiceImpl implements AirQualityService {
    private final RestTemplate restTemplate;

    public AirQualityServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getStations() {
        String url = "https://api.gios.gov.pl/pjp-api/v1/rest/station/findAll?size=5&sort=+";

        return getApiResult(url);
    }

    @Override
    public String getInstallations(Long stationId) {
        String url = "https://api.gios.gov.pl/pjp-api/v1/rest/station/sensors/" + stationId;

        return getApiResult(url);
    }

    private String getApiResult(String url) {
        try{
            String installations = restTemplate.getForObject(url, String.class);

            if(installations != null && !installations.isEmpty()){
                return installations;
            }else{
                return "";
            }
        }catch (HttpStatusCodeException e){
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            return "Błąd Http " + statusCode.value() + ": " + statusCode.getReasonPhrase();
        }catch (Exception e){
            return "Unexpected error: " + e.getMessage();
        }
    }
}
