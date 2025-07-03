package com.library;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.library.view.LoginFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            FlatMacLightLaf.setup();

            // Pengaturan UIManager global untuk font dan properti lainnya
            UIManager.put("Label.font", new FontUIResource("Segoe UI", Font.PLAIN, 14));
            UIManager.put("Button.font", new FontUIResource("Segoe UI", Font.BOLD, 14));
            UIManager.put("TextField.font", new FontUIResource("Segoe UI", Font.PLAIN, 14));
            UIManager.put("PasswordField.font", new FontUIResource("Segoe UI", Font.PLAIN, 14));
            UIManager.put("TextArea.font", new FontUIResource("Segoe UI", Font.PLAIN, 14));
            UIManager.put("ComboBox.font", new FontUIResource("Segoe UI", Font.PLAIN, 14));

            UIManager.put("TabbedPane.font", new FontUIResource("Segoe UI", Font.BOLD, 15));
            UIManager.put("TabbedPane.tabHeight", 45);
            UIManager.put("TabbedPane.selectedBackground", new Color(240, 240, 240));
            UIManager.put("TabbedPane.showTabSeparators", true);
            UIManager.put("TabbedPane.tabSeparatorsFullHeight", true);
            UIManager.put("TabbedPane.selectedUnderlineColor", new Color(33, 150, 243)); // Garis bawah tab aktif
            UIManager.put("TabbedPane.selectedUnderlineHeight", 3);

            UIManager.put("TableHeader.font", new FontUIResource("Segoe UI", Font.BOLD, 14));
            UIManager.put("Table.font", new FontUIResource("Segoe UI", Font.PLAIN, 13));
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.rowHeight", 30);
            UIManager.put("Table.alternateRowColor", new Color(245, 245, 245));
            UIManager.put("Table.selectionBackground", new Color(173, 216, 230, 150)); // Lebih transparan
            UIManager.put("Table.selectionForeground", Color.BLACK);
            UIManager.put("Table.gridColor", new Color(220, 220, 220)); // Warna garis tabel

            UIManager.put("OptionPane.messageFont", new FontUIResource("Segoe UI", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new FontUIResource("Segoe UI", Font.BOLD, 14));

            UIManager.put("Panel.background", new Color(250, 250, 250)); // Warna latar belakang panel umum

            // Properti kustom FlatLaf untuk sudut membulat dan efek visual
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8); // Akan mempengaruhi panel, combobox, dll.
            UIManager.put("ScrollBar.thumbArc", 999); // Scrollbar lebih halus
            UIManager.put("ScrollBar.thumbSize", 12);
            UIManager.put("ScrollBar.trackArc", 999);

        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf Look and Feel: " + ex.getMessage());
            ex.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set cross-platform L&F: " + e.getMessage());
            }
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}