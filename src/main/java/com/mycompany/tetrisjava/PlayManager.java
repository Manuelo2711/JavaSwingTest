/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava;

import com.mycompany.tetrisjava.mino.Block;
import com.mycompany.tetrisjava.mino.Mino;
import com.mycompany.tetrisjava.mino.*;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author manue
 */
public class PlayManager {

//     Aria Gioco
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    ImageIcon icon;
//    Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    Mino nextMimo;
    final int NEXTMIMO_X;
    final int NEXTMIMO_Y;

    GhostMino ghostMino;

    public static ArrayList<Block> staticBlocks = new ArrayList<>();
//    Altro

    public static int dropInterval = 45;

    boolean gameOver;

//    Punteggio
    int level = 1;
    int lines = 0;
    int score = 0;

    private float border = 4f;

    AnimationDelete ani;

    public PlayManager() {

        left_x = (GamePanel.WIDTH / 2) - (this.WIDTH );
        right_x = left_x + this.WIDTH;
        top_y = 50;
        bottom_y = top_y + this.HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMIMO_X = right_x + 175;
        NEXTMIMO_Y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);

        nextMimo = pickMino();
        nextMimo.setXY(NEXTMIMO_X, NEXTMIMO_Y);

        ani = new AnimationDelete();
        staticBlocks = new ArrayList<>();
        dropInterval = 45;

        icon = new ImageIcon(getClass().getResource("/wallpaper/wallpaper.png"));
    }

    private Mino pickMino() {

        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0 ->
                mino = new Mino_L1();
            case 1 ->
                mino = new Mino_L2();
            case 2 ->
                mino = new Mino_Square();
            case 3 ->
                mino = new Mino_Bar();
            case 4 ->
                mino = new Mino_T();
            case 5 ->
                mino = new Mino_Z1();
            case 6 ->
                mino = new Mino_Z2();
        }
        return mino;
    }

    private void updateScore() {
        
        if (lines > 10) {
            lines = lines - 10;
            level++;
            if (dropInterval > 10) {
                dropInterval = dropInterval - level;
            }
        }

    }
    

    public void update() {

 
        updateScore();

        if (currentMino.active == false) {

            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {

                gameOver = true;
                Key.enterPressed = false;
            }

            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            currentMino.deactiving = false;

            currentMino = nextMimo;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMimo = pickMino();
            nextMimo.setXY(NEXTMIMO_X, NEXTMIMO_Y);

            checkDelete();
        } else {

            currentMino.update();
            ghostMino = new GhostMino(currentMino);
            ghostMino.update();

        }

    }

    private void checkDelete() {

        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {

                if (blockCount == 12) {
                    
                        
                        lines++;
                        score = score + (level * lines);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {

                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                            ani.addRow(y);
                        }
  
                        
                    }
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
    }

    public void draw(Graphics2D g2) {
        
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
  

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(border));
        g2.drawRect(left_x - (int) border, top_y - (int) border, WIDTH + (int) border * 2, HEIGHT + (int) border * 2);
        
        g2.drawImage(icon.getImage(), left_x, top_y, WIDTH, HEIGHT, null);

        //        Next mino border
        g2.drawRect(right_x + 115, top_y + 438, 180, 150);

        //        Score panel
        g2.drawRect(right_x + 100, top_y, 250, 200);
        g2.setFont(new Font("Suige UI", Font.BOLD, 30));

        g2.drawString("Score: " + String.valueOf(score), right_x + 165, top_y + 40);
        g2.drawString("Livello: " + String.valueOf(level), right_x + 165, top_y + 40 + g2.getFontMetrics().getHeight());
        g2.drawString("Linee: " + String.valueOf(lines), right_x + 165, top_y + 40 + 2 * g2.getFontMetrics().getHeight());

        //        Griglia posizione
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(new Color(255, 255, 255, 50));

        for (int i = left_x; i < left_x + WIDTH; i += Block.SIZE) {

            for (int j = top_y; j < top_y + HEIGHT; j += Block.SIZE) {

                g2.drawRect(i, j, Block.SIZE, Block.SIZE);
            }
        }

        if (currentMino != null) {
            currentMino.draw(g2);
        }

        nextMimo.draw(g2);

        for (Block staticBlock : staticBlocks) {
            staticBlock.draw(g2);
        }

        ghostMino.draw(g2);

        ani.draw(g2);

    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    
    

}
