package br.com.cruzeirodosul.airquality.backend.repository;

import br.com.cruzeirodosul.airquality.backend.model.Poluente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoluenteRepository extends JpaRepository<Poluente, Integer> {
    Optional<Poluente> findByNome(String nome);
}
