import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel implements ActionListener, KeyListener {

    private int x = 50;
    private int y = 50;
    private int xCell;
    private int yCell;
    public static String NAME = "SNAKE"; //заголовок окна
    private Timer timer;
    private static JFrame frame;
    private int stepSnake = 50;
    private boolean xRight = false;
    private boolean xLeft = false;
    private boolean yUp = false;
    private boolean yDown = false;
    private ArrayList<Cell> snake = new ArrayList<>();

    public Snake() {
        addKeyListener(this);
        setFocusable(true);
        snake.add(new Cell(x, y));
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(500, this);  // Тут создаем таймер
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 500, 500);

        g.setColor(Color.blue);
        g.fillRect(xCell, yCell, 25, 25);

        if (snake != null) {
            for (Cell cell : snake) {
                cell.draw(g);
            }
        }


    }

    public void spawn() {
        Random random = new Random();
        xCell = random.nextInt(500);
        yCell = random.nextInt(500);


    }


    public static void main(String[] args) {

        Snake snake = new Snake();
        frame = new JFrame(Snake.NAME);
        frame.add(snake);

        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if (xRight) {
            x += stepSnake;
        }

        if (yDown) {
            y -= stepSnake;
        }

        if (xLeft) {
            x -= stepSnake;
        }

        if (yUp) {
            y += stepSnake;
        }
        if (xCell == 0) {
            spawn();
        }

        checkCollision();

        if (snake.size() > 1) {
            for (int i = snake.size() - 1; i != 0; i--) {
                snake.get(i).setX(snake.get(i - 1).getX());
                snake.get(i).setY(snake.get(i - 1).getY());
            }
        }
        snake.get(0).setX(x);
        snake.get(0).setY(y);

        repaint();

    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && x < 500 && !xLeft) {
            xLeft = false;
            yDown = false;
            yUp = false;
            xRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && x > 0 && !xRight) {
            xLeft = true;
            yDown = false;
            yUp = false;
            xRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && y < 500 && !yDown) {
            xLeft = false;
            yDown = false;
            yUp = true;
            xRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && y > 0 && !yUp) {
            xLeft = false;
            yDown = true;
            yUp = false;
            xRight = false;
        }


    }


    public void checkCollision() {
        Rectangle rectangle = new Rectangle(x, y, 50, 50);
        Rectangle rectangleCell = new Rectangle(xCell, yCell, 30, 30);
        if (rectangle.intersects(rectangleCell)) {
            xCell = 0;
            yCell = 0;
            snake.add(new Cell(x, y));
        }


    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
