package org.example.bea.controller;

import org.example.bea.model.Imagem;
import org.example.bea.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagens")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping
    public List<Imagem> getAllImagens() {
        return imagemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagem> getImagemById(@PathVariable Integer id) {
        return imagemRepository.findById(id)
                .map(imagem -> ResponseEntity.ok().body(imagem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Imagem createImagem(@RequestBody Imagem imagem) {
        return imagemRepository.save(imagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagem> updateImagem(@PathVariable Integer id, @RequestBody Imagem imagemDetails) {
        return imagemRepository.findById(id)
                .map(imagem -> {
                    imagem.setFormato(imagemDetails.getFormato());
                    imagem.setLegenda(imagemDetails.getLegenda());
                    imagem.setEvento(imagemDetails.getEvento());
                    Imagem updatedImagem = imagemRepository.save(imagem);
                    return ResponseEntity.ok(updatedImagem);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImagem(@PathVariable Integer id) {
        return imagemRepository.findById(id)
                .map(imagem -> {
                    imagemRepository.delete(imagem);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
