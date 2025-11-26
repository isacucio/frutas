package br.com.isa.frutas.entity;

public class Uva extends Fruta {
    private static int idContagem = 1;
    public Uva() {
        setNome("Uva");
        setCor("Verde");
        setFormato(Formato.REDONDO);
        setTextura("Suculenta");
        setSemente(3);
    }



}
