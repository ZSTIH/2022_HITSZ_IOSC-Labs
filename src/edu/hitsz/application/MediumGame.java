package edu.hitsz.application;

import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

import java.io.IOException;

/**
 * @author wb-yu
 */
public class MediumGame extends AbstractGame {
    public MediumGame(boolean isMusic) throws IOException {
        super(isMusic);
        this.difficulty = "MEDIUM";
        this.backgroundImage = ImageManager.BACKGROUND_IMAGE2;
    }

    @Override
    protected boolean needBossOrNot() {
        return true;
    }

    @Override
    protected void setEnemyCycleDuration() {
        this.enemyCycleDuration = 560;
    }

    @Override
    protected void setHeroBulletCycleDuration() {
        this.heroBulletCycleDuration = 240;
    }

    @Override
    protected void setEnemyBulletCycleDuration() {
        this.enemyBulletCycleDuration = 480;
    }

    @Override
    protected void setRateBetweenMobAndElite() {
        this.rateBetweenMobAndElite = 0.5;
    }

    @Override
    protected void setEnemyMaxNumber() {
        this.enemyMaxNumber = 6;
    }

    @Override
    protected boolean difficultyIncreaseOrNot() {
        return true;
    }

    @Override
    protected void increaseDifficulty() {

        if (enemyCycleDuration > 240) {
            // 每次敌机生成周期降低40毫秒，最低不低于240毫秒
            enemyCycleDuration -= 40;
        }

        if (heroBulletCycleDuration < 600) {
            // 每次英雄机发射子弹周期提高20毫秒，最高不高于600毫秒
            heroBulletCycleDuration += 20;
        }

        if (enemyBulletCycleDuration > 120) {
            // 每次敌机发射子弹周期降低40毫秒，最低不低于120毫秒
            enemyBulletCycleDuration -= 40;
        }

        rateBetweenMobAndElite *= 0.95;
        double rateOfMob = 1 / (1 + rateBetweenMobAndElite);

        if (enemyMaxNumber <= 15) {
            // 每次最大敌机数加1，最多不超过15
            enemyMaxNumber += 1;
        }

        if (generateBossThreshold > 2000) {
            // 每次生成Boss机的阈值降低200分，最低不低于2000分
            generateBossThreshold -= 200;
        }

        // 提高血量
        MobEnemyFactory.initialHp += 3;
        EliteEnemyFactory.initialHp += 5;
        BossEnemyFactory.initialHp += 10;

        // 提高发射子弹数
        if (EliteEnemyFactory.initialShootNum < 5) {
            // 每次子弹发射数加1，最多不超过5
            EliteEnemyFactory.initialShootNum += 1;
        }
        if (BossEnemyFactory.initialShootNum < 15) {
            // 每次子弹发射数加1，最多不超过15
            BossEnemyFactory.initialShootNum += 1;
        }

        // 提高子弹伤害
        if (EliteEnemyFactory.initialPower < 30) {
            // 每次伤害加1，最多不超过30
            EliteEnemyFactory.initialPower += 1;
        }
        if (BossEnemyFactory.initialPower < 50) {
            // 每次伤害加2，最多不超过50
            BossEnemyFactory.initialPower += 2;
        }

        System.out.println("-------------------------*提高游戏难度*-------------------------");
        System.out.printf("敌机生成周期为%d毫秒，英雄机子弹发射周期为%d毫秒，敌机子弹发射周期为%d毫秒，\n",
                enemyCycleDuration,
                heroBulletCycleDuration,
                enemyBulletCycleDuration);
        System.out.printf("Boss机生成阈值为%d分，精英机生成概率为%2.3f，最大敌机数量为%d，\n",
                generateBossThreshold,
                rateOfMob,
                enemyMaxNumber);
        System.out.println("【普通敌机属性提高】血量为" + MobEnemyFactory.initialHp);
        System.out.printf("【精英敌机属性提高】血量为%d，子弹发射数目为%d，子弹伤害为%d\n",
                EliteEnemyFactory.initialHp,
                EliteEnemyFactory.initialShootNum,
                EliteEnemyFactory.initialPower);
        System.out.printf("【Boss敌机属性提高】血量为%d，子弹发射数目为%d，子弹伤害为%d\n",
                BossEnemyFactory.initialHp,
                BossEnemyFactory.initialShootNum,
                BossEnemyFactory.initialPower);
        System.out.println("----------------------------------------------------------------");
    }

    @Override
    protected void setDifficultyDuration() {
        // 每15秒提升1次难度
        this.increaseDifficultyCycleDuration = 15000;
    }

    @Override
    protected boolean bossHpIncreaseOrNot() {
        return false;
    }

}
