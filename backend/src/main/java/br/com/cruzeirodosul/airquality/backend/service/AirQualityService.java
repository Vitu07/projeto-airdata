package br.com.cruzeirodosul.airquality.backend.service;

import br.com.cruzeirodosul.airquality.backend.dto.AirQualityResponseDTO;
import br.com.cruzeirodosul.airquality.backend.model.NivelRisco;
import br.com.cruzeirodosul.airquality.backend.repository.NivelRiscoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirQualityService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NivelRiscoRepository nivelRiscoRepository;

    @Value("${API_URL}")
    private String apiURL;

    @Value("${EXTERNAL_API_KEY}")
    private String apiKey;

    public AirQualityResponseDTO getAirQualityDataForCity(String city){
        String fullUrl = String.format("%s/feed/%s/?token=%s", apiURL, city, apiKey);

        try {
            String jsonResponse = restTemplate.getForObject(fullUrl, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (!"ok".equals(root.path("status").asText())) {
                AirQualityResponseDTO errorDto = new AirQualityResponseDTO();
                errorDto.setDadosApiExterna(root.path("data"));
                return errorDto;
            }

            JsonNode dataNode = root.path("data");
            int aqiValue = dataNode.path("aqi").asInt();

            NivelRisco nivelRisco = nivelRiscoRepository.findByAqiValue(aqiValue)
                    .orElse(null);

            AirQualityResponseDTO responseDTO = new AirQualityResponseDTO();
            responseDTO.setDadosApiExterna(dataNode);

            if (nivelRisco != null) {
                responseDTO.setClassificacaoRisco(nivelRisco.getClassificacao());

                List<String> recomendacoes = nivelRisco.getRiscoRecomendacoes().stream()
                        .map(rr -> rr.getRecomendacao().getTextoRecomendacao())
                        .collect(Collectors.toList());
                responseDTO.setRecomendacoes(recomendacoes);
            } else {
                responseDTO.setRecomendacoes(Collections.emptyList());
            }

            return responseDTO;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
