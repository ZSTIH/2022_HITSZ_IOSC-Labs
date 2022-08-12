package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.dao.GameRecordDaoImpl;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.EnemyAircraftFactory;
import edu.hitsz.factory.MobEnemyFactory;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 游戏主面板，游戏启动
 *
 * @author wb-yu
 */
public abstract class AbstractGame extends JPanel {

    /**
     * 游戏记录的数据访问对象
     */
    public final GameRecordDaoImpl gameRecordDao;
    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔（单位为毫秒），控制刷新频率
     */
    private final int timeInterval = 40;
    private final HeroAircraft heroAircraft;
    private final List<AbstractEnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;
    /**
     * 游戏难度名称
     */
    public String difficulty;
    public int score = 0;
    protected int generateBossThreshold = 4000;
    /**
     * 最大敌机数量
     */
    protected int enemyMaxNumber;
    /**
     * 背景图片
     */
    protected Image backgroundImage;
    /**
     * 是否有音效
     */
    protected boolean isMusic;
    /**
     * 普通敌机与精英敌机的产生概率之比
     */
    protected double rateBetweenMobAndElite;
    protected int enemyCycleDuration;
    protected int heroBulletCycleDuration;
    protected int enemyBulletCycleDuration;
    protected int increaseDifficultyCycleDuration;
    private int backGroundTop = 0;
    private EnemyAircraftFactory enemyAircraftFactory;
    private int generateBossScore = 0;
    private boolean isBossAliveFlag = false;
    private MusicThread bossBackgroundMusic;
    private int time = 0;
    private int enemyCycleTime = 0;
    private int heroBulletCycleTime = 0;
    private int enemyBulletCycleTime = 0;
    private int increaseDifficultyCycleTime = 0;


    public AbstractGame(boolean isMusic) throws IOException {
        heroAircraft = HeroAircraft.getHeroAircraftInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();


        gameRecordDao = new GameRecordDaoImpl();

        // Scheduled 线程池，用于定时任务调度
        executorService = new ScheduledThreadPoolExecutor(1);

        // 启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

        // 设置是否有音效
        this.isMusic = isMusic;

        // 设置时间频率
        this.setEnemyCycleDuration();
        this.setHeroBulletCycleDuration();
        this.setEnemyBulletCycleDuration();
        this.setDifficultyDuration();

        // 设置精英敌机与普通敌机产生频率之比
        this.setRateBetweenMobAndElite();

        // 设置最大敌机数量
        this.setEnemyMaxNumber();

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性产生敌机
            if (enemyTimeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    if ((1 + rateBetweenMobAndElite) * Math.random() > 1) {
                        enemyAircraftFactory = new MobEnemyFactory();
                        enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                    } else {
                        enemyAircraftFactory = new EliteEnemyFactory();
                        enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                    }
                    if (this.needBossOrNot() && generateBossScore >= generateBossThreshold) {
                        if (!isBossAliveFlag) {

                            if (bossHpIncreaseOrNot()) {
                                BossEnemyFactory.initialHp += 240;
                                System.out.println("Boss敌机血量每次召唤需要提升！提升后Boss敌机的血量为" + BossEnemyFactory.initialHp);
                            } else {
                                System.out.println("Boss敌机血量每次召唤不需提升！Boss敌机的血量仍然为" + BossEnemyFactory.initialHp);
                            }

                            enemyAircraftFactory = new BossEnemyFactory();
                            enemyAircrafts.add(enemyAircraftFactory.createEnemyAircraft());
                            isBossAliveFlag = true;

                            if (isMusic) {
                                bossBackgroundMusic = new MusicThread("src/videos/bgm_boss.wav", true, false);
                                bossBackgroundMusic.start();
                            }
                        }
                    }
                }
            }

            // 周期性射出敌机子弹
            if (enemyBulletTimeCountAndNewCycleJudge()) {
                // 敌机射出子弹
                enemyShootAction();
            }

            // 周期性射出英雄机子弹
            if (heroBulletTimeCountAndNewCycleJudge()) {
                // 英雄机射出子弹
                heroShootAction();
            }

