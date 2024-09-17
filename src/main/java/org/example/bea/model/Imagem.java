package org.example.bea.model;

import jakarta.persistence.*;


@Entity
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imagemId;
    private String formato;
    private String legenda;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    // Getters e Setters
    public Integer getImagemId() { return imagemId; }
    public void setImagemId(Integer imagemId) { this.imagemId = imagemId; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getLegenda() { return legenda; }
    public void setLegenda(String legenda) { this.legenda = legenda; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
}
