import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ConfigFrame extends JFrame implements ActionListener, ChangeListener {
    //Declaração das variáveis da classe ConfigFrame, necessárias para construção da GUI das configurações do jogo
    JFrame frame = new JFrame();
    JPanel numTypePlayersPanel;
    JPanel sizeOptionPanel;
    JPanel gameOptionPanel;
    JPanel startPanel;
    JButton startButton;
    JPanel numPlayersPanel;
    JPanel typePlayersPanel;
    JSlider numPlayersSlider;
    JLabel numPlayerLabel;
    JLabel gridLabel;
    JLabel winLabel;
    JLabel numWinLabel;
    JLabel numGridLabel;
    JLabel typeLabel;
    JSlider numGridSlider;
    JSlider numWinSlider;
    JComboBox playerComboBox1;
    JComboBox playerComboBox2;
    JComboBox playerComboBox3;
    JComboBox playerComboBox4;
    JPanel gridPanel;
    JPanel winPanel;
    JRadioButton normalButton;
    JRadioButton misereButton;
    JRadioButton randomButton;
    int playerNumber;
    int gridSize;
    int winNumber;
    String[] playerOption;
    List selectedPlayers;
    ButtonGroup radioButtonGroup;


    //Construtor da classe ConfigFrame. Aqui foram definidos valores para as variáveis declaradas na classe e definidas
    //as caracteristicas dos objetos da GUI
    ConfigFrame(){
        //Configurar programa para fechar quando o utilizador clicar no botão de fechar a janela e colocar o layout a
        //null para podermos indicar as coordenadas, x e y, de onde queremos cada objeto na JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //Atribuíção dos valores das variaveis da classe ConfigFrame
        numTypePlayersPanel = new JPanel();
        numPlayersPanel = new JPanel();
        typePlayersPanel = new JPanel();
        sizeOptionPanel = new JPanel();
        gameOptionPanel = new JPanel();
        startPanel = new JPanel();
        startButton = new JButton("Start");
        numPlayersSlider = new JSlider(2, 4, 2);
        numPlayerLabel = new JLabel("2", SwingConstants.CENTER);
        gridLabel = new JLabel("Grid", SwingConstants.CENTER);
        winLabel = new JLabel("Line Size", SwingConstants.CENTER);
        numWinLabel = new JLabel("3");
        numGridLabel = new JLabel("3x3", SwingConstants.CENTER);
        numGridSlider = new JSlider(3, 9, 3);
        numWinSlider = new JSlider(3, 3, 3);
        typeLabel = new JLabel("Type", SwingConstants.CENTER);
        playerOption = new String[] {"Human", "A3 Random"};
        playerComboBox1 = new JComboBox(playerOption);
        playerComboBox2 = new JComboBox(playerOption);
        playerComboBox3 = new JComboBox(playerOption);
        playerComboBox4 = new JComboBox(playerOption);
        gridPanel = new JPanel();
        winPanel = new JPanel();
        normalButton = new JRadioButton("Normal");
        misereButton = new JRadioButton("Misère");
        randomButton = new JRadioButton("Random Turn");
        playerNumber = 2;
        gridSize = 3;
        winNumber = 1;
        selectedPlayers = new ArrayList();
        radioButtonGroup = new ButtonGroup();

//-----------------------Players Panel-------------------------------------------------------------------------

        //Definição do JPanel com o titulo "Players" e com o layout "BorderLayout" onde serão colocados mais dois
        //JPanels encostados à esquerda e à direita (WEST e EAST)
        numTypePlayersPanel.setBounds(0, 0, 300, 300);
        numTypePlayersPanel.setBorder(BorderFactory.createTitledBorder("Players"));
        numTypePlayersPanel.setLayout(new BorderLayout(10, 10));

        //Sub-painel dentro do JPanel "numTypePlayersPanel" onde iremos colocar um JSlider para seleção do número de
        //jogadores que irão participar no jogo e uma JLabel que irá indicar o número de jogadores selecionados no
        //JSlider
        numPlayersPanel.setPreferredSize(new Dimension(125, 150));
        numPlayersPanel.setLayout(new BorderLayout());
        numPlayersPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        //JLabel com o número de jogadores selecionados no JSlider. O texto definido nesta JLabel será atualizado na
        //função stateChanged()
        numPlayerLabel.setFont(new Font("Consolas", Font.BOLD, 20));
        numPlayerLabel.setPreferredSize(new Dimension(100, 75));

        //Configuração da aparencia do JSlider e atribuição de ChangeListener para atualização da JLabel numPlayerLabel
        //sempre que o utilizador alterar o JSlider. O valor minimo de 2 e o valor máximo de 4 do JSlider foram
        //definidos na declaração da variável
        numPlayersSlider.setPaintTicks(true);
        numPlayersSlider.setPaintTrack(true);
        numPlayersSlider.setOrientation(SwingConstants.VERTICAL);
        numPlayersSlider.setMajorTickSpacing(1);
        numPlayersSlider.addChangeListener(this);

        //Sub-painel que irá conter 4 JComboBox onde o utilizador irá indicar se os jogadores serão humanos ou bots
        typePlayersPanel.setPreferredSize(new Dimension(160, 150));
        typePlayersPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        //Definição da Layout "GridLayout" para facilitar a organização dos objetos no JPanel typePlayersPanel.
        //A grid terá apenas uma coluna e 5 linhas, onde será colocado um objeto em cada linha com uma distancia de
        //10px entre cada um
        typePlayersPanel.setLayout(new GridLayout(5, 1, 10, 10));

        //Colocar as JComboBox dos jogadores 3 e 4 desabilitadas ao inicarmos o programa, visto que o JSlider com o
        //número de jogadores inicia com o valor 2
        playerComboBox3.setEnabled(false);
        playerComboBox4.setEnabled(false);

//-----------------------Players Panel-------------------------------------------------------------------------


//-----------------------Size Panel-------------------------------------------------------------------------
        //JPanel com o titulo "Size" que irá conter os objetos para o utilizador configurar o tamanho da tabela de
        //jogo e o tamanho da linha necessário para cada jogador pontuar
        sizeOptionPanel.setBounds(300, 0, 300, 300);
        sizeOptionPanel.setBorder(BorderFactory.createTitledBorder("Size"));
        sizeOptionPanel.setLayout(new BorderLayout());

        //Sub-painel que irá conter o JSlider que define o tamanho da tabela de jogo e uma JLabel com a indicação do
        //valor seleconado no JSlider.
        gridPanel.setPreferredSize(new Dimension(150, 150));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        gridPanel.setLayout(new BorderLayout());

        //JLabel que indica o valor selecionado no JSlider. Definido o tipo de letra "Consolas", a negrito, com o
        //tamanho de letra 25
        numGridLabel.setFont(new Font("Consolas", Font.BOLD, 25));
        numGridLabel.setPreferredSize(new Dimension(100, 100));

        //JSlider que define o tamanho da tabela de jogo. Slider colocado na vertical com o método setOrientation
        //e adicionado um "ChangeListener" para que a JLabel numGridLabel seja atualizada com o valor selecionado pelo
        //utilizador no JSlider.
        numGridSlider.setPaintTicks(true);
        numGridSlider.setPaintTrack(true);
        numGridSlider.setOrientation(SwingConstants.VERTICAL);
        numGridSlider.setMajorTickSpacing(1);
        numGridSlider.addChangeListener(this);

        //Sub-painel que irá conter os objetos necessários para definirmos o tamanho da linha para pontuar
        winPanel.setPreferredSize(new Dimension(125, 125));
        winPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        winPanel.setLayout(new BorderLayout());

        //JSlider que irá definir o tamanho de linha necessário para um jogador pontuar. Adicionado um ChangeListener
        //que atualiza o valor do JLabel que apresenta o valor selecionado no JSlider
        numWinSlider.setPaintTicks(true);
        numWinSlider.setPaintTrack(true);
        numWinSlider.setOrientation(SwingConstants.VERTICAL);
        numWinSlider.setMajorTickSpacing(1);
        numWinSlider.addChangeListener(this);

        //JLabel que apresenta o valor selecionado no JSlider
        numWinLabel.setFont(new Font("Consolas", Font.BOLD, 25));
        numWinLabel.setPreferredSize(new Dimension(50, 100));

//-----------------------Size Panel-------------------------------------------------------------------------


//----------------------Game Options Panel-----------------------------------------------------------------

        //JPanel, com o titulo Game Options, que irá conter os JRadioButton para seleção do modo de jogo.
        gameOptionPanel.setBounds(0, 300, 300, 300);
        gameOptionPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionPanel.setLayout(new GridLayout(3, 1, -50, -50));

        //Adicionar JRadioButton de cada modo de jogo a um JRadioButtonGroup para que apenas um dos botões possa estar
        //selecionado
        radioButtonGroup.add(normalButton);
        radioButtonGroup.add(misereButton);
        radioButtonGroup.add(randomButton);

        //Mostrar modo normal selecionado ao iniciarmos o jogo. Restantes modos não disponíveis
        normalButton.setSelected(true);
        misereButton.setEnabled(false);
        randomButton.setEnabled(false);


//----------------------Game Options Panel-----------------------------------------------------------------

//----------------------Start Panel-------------------------------------------------------------------------

        //JPanel para botão de iniciar o jogo
        startPanel.setBounds(300, 300, 300, 300);
        startPanel.setLayout(null);

        //Botão iniciar com ActionListener para inicializar a frame do jogo
        startButton.setBounds(75, 120, 150, 50);
        startButton.addActionListener(this);


//----------------------Start Panel-------------------------------------------------------------------------

        //Adicionar objetos ao JPanel gameOptionPanel
        gameOptionPanel.add(normalButton);
        gameOptionPanel.add(misereButton);
        gameOptionPanel.add(randomButton);

        //Adicionar objetos ao JPanel winPanel
        winPanel.add(numWinLabel, BorderLayout.EAST);
        winPanel.add(numWinSlider, BorderLayout.WEST);
        winPanel.add(winLabel, BorderLayout.NORTH);

        //Adicionar objetos ao JPanel gridPanel
        gridPanel.add(numGridLabel, BorderLayout.EAST);
        gridPanel.add(numGridSlider, BorderLayout.WEST);
        gridPanel.add(gridLabel, BorderLayout.NORTH);

        //Adicionar objetos ao JPanel sizeOptionPanel
        sizeOptionPanel.add(winPanel, BorderLayout.EAST);
        sizeOptionPanel.add(gridPanel, BorderLayout.WEST);

        //Adicionar objetos ao JPanel typePlayersPanel
        typePlayersPanel.add(typeLabel);
        typePlayersPanel.add(playerComboBox1);
        typePlayersPanel.add(playerComboBox2);
        typePlayersPanel.add(playerComboBox3);
        typePlayersPanel.add(playerComboBox4);

        //Adicionar objetos ao JPanel numPlayersPanel
        numPlayersPanel.add(numPlayerLabel, BorderLayout.NORTH);
        numPlayersPanel.add(numPlayersSlider, BorderLayout.SOUTH);

        //Adicionar objetos ao JPanel numTypePlayersPanel
        numTypePlayersPanel.add(numPlayersPanel, BorderLayout.WEST);
        numTypePlayersPanel.add(typePlayersPanel, BorderLayout.EAST);

        //Adicionar objetos ao JPanel startPanel
        startPanel.add(startButton);

        //Adicionar  JPanels à frame
        frame.add(startPanel);
        frame.add(gameOptionPanel);
        frame.add(sizeOptionPanel);
        frame.add(numTypePlayersPanel);
        frame.setTitle("Game Configuration");
        frame.setSize(610, 620);
        frame.setResizable(false);
        frame.setBackground(Color.lightGray);
        frame.setVisible(true);
    }

    //Método que irá correr apenas quando se clica no botão iniciar. Neste método é fechada a frame com as configurações
    // de jogo (ConfigFrame)
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==startButton){
            frame.dispose(); //Fechar a frame ConfigFrame
            //Guardar os valores selecionados nos sliders em variáveis
            playerNumber = numPlayersSlider.getValue();
            gridSize = numGridSlider.getValue();
            winNumber = numWinSlider.getValue();
            //Validar quantos jogadores vão a jogo e guardar os valores (Human ou A3 Random) das respetivas JComboBox
            //numa lista
            selectedPlayers.add(playerComboBox1.getSelectedItem());
            selectedPlayers.add(playerComboBox2.getSelectedItem());
            if(playerComboBox3.isEnabled() && playerComboBox4.isEnabled()){
                selectedPlayers.add(playerComboBox3.getSelectedItem());
                selectedPlayers.add(playerComboBox4.getSelectedItem());
            } else if(playerComboBox3.isEnabled()){
                selectedPlayers.add(playerComboBox3.getSelectedItem());
            }
            //Declarar variavel com a classe GameFrame e enviar variaveis declaradas anteriormente como parametros
            //para o construtor da classe
            GameFrame gameFrame = new GameFrame(playerNumber, gridSize, winNumber, selectedPlayers);
        }
    }

    //Método que será chamado sempre que o utilizador interage com os sliders e seleciona um novo valor.
    //Com e.getSource() é possivel validar de que objeto (neste caso sliders) foi efetuada a alteração de valor, podendo
    //assim alterar a respetiva JLabel
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == numPlayersSlider){
            numPlayerLabel.setText(String.valueOf(numPlayersSlider.getValue()));
            if(numPlayersSlider.getValue() == 3){
                playerComboBox3.setEnabled(true);
                playerComboBox4.setEnabled(false);
            } else if(numPlayersSlider.getValue() == 4){
                playerComboBox4.setEnabled(true);
            } else if(numPlayersSlider.getValue() == 2){
                playerComboBox3.setEnabled(false);
                playerComboBox4.setEnabled(false);
            }
        } else if(e.getSource() == numGridSlider){
            numGridLabel.setText(numGridSlider.getValue() + "x" + numGridSlider.getValue());
            numWinSlider.setMaximum(numGridSlider.getValue());
        } else if(e.getSource() == numWinSlider){
            numWinLabel.setText(String.valueOf(numWinSlider.getValue()));
        }
    }
}
