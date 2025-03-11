import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain2 implements Terrain {
    Viewer v;
    Lock lock;
    Condition[][] cond;
    
    public  Terrain2 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,"2");
        lock = new ReentrantLock();
        
        cond = new Condition[t][t];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                cond[i][j] = lock.newCondition();
            }
        }
    }
    
    public void hi(int a){
        try{
            lock.lock();
            v.hi(a);
        } finally{lock.unlock();}
    }
    
    public void bye(int a){
        try{
            lock.lock();
            Pos p = v.getPos(a);
            cond[p.x][p.y].signalAll();
            v.bye(a);
        } finally{lock.unlock();}
    }
    
    public void move(int a) throws InterruptedException {
        try{
            lock.lock();
            v.turn(a);
            Pos p = v.getPos(a);
            Pos dest = v.dest(a);
            while (v.occupied(dest)){
                cond[dest.x][dest.y].await();
                v.retry(a);
            }
            v.go(a);
            cond[p.x][p.y].signalAll();
        } finally{lock.unlock();}
    }
}
