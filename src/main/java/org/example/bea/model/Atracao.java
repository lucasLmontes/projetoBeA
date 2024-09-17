package org.example.bea.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Atracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer atracaoId;
    private String texto;
    private Timestamp dataInicio;
    private Timestamp dataFim;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    // Getters e Setters
    public Integer getAtracaoId() { return atracaoId; }
    public void setAtracaoId(Integer atracaoId) { this.atracaoId = atracaoId; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public Timestamp getDataInicio() { return dataInicio; }
    public void setDataInicio(Timestamp dataInicio) { this.dataInicio = dataInicio; }
    public Timestamp getDataFim() { return dataFim; }
    public void setDataFim(Timestamp dataFim) { this.dataFim = dataFim; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
}
