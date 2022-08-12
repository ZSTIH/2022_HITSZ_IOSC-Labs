package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * @author wb-yu
 */
public class StartMenu {
    private final String musicOn = "开";
    public String difficulty;
    public boolean isMusic;
    private JButton easyButton;
    private JButton hardButton;
    private JButton mediumButton;
    private JPanel startPanel;
    private JLabel musicLabel;
    private JComboBox musicBox;
    private JPanel musicPanel1;
    private JPanel musicPanel2;

    public StartMenu() {

        easyButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = "EASY";
                isMusic = Objects.equals(Objects.requireNonNull(musicBox.getSelectedItem()).toString(), musicOn);
                startPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    // 选定难度，通知主线程结束
                    Main.MAIN_LOCK.notify();
                }
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = "MEDIUM";
                isMusic = Objects.equals(Objects.requireNonNull(musicBox.getSelectedItem()).toString(), musicOn);
                startPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    // 选定难度，通知主线程结束
                    Main.MAIN_LOCK.notify();
                }
            }
        });

        hardButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = "HARD";
                isMusic = Objects.equals(Objects.requireNonNull(musicBox.getSelectedItem()).toString(), musicOn);
                startPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    // 选定难度，通知主线程结束
                    Main.MAIN_LOCK.notify();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Start");
        frame.setContentPane(new StartMenu().startPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getStartPanel() {
        return startPanel;
    }


}
