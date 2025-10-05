package br.com.cruzeirodosul.airquality.backend.dto;

import java.time.LocalDate;

public class MedicaoDiariaDTO {

    private LocalDate data;
    private int valorAqi;
    private String nomePoluente;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getValorAqi() {
        return valorAqi;
    }

    public void setValorAqi(int valorAqi) {
        this.valorAqi = valorAqi;
    }

    public String getNomePoluente() {
        return nomePoluente;
    }

    public void setNomePoluente(String nomePoluente) {
        this.nomePoluente = nomePoluente;
    }
}
