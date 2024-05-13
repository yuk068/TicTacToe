package tictactoe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class TicTacToeGameGUI extends JFrame {

    private TicTacToeGame game;
    private TicTacToeBot bot;
    private final JPanel panel;

    public TicTacToeGameGUI() {
        super("TicTacToe, depth 5");
        JOptionPane.showMessageDialog(null, """
                <- say hi to her btw
                TicTacToe game, test for my first ever bot
                using minimax algorithm with alpha beta pruning
                test different depths (or in other word, difficulty)
                by pressing numbers from 1-9.
                Thanks for trying out my program!
                """, "Yuk's note", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getImageURL("asset/hare.png")));
        game = new TicTacToeGame();
        bot = new TicTacToeBot(game, 5);
        panel = new JPanel();
        panel.setLayout(new GridLayout(game.height, game.width));
        setIconImage(new ImageIcon(getImageURL("asset/nihahaha.png")).getImage());

        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                JPanel square = getSquare();
                panel.add(square);
            }
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean newGame = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_1:
                        setTitle("TicTacToe, depth 1");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 1);
                        newGame = true;
                        break;
                    case KeyEvent.VK_2:
                        setTitle("TicTacToe, depth 2");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 2);
                        newGame = true;
                        break;
                    case KeyEvent.VK_3:
                        setTitle("TicTacToe, depth 3");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 3);
                        newGame = true;
                        break;
                    case KeyEvent.VK_4:
                        setTitle("TicTacToe, depth 4");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 4);
                        newGame = true;
                        break;
                    case KeyEvent.VK_5:
                        setTitle("TicTacToe, depth 5");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 5);
                        newGame = true;
                        break;
                    case KeyEvent.VK_6:
                        setTitle("TicTacToe, depth 6");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 6);
                        newGame = true;
                        break;
                    case KeyEvent.VK_7:
                        setTitle("TicTacToe, depth 7");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 7);
                        newGame = true;
                        break;
                    case KeyEvent.VK_8:
                        setTitle("TicTacToe, depth 8");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 8);
                        newGame = true;
                        break;
                    case KeyEvent.VK_9:
                        setTitle("TicTacToe, depth 9");
                        game = new TicTacToeGame();
                        bot = new TicTacToeBot(game, 9);
                        newGame = true;
                        break;
                }
                if (newGame) {
                    if (game.turn) {
                        int randomMoveIndex = game.random.nextInt(9);
                        int[] randomMove = Utility.indexToCoordinates(randomMoveIndex);
                        game.makeMove(randomMove[0], randomMove[1]);
                        update();
                    }
                    update();
                }
            }
        });

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        if (game.turn) {
            int randomMoveIndex = game.random.nextInt(9);
            int[] randomMove = Utility.indexToCoordinates(randomMoveIndex);
            game.makeMove(randomMove[0], randomMove[1]);
            update();
        }
    }

    private JPanel getSquare() {
        JPanel square = new JPanel();
        square.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!game.gameIsOver()) {
                    int index = panel.getComponentZOrder(square);
                    int x = index / game.width;
                    int y = index % game.width;
                    if (game.withinBoard(x, y)) {
                        boolean moveSuccess = game.makeMove(x, y);
                        if (moveSuccess) {
                            update();
                            if (!game.gameIsOver()) {
                                Timer timer = new Timer(200, event -> {
                                    bot.makeMove();
                                    update();
                                });
                                timer.setRepeats(false);
                                timer.start();
                            }
                        }
                    }
                }
            }
        });
        return square;
    }

    public BufferedImage loadImage(String fileName, int width, int height) throws IOException {
        URL imageUrl = getImageURL(fileName);
        if (imageUrl != null) {
            BufferedImage image = ImageIO.read(imageUrl);
            BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
            Graphics2D graphics = scaledImage.createGraphics();
            graphics.drawImage(image, 0, 0, width, height, null);
            graphics.dispose();
            return scaledImage;
        }
        throw new IOException("Failed to load image: " + fileName);
    }

    public ImageIcon getImageIcon(String fileName, int width, int height) throws IOException {
        BufferedImage image = loadImage(fileName, width, height);
        return new ImageIcon(image);
    }

    public void update() {
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                JPanel square = (JPanel) panel.getComponent(i * game.width + j);
                int value = game.getSquare(i, j);

                JLabel label = new JLabel();
                square.removeAll();
                square.setLayout(new BorderLayout());
                square.add(label, BorderLayout.CENTER);
                try {
                    if (value == -1) {
                        label.setIcon(getImageIcon("asset/Artboard 2.png", 200, 200));
                    } else if (value == 1) {
                        label.setIcon(getImageIcon("asset/Artboard 1.png", 200, 200));
                    } else {
                        label.setIcon(null);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                square.revalidate();
                square.repaint();
            }
        }
        if (game.gameIsOver()) {
            try {
                String msg = ", '1-9' to test different bot depths";
                switch (game.checkWin()) {
                    case -1:
                        JOptionPane.showMessageDialog(null, "O won!" + msg, "Game Over", JOptionPane.INFORMATION_MESSAGE, getImageIcon("asset/nihahaha.png", 100, 100));
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, "X won!" + msg, "Game Over", JOptionPane.INFORMATION_MESSAGE, getImageIcon("asset/nihahaha.png", 100, 100));
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Draw!" + msg, "Game Over", JOptionPane.INFORMATION_MESSAGE, getImageIcon("asset/nihahaha.png", 100, 100));
                        break;
                }
            } catch (IOException e) {
                System.out.println("Exception while loading image");
            }
        }
    }

    public static URL getImageURL(String fileName) {
        return TicTacToeGameGUI.class.getResource(fileName);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGameGUI::new);
    }

}
