package br.com.cruzeirodosul.airquality.backend.repository;

import br.com.cruzeirodosul.airquality.backend.model.Localizacao;
import br.com.cruzeirodosul.airquality.backend.model.Medicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MedicaoRepository extends JpaRepository<Medicao, Integer> {
    @Query("SELECT m FROM Medicao m WHERE m.localizacao.id = :localizacaoId AND m.dataHora >= :seteDiasAtras ORDER BY m.dataHora ASC")
    List<Medicao> findLast7DaysByLocation(@Param("localizacaoId") Integer localizacaoId, @Param("seteDiasAtras") OffsetDateTime seteDiasAtras);

    @Query("SELECT m FROM Medicao m WHERE m.localizacao = :localizacao AND m.dataHora >= :inicioDoDia AND m.dataHora <= :fimDoDia")
    Optional<Medicao> findByLocalizacaoAndDia(
            @Param("localizacao") Localizacao localizacao,
            @Param("inicioDoDia") OffsetDateTime inicioDoDia,
            @Param("fimDoDia") OffsetDateTime fimDoDia
    );
}
