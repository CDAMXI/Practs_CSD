package Ants;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
@SuppressWarnings("unused")
public class Terrain1 implements Terrain {
    Viewer v;
    ReentrantLock lock;
    Condition occupied;
    
    public  Terrain1 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,"1");
        lock = new ReentrantLock();
        occupied = lock.newCondition();
        
        for(int i = 0; i < ants; i++){new Ant(i,this,movs).start();}
    }
    
    public synchronized void hi(int a){
        try{
            lock.lock();
            v.hi(a);
        }catch(Exception e){e.printStackTrace();}
        finally{lock.unlock();}
    }
    
    public synchronized void bye(int a){
        try{
            lock.lock();
            v.bye(a);
        }catch(Exception e){e.printStackTrace();}
        finally{lock.unlock();}
    }
    
    public synchronized void move(int a) throws InterruptedException {
        try{
            lock.lock();
            v.turn(a);
            Pos dest=v.dest(a); 
            while (v.occupied(dest)){
                occupied.await();
                v.retry(a);
            }
            
            v.go(a);
        } catch(Exception e){e.printStackTrace();}
        finally{lock.unlock();}
    }
}
