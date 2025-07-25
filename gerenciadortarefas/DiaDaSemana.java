package gerenciadortarefas;

public enum DiaDaSemana {
    SEGUNDA("Segunda-feira"),
    TERCA("Terça-feira"),
    QUARTA("Quarta-feira"),
    QUINTA("Quinta-feira"),
    SEXTA("Sexta-feira"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String nomeFormatado;

    DiaDaSemana(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }

    public static DiaDaSemana fromString(String texto) {
        for (DiaDaSemana dia : DiaDaSemana.values()) {
            if (dia.name().equalsIgnoreCase(texto) || dia.getNomeFormatado().equalsIgnoreCase(texto)) {
                return dia;
            }
        }
        try {
            int diaNum = Integer.parseInt(texto);
            if (diaNum >= 1 && diaNum <= DiaDaSemana.values().length) {
                return DiaDaSemana.values()[diaNum - 1];
            }
        } catch (NumberFormatException e) {
            
        }
        return null; 
    }
}