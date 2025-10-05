package br.com.cruzeirodosul.airquality.backend.repository;

import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {
    Optional<Localizacao> findByNomeCidade(String nomeCidade);
}
