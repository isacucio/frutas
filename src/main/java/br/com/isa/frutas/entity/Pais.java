package br.com.isa.frutas.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pais")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private UUID uuid;
    @Column(nullable = false)
    private String nome;
    private String clima;
    private String vegetacao;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais")
    private Collection<Fruta> frutas = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais")
    private Collection<Pessoa> pessoas = new ArrayList<>();

    public Pais() {
    }

    public Pais(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getVegetacao() {
        return vegetacao;
    }

    public void setVegetacao(String vegetacao) {
        this.vegetacao = vegetacao;
    }

    public Collection<Fruta> getFrutas() {
        return frutas;
    }

    public void setFrutas(Collection<Fruta> frutas) {
        this.frutas = frutas;
    }

    public Collection<Pessoa> getPessoas() {
        return pessoas;
    }
    
    public void setPessoas(Collection<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
