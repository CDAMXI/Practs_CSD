import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain1 implements Terrain {
    Viewer v;
    Lock lock;
    Condition cond;
    
    public  Terrain1 (int t, int ants, int movs, String msg) {
        v = new Viewer(t,ants,movs,msg);
        lock = new ReentrantLock();
        cond = lock.newCondition();
    }
    
    public void hi(int a){
        try{
            lock.lock();
            v.hi(a);
        }finally{lock.unlock();}
    }
    
    public void bye(int a){
        try{
            lock.lock();
            cond.signalAll();
            v.bye(a);
        }finally{lock.unlock();}
    }
    
    public void move(int a) throws InterruptedException {
        try{
            lock.lock();
            v.turn(a);
            Pos dest = v.dest(a); 
            while (v.occupied(dest)){
                cond.await();
                v.retry(a);
            }
            v.go(a);
            cond.signalAll();
        }finally{lock.unlock();}
    }
}
