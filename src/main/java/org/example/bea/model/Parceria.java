package org.example.bea.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Parceria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parceriaId;
    private String nome;
    private String email;
    private String telefone;

    // Getters e Setters
    public Integer getParceriaId() { return parceriaId; }
    public void setParceriaId(Integer parceriaId) { this.parceriaId = parceriaId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
