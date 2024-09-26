package org.example.bea.controller;

import org.example.bea.model.Parceria;
import org.example.bea.repository.ParceriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcerias")
public class ParceriaController {

    @Autowired
    private ParceriaRepository parceriaRepository;

    @GetMapping
    public List<Parceria> getAllParcerias() {
        return parceriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parceria> getParceriaById(@PathVariable Integer id) {
        return parceriaRepository.findById(id)
                .map(parceria -> ResponseEntity.ok().body(parceria))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Parceria createParceria(@RequestBody Parceria parceria) {
        return parceriaRepository.save(parceria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parceria> updateParceria(@PathVariable Integer id, @RequestBody Parceria parceriaDetails) {
        return parceriaRepository.findById(id)
                .map(parceria -> {
                    parceria.setNome(parceriaDetails.getNome());
                    parceria.setEmail(parceriaDetails.getEmail());
                    parceria.setTelefone(parceriaDetails.getTelefone());
                    Parceria updatedParceria = parceriaRepository.save(parceria);
                    return ResponseEntity.ok(updatedParceria);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParceria(@PathVariable Integer id) {
        return parceriaRepository.findById(id)
                .map(parceria -> {
                    parceriaRepository.delete(parceria);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
