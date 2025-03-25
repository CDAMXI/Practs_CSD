import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain1 implements Terrain {
    Viewer v;
    Lock l;
    Condition cond;
    public  Terrain1 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        l = new ReentrantLock();
        cond = l.newCondition();
    }
    
    public synchronized void     hi      (int a) {
        l.lock();
        try{v.hi(a); } finally{l.unlock();}  
    }
    
    public synchronized void     bye     (int a) {
        l.lock();
        try{cond.signalAll();}
        finally{l.unlock();}        
    }
    public synchronized void     move    (int a) throws InterruptedException {
        l.lock();
        try{
            v.turn(a); Pos dest=v.dest(a);
        
         
            while (v.occupied(dest)) {cond.await(); v.retry(a);}
            v.go(a); notifyAll();
        }
        finally{
            l.unlock();
        }
    }
}