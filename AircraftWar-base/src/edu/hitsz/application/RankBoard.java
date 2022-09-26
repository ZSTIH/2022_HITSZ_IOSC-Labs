package edu.hitsz.application;

import edu.hitsz.dao.GameRecord;
import edu.hitsz.dao.GameRecordDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wb-yu
 */
public class RankBoard {
    private final AbstractGame game;
    private final GameRecordDaoImpl gameRecordDao;
    private JPanel rankPanel;
    private JLabel difficultyLabel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JLabel difficultyLabel2;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JScrollPane tableScrollPane;
    private JLabel rankLabel;
    private String userName = "User";
    private List<GameRecord> gameRecords;
    private DefaultTableModel model;


    public RankBoard(AbstractGame game) throws IOException {

        this.game = game;
        gameRecordDao = game.gameRecordDao;
        difficultyLabel2.setText(game.difficulty);
        gameRecords = gameRecordDao.getAllRecords();

        String input = JOptionPane.showInputDialog(rankPanel, "游戏结束！你的得分为" + game.score + "分，请输入用户名记录得分（若输入空则默认为User，若点击取消则不保存游戏记录）", "User");
        if (!Objects.equals(input, "")) {
            userName = input;
        }

        if (input != null) {
            saveRecord();
        }

        showRank();

        deleteButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int userOption = JOptionPane.showConfirmDialog(rankPanel, "是否删除选中的记录？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (userOption != JOptionPane.OK_OPTION) {
                    return;
                }
                int row = scoreTable.getSelectedRow();
                if (row != -1) {
                    String timeOfDeletedRecord = (String) scoreTable.getValueAt(row, 3);
                    for (GameRecord record : gameRecords) {
                        if (Objects.equals(record.getTime(), timeOfDeletedRecord)) {
                            gameRecordDao.deleteRecord(record);
                        }
                    }
                    model.removeRow(row);
                    try {
                        gameRecordDao.writeToFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public JPanel getRankPanel() {
        return rankPanel;
    }


    /**
     * 保存游戏记录
     */
    private void saveRecord() throws IOException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(date);
        gameRecordDao.addRecord(new GameRecord(this.userName, time, game.score, game.difficulty));
        gameRecordDao.writeToFile();
    }

    /**
     * 打印游戏排名
     */
    public void showRank() {
        gameRecords = gameRecords.stream().filter(record -> Objects.equals(record.getDifficulty(), game.difficulty)).collect(Collectors.toList());
        gameRecords.sort((o1, o2) -> o2.getScore() - o1.getScore());

        String[] columnName = {"名次", "玩家名", "得分", "记录时间"};
        String[][] tableData = new String[gameRecords.size()][4];
        for (int i = 0; i < gameRecords.size(); i++) {
            tableData[i][0] = String.valueOf(i + 1);
            tableData[i][1] = gameRecords.get(i).getUserName();
            tableData[i][2] = String.valueOf(gameRecords.get(i).getScore());
            tableData[i][3] = gameRecords.get(i).getTime();
        }

        //表格模型
        model = new DefaultTableModel(tableData, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        //JTable并不存储自己的数据，而是从表格模型那里获取它的数据
        scoreTable.setModel(model);
        tableScrollPane.setViewportView(scoreTable);
    }


}
