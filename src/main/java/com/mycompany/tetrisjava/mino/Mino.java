/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava.mino;


import com.mycompany.tetrisjava.Key;
import com.mycompany.tetrisjava.PlayManager;
import java.awt.Graphics2D;

/**
 *
 * @author manue
 */
public class Mino {
    
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autodropCounter = 0;
    public int direction = 1; // 4 direzioni
    public boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactiving = false;
    int deactivingConter = 0;
    final int maxDeactiving = 45;
    
    public int type;
    
    public void create(TetrisColor color){
        b[0] = new Block(color);
        b[1] = new Block(color);
        b[2] = new Block(color);
        b[3] = new Block(color);
        
        tempB[0] = new Block(color);
        tempB[1] = new Block(color);
        tempB[2] = new Block(color);
        tempB[3] = new Block(color);
    }

    
   

    
    
    public void setXY(int x, int y){
        
    }
    
    public void updateXY(int direction){
        
        ceckRotationCollision();
        
        if(bottomCollision == false && leftCollision == false && rightCollision == false){

        this.direction = direction;
        
        b[0].x = tempB[0].x;
        b[0].y = tempB[0].y;
        b[1].x = tempB[1].x;
        b[1].y = tempB[1].y;
        b[2].x = tempB[2].x;
        b[2].y = tempB[2].y;
        b[3].x = tempB[3].x;
        b[3].y = tempB[3].y;
        
        }
        
    }
    
    
    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    
    public void ceckMovmentCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        
        checkStaticBlockCollision();
        
        for(int i=0;i<b.length;i++){
            if(b[i].x == PlayManager.left_x ){
                leftCollision = true;
            }
        }
        
        for(int i=0;i<b.length;i++){
            if(b[i].x + Block.SIZE == PlayManager.right_x){
                rightCollision = true;
            }
        }
        
        for(int i=0;i<b.length;i++){
            if(b[i].y + Block.SIZE == PlayManager.bottom_y ){
                bottomCollision = true;
            }
        }
        
    }
    
    public void ceckRotationCollision( ){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        
        checkStaticBlockCollision();
        
        for(int i=0;i<b.length;i++){
            if(tempB[i].x < PlayManager.left_x ){
                leftCollision = true;
            }
        }
        
        for(int i=0;i<b.length;i++){
            if(tempB[i].x + Block.SIZE > PlayManager.right_x){
                rightCollision = true;
            }
        }
        
        for(int i=0;i<b.length;i++){
            if(tempB[i].y + Block.SIZE > PlayManager.bottom_y ){
                bottomCollision = true;
            }
        }
    }
    
    private void checkStaticBlockCollision(){
        
        for (Block staticBlock : PlayManager.staticBlocks) {
            
            int targetX = staticBlock.x;
            int targetY = staticBlock.y;
            
            for (Block b1 : b) {
                if(b1.y + Block.SIZE == targetY && b1.x == targetX){
                    bottomCollision = true;
                }
            }
            for (Block b1 : b) {
                if(b1.x - Block.SIZE == targetX && b1.y == targetY){
                    leftCollision = true;
                }
            }   
            for (Block b1 : b) {
                if(b1.x + Block.SIZE == targetX && b1.y == targetY){
                    rightCollision = true;
                }
            }
            
        }
        
    }
    
    
    public void update(){
        
        if(deactiving){
            deactiving();
        }
        
        ceckMovmentCollision();
        
        if(Key.downPressed){
            if(bottomCollision == false){
            b[0].y +=  Block.SIZE;
            b[1].y +=  Block.SIZE;
            b[2].y +=  Block.SIZE;
            b[3].y +=  Block.SIZE;
            autodropCounter = 0;
            }
            Key.downPressed = false;
            
        }
        if(Key.leftPressed){
            if(leftCollision == false){
            b[0].x -=  Block.SIZE;
            b[1].x -=  Block.SIZE;
            b[2].x -=  Block.SIZE;
            b[3].x -=  Block.SIZE;
            }
            Key.leftPressed = false;
   
        }
        if(Key.rightPressed){
            if(rightCollision == false){
            b[0].x +=  Block.SIZE;
            b[1].x +=  Block.SIZE;
            b[2].x +=  Block.SIZE;
            b[3].x +=  Block.SIZE;
            }
            Key.rightPressed = false;
        }
        
        if(Key.upPressed){
            
            updateDirection();
            
            Key.upPressed = false;
        }
        
        
        if(bottomCollision){
            deactiving = true;
        }else{
               
        
        autodropCounter++;
        if(autodropCounter == PlayManager.dropInterval){
            b[0].y +=  Block.SIZE;
            b[1].y +=  Block.SIZE;
            b[2].y +=  Block.SIZE;
            b[3].y +=  Block.SIZE;
            autodropCounter = 0;
        }
            
        }
        
    }
    
    private void updateDirection(){
        switch (direction) {
                case 1 -> getDirection2();
                case 2 -> getDirection3();
                case 3 -> getDirection4();
                case 4 -> getDirection1();
                    
            }
    }
    
    public void deactiving(){
        deactivingConter++;
        if(deactivingConter == maxDeactiving){
            deactivingConter = 0;
            
            ceckMovmentCollision();
            
            if(bottomCollision){
                active = false;
            }
        }
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(b[0].color.getImage(),b[0].x,b[0].y,Block.SIZE,Block.SIZE,null);
        g2.drawImage(b[1].color.getImage(),b[1].x,b[1].y,Block.SIZE,Block.SIZE,null);
        g2.drawImage(b[2].color.getImage(),b[2].x,b[2].y,Block.SIZE,Block.SIZE,null);
        g2.drawImage(b[3].color.getImage(),b[3].x,b[3].y,Block.SIZE,Block.SIZE,null);
    }
    
    
   
    
}
