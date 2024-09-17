package org.example.bea.controller;

import org.example.bea.model.Atracao;
import org.example.bea.repository.AtracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atracoes")
public class AtracaoController {

    @Autowired
    private AtracaoRepository atracaoRepository;

    @GetMapping
    public List<Atracao> getAllAtracoes() {
        return atracaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atracao> getAtracaoById(@PathVariable Integer id) {
        return atracaoRepository.findById(id)
                .map(atracao -> ResponseEntity.ok().body(atracao))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Atracao createAtracao(@RequestBody Atracao atracao) {
        return atracaoRepository.save(atracao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atracao> updateAtracao(@PathVariable Integer id, @RequestBody Atracao atracaoDetails) {
        return atracaoRepository.findById(id)
                .map(atracao -> {
                    atracao.setTexto(atracaoDetails.getTexto());
                    atracao.setDataInicio(atracaoDetails.getDataInicio());
                    atracao.setDataFim(atracaoDetails.getDataFim());
                    atracao.setEvento(atracaoDetails.getEvento());
                    Atracao updatedAtracao = atracaoRepository.save(atracao);
                    return ResponseEntity.ok().body(updatedAtracao);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAtracao(@PathVariable Integer id) {
        return atracaoRepository.findById(id)
                .map(atracao -> {
                    atracaoRepository.delete(atracao);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
