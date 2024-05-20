/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava;

import com.mycompany.tetrisjava.mino.Block;
import com.mycompany.tetrisjava.mino.TetrisColor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

/**
 *
 * @author manue
 */
public class AnimationDelete {

    public Vector<Integer> rows;

    final static int MAX_FRAME = 15;
    int counter = MAX_FRAME;

    public AnimationDelete() {

        rows = new Vector<>();

    }
    public void addRow(int otherRow){
        rows.add(otherRow);
        counter=MAX_FRAME;
    }

    public void draw(Graphics2D g2) {
        
        counter--;
        if (counter <= 0) {
                rows.clear(); 
            }
        
        float alpha = counter / (float) MAX_FRAME; // Calcola l'opacità in base al conteggio
            if(alpha<0){
                alpha = 0;
            }
        
        for (int i = 0; i < rows.size(); i++) {
            
            int row = rows.get(i);
            
            
           g2.setColor(new Color(1.0f, 1.0f, 1.0f, alpha)); // Imposta il colore con opacità
           g2.fillRect(PlayManager.left_x, row, Block.SIZE * 12, Block.SIZE);
            
            

            i++;
        }

    }

}
