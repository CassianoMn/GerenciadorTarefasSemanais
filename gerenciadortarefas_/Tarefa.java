package gerenciadortarefas_;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descricao;
    private DiaDaSemana diaDaSemana;
    private StatusTarefa status;

    public Tarefa(String descricao, DiaDaSemana diaDaSemana) {
        this.descricao = descricao;
        this.diaDaSemana = diaDaSemana;
        this.status = StatusTarefa.PENDENTE;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public DiaDaSemana getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public double getPontuacao() {
        return status.getPontos();
    }

    @Override
    public String toString() {
        return String.format("Tarefa: %s (%s) - Status: %s - Pontos: %.1f",
                descricao, diaDaSemana.getNomeFormatado(), status.getDescricao(), getPontuacao());
    }
}