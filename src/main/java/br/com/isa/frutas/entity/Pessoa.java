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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column (nullable = false, unique = true)
    private UUID uuid;
    @Column(nullable = false)
    private String nome;
    private String etnia;
    private String genero;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    private Collection<Compra> compras = new ArrayList<>();

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

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Collection<Compra> getCompras() {
        return compras;
    }

    public void setCompras(Collection<Compra> compras) {
        this.compras = compras;
    }

}
