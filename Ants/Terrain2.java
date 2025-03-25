import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain2 implements Terrain {
    Viewer v;
    ReentrantLock lock;
    Condition[] occupied;
    
    private int grid_size;
    
    public  Terrain2 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,"2");
        lock = new ReentrantLock();
        grid_size = t;
        
        occupied = new Condition[grid_size * grid_size];
        
        for(int i = 0; i < grid_size; i++){
            for(int j = 0; j < grid_size; j++){
                occupied[j + i*grid_size] = lock.newCondition();
            }
        }
        
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
        lock.lock();
        try{
            v.turn(a);
            Pos dest=v.dest(a); 
            Pos currentPos = v.getPos(a);
            while (v.occupied(dest)){
                occupied[dest.x*grid_size + dest.y].await();
                v.retry(a);
            }
            
            v.go(a);
            occupied[currentPos.x * grid_size + currentPos.y].signalAll();
        } catch(Exception e){e.printStackTrace();}
        finally{lock.unlock();}
    }
}
