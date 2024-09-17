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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ResponseEntity<Evento> createEvento(@RequestBody Evento evento) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(evento.getUsuario().getUsuarioId());
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null); // ou retornar um erro mais específico
        }

        evento.setUsuario(usuarioOptional.get()); // Associar o usuário ao evento
        Evento savedEvento = eventoRepository.save(evento);
        return ResponseEntity.ok(savedEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @RequestBody Evento eventoDetails) {
        return (ResponseEntity<Evento>) eventoRepository.findById(id)
                .map(evento -> {
                    evento.setTituloEvento(eventoDetails.getTituloEvento());
                    evento.setData(eventoDetails.getData());
                    evento.setCep(eventoDetails.getCep());
                    evento.setEndereco(eventoDetails.getEndereco());
                    evento.setDescricao(eventoDetails.getDescricao());
                    evento.setThumbnail(eventoDetails.getThumbnail());

                    // Atualizar o usuário apenas se ele estiver presente
                    if (eventoDetails.getUsuario() != null) {
                        Optional<Usuario> usuarioOptional = usuarioRepository.findById(eventoDetails.getUsuario().getUsuarioId());
                        if (usuarioOptional.isPresent()) {
                            evento.setUsuario(usuarioOptional.get());
                        } else {
                            return ResponseEntity.badRequest().body(null); // ou retornar um erro mais específico
                        }
                    }

                    Evento updatedEvento = eventoRepository.save(evento);
                    return ResponseEntity.ok().body(updatedEvento);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvento(@PathVariable Integer id) {
        return eventoRepository.findById(id)
                .map(evento -> {
                    eventoRepository.delete(evento);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
