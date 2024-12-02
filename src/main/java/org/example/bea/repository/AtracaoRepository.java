package org.example.bea.repository;
import org.example.bea.model.Atracao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtracaoRepository extends JpaRepository<Atracao, Integer> {
    List<Atracao> findByEvento_EventoId(Integer eventoId);
}