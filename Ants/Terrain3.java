import java.util.concurrent.*;
import java.util.concurrent.locks.*;
/**
 * Native monitor based Terrain
 * 
 * @author CSD Juansa Sendra
 * @version 2021
 */
public class Terrain3 implements Terrain {
    Viewer v;
    ReentrantLock lock;
    Condition[][] notAvailable;
    long defaultTimeoutMs = 1000;
    TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;
    
    
    public  Terrain3 (int t, int ants, int movs, String msg) {
        v=new Viewer(t,ants,movs,"3");
        lock = new ReentrantLock();
        
        notAvailable = new Condition[t][t]; //Tama�o del escenario
        
        for(int i = 0; i < t; i++){ //Crear los locks de cada celda del escenario
            for(int j = 0; j < t; j++){notAvailable[i][j] = lock.newCondition();}
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
        try{
            lock.lock();
            v.turn(a);
            Pos dest=v.dest(a); 
            Pos currentPos = v.getPos(a);
            while (v.occupied(dest)){
                if(!notAvailable[dest.x][dest.y].await(defaultTimeoutMs, defaultTimeUnit)){
                    v.chgDir(a);
                    dest = v.dest(a);
                }
                v.retry(a);
            }
            v.go(a);
            
            notAvailable[currentPos.x][currentPos.y].signal();
        } catch(Exception e){e.printStackTrace();}
        finally{lock.unlock();}
    }
}
