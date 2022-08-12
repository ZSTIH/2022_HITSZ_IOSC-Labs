package edu.hitsz.prop;

/**
 * @author wb-yu
 */
public interface Observer {

    /**
     * 更新观察者的状态
     *
     * @param flag 是否需要销毁当前飞行物
     *             观察者的更新方法
     */
    void update(boolean flag);
}
