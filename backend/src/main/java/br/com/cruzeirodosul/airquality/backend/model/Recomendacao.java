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
@Table(name = "recomendacao")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recomendacao")
    private Integer id;

    @Column(name = "texto_recomendacao", nullable = false)
    private String textoRecomendacao;

    @OneToMany(mappedBy = "recomendacao", fetch = FetchType.LAZY)
    private Set<RiscoRecomendacao> riscoRecomendacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextoRecomendacao() {
        return textoRecomendacao;
    }

    public void setTextoRecomendacao(String textoRecomendacao) {
        this.textoRecomendacao = textoRecomendacao;
    }

    public Set<RiscoRecomendacao> getRiscoRecomendacoes() {
        return riscoRecomendacoes;
    }

    public void setRiscoRecomendacoes(Set<RiscoRecomendacao> riscoRecomendacoes) {
        this.riscoRecomendacoes = riscoRecomendacoes;
    }
}
