package org.example.bea.controller;

import org.example.bea.model.Doacao;
import org.example.bea.model.Usuario;
import org.example.bea.repository.DoacaoRepository;
import org.example.bea.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ResponseEntity<Doacao> createDoacao(@RequestBody Doacao doacao) {
        Usuario doador = usuarioRepository.findById(doacao.getDoador().getUsuarioId())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));

        doacao.setDoador(doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doacao> updateDoacao(@PathVariable Integer id, @RequestBody Doacao doacaoDetails) {
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doação não encontrada"));

        doacao.setValor(doacaoDetails.getValor());
        doacao.setItem(doacaoDetails.getItem());
        doacao.setDoador(doacaoDetails.getDoador());

        Doacao updatedDoacao = doacaoRepository.save(doacao);
        return ResponseEntity.ok(updatedDoacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoacao(@PathVariable Integer id) {
        return doacaoRepository.findById(id)
                .map(doacao -> {
                    doacaoRepository.delete(doacao);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}

