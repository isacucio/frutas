package br.com.isa.frutas.entity;

public enum Formato {
    REDONDO("Redondo");

    private String formato;
    private Formato (String formato) {
        this.formato = formato;
    }

    public String toString() {
        return formato;
    }

    public static Formato from(String formato) {
        if (formato == null) {
            return null;
        }
        if (formato.equalsIgnoreCase("Redondo")) {
            return Formato.REDONDO;
        }
        return null;
    }

}
