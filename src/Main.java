package com.library;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf; // Menggunakan FlatMacLightLaf untuk tampilan lebih modern
// import com.formdev.flatlaf.FlatDarculaLaf; // Contoh untuk tema gelap

import com.library.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Mengatur Look and Feel FlatLaf
        try {
            FlatMacLightLaf.setup(); // Menggunakan tema FlatMacLightLaf
            // UIManager.setLookAndFeel(new FlatDarculaLaf()); // Untuk tema gelap, uncomment ini
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf Look and Feel:" + ex);
        }

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}