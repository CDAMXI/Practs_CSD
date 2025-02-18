// CSD feb 2015 Juansa Sendra
public class Pool2 extends Pool {//max kids/instructor
    private int intstructor, kids, ki;
    public synchronized void init(int ki, int cap){this.ki = ki;}
    public synchronized void kidSwims() throws InterruptedException{
        while(intstructor==0 || kids >= ki * intstructor){
            log.waitingToSwim();
            wait();
        }
        kids++;
        notifyAll();
        log.swimming();
    }
    public synchronized void kidRests(){
        kids--;
        notifyAll();
        log.resting();
    }
    public synchronized void instructorSwims(){
        log.swimming();
        intstructor++;
        notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException {
        while (kids > ki * (intstructor - 1)){
            log.waitingToRest();
            wait();
        }
        intstructor--;
        log.resting();
        notifyAll();
    }
}
