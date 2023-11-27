package com.x.work.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private final ArrayList<Point> snake;

    private final Point food;

    private int direction;

    private boolean gameOver;


    public SnakeGame() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        Timer timer = new Timer(200, this);
        snake = new ArrayList<>();
        snake.add(new Point(10, 10));
        food = new Point((int) (Math.random() * 50), (int) (Math.random() * 50));
        direction = KeyEvent.VK_RIGHT;
        gameOver = false;
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", 250, 300);
        } else {
            g.setColor(Color.RED);
            g.fillRect(food.x * 10, food.y * 10, 10, 10);
            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * 10, p.y * 10, 10, 10);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            Point head = snake.get(0);
            Point newHead = new Point(head.x, head.y);

            switch (direction) {
                case KeyEvent.VK_UP:
                    newHead.y--;
                    break;
                case KeyEvent.VK_DOWN:
                    newHead.y++;
                    break;
                case KeyEvent.VK_LEFT:
                    newHead.x--;
                    break;
                case KeyEvent.VK_RIGHT:
                    newHead.x++;
                    break;
            }

            if (newHead.equals(food)) {
                snake.add(0, newHead);
                food.setLocation((int) (Math.random() * 50), (int) (Math.random() * 50));
            } else if (snake.contains(newHead) || newHead.x < 0 || newHead.x >= 50 || newHead.y < 0 || newHead.y >= 50) {
                gameOver = true;
            } else {
                snake.remove(snake.size() - 1);
                snake.add(0, newHead);
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) && (Math.abs(key - direction) != 2)) {
            direction = key;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new SnakeGame());
        frame.pack();
        frame.setVisible(true);
    }
}