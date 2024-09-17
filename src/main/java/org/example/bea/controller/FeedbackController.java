package org.example.bea.controller;

import org.example.bea.model.Feedback;
import org.example.bea.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Integer id) {
        return feedbackRepository.findById(id)
                .map(feedback -> ResponseEntity.ok().body(feedback))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Integer id, @RequestBody Feedback feedbackDetails) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedback.setTexto(feedbackDetails.getTexto());
                    feedback.setDataCriacao(feedbackDetails.getDataCriacao());
                    feedback.setAutor(feedbackDetails.getAutor());
                    feedback.setEvento(feedbackDetails.getEvento());
                    Feedback updatedFeedback = feedbackRepository.save(feedback);
                    return ResponseEntity.ok().body(updatedFeedback);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable Integer id) {
        return feedbackRepository.findById(id)
                .map(feedback -> {
                    feedbackRepository.delete(feedback);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
