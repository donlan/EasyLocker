package dong.lan.lock.lockAction;

/**
 * Created by 梁桂栋 on 2016年08月15日 13:26.
 * Email:760625325@qq.com
 * github:gitbub.com/donlan
 * desc:
 */
public class NoLockStatusListenerException extends Exception {

    public NoLockStatusListenerException(String exStr){
        super(exStr);
    }
    public NoLockStatusListenerException(){
        this("没有设置锁屏状态监听");
    }
}
