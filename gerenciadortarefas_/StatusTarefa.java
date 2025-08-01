package gerenciadortarefas_;


public enum StatusTarefa {
    PENDENTE("Pendente"),
    EXECUTADA("Executada"),
    EXECUTADA_PARCIALMENTE("Executada Parcialmente");

    private final String descricao;

    StatusTarefa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPontos() {
        switch (this) {
            case EXECUTADA:
                return 1.0;
            case EXECUTADA_PARCIALMENTE:
                return 0.5;
            case PENDENTE:
            default:
                return 0.0;
        }
    }
}
