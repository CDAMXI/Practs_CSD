import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain2 implements Terrain {
    Viewer v;
    Lock l;
    Condition [][] cond;
    public  Terrain2 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,msg);
        l = new ReentrantLock();
        cond = new Condition [t][t];
        for(int i = 0; i < t; i++){
            for(int j = 0; j < t; i++){
                cond [i][j] = l.newCondition();
            }
        }
    }
    
    public synchronized void     hi      (int a) {
        l.lock();
        try{
            v.hi(a); 
        } finally{
            l.unlock();
        }  
    }
    
    public synchronized void     bye     (int a) {
        Pos p = v.getPos(a);
        l.lock();
        try{
            v.bye(a);
            cond [p.x][p.y].signalAll();        
        }finally{
            l.unlock();
        }        
    }
    public synchronized void     move    (int a) throws InterruptedException {
        Pos p = v.getPos(a);
        l.lock();
        try{
            v.turn(a); Pos dest=v.dest(a);
            while (v.occupied(dest)) {
                cond[dest.x][dest.y].await(); v.retry(a);
            }
            v.go(a); cond [p.x][p.y].signalAll();
        }finally{
            l.unlock();
        }
    }
}
