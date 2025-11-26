package br.com.isa.frutas.entity;

public class Laranja extends Fruta {
    private static int idContagem = 1;
    public Laranja () {
        setNome("Laranja");
        setCor("Laranja");
        setFormato(Formato.REDONDO);
        setTextura("Fibrosa");
        setSemente(15);
    }

}
