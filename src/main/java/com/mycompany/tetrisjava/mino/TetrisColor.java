package com.mycompany.tetrisjava.mino;

import java.awt.Image;
import javax.swing.ImageIcon;

public enum TetrisColor {
    BLUE("/block/blue.png"),
    RED("/block/red.png"),
    YELLOW("/block/yellow.png"),
    GREEN("/block/green.png"),
    ORANGE("/block/orange.png"),
    PURPLE("/block/purple.png"),
    CYAN("/block/cyan.png");

    private String imagePath;

    TetrisColor(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        return loadImage(imagePath);
    }

    private Image loadImage(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        return icon.getImage();
    }
}
