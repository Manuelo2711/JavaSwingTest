/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava.mino;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author manue
 */
public class Block extends Rectangle{
    
    public int x,y;
    public static final int SIZE = 30;
    public TetrisColor color;
    
    public Block(TetrisColor color){
        this.color = color;
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(this.color.getImage(),x,y,SIZE,SIZE,null);
    }
    public Block(Block other) {
        this.x = other.x;
        this.y = other.y;
        this.color = other.color;
    }
            
}
