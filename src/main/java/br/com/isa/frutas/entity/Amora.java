package br.com.isa.frutas.entity;

public class Amora extends Fruta {
    public static int idContagem = 1;
    public Amora() {
        setNome("Amora");
        setCor("Roxa");
        setFormato(Formato.REDONDO);
        setTextura("Suculenta");
        setSemente(3);
    }

}
