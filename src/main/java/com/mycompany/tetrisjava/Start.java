/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.tetrisjava;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.ui.FlatWindowResizer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author manue
 */
public class Start {

    public static void main(String[] args) {
        
        FlatDarculaLaf.setup();
        
       

        JFrame window = createWindow();

        MenuPanel menuPanel = new MenuPanel();
        GamePanel gamePanel = new GamePanel();
        SettingsPanel settingsPanel = new SettingsPanel();

        setupActions(window, menuPanel, gamePanel, settingsPanel);

        window.add(menuPanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static JFrame createWindow() {
        JFrame window = new JFrame("Tetris Java");
        ImageIcon icon = new ImageIcon(Start.class.getResource("/icon/icon.png"));
        window.setIconImage(icon.getImage());

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        return window;
    }

    private static void setupActions(JFrame window, MenuPanel menuPanel, GamePanel gamePanel, SettingsPanel settingsPanel) {
        
        menuPanel.getStartButton().addActionListener(e -> {
            switchToPanel(window, gamePanel);
            ImageIcon selectedImageIcon = settingsPanel.getSelectedImageIcon();
            if (selectedImageIcon != null) {
                gamePanel.pm.setIcon(selectedImageIcon);
            }
            
            window.setLocationRelativeTo(null);
            gamePanel.startGame();
        });

        menuPanel.getSettingsButton().addActionListener(e -> {
            switchToPanel(window, settingsPanel);
            window.setMinimumSize(new Dimension(400, 300));
        });

        settingsPanel.getBackButton().addActionListener(e -> {
            switchToPanel(window, menuPanel);
            window.setResizable(false);
        });

        gamePanel.getBackButton().addActionListener(e -> {
           
            switchToPanel(window, menuPanel);
            window.setResizable(false);
        });
    }

    private static void switchToPanel(JFrame window, JPanel panel) {
        window.getContentPane().removeAll();
        window.add(panel);
        window.pack();
        window.revalidate();
        panel.requestFocusInWindow();
        window.repaint();
    }
}
