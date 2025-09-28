package br.com.cruzeirodosul.airquality.backend.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "risco_recomendacao")
public class RiscoRecomendacao {

    @EmbeddedId
    private RiscoRecomendacaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nivelRiscoId")
    @JoinColumn(name = "id_nivel_risco")
    private NivelRisco nivelRisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recomendacaoId")
    @JoinColumn(name = "id_recomendacao")
    private Recomendacao recomendacao;

    public RiscoRecomendacaoId getId() {
        return id;
    }

    public void setId(RiscoRecomendacaoId id) {
        this.id = id;
    }

    public NivelRisco getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public Recomendacao getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(Recomendacao recomendacao) {
        this.recomendacao = recomendacao;
    }
}
