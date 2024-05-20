import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;

public class AccentColorDemo extends JFrame {
    private static String[] accentColorNames = {
            "Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green",
    };

    private final JToggleButton[] accentColorButtons = new JToggleButton[accentColorNames.length];
    private JLabel accentColorLabel;
    private Color accentColor;
    private JToolBar toolBar;

    public AccentColorDemo() {
        initUI();
        initAccentColors();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        toolBar = new JToolBar();
        add(toolBar, BorderLayout.NORTH);

        accentColorLabel = new JLabel("Accent color:");
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(accentColorLabel);
    }

    private void initAccentColors() {
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < accentColorButtons.length; i++) {
            accentColorButtons[i] = new JToggleButton(accentColorNames[i]);
            accentColorButtons[i].setToolTipText(accentColorNames[i]);
            group.add(accentColorButtons[i]);
            toolBar.add(accentColorButtons[i]);
        }

        accentColorButtons[0].setSelected(true);



        
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            new AccentColorDemo().setVisible(true);
        });
    }
}
