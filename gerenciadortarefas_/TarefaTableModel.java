package gerenciadortarefas_;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TarefaTableModel extends AbstractTableModel {
    private final List<Tarefa> tarefas;
    private final String[] colunas = {"Descrição", "Dia da Semana", "Status", "Pontos"};

    public TarefaTableModel(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public int getRowCount() {
        return tarefas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Tarefa tarefa = tarefas.get(rowIndex);
            
            if (tarefa == null) return "Objeto de tarefa nulo";

            switch (columnIndex) {
                case 0:
                    return tarefa.getDescricao();
                case 1:
                    return (tarefa.getDiaDaSemana() != null) ? tarefa.getDiaDaSemana().getNomeFormatado() : "Dia Inválido";
                case 2:
                    return (tarefa.getStatus() != null) ? tarefa.getStatus().getDescricao() : "Status Inválido";
                case 3:
                    return (tarefa.getStatus() != null) ? String.format("%.1f", tarefa.getPontuacao()) : "0.0";
                default:
                    return null;
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Erro de índice na tabela: " + e.getMessage());
            return "Dado Inválido";
        } catch (Exception e) {
            System.err.println("Erro inesperado ao renderizar célula ["+ rowIndex + "," + columnIndex +"]: " + e.getMessage());
            return "ERRO";
        }
    }

    public void atualizarDados() {
        fireTableDataChanged();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        fireTableRowsInserted(tarefas.size() - 1, tarefas.size() - 1);
    }

    public void removerTarefa(int rowIndex) {
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void atualizarTarefa(int rowIndex) {
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