            // 周期性提高游戏难度
            if (difficultyIncreaseOrNot() && setDifficultyTimeCountAndNewCycleJudge()) {
                increaseDifficulty();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            enemyAircraftMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 我方获得道具，道具生效
            propsTakeIntoEffect();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                System.out.println("Game Over!");

                if (isMusic) {
                    Main.backgroundMusic.setIsStop(true);
                    Main.backgroundMusic.setIsLoop(false);
                    if (isBossAliveFlag) {
                        bossBackgroundMusic.setIsLoop(false);
                        bossBackgroundMusic.setIsStop(true);
                    }
                    new MusicThread("src/videos/game_over.wav", false, false).start();
                }

                this.setVisible(false);
                synchronized (Main.MAIN_LOCK) {
                    // 游戏结束，通知主线程停止等待
                    Main.MAIN_LOCK.notify();
                }

            }

        };

        /*
          以固定延迟时间进行执行
          本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    /**
     * 钩子方法，决定是否生成Boss机
     *
     * @return 是否生成Boss机 (生成：true，不生成：false)
     */
    protected abstract boolean needBossOrNot();

    /**
     * 设置敌机生成周期
     */
    protected abstract void setEnemyCycleDuration();

    /**
     * 设置英雄子弹发射周期
     */
    protected abstract void setHeroBulletCycleDuration();

    /**
     * 设置敌机子弹发射周期
     */
    protected abstract void setEnemyBulletCycleDuration();

    /**
     * 设置普通敌机与精英敌机的生成概率之比
     */
    protected abstract void setRateBetweenMobAndElite();

    /**
     * 设置最大敌机数量
     */
    protected abstract void setEnemyMaxNumber();

    /**
     * 钩子方法，决定难度是否需要随着时间推移增加
     *
     * @return 难度是否需要随着时间推移增加
     */
    protected abstract boolean difficultyIncreaseOrNot();

    /**
     * 随着时间推移提高游戏难度
     */
    protected abstract void increaseDifficulty();

    /**
     * 设置游戏难度提高的频率
     */
    protected abstract void setDifficultyDuration();

    /**
     * 决定是否每次召唤提高Boss机血量
     *
     * @return 每次召唤是否提高Boss机血量
     */
    protected abstract boolean bossHpIncreaseOrNot();

    //***********************
    //      Action 各部分
    //***********************

    /**
     * 敌机产生频率控制
     *
     * @return 是否应当产生敌机
     */
    private boolean enemyTimeCountAndNewCycleJudge() {
        enemyCycleTime += timeInterval;
        if (enemyCycleTime >= enemyCycleDuration) {
            // 跨越到新的周期
            enemyCycleTime %= enemyCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 英雄机子弹产生频率控制
     *
     * @return 是否应当产生英雄机子弹
     */
    private boolean heroBulletTimeCountAndNewCycleJudge() {
        heroBulletCycleTime += timeInterval;
        if (heroBulletCycleTime >= heroBulletCycleDuration) {
            // 跨越到新的周期
            heroBulletCycleTime %= heroBulletCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 敌机子弹产生频率控制
     *
     * @return 是否应当产生敌机子弹
     */
    private boolean enemyBulletTimeCountAndNewCycleJudge() {
        enemyBulletCycleTime += timeInterval;
        if (enemyBulletCycleTime >= enemyBulletCycleDuration) {
            // 跨越到新的周期
            enemyBulletCycleTime %= enemyBulletCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 游戏难度提升频率控制
     *
     * @return 是否应当产生提升游戏难度
     */
    private boolean setDifficultyTimeCountAndNewCycleJudge() {
        increaseDifficultyCycleTime += timeInterval;
        if (increaseDifficultyCycleTime >= increaseDifficultyCycleDuration) {
            // 跨越到新的周期
            increaseDifficultyCycleTime %= increaseDifficultyCycleDuration;
            return true;
        } else {
            return false;
        }
    }


    private void enemyShootAction() {
        // 敌机射击
        for (AbstractAircraft aircraft : enemyAircrafts) {
            enemyBullets.addAll(aircraft.shoot());
        }
    }

    private void heroShootAction() {
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void enemyAircraftMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    if (isMusic) {
                        new MusicThread("src/videos/bullet_hit.wav", false, false).start();
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // 获得分数，产生道具补给
                        if (enemyAircraft instanceof MobEnemy) {
                            score += enemyAircraft.getScoreAward();
                            generateBossScore += enemyAircraft.getScoreAward();
                        } else if (enemyAircraft instanceof EliteEnemy) {
                            score += enemyAircraft.getScoreAward();
                            generateBossScore += enemyAircraft.getScoreAward();
                            enemyAircraft.generateProp(props);
                        } else {
                            isBossAliveFlag = false;
                            score += enemyAircraft.getScoreAward();
                            // 重新开始计算产生Boss机的得分
                            generateBossScore = 0;

                            if (isMusic) {
                                bossBackgroundMusic.setIsLoop(false);
                                bossBackgroundMusic.setIsStop(true);
                            }

                            enemyAircraft.generateProp(props);
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
    }


    /**
     * 道具生效函数
     */
    private void propsTakeIntoEffect() {
        List<AbstractProp> propsGenerated = null;
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                if (prop instanceof BloodProp) {
                    if (isMusic) {
                        new MusicThread("src/videos/get_supply.wav", false, false).start();
                    }
                    prop.activate(heroAircraft, enemyAircrafts, enemyBullets);
                } else if (prop instanceof BombProp) {
                    if (isMusic) {
                        new MusicThread("src/videos/bomb_explosion.wav", false, false).start();
                    }
                    prop.activate(heroAircraft, enemyAircrafts, enemyBullets);
                    score += ((BombProp) prop).getScoresAward();
                    propsGenerated = ((BombProp) prop).getPropsGenerated();

                } else {
                    prop.activate(heroAircraft, enemyAircrafts, enemyBullets);
                }
                prop.vanish();
            }
        }
        if (propsGenerated != null) {
            props.addAll(propsGenerated);
        }
    }


    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * 4. 删除无效的道具
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(this.backgroundImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(this.backgroundImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);

        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
