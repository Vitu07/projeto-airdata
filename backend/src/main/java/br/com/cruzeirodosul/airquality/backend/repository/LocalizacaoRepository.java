package br.com.cruzeirodosul.airquality.backend.repository;

import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Integer> {

    @Query(
            value = "SELECT * FROM localizacao WHERE lower(unaccent(nome_cidade)) = lower(unaccent(:nomeCidade))",
            nativeQuery = true
    )
    Optional<Localizacao> findByNomeCidadeUnaccentIgnoreCase(@Param("nomeCidade") String nomeCidade);
}
