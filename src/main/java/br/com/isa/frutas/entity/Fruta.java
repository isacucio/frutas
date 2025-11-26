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
@Table(name = "fruta")
public class Fruta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column (nullable = false, unique = true)
    private UUID uuid;
    private String nome;
    @Column(nullable = false)
    private int semente;
    private String cor;
    private String textura;
    private Formato formato = Formato.REDONDO;
    @ManyToOne(fetch = FetchType.LAZY)
    private Pais pais;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cidade cidade;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fruta")
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

    public int getSemente() {
        return semente;
    }

    public void setSemente(int semente) {
        this.semente = semente;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getTextura() {
        return textura;
    }

    public void setTextura(String textura) {
        this.textura = textura;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
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

    public Cidade getCidade() {
        return cidade;
    }
    
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
