package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;

/**
 * @author wb-yu
 */
public interface PropFactory {

    /**
     * 用于通过工厂模式创建道具实例
     *
     * @param locationX 道具的初始X坐标
     * @param locationY 道具的初始Y坐标
     * @param speedX    道具的初始X方向速度
     * @param speedY    道具的初始Y方向速度
     * @return 返回创建的道具实例
     */
    AbstractProp createProp(int locationX, int locationY, int speedX, int speedY);

}
