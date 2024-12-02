package org.example.bea.controller;

import org.example.bea.model.Atracao;
import org.example.bea.model.Evento;
import org.example.bea.repository.AtracaoRepository;
import org.example.bea.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Base URL para manter consistência com o FeedbackController
public class AtracaoController {

    @Autowired
    private AtracaoRepository atracaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    // Endpoint para obter todas as atrações
    @GetMapping("/atracoes")
    public List<Atracao> getAllAtracoes() {
        return atracaoRepository.findAll();
    }

    // Endpoint para obter uma atração específica pelo ID
    @GetMapping("/atracoes/{id}")
    public ResponseEntity<Atracao> getAtracaoById(@PathVariable Integer id) {
        return atracaoRepository.findById(id)
                .map(atracao -> ResponseEntity.ok().body(atracao))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obter as atrações de um evento específico
    @GetMapping("/eventos/{eventoId}/atracoes")
    public ResponseEntity<List<Atracao>> getAtracoesByEventoId(@PathVariable Integer eventoId) {
        List<Atracao> atracoes = atracaoRepository.findByEvento_EventoId(eventoId);

        if (atracoes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver atrações
        }
        return ResponseEntity.ok().body(atracoes);
    }

    // Endpoint para criar uma nova atração
    @PostMapping("/atracoes")
    public ResponseEntity<Atracao> createAtracao(@RequestBody Atracao atracao) {
        if (atracao == null || atracao.getTexto() == null || atracao.getTexto().isEmpty()) {
            return ResponseEntity.badRequest().build();  // Retorna 400 se os dados forem inválidos
        }

        // Verifica se o evento existe
        Integer eventoId = atracao.getEvento().getEventoId();
        if (!eventoRepository.existsById(eventoId)) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o evento não existir
        }

        // Se o evento existe, cria a atração
        Atracao savedAtracao = atracaoRepository.save(atracao);
        return ResponseEntity.status(201).body(savedAtracao); // Retorna 201 Created
    }

    // Endpoint para atualizar uma atração existente
    @PutMapping("/atracoes/{id}")
    public ResponseEntity<Atracao> updateAtracao(@PathVariable Integer id, @RequestBody Atracao atracaoDetails) {
        return atracaoRepository.findById(id)
                .map(atracao -> {
                    atracao.setTexto(atracaoDetails.getTexto());
                    atracao.setDataInicio(atracaoDetails.getDataInicio());
                    atracao.setDataFim(atracaoDetails.getDataFim());
                    atracao.setEvento(atracaoDetails.getEvento()); // Verifica se o evento é válido
                    Atracao updatedAtracao = atracaoRepository.save(atracao);
                    return ResponseEntity.ok(updatedAtracao); // Retorna 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }

    // Endpoint para deletar uma atração
    @DeleteMapping("/atracoes/{id}")
    public ResponseEntity<Void> deleteAtracao(@PathVariable Integer id) {
        return atracaoRepository.findById(id)
                .map(atracao -> {
                    atracaoRepository.delete(atracao);
                    return ResponseEntity.ok().<Void>build(); // Retorna 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado
    }
}