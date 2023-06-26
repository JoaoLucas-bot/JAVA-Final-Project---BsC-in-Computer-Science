import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameFrame extends JFrame implements ActionListener, MouseListener {

    //Declaração das variáveis da classe GameFrame, necessárias para construção da GUI do jogo
    JFrame frame = new JFrame();
    JPanel gameBoardPanel;
    JPanel playerInfoPanel;
    JPanel cancelPanel;
    JLabel currentPlayerLabel;
    JLabel scoreLabel;
    JLabel player1Label;
    JLabel player2Label;
    JLabel player3Label;
    JLabel player4Label;
    JLabel player1ScoreLabel;
    JLabel player2ScoreLabel;
    JLabel player3ScoreLabel;
    JLabel player4ScoreLabel;
    JButton cancelButton;
    JRadioButton[] playerButton;
    JLabel[] playerScoreLabel;
    JLabel[][] gridLabel;
    JPanel currentPlayerScorePanel;
    JPanel radioButtonPanel;
    JPanel scorePanel;
    String currentPlayer;
    ButtonGroup radioButtonGroup;
    JButton resetButton;
    int scoreSize;
    HashMap<String, String> iconGridMap;
    List availableLabels;
    boolean humanPlayers;
    int sizeGrid;



    //Construtor da classe ConfigFrame. Aqui foram definidos valores para as variáveis declaradas na classe e definidas
    //as caracteristicas dos objetos da GUI
    GameFrame(int playerNumber, int gridSize, int scoreNumber, List selectedPlayers){

        //Configurar programa para fechar quando o utilizador clicar no botão de fechar a janela e colocar o layout a
        //null para podermos indicar as coordenadas, x e y, de onde queremos cada objeto na JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Atribuíção dos valores das variaveis da classe ConfigFrame
        gameBoardPanel = new JPanel();
        playerInfoPanel = new JPanel();
        cancelPanel = new JPanel();
        currentPlayerLabel = new JLabel("<html><br>Current<br>Player", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score", SwingConstants.CENTER);
        player1Label = new JLabel();
        player2Label = new JLabel();
        player3Label = new JLabel();
        player4Label = new JLabel();
        player1ScoreLabel = new JLabel("0", SwingConstants.RIGHT);
        player2ScoreLabel = new JLabel("0", SwingConstants.RIGHT);
        player3ScoreLabel = new JLabel("0", SwingConstants.RIGHT);
        player4ScoreLabel = new JLabel("0", SwingConstants.RIGHT);
        cancelButton = new JButton("Cancel");
        playerButton = new JRadioButton[playerNumber];
        gridLabel = new JLabel[gridSize][gridSize];
        playerScoreLabel = new JLabel[playerNumber];
        currentPlayerScorePanel = new JPanel();
        radioButtonPanel = new JPanel();
        scorePanel = new JPanel();
        radioButtonGroup = new ButtonGroup();
        resetButton = new JButton("Reset Grid");
        scoreSize = scoreNumber;
        iconGridMap = new HashMap<>();
        availableLabels = new ArrayList();
        humanPlayers = false;
        sizeGrid = gridSize;


//-----------------------------Game Grid Panel----------------------------------------

        //JPanel que irá conter a tabela do jogo e definido com o layout GridLayout para o correto posicionamento das
        //JLabels, conforme o tamanho definido pelo utilizador no slider numGridSlider da classe ConfigFrame.
        //Cada JLabel será uma posição onde o jogador poderá efetuar uma jogada
        gameBoardPanel.setBounds(25, 25, 500, 600);
        gameBoardPanel.setLayout(new GridLayout(gridSize, gridSize)); //Número de linhas e colunas será igual ao valor
                                                                      //da variavel gridSize

        //Ciclo for dentro de outro ciclo for para criação de uma matriz de JLabels, onde cada JLabel será uma posição
        //da tabela do jogo. Para facilitar a identificação das JLabels, o texto definido em cada uma é a sua posição
        //(x e y) na matriz de JLabels, desta forma foi posivel detetar que JLabel foi clicada e executar funções sobre
        //essa mesma JLabel.
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++){
                gridLabel[i][j] = new JLabel(i + " " + j, JLabel.CENTER);
                gridLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridLabel[i][j].setBackground(Color.WHITE);
                gridLabel[i][j].setOpaque(true);

                //Texto das JLabels colocado a 0 para que não seja apresentada aos users
                gridLabel[i][j].setFont(new Font(null, Font.PLAIN, 0));

                //Adicionado MouseListener a cada JLabel, para utilização do método mousePressed
                gridLabel[i][j].addMouseListener(this);

                //Adicionar texto de cada JLabel à variável availableLabels, que irá conter todas as JLabels disponíveis
                //para jogar. JLabels que sejam clicadas por jogadores, serão removidas desta lista
                availableLabels.add(gridLabel[i][j].getText());

                //Adicionar cada JLabel criada ao JPanel gameBoardPanel
                gameBoardPanel.add(gridLabel[i][j]);
            }
        }

//-----------------------------Game Grid Panel----------------------------------------


//-----------------------------Player Info Panel----------------------------------------
        //JPanel onde será apresentada informação sobre os jogadores em jogo, os seus pontos e que jogador tem a
        //proxima jogada.
        playerInfoPanel.setBounds(550, 25, 195, 295);
        playerInfoPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        playerInfoPanel.setLayout(new BorderLayout());

        //JPanel onde serão colocados os JRadioButtons com indicação dos jogadores em jogo e qual deve ser o próximo a
        //jogar. Este panel tem o GridLayout definido com 5 linhas e uma coluna onde, em cada linha, será colocado
        //um JRadioButton com cada jogador em jogo
        radioButtonPanel.setPreferredSize(new Dimension(100, 50));
        radioButtonPanel.setLayout(new GridLayout(5, 1, -5, -5));

        currentPlayerLabel.setPreferredSize(new Dimension(70, 50));

        //JLabel currentPlayerLabel adicionado já ao jPanel radioButtonPanel para que a mesma seja apresentada acima
        //dos JRadioButtons que serão adicionados mais há frente
        radioButtonPanel.add(currentPlayerLabel);

        //JPanel onde serão apresentados os pontos de cada jogador
        scorePanel.setPreferredSize(new Dimension(70, 50));
        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));


        scoreLabel.setPreferredSize(new Dimension(80, 45));

        //JLabel scoreLabel adicionada já ao JPanel scorePanel para que seja apresentado acima das JLabels que irão
        //mostrar os pontos de cada jogador
        scorePanel.add(scoreLabel);

        //Ciclo for para a criação dinâmica de JRadioButtons e JLabels, conforme o número de jogadores selecionados
        //nas JComboBoxes e no slider numPlayersSlider da ConfigFrame
        for(int p = 0; p < playerNumber; p++){

            //Adicionar novo JRadioButton à lista de JRadioButtons playerButton, onde o texto de cada JRadioButton terá
            //o número do jogador e o tipo de jogador selecionado nas JComboBoxes na ConfigFrame. Cada objeto estará
            //desabilitado por defeito
            playerButton[p] = new JRadioButton("P" + (p + 1) + " - " + selectedPlayers.get(p));
            playerButton[p].setEnabled(false);

            //Adicionar cada JRadioButton a um JRadioButtonGroup, visto que apenas um pode estar selecionado de cada
            //vez
            radioButtonGroup.add(playerButton[p]);
            radioButtonPanel.add(playerButton[p]);

            //Adicionar novas JLabels à lista de JLabels playerScoreLabel, cada uma com o texto 0 predefinido.
            //O texto de cada JLabel será atualizado sempre que for detetado que um jogador conseguiu pontuar.
            //A posição de cada JLabel na lista playerScoreLabel é equivalente à posição do seu jogador na lista
            //playerButton
            playerScoreLabel[p] = new JLabel("0", JLabel.RIGHT);
            playerScoreLabel[p].setBackground(Color.WHITE);
            playerScoreLabel[p].setOpaque(true);
            playerScoreLabel[p].setForeground(Color.GREEN);
            playerScoreLabel[p].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            playerScoreLabel[p].setFont(new Font(null, Font.BOLD, 40));
            playerScoreLabel[p].setVerticalTextPosition(JLabel.CENTER);
            playerScoreLabel[p].setPreferredSize(new Dimension(65, 45));
            //Adicionar cada JLabel de playerScoreLabel ao JPanel scorePanel
            scorePanel.add(playerScoreLabel[p]);

        }
        //JRadioButton do jogador primeiro jogador estará selecionado por defeito
        playerButton[0].setSelected(true);
        playerButton[0].setEnabled(true);

