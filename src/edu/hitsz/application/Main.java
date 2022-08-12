package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * 程序入口
 *
 * @author wb-yu
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object MAIN_LOCK = new Object();
    public static AbstractGame game;
    public static StartMenu startMenu = new StartMenu();
    public static RankBoard rankBoard;

    public static MusicThread backgroundMusic;

    public static void main(String[] args) throws IOException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 第一个界面
        JPanel startPanel = startMenu.getStartPanel();
        frame.setContentPane(startPanel);
        frame.setVisible(true);

        synchronized (MAIN_LOCK) {
            while (startPanel.isVisible()) {
                // 主线程等待开始面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 移除第一个开始面板，加入游戏面板，实现界面切换
        frame.remove(startPanel);

        // 第二个界面
        if (Objects.equals(startMenu.difficulty, "EASY")) {
            game = new EasyGame(startMenu.isMusic);
        } else if (Objects.equals(startMenu.difficulty, "MEDIUM")) {
            game = new MediumGame(startMenu.isMusic);
        } else {
            game = new HardGame(startMenu.isMusic);
        }

        if (startMenu.isMusic) {
            backgroundMusic = new MusicThread("src/videos/bgm.wav", true, false);
            backgroundMusic.start();
        }

        frame.setContentPane(game);
        game.action();
        frame.setVisible(true);

        synchronized (MAIN_LOCK) {
            while (game.isVisible()) {
                // 主线程等待游戏面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        // 移除第二个游戏面板，加入排名面板，实现界面切换
        frame.remove(game);
        rankBoard = new RankBoard(game);

        // 第三个界面
        JPanel rankPanel = rankBoard.getRankPanel();
        frame.setContentPane(rankPanel);
        frame.setVisible(true);


    }
}
