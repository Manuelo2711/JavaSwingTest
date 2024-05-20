/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisjava.mino;

import com.mycompany.tetrisjava.Key;
import com.mycompany.tetrisjava.PlayManager;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

/**
 *
 * @author manue
 */
public class GhostMino {

    private Mino currentMino;
    private Mino ghostMino;

    public GhostMino(Mino currentMino) {

        this.currentMino = currentMino;
    }

    ;
    

 
    public void update() {

//        ghostMino.setXY(currentMino.b[0].x, currentMino.b[0].y);
//            ghostMino = pickMino(currentMino.type); 
        ghostMino = new Mino();
        

        for (int i = 0; i < ghostMino.b.length; i++) {
            ghostMino.b[i] = new Block(currentMino.b[i]);
        }

//        ghostMino.setXY(currentMino.b[0].x, currentMino.b[0].y);
        ceckMovmentCollision();
        while (!ghostMino.bottomCollision) {

            for (int i = 0; i < ghostMino.b.length; i++) {
                ghostMino.b[i].y += Block.SIZE;

            }

            ceckMovmentCollision();

        }

        if (Key.spacePressed) {
            for (int i = 0; i < ghostMino.b.length; i++) {
                currentMino.b[i] = new Block(ghostMino.b[i]);
            }
            Key.spacePressed = false;
        }

    }

    public void checkStaticBlockCollision() {

        for (Block staticBlock : PlayManager.staticBlocks) {

            int targetX = staticBlock.x;
            int targetY = staticBlock.y;

            for (Block b1 : ghostMino.b) {
                if (b1.y + Block.SIZE == targetY && b1.x == targetX) {
                    ghostMino.bottomCollision = true;
                }
            }

        }

    }

    public void ceckMovmentCollision() {

        ghostMino.bottomCollision = false;

        checkStaticBlockCollision();

        for (Block b : ghostMino.b) {
            if (b.y + Block.SIZE == PlayManager.bottom_y) {
                ghostMino.bottomCollision = true;
            }
        }

    }


    public void draw(Graphics2D g2) {
        float opacity = 0.5f; // Opacità del 50% (0 completamente trasparente, 1 completamente opaco)
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2.setComposite(alphaComposite);

        // Disegna l'immagine utilizzando l'opacità impostata
        ghostMino.draw(g2);

        // Ripristina l'opacità predefinita
        g2.setComposite(AlphaComposite.SrcOver);

       

    }

}
