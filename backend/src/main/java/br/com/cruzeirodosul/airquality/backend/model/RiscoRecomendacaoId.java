package br.com.cruzeirodosul.airquality.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RiscoRecomendacaoId implements Serializable {
    @Column(name = "id_nivel_risco")
    private Integer nivelRiscoId;

    @Column(name = "id_recomendacao")
    private Integer recomendacaoId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RiscoRecomendacaoId that = (RiscoRecomendacaoId) o;
        return Objects.equals(nivelRiscoId, that.nivelRiscoId) && Objects.equals(recomendacaoId, that.recomendacaoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nivelRiscoId, recomendacaoId);
    }

    public Integer getNivelRiscoId() {
        return nivelRiscoId;
    }

    public void setNivelRiscoId(Integer nivelRiscoId) {
        this.nivelRiscoId = nivelRiscoId;
    }

    public Integer getRecomendacaoId() {
        return recomendacaoId;
    }

    public void setRecomendacaoId(Integer recomendacaoId) {
        this.recomendacaoId = recomendacaoId;
    }
}
