/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava;

import com.formdev.flatlaf.icons.FlatClearIcon;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author manue
 */
public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 860;
    public static final int HEIGHT = 700;
    private int FPS = 60;
    private Thread gameThread;
    PlayManager pm;

    private boolean running = true;
    private final int TARGET_TIME = 1000 / FPS;
    private boolean pausedGame;

    private JButton backButton;

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setLayout(null);

        this.addKeyListener(new Key());
        this.setFocusable(true);

        pm = new PlayManager();

        //setBackground(new Color(232,231 ,231 ));
        //setBackground(new Color(192, 192, 192));
        //setBackground(Color.DARK_GRAY);

        backButton = new JButton();
        backButton.setIcon(new FlatClearIcon());
        backButton.setBounds(20, 10, 40, 30);
        backButton.setFocusable(false);
        backButton.setVisible(false);
        this.add(backButton);
    }

    public void startGame() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }

    }

    @Override
    public void run() {
        long startTime, elapsed, wait;

        while (running) {
            startTime = System.nanoTime();

            update();
            repaint();

            elapsed = System.nanoTime() - startTime;
            wait = TARGET_TIME - elapsed / 1000000;

            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void listenPause() {
        if (Key.escapePressed) {
            Key.escapePressed = false;
            pausedGame = !pausedGame;
            if (pausedGame) {
                backButton.setVisible(true);
            } else {
                backButton.setVisible(false);
            }
            Key.enterPressed = false;
        }

    }

    private void update() {

        if (!pm.gameOver) {
            listenPause();
        }

        if (!pm.gameOver && !pausedGame) {
            pm.update();

        } else {
            if (Key.enterPressed) {
                Key.enterPressed = false;
                pm = new PlayManager();
                pm.update();
            }
        }

    }

    private void makeEveryThingsDark(Graphics2D g2) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
        g2.setComposite(alphaComposite);
        g2.setColor(Color.black);
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setComposite(AlphaComposite.SrcOver);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        pm.draw(g2);

        if (pm.gameOver == true) {

            makeEveryThingsDark(g2);
            g2.setColor(Color.red);
            g2.setFont(new Font("Suige UI", Font.BOLD, 78));
            g2.drawString("GAME OVER", WIDTH / 2 - 270, HEIGHT / 2);

        }
        if (pausedGame == true) {

            makeEveryThingsDark(g2);
            g2.setColor(Color.white);
            g2.setFont(new Font("Suige UI", Font.BOLD, 78));
            g2.drawString("PAUSE", WIDTH / 2 - 315, HEIGHT / 2);

        }

    }

    public JButton getBackButton() {
        return backButton;
    }

}
