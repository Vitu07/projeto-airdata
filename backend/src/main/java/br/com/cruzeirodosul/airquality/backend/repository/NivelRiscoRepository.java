package br.com.cruzeirodosul.airquality.backend.repository;

import br.com.cruzeirodosul.airquality.backend.model.NivelRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NivelRiscoRepository extends JpaRepository<NivelRisco, Integer> {

    @Query("SELECT n FROM NivelRisco n WHERE :aqiValue BETWEEN n.aqi_min AND n.aqi_max")
    Optional<NivelRisco> findByAqiValue(@Param("aqiValue") int aqiValue);
}
