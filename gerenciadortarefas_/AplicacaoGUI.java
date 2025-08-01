
package gerenciadortarefas_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AplicacaoGUI extends JFrame {
    private GerenciadorDeTarefas gerenciador;
    private TarefaTableModel tableModel;
    private JTable tabelaTarefas;
    private JTextField campoDescricao;
    private JComboBox<DiaDaSemana> comboBoxDiaSemana;
    private JButton btnAdicionar, btnExecutada, btnParcialmente, btnRemover;
    private JLabel lblPontuacaoTotal, lblPontuacaoDia;
    private JComboBox<DiaDaSemana> comboBoxFiltroDiaPontos;

    public AplicacaoGUI() {
        gerenciador = new GerenciadorDeTarefas();
        tableModel = new TarefaTableModel(gerenciador.getTodasAsTarefas());

        setTitle("Gerenciador de Tarefas Semanal");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Nova Tarefa"));
        
        painelEntrada.add(new JLabel("Descrição:"));
        campoDescricao = new JTextField(20);
        painelEntrada.add(campoDescricao);

        painelEntrada.add(new JLabel("Dia:"));
        comboBoxDiaSemana = new JComboBox<>(DiaDaSemana.values());
        painelEntrada.add(comboBoxDiaSemana);

        btnAdicionar = new JButton("Adicionar Tarefa");
        painelEntrada.add(btnAdicionar);
        add(painelEntrada, BorderLayout.NORTH);

        tabelaTarefas = new JTable(tableModel);
        tabelaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaTarefas.getTableHeader().setReorderingAllowed(false);
        
        tabelaTarefas.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabelaTarefas.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaTarefas.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabelaTarefas.getColumnModel().getColumn(3).setPreferredWidth(50);
        
        JScrollPane scrollPane = new JScrollPane(tabelaTarefas);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelAcoesEPontos = new JPanel(new BorderLayout(10,10));
        
        JPanel painelBotoesAcao = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelBotoesAcao.setBorder(BorderFactory.createTitledBorder("Ações na Tarefa Selecionada"));
        btnExecutada = new JButton("Marcar como Executada");
        btnParcialmente = new JButton("Marcar como Parcialmente Executada");
        btnRemover = new JButton("Remover Tarefa");
        
        painelBotoesAcao.add(btnExecutada);
        painelBotoesAcao.add(btnParcialmente);
        painelBotoesAcao.add(btnRemover);
        painelAcoesEPontos.add(painelBotoesAcao, BorderLayout.NORTH);

        JPanel painelPontuacao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelPontuacao.setBorder(BorderFactory.createTitledBorder("Pontuação"));
        lblPontuacaoTotal = new JLabel("Total Semanal: 0.0");
        painelPontuacao.add(lblPontuacaoTotal);
        
        painelPontuacao.add(new JLabel(" | Pontos do dia:"));
        comboBoxFiltroDiaPontos = new JComboBox<>(DiaDaSemana.values());
        painelPontuacao.add(comboBoxFiltroDiaPontos);
        lblPontuacaoDia = new JLabel("Dia: 0.0");
        painelPontuacao.add(lblPontuacaoDia);
        
        painelAcoesEPontos.add(painelPontuacao, BorderLayout.SOUTH);
        
        add(painelAcoesEPontos, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarTarefa());
        btnExecutada.addActionListener(e -> marcarTarefa(StatusTarefa.EXECUTADA));
        btnParcialmente.addActionListener(e -> marcarTarefa(StatusTarefa.EXECUTADA_PARCIALMENTE));
        btnRemover.addActionListener(e -> removerTarefa());
        
        comboBoxFiltroDiaPontos.addActionListener(e -> atualizarPontuacaoDiaSelecionado());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSaida();
            }
        });

        atualizarPontuacoes();
        setVisible(true);
    }

    private void adicionarTarefa() {
        String descricao = campoDescricao.getText().trim();
        DiaDaSemana dia = (DiaDaSemana) comboBoxDiaSemana.getSelectedItem();

        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A descrição da tarefa não pode estar vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dia == null) {
            JOptionPane.showMessageDialog(this, "Selecione um dia da semana.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tarefa novaTarefa = new Tarefa(descricao, dia);
        gerenciador.adicionarTarefa(novaTarefa);
        tableModel.adicionarTarefa(novaTarefa);
        campoDescricao.setText("");
        atualizarPontuacoes();
    }

    private void marcarTarefa(StatusTarefa novoStatus) {
        int selectedRow = tabelaTarefas.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tabelaTarefas.convertRowIndexToModel(selectedRow);
            Tarefa tarefa = gerenciador.getTarefa(modelRow);
            if (tarefa != null) {
                tarefa.setStatus(novoStatus);
                tableModel.atualizarTarefa(modelRow);
                atualizarPontuacoes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerTarefa() {
        int selectedRow = tabelaTarefas.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = tabelaTarefas.convertRowIndexToModel(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover a tarefa '" + gerenciador.getTarefa(modelRow).getDescricao() + "'?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                gerenciador.removerTarefa(modelRow);
                tableModel.removerTarefa(modelRow);
                atualizarPontuacoes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void atualizarPontuacoes() {
        lblPontuacaoTotal.setText(String.format("Total Semanal: %.1f", gerenciador.calcularPontuacaoTotalDaSemana()));
        atualizarPontuacaoDiaSelecionado();
    }

    private void atualizarPontuacaoDiaSelecionado() {
        DiaDaSemana diaSelecionado = (DiaDaSemana) comboBoxFiltroDiaPontos.getSelectedItem();
        if (diaSelecionado != null) {
            lblPontuacaoDia.setText(String.format("%s: %.1f", diaSelecionado.getNomeFormatado(), gerenciador.calcularPontuacaoPorDia(diaSelecionado)));
        } else if (gerenciador.getTodasAsTarefas().isEmpty()){
             lblPontuacaoDia.setText("Dia: 0.0");
        }
    }

    private void confirmarSaida() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja salvar as alterações antes de sair?",
                "Sair da Aplicação",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gerenciador.salvarTarefas();
            dispose();
            System.exit(0);
        } else if (confirm == JOptionPane.NO_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Não foi possível definir o Look and Feel do sistema: " + e);
        }

        SwingUtilities.invokeLater(() -> new AplicacaoGUI());
    }
}