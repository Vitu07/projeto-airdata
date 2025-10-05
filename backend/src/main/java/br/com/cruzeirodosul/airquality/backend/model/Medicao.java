package br.com.cruzeirodosul.airquality.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "medicao")
public class Medicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicao")
    private Integer id;

    @Column(name = "valor_aqi", nullable = false)
    private int valorAqi;

    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_localizacao", nullable = false)
    private Localizacao localizacao;

    @ManyToOne
    @JoinColumn(name = "id_poluente_dominante", nullable = false)
    private Poluente poluenteDominante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getValorAqi() {
        return valorAqi;
    }

    public void setValorAqi(int valorAqi) {
        this.valorAqi = valorAqi;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Poluente getPoluenteDominante() {
        return poluenteDominante;
    }

    public void setPoluenteDominante(Poluente poluenteDominante) {
        this.poluenteDominante = poluenteDominante;
    }
}