//-----------------------------Game Grid Panel----------------------------------------


//-----------------------------Cancel Button Panel----------------------------------------

        //JPanel para o botão de cancelamento do jogo e reset da tabela do jogo
        cancelPanel.setBounds(550, 430, 195, 195);
        cancelPanel.setLayout(null);
        cancelPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        //Botão de cancelamento do jogo com ActionListener que irá fechar a GameFrame e reabrir a ConfigFrame
        cancelButton.setBounds(50, 45, 100, 50);
        cancelButton.addActionListener(this);

        //Botão de reset da tabela de jogo com ActionListener que irá colocar o icon de cada JLabel da matriz a null e
        //colocar, também, cada JLabel na lista availableLabels
        resetButton.setBounds(50, 105, 100, 50);
        resetButton.addActionListener(this);
        resetButton.setVisible(false);

//-----------------------------Cancel Button Panel----------------------------------------

        //Adicionar objetos aos respetivos JPanels

        playerInfoPanel.add(radioButtonPanel, BorderLayout.WEST);
        playerInfoPanel.add(scorePanel, BorderLayout.EAST);


        cancelPanel.add(cancelButton);
        cancelPanel.add(resetButton);

        frame.add(playerInfoPanel);
        frame.add(cancelPanel);
        frame.add(gameBoardPanel);
        frame.setTitle("TicTacToe Game");
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setBackground(Color.lightGray);
        frame.setVisible(true);

        //Validação da presença de jogadores Humanos no jogo. Este ciclo irá verificar se pelo menos um dos
        //JRadioButtons dos jogadores contem a String "Human" onde, caso a condição se verifique, atribui o valor true
        //à variável humanPlayers. Caso humanPlayers se mantenha com o valor false, o botão de reset não será
        //apresentado quando a tabela de jogo estiver preenchida.
        for(JRadioButton i: playerButton){
            if (i.getText().contains("Human")){
                humanPlayers = true;
            }
        }

        //Efetuar jogada automática após iniciação da JFrame, no caso do primeiro jogador ser A3 Random (Bot)
        if(selectedPlayers.get(0).equals("A3 Random")){
            gameTurn(botPlay((ArrayList)availableLabels));
        }
    }

    //Método executado quando um dos JButtons cancelButton ou resetButton é pressionado. O cancelButton irá fechar a
    //GameFrame e abrir uma nova ConfigFrame, enquanto o resetButton irá correr o método resetGrid e colocar-se
    //invisível.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton){
            frame.dispose();
            ConfigFrame configFrame = new ConfigFrame();
        } else if(e.getSource() == resetButton){
            resetGrid();
            resetButton.setVisible(false);
        }
    }

    //Método implementado apenas devido à obrigatoriadade de implementação de todos os métodos da Interface
    //MouseListener.
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    //Método que irá correr quando o rato for pressionado sobre uma das JLabels da matriz de JLabels.
    //Será corrido o método gameTurn que irá receber a JLabel pressionada como parametro
    @Override
    public void mousePressed(MouseEvent e) {
        gameTurn((JLabel) e.getSource());
    }

    //Método implementado apenas devido à obrigatoriadade de implementação de todos os métodos da Interface
    //MouseListener.
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    //Método implementado apenas devido à obrigatoriadade de implementação de todos os métodos da Interface
    //MouseListener.
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    //Método implementado apenas devido à obrigatoriadade de implementação de todos os métodos da Interface
    //MouseListener.
    @Override
    public void mouseExited(MouseEvent e) {

    }

    //Método para a jogada automática do Bot A3 Random e será usada como parametro para o método gameTurn. Este método
    //irá selecionar aleatóriamente uma posição da lista availableLabels, que contém as coordenadas de todas as JLabels
    //disponiveis e retorna a JLabel correspondente a essas coordenadas. A JLabel selecionada será depois utilizada
    //pelo método gameTurn que irá efetuar a jogada.
    public JLabel botPlay(ArrayList availableLabels){
        String[] selectedLabel;
        try {
            selectedLabel = availableLabels.get(ThreadLocalRandom.current().nextInt(0, availableLabels.size())).toString().split(" ");
        } catch (Exception e){
            selectedLabel = new String[]{"0", "0"};
        }
        return gridLabel[Integer.parseInt(selectedLabel[0])][Integer.parseInt(selectedLabel[1])];
    }

    //Método de execução da jogada efetuada pelo jogador humano ou bot. O mesmo recebe uma JLabel da matriz de JLabels
    //como parametro de entrada e efetua todas as operações necessárias, como alteração de icons e validação de linhas,
    //com base na JLabel clicada.
    public void gameTurn(JLabel clickedLabel){
        //Validar se a JLabel clicada não tem um icone definido. Caso a condição seja verdadeira, a coordenada da JLabel
        //será retirada da lista availableLabels e será iniciado o ciclo for. Caso seja falso, o método irá terminar sem
        //efetuar qualquer outra operação.
        if(clickedLabel.getIcon() == null){
            availableLabels.remove(clickedLabel.getText());
            for(int b = 0; b < playerButton.length; b++){

                //Validar que jogador efetuou a jogada, verificando que JRadioButton se encontra ativo.
                if(playerButton[b].isSelected()){
                    switch (b){
                        //Caso o JRadioButton no index 0 se encontre ativo, a jogada foi efetuada pelo jogador P1.
                        //Será adicionado ao HashMap iconGridMap o mapeamento entre as coordenadas da JLabel selecionada
                        //e o caminho da imagem de "X", o icone da JLabel selecionada será alterado para "X" e o
                        //JRadioButton do jogador P1 será desativado.
                        case 0:
                            iconGridMap.put(clickedLabel.getText(), "images\\x_mark.png");
                            gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].setIcon(new ImageIcon(new ImageIcon("images\\x_mark.png").getImage().getScaledInstance(gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getWidth(),gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getHeight(), Image.SCALE_DEFAULT)));
                            playerButton[b].setEnabled(false);
                            break;

                        //Caso o JRadioButton no index 1 se encontre ativo, a jogada foi efetuada pelo jogador P2.
                        //Será adicionado ao HashMap iconGridMap o mapeamento entre as coordenadas da JLabel selecionada
                        //e o caminho da imagem de "circulo", o icone da JLabel selecionada será alterado para "circulo"
                        // e o JRadioButton do jogador P2 será desativado.
                        case 1:
                            iconGridMap.put(clickedLabel.getText(), "images\\circle_mark.png");
                            gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].setIcon(new ImageIcon(new ImageIcon("images\\circle_mark.png").getImage().getScaledInstance(gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getWidth(),gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getHeight(), Image.SCALE_DEFAULT)));
                            playerButton[b].setEnabled(false);
                            break;

                        //Caso o JRadioButton no index 2 se encontre ativo, a jogada foi efetuada pelo jogador P3.
                        //Será adicionado ao HashMap iconGridMap o mapeamento entre as coordenadas da JLabel selecionada
                        //e o caminho da imagem de "triangulo", o icone da JLabel selecionada será alterado para
                        // "triangulo" e o JRadioButton do jogador P3 será desativado.
                        case 2:
                            iconGridMap.put(clickedLabel.getText(), "images\\triangle.png");
                            gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].setIcon(new ImageIcon(new ImageIcon("images\\triangle.png").getImage().getScaledInstance(gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getWidth(),gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getHeight(), Image.SCALE_DEFAULT)));
                            playerButton[b].setEnabled(false);
                            break;

                        //Caso o JRadioButton no index 3 se encontre ativo, a jogada foi efetuada pelo jogador P4.
                        //Será adicionado ao HashMap iconGridMap o mapeamento entre as coordenadas da JLabel selecionada
                        //e o caminho da imagem de "quadrado", o icone da JLabel selecionada será alterado para
                        // "quadrado" e o JRadioButton do jogador P4 será desativado.
                        case 3:
                            iconGridMap.put(clickedLabel.getText(), "images\\square.png");
                            gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].setIcon(new ImageIcon(new ImageIcon("images\\square.png").getImage().getScaledInstance(gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getWidth(),gridLabel[Integer.parseInt(clickedLabel.getText().split(" ")[0])][Integer.parseInt(clickedLabel.getText().split(" ")[1])].getHeight(), Image.SCALE_DEFAULT)));
                            playerButton[b].setEnabled(false);
                            break;
                    }

                    //Validar se o jogador pontuou na linha da JLabel selecionada. Caso o método retorne "true", o texto
                    //da JLabel com a pontuação do jogador é atualizada para o valor atual + 1.
                    List result = verifyRowScored(clickedLabel);
                    if(result.get(0).equals(true)){
                        System.out.println("Row Scored, adding 1 point + " + result.get(1) + " extra");
                        playerScoreLabel[b].setText(String.valueOf(Integer.parseInt(playerScoreLabel[b].getText()) + 1 + (int) result.get(1)));
                    }

                    //Validar se o jogador pontuou na coluna da JLabel selecionada. Caso o método retorne "true", o
                    //texto da JLabel com a pontuação do jogador é atualizada para o valor atual + 1.
                    result = verifyColumnScored(clickedLabel);
                    if(result.get(0).equals(true)){
                        System.out.println("Column Scored, adding 1 point + " + result.get(1) + " extra");
                        playerScoreLabel[b].setText(String.valueOf(Integer.parseInt(playerScoreLabel[b].getText()) + 1 + (int) result.get(1)));
                    }

                    //Validar se o jogador pontuou na diagonal principal da JLabel selecionada. Caso o método retorne
                    //"true", o texto da JLabel com a pontuação do jogador é atualizada para o valor atual + 1.
                    result = verifyPDiagScored(clickedLabel);
                    if(result.get(0).equals(true)){
                        System.out.println("PDiag Scored, adding 1 point + " + result.get(1) + " extra");

                        playerScoreLabel[b].setText(String.valueOf(Integer.parseInt(playerScoreLabel[b].getText()) + 1 + (int) result.get(1)));
                    }

                    //Validar se o jogador pontuou na diagonal secundária da JLabel selecionada. Caso o método retorne
                    //"true", o texto da JLabel com a pontuação do jogador é atualizada para o valor atual + 1.
                    result = verifySDiagScored(clickedLabel);
                    if(result.get(0).equals(true)){
                        System.out.println("SDiag Scored, adding 1 point + " + result.get(1) + " extra");

                        playerScoreLabel[b].setText(String.valueOf(Integer.parseInt(playerScoreLabel[b].getText()) + 1 + (int) result.get(1)));
                    }

                    //Verificar se a lista availableLabels se encontra vazia e se existem jogadores humanos em jogo.
                    //O JButton resetButton ficará disponível caso ambas as condições sejam verdadeiras.
                    if(availableLabels.size() == 0 && humanPlayers){
                        resetButton.setVisible(true);
                    }

                    //Validar quem será o próximo jogador, ativando o JRadioButton que se segue na lista playerButton.
                    //Caso o JRadioButton que se encontra ativo seja o último da lista, será ativado o JRadioButton do
                    //index 0 da lista playerButton.
                    if(b == playerButton.length - 1){
                        playerButton[0].setSelected(true);
                        playerButton[0].setEnabled(true);
                        currentPlayer = playerButton[0].getText();
                        //Caso o jogador seguinte seja um bot e ainda existam JLabels disponíveis na tabela do jogo,
                        //efetuar jogada automática.
                        if(currentPlayer.contains("A3 Random") && availableLabels.size() > 0){
                            gameTurn(botPlay((ArrayList)availableLabels));
                        }
                    } else {
                        playerButton[b+1].setEnabled(true);
                        playerButton[b+1].setSelected(true);
                        currentPlayer = playerButton[b + 1].getText();
                        if(currentPlayer.contains("A3 Random") && availableLabels.size() > 0){
                            gameTurn(botPlay((ArrayList)availableLabels));
                        }
                    }

                    break;
                }
            }
        }
    }

    //Método de validação da linha da JLabel selecionada. A partir das coordenadas da JLabel, é efetuada forward lookup
    //e backwards lookup, ambas sequenciais, onde cada uma para apenas quando uma das seguintes situações é detetada:
    //É encontrada uma JLabel na linha que não contem um icone igual ao da JLabel selecionada;
    //A pesquisa chega ao início da linha (no caso da backwards lookup);
    //A pesquisa chega ao final da linha (no caso do forward lookup).
    public List verifyRowScored(JLabel clickedLabel) {
        //Criação de array de int com o resultado do split do texto da JLabel. Irá devolver as coordenadas x e y da JLabel.
        int[] rowColumn = {Integer.parseInt(clickedLabel.getText().split(" ")[0]), Integer.parseInt(clickedLabel.getText().split(" ")[1])};

        //Variáveis de estado das pesquisas. O valor de uma ou ambas as variaveis será alterado para false caso seja
        //detetada uma ou mais situações indicadas anteriormente, o que fará com que essa pesquisa não seja novamente
        //efetuada durante a restante execução do método.
        boolean fRowState = true;
        boolean bRowState = true;
        int extraScore = 0;

        //ArrayList de JLabels que irá conter todas as JLabels encontradas, cujo o icone definido é igual ao icone da
        //Jlabel selecionada.
        ArrayList<JLabel> rowSequence = new ArrayList<>();

        for (int i = 1; i < sizeGrid; i++) {

            //Forward Lookup da linha. Caso não tenhamos ultrapassado a margem da tabela de jogo e fRowState sejam
            //"true", é validado se a JLabel na tabela de jogo tem icone definido e se na HashMap iconGridMap, o
            //icone da coordenada da nova JLabel é igual ao icone da coordenada da JLabel selecionada. Se sim, a nova
            //JLabel pesquisada é adicionada à lista de JLabels rowSequence.
            if(rowColumn[1] + i < gridLabel.length && fRowState){
                if(gridLabel[rowColumn[0]][rowColumn[1] + i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0]][rowColumn[1] + i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    rowSequence.add(gridLabel[rowColumn[0]][rowColumn[1] + i]);
                } else{
                    fRowState = false;
                }
            }
            //Backwards Lookup da linha. Efetua o mesmo procedimento que a forward lookup mas no sentido inverso.
            if(rowColumn[1] - i >= 0 && bRowState){
                if(gridLabel[rowColumn[0]][rowColumn[1] - i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0]][rowColumn[1] - i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    rowSequence.add(gridLabel[rowColumn[0]][rowColumn[1] - i]);
                } else{
                    bRowState = false;
                }
            }
        }
        //Caso rowSequence contenha um valor igual ou maior de JLabels que o definido no slider numWinSlider na
        //ConfigFrame, executar o método changeSequenceColor. Enviadas as variáveis rowSequence e clickedLabel como
        //parametros de entrada.
        if(rowSequence.size() + 1 >= scoreSize){
            extraScore = extraPoints(rowSequence);
            changeSequenceColor(rowSequence, clickedLabel);
        }
        //Returnar "true" ou "false" conforme o resultado da validação entre o tamanho de rowSequence e o valor de
        //scoreSize.
        List results = new ArrayList();
        results.add(rowSequence.size() + 1 >= scoreSize);
        results.add(extraScore);
        return results;
    }

    //Executados os mesmos processos que em verifyRowScored() mas para a coluna.
    public List verifyColumnScored(JLabel clickedLabel){
        int[] rowColumn = {Integer.parseInt(clickedLabel.getText().split(" ")[0]), Integer.parseInt(clickedLabel.getText().split(" ")[1])};
        boolean fColumnState = true;
        boolean bColumnState = true;
        ArrayList<JLabel> columnSequence = new ArrayList<>();
        int extraScore = 0;

        for (int i = 1; i < sizeGrid; i++) {
            //Forward Row Lookup
            if(rowColumn[0] + i < gridLabel.length && fColumnState){
                if(gridLabel[rowColumn[0] + i][rowColumn[1]].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] + i][rowColumn[1]].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    columnSequence.add(gridLabel[rowColumn[0] + i][rowColumn[1]]);
                } else{
                    fColumnState = false;
                }
            }
            //Backward Row Lookup
            if(rowColumn[0] - i >= 0 && bColumnState){
                if(gridLabel[rowColumn[0] - i][rowColumn[1]].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] - i][rowColumn[1]].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    columnSequence.add(gridLabel[rowColumn[0] - i][rowColumn[1]]);
                } else{
                    bColumnState = false;
                }
            }
        }
        if(columnSequence.size() + 1 >= scoreSize){
            extraScore = extraPoints(columnSequence);
            changeSequenceColor(columnSequence, clickedLabel);
        }
        List results = new ArrayList();
        results.add(columnSequence.size() + 1 >= scoreSize);
        results.add(extraScore);
        return results;
    }

    //Executados os mesmos processos que em verifyRowScored() mas para a diagonal principal.
    public List verifyPDiagScored(JLabel clickedLabel){
        int[] rowColumn = {Integer.parseInt(clickedLabel.getText().split(" ")[0]), Integer.parseInt(clickedLabel.getText().split(" ")[1])};
        boolean fPDiagState = true;
        boolean bPDiagState = true;
        ArrayList<JLabel> pDiagSequence = new ArrayList<>();
        int extraScore = 0;

        for (int i = 1; i < sizeGrid; i++) {

            //Forward Row Lookup
            if((rowColumn[0] + i < gridLabel.length && rowColumn[1] + i < gridLabel.length) && fPDiagState){
                if(gridLabel[rowColumn[0] + i][rowColumn[1] + i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] + i][rowColumn[1] + i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    pDiagSequence.add(gridLabel[rowColumn[0] + i][rowColumn[1] + i]);
                } else{
                    fPDiagState = false;
                }
            }
            //Backward Row Lookup
            if((rowColumn[0] - i  >= 0 && rowColumn[1] - i >= 0) && bPDiagState){
                if(gridLabel[rowColumn[0] - i][rowColumn[1] - i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] - i][rowColumn[1] - i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    pDiagSequence.add(gridLabel[rowColumn[0] - i][rowColumn[1] - i]);
                } else{
                    bPDiagState = false;
                }
            }
        }
        if(pDiagSequence.size() + 1 >= scoreSize){
            extraScore = extraPoints(pDiagSequence);
            changeSequenceColor(pDiagSequence, clickedLabel);
        }
        List results = new ArrayList();
        results.add(pDiagSequence.size() + 1 >= scoreSize);
        results.add(extraScore);
        return results;
    }

    //Executados os mesmos processos que em verifyRowScored(), mas para a diagonal secundária.
    public List verifySDiagScored(JLabel clickedLabel){
        int[] rowColumn = {Integer.parseInt(clickedLabel.getText().split(" ")[0]), Integer.parseInt(clickedLabel.getText().split(" ")[1])};
        boolean fSDiagState = true;
        boolean bSDiagState = true;
        ArrayList<JLabel> sDiagSequence = new ArrayList<>();
        int extraScore = 0;

        for (int i = 1; i < sizeGrid; i++) {

            //Forward Row Lookup
            if((rowColumn[0] + i < gridLabel.length && rowColumn[1] - i >= 0) && fSDiagState){
                if(gridLabel[rowColumn[0] + i][rowColumn[1] - i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] + i][rowColumn[1] - i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    sDiagSequence.add(gridLabel[rowColumn[0] + i][rowColumn[1] - i]);
                } else{
                    fSDiagState = false;
                }
            }
            //Backward Row Lookup
            if((rowColumn[0] - i  >= 0 && rowColumn[1] + i < gridLabel.length) && bSDiagState){
                if(gridLabel[rowColumn[0] - i][rowColumn[1] + i].getIcon() != null && (iconGridMap.get(gridLabel[rowColumn[0] - i][rowColumn[1] + i].getText()).equals(iconGridMap.get(clickedLabel.getText())))){
                    sDiagSequence.add(gridLabel[rowColumn[0] - i][rowColumn[1] + i]);
                } else{
                    bSDiagState = false;
                }
            }
        }
        if(sDiagSequence.size() + 1 >= scoreSize){
            extraScore = extraPoints(sDiagSequence);
            changeSequenceColor(sDiagSequence, clickedLabel);
        }
        List results = new ArrayList();
        results.add(sDiagSequence.size() + 1 >= scoreSize);
        results.add(extraScore);
        return results;
    }

    //Método executado após ser o botão resetButton ser clicado. Efetuado um ciclo for dentro de outro ciclo for que
    //irão percorrer a matriz de JLabels, alterando os icones de cada JLabel para null. As coordenadas de cada JLabel
    //são também repostas na lista availableLabels. Caso, no momento em que o botão resetButton é pressionado, o próximo
    //jogador seja A3 Random, é efetuada uma jogada automática.
    public void resetGrid(){
        for(int i = 0; i < gridLabel.length; i++){
            for(int j = 0; j < gridLabel.length; j++){
                gridLabel[i][j].setIcon(null);
                gridLabel[i][j].setBackground(null);
                availableLabels.add(gridLabel[i][j].getText());
            }
        }
        if(currentPlayer.contains("A3 Random")){
            gameTurn(botPlay((ArrayList)availableLabels));
        }
    }

    //Método para validar se a sequência efetuada pelo jogador, é maior que o valor definido para pontuar.
    //Se sim, são atribuídos os pontos extra por cada campo a mais. Não são atribuídos pontos por campos que já
    //pontuaram.
    public int extraPoints(ArrayList<JLabel> sequence) {
        List<String> coloredBg = new ArrayList<>();
        List<String> nonColoredBg = new ArrayList<>();
        System.out.println("Sequence size: " + sequence.size());

        for (JLabel i : sequence) {
            if (!i.getBackground().toString().equals("java.awt.Color[r=255,g=255,b=255]")) {
                coloredBg.add(i.getText());
            } else {
                nonColoredBg.add(i.getText());
            }
        }

        System.out.println("Colored = " + coloredBg.size());
        System.out.println("nonColored = " + nonColoredBg.size());

        if (coloredBg.size() == 0 && nonColoredBg.size() == scoreSize - 1) {
            System.out.println(0);
            return 0;
        } else if (coloredBg.size() > scoreSize) {
            System.out.println(1);
            return nonColoredBg.size();
        } else if (coloredBg.size() == 0) {
            System.out.println(2);
            System.out.println("Result: " + (nonColoredBg.size() - (scoreSize - 1)));
            return nonColoredBg.size() - (scoreSize - 1);
        } else if (coloredBg.size() == scoreSize) {
            System.out.println(3);
            return nonColoredBg.size();
        } else {
            System.out.println(4);
            return nonColoredBg.size() - (scoreSize - coloredBg.size() - 1);
        }
    }


    //Método para alteração do background das JLabels recebidas pelo parametro de entrada sequence. Este método é
    //executado quando é detatada uma sequência de JLabels da tabela de jogo com tamanho igual ou maior que o definido
    //nas configurações, e com o mesmo icone definido. As cores serão diferentes dependendo do jogador que efetuou a
    //jogada.
    public void changeSequenceColor(ArrayList<JLabel> sequence, JLabel clickedLabel){

        for(JLabel i: sequence){
            int[] rowColumn = {Integer.parseInt(i.getText().split(" ")[0]), Integer.parseInt(i.getText().split(" ")[1])};
            if(playerButton[0].isSelected()){
                gridLabel[rowColumn[0]][rowColumn[1]].setBackground(Color.red.darker());
                clickedLabel.setBackground(Color.RED.darker());
            } else if(playerButton[1].isSelected()){
                gridLabel[rowColumn[0]][rowColumn[1]].setBackground(Color.GREEN.darker());
                clickedLabel.setBackground(Color.GREEN.darker());
            } else if(playerButton[2].isSelected()){
                gridLabel[rowColumn[0]][rowColumn[1]].setBackground(Color.BLUE.darker());
                clickedLabel.setBackground(Color.BLUE.darker());
            } else if(playerButton[3].isSelected()) {
                gridLabel[rowColumn[0]][rowColumn[1]].setBackground(Color.YELLOW.darker());
                clickedLabel.setBackground(Color.YELLOW.darker());
            }
        }
    }
}
