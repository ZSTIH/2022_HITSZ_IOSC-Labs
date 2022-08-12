package edu.hitsz.aircraft;


import edu.hitsz.application.Main;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.prop.AbstractProp;

import java.util.List;

/**
 * 抽象敌机
 *
 * @author wb-yu
 */
public abstract class AbstractEnemyAircraft extends AbstractAircraft {


    /**
     * 产生道具的概率
     * generatePropProbability 精英机、Boss机击毁时产生道具的概率
     * rateBetweenBombAndBlood 产生炸弹道具与产生加血道具的概率之比
     * rateBetweenBulletAndBlood 产生火力道具与产生加血道具的概率之比
     */
    private final double generatePropProbability = 0.9;
    private final double rateBetweenBombAndBlood = 1;
    private final double rateBetweenBulletAndBlood = 1;
    /**
     * 被击毁后给予英雄机的分数奖励
     */
    protected int scoreAward;

    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, 1, false);
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    public int getScoreAward() {
        return this.scoreAward;
    }

    public void generateProp(List<AbstractProp> props) {
        PropFactory propFactory;
        if (Math.random() > generatePropProbability) {
            return;
        }
        double randomNumber = Math.random() * (1 + rateBetweenBombAndBlood + rateBetweenBulletAndBlood);
        if (randomNumber < 1) {
            propFactory = new BloodPropFactory();
        } else if (randomNumber < 1 + rateBetweenBombAndBlood) {
            propFactory = new BombPropFactory();
        } else {
            propFactory = new BulletPropFactory();
        }
        props.add(propFactory.createProp(locationX, locationY, 0, 10));
    }

}
