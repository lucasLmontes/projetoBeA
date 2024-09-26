package org.example.bea.model;

import jakarta.persistence.*;


@Entity
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doacaoId;
    private Integer valor;
    private String item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doador_id", nullable = false)
    private Usuario doador;

    // Getters e Setters
    public Integer getDoacaoId() { return doacaoId; }
    public void setDoacaoId(Integer doacaoId) { this.doacaoId = doacaoId; }
    public Integer getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public Usuario getDoador() { return doador; }
    public void setDoador(Usuario doador) { this.doador = doador; }
}
