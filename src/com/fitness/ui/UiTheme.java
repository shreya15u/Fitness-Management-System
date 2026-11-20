package com.fitness.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public final class UiTheme {
    public static final Color PAGE_BACKGROUND = new Color(245, 247, 250);
    public static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    public static final Color PRIMARY = new Color(27, 94, 32);
    public static final Color PRIMARY_DARK = new Color(18, 70, 24);
    public static final Color ACCENT = new Color(255, 183, 77);
    public static final Color TEXT = new Color(34, 40, 49);
    public static final Color MUTED_TEXT = new Color(108, 117, 125);
    public static final Color BORDER = new Color(220, 224, 230);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private UiTheme() {
    }

    public static JPanel createPagePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PAGE_BACKGROUND);
        return panel;
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(20, 20, 20, 20)));
        return panel;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(SUBTITLE_FONT);
        label.setForeground(MUTED_TEXT);
        return label;
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static void styleTextField(JTextField field) {
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(220, 36));
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(10, 18, 10, 18));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(ACCENT);
        button.setForeground(TEXT);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(10, 18, 10, 18));
        return button;
    }

    public static void setPanelOpaque(Component component, boolean opaque) {
        if (component instanceof JComponent) {
            ((JComponent) component).setOpaque(opaque);
        }
    }
}
