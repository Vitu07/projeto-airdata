package br.com.cruzeirodosul.airquality.backend.dto;

import java.util.List;

public class HistoricoDTO {

    private int aqiMax;
    private int aqiMin;
    private double aqiAvg;
    private List<MedicaoDiariaDTO> historicoDiario;
    private String poluentePredominanteDescricao;

    public String getPoluentePredominanteDescricao() {
        return poluentePredominanteDescricao;
    }

    public void setPoluentePredominanteDescricao(String poluentePredominanteDescricao) {
        this.poluentePredominanteDescricao = poluentePredominanteDescricao;
    }

    public int getAqiMax() {
        return aqiMax;
    }

    public void setAqiMax(int aqiMax) {
        this.aqiMax = aqiMax;
    }

    public int getAqiMin() {
        return aqiMin;
    }

    public void setAqiMin(int aqiMin) {
        this.aqiMin = aqiMin;
    }

    public double getAqiAvg() {
        return aqiAvg;
    }

    public void setAqiAvg(double aqiAvg) {
        this.aqiAvg = aqiAvg;
    }

    public List<MedicaoDiariaDTO> getHistoricoDiario() {
        return historicoDiario;
    }

    public void setHistoricoDiario(List<MedicaoDiariaDTO> historicoDiario) {
        this.historicoDiario = historicoDiario;
    }
}
