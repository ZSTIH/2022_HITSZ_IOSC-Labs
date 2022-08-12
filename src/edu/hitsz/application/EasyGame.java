package edu.hitsz.application;

import java.io.IOException;

/**
 * @author wb-yu
 */
public class EasyGame extends AbstractGame {
    public EasyGame(boolean isMusic) throws IOException {
        super(isMusic);
        this.difficulty = "EASY";
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE;
    }

    @Override
    protected boolean needBossOrNot() {
        return false;
    }

    @Override
    protected void setEnemyCycleDuration() {
        this.enemyCycleDuration = 600;
    }

    @Override
    protected void setHeroBulletCycleDuration() {
        this.heroBulletCycleDuration = 120;
    }

    @Override
    protected void setEnemyBulletCycleDuration() {
        this.enemyBulletCycleDuration = 600;
    }

    @Override
    protected void setRateBetweenMobAndElite() {
        this.rateBetweenMobAndElite = 1;
    }

    @Override
    protected void setEnemyMaxNumber() {
        this.enemyMaxNumber = 5;
    }

    @Override
    protected boolean difficultyIncreaseOrNot() {
        return false;
    }

    @Override
    protected void increaseDifficulty() {
        // 空实现
    }

    @Override
    protected void setDifficultyDuration() {
        // 空实现
    }

    @Override
    protected boolean bossHpIncreaseOrNot() {
        return false;
    }


}
