package org.example.bea.controller;

import org.example.bea.model.Evento;
import org.example.bea.model.Usuario;
import org.example.bea.repository.EventoRepository;
import org.example.bea.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping
    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .map(evento -> ResponseEntity.ok().body(evento))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Evento createEvento(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @RequestBody Evento eventoDetails) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    evento.setTituloEvento(eventoDetails.getTituloEvento());
                    evento.setData(eventoDetails.getData());
                    evento.setCep(eventoDetails.getCep());
                    evento.setEndereco(eventoDetails.getEndereco());
                    evento.setDescricao(eventoDetails.getDescricao());
                    evento.setThumbnail(eventoDetails.getThumbnail());
                    evento.setUsuario(eventoDetails.getUsuario());
                    Evento updatedEvento = eventoRepository.save(evento);
                    return ResponseEntity.ok(updatedEvento);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    eventoRepository.delete(evento);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
