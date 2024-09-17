package org.example.bea.controller;

import org.example.bea.model.Doacao;
import org.example.bea.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @GetMapping
    public List<Doacao> getAllDoacoes() {
        return doacaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doacao> getDoacaoById(@PathVariable Integer id) {
        return doacaoRepository.findById(id)
                .map(doacao -> ResponseEntity.ok().body(doacao))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doacao createDoacao(@RequestBody Doacao doacao) {
        return doacaoRepository.save(doacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doacao> updateDoacao(@PathVariable Integer id, @RequestBody Doacao doacaoDetails) {
        return doacaoRepository.findById(id)
                .map(doacao -> {
                    doacao.setValor(doacaoDetails.getValor());
                    doacao.setItem(doacaoDetails.getItem());
                    doacao.setDoador(doacaoDetails.getDoador());
                    Doacao updatedDoacao = doacaoRepository.save(doacao);
                    return ResponseEntity.ok().body(updatedDoacao);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoacao(@PathVariable Integer id) {
        return doacaoRepository.findById(id)
                .map(doacao -> {
                    doacaoRepository.delete(doacao);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
