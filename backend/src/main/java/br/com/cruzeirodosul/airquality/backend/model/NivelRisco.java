package br.com.cruzeirodosul.airquality.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "nivel_risco")
public class NivelRisco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel_risco")
    private Integer id;

    @Column(nullable = false)
    private String classificacao;

    @Column(name = "descricao_efeitos")
    private String descricaoEfeitos;

    @Column(nullable = false)
    private int aqi_min;

    @Column(nullable = false)
    private int aqi_max;

    @OneToMany(mappedBy = "nivelRisco", fetch = FetchType.LAZY)
    private Set<RiscoRecomendacao> riscoRecomendacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getDescricaoEfeitos() {
        return descricaoEfeitos;
    }

    public void setDescricaoEfeitos(String descricaoEfeitos) {
        this.descricaoEfeitos = descricaoEfeitos;
    }

    public int getAqi_min() {
        return aqi_min;
    }

    public void setAqi_min(int aqi_min) {
        this.aqi_min = aqi_min;
    }

    public int getAqi_max() {
        return aqi_max;
    }

    public void setAqi_max(int aqi_max) {
        this.aqi_max = aqi_max;
    }

    public Set<RiscoRecomendacao> getRiscoRecomendacoes() {
        return riscoRecomendacoes;
    }

    public void setRiscoRecomendacoes(Set<RiscoRecomendacao> riscoRecomendacoes) {
        this.riscoRecomendacoes = riscoRecomendacoes;
    }
}
