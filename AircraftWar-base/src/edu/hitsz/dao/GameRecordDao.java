package edu.hitsz.dao;

import java.util.List;

/**
 * @author wb-yu
 */
public interface GameRecordDao {

    /**
     * 返回全部游戏记录
     *
     * @return 全部游戏记录
     */
    List<GameRecord> getAllRecords();

    /**
     * 添加游戏记录
     *
     * @param gameRecord 需要添加的游戏记录
     */
    void addRecord(GameRecord gameRecord);

    /**
     * 删除游戏记录
     *
     * @param gameRecord 需要删除的游戏记录
     */
    void deleteRecord(GameRecord gameRecord);

}
