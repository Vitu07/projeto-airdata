package br.com.cruzeirodosul.airquality.backend.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class AirQualityResponseDTO {

    private JsonNode dadosApiExterna;
    private String classificacaoRisco;
    private List<String> recomendacoes;

    public JsonNode getDadosApiExterna() {
        return dadosApiExterna;
    }

    public void setDadosApiExterna(JsonNode dadosApiExterna) {
        this.dadosApiExterna = dadosApiExterna;
    }

    public String getClassificacaoRisco() {
        return classificacaoRisco;
    }

    public void setClassificacaoRisco(String classificacaoRisco) {
        this.classificacaoRisco = classificacaoRisco;
    }

    public List<String> getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(List<String> recomendacoes) {
        this.recomendacoes = recomendacoes;
    }
}
