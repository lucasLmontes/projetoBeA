package org.example.bea.controller;

import org.example.bea.model.Evento;
import org.example.bea.model.Feedback;
import org.example.bea.repository.EventoRepository;
import org.example.bea.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api") // Base URL, mantivemos para consistência
public class FeedbackController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Endpoint para obter todos os feedbacks
    @GetMapping("/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Endpoint para obter feedbacks de um evento específico
    @GetMapping("/eventos/{eventoId}/feedbacks")  // Mantivemos a mesma URL
    public ResponseEntity<List<Feedback>> getFeedbacksByEvento(@PathVariable Integer eventoId) {
        // Usando findByEvento_EventoId para pegar os feedbacks do evento
        List<Feedback> feedbacks = feedbackRepository.findByEvento_EventoId(eventoId);

        if (feedbacks.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não houver feedbacks
        }
        return ResponseEntity.ok().body(feedbacks);
    }

    // Endpoint para obter um evento específico (caso queira usar a mesma URL em outro lugar, garanta que ela seja única)
    @GetMapping("/eventos/detalhes/{eventoId}")  // Mudança aqui para não gerar conflito com o FeedbackController
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer eventoId) {
        return eventoRepository.findById(eventoId)
                .map(evento -> ResponseEntity.ok().body(evento))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obter um feedback específico pelo ID
    @GetMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer id) {
        return feedbackRepository.findById(id)
                .map(feedback -> ResponseEntity.ok().body(feedback))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para criar um feedback
    @PostMapping("/feedbacks")
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // Endpoint para atualizar um feedback existente
    @PutMapping("/feedbacks/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Integer id, @RequestBody Feedback feedbackDetails) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedback.setTexto(feedbackDetails.getTexto());
                    feedback.setDataCriacao(feedbackDetails.getDataCriacao());
                    feedback.setAutor(feedbackDetails.getAutor());
                    feedback.setEvento(feedbackDetails.getEvento());
                    Feedback updatedFeedback = feedbackRepository.save(feedback);
                    return ResponseEntity.ok(updatedFeedback);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um feedback
    @DeleteMapping("/feedbacks/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedbackRepository.delete(feedback);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}