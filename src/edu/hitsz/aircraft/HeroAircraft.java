package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShootStrategy;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author wb-yu
 */
public class HeroAircraft extends AbstractAircraft {


    private static HeroAircraft instance;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction, boolean isHero) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction, isHero);
    }

    /**
     * 使用懒汉式方法创建英雄机实例以保证
     *
     * @return instance 唯一的英雄机实例
     */
    public static synchronized HeroAircraft getHeroAircraftInstance() {
        if (instance == null) {
            instance = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                    0,
                    0,
                    10000,
                    1,
                    30,
                    -1,
                    true);
            instance.setShootStrategy(new DirectShootStrategy());
        }
        return instance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void changeShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public int getShootNum() {
        return this.shootNum;
    }

}
