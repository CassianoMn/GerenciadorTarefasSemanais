package gerenciadortarefas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeTarefas {
    private List<Tarefa> tarefas;
    private final String NOME_ARQUIVO = "tarefas.dat";

    public GerenciadorDeTarefas() {
        this.tarefas = new ArrayList<>();
        carregarTarefas();
    }

    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
    }

    public Tarefa getTarefa(int indice) {
        if (indice >= 0 && indice < tarefas.size()) {
            return tarefas.get(indice);
        }
        return null;
    }
    
    public List<Tarefa> getTodasAsTarefas() {
        return tarefas;
    }

    public void removerTarefa(int indice) {
        if (indice >= 0 && indice < tarefas.size()) {
            tarefas.remove(indice);
        }
    }
    
    public double calcularPontuacaoTotalDaSemana() {
        return tarefas.stream().mapToDouble(Tarefa::getPontuacao).sum();
    }

    public double calcularPontuacaoPorDia(DiaDaSemana dia) {
        return tarefas.stream()
                      .filter(t -> t.getDiaDaSemana() == dia)
                      .mapToDouble(Tarefa::getPontuacao)
                      .sum();
    }

    @SuppressWarnings("unchecked")
    public void carregarTarefas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                this.tarefas = (List<Tarefa>) obj;
                System.out.println("Tarefas carregadas de " + NOME_ARQUIVO);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de tarefas ("+NOME_ARQUIVO+") não encontrado. Começando com lista vazia.");
            this.tarefas = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar tarefas: " + e.getMessage());
            e.printStackTrace();
            this.tarefas = new ArrayList<>();
        }
    }

    public void salvarTarefas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(this.tarefas);
            System.out.println("Tarefas salvas em " + NOME_ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}