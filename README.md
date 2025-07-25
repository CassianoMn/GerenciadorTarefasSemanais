# Gerenciador de Tarefas Semanal

Este é um projeto de um Gerenciador de Tarefas Semanal desenvolvido em Java com uma interface gráfica (GUI) utilizando a biblioteca Swing. A aplicação permite que o usuário adicione, remova e atualize o status de suas tarefas diárias. O sistema calcula uma pontuação baseada no status das tarefas, incentivando a produtividade.

As tarefas são salvas em um arquivo local (`tarefas.dat`) para que não sejam perdidas ao fechar a aplicação.

## Funcionalidades

  * **Adicionar Tarefas**: Permite criar novas tarefas com uma descrição e um dia da semana.
  * **Visualização em Tabela**: Exibe todas as tarefas de forma organizada, mostrando descrição, dia, status e pontuação.
  * **Atualização de Status**: É possível marcar uma tarefa como "Executada" (vale 1.0 ponto), "Executada Parcialmente" (0.5 pontos) ou "Pendente" (0.0 pontos).
  * **Remoção de Tarefas**: Apaga tarefas selecionadas da lista.
  * **Cálculo de Pontuação**:
      * Mostra a pontuação total acumulada na semana.
      * Permite selecionar um dia específico para ver a pontuação daquele dia.
  * **Persistência de Dados**: Salva a lista de tarefas ao fechar o programa e a carrega novamente ao ser iniciado.

## Tecnologias Utilizadas

  * **Java**: Linguagem de programação principal.
  * **Java Swing**: Biblioteca utilizada para a criação da interface gráfica do usuário (GUI).

## Como Executar

Para compilar e executar o projeto, você precisará ter o **JDK (Java Development Kit)** instalado em sua máquina.

1.  **Estrutura de Pastas**: Certifique-se de que todos os arquivos `.java` estejam dentro de uma pasta chamada `gerenciadortarefas`, para corresponder à declaração `package` no código.

2.  **Compilação**: Abra um terminal ou prompt de comando na pasta **raiz** do projeto (a pasta que contém a subpasta `gerenciadortarefas`) e execute o seguinte comando para compilar todos os arquivos:

    ```bash
    javac gerenciadortarefas/*.java
    ```

3.  **Execução**: Após a compilação ser concluída sem erros, execute a aplicação com o comando abaixo, que chama a classe principal `AplicacaoGUI`:

    ```bash
    java gerenciadortarefas.AplicacaoGUI
    ```

A janela do "Gerenciador de Tarefas Semanal" deverá aparecer.

## Estrutura do Projeto

O projeto é dividido nas seguintes classes, cada uma com sua responsabilidade:

  * `AplicacaoGUI.java`: Classe principal que constrói e gerencia a janela e todos os componentes da interface gráfica.
  * `GerenciadorDeTarefas.java`: Responsável pela lógica de negócio, como adicionar, remover e calcular pontos das tarefas, além de salvá-las e carregá-las do arquivo.
  * `Tarefa.java`: Classe de modelo (POJO) que representa uma única tarefa, com seus atributos (descrição, dia, status).
  * `TarefaTableModel.java`: Adaptador que conecta a lista de tarefas com o componente `JTable` da interface, permitindo que os dados sejam exibidos corretamente.
  * `DiaDaSemana.java`: Enumeração que define os dias da semana de forma segura e organizada.
  * `StatusTarefa.java`: Enumeração para os status possíveis de uma tarefa e seus pontos correspondentes.
