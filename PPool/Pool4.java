// CSD feb 2013 Juansa Sendra

public class Pool4 extends Pool {//kids cannot enter if there are instructors waiting to exit
    private int intstructor, kids, ki, cap;
    private boolean insWaitingToRest;
    public synchronized void init(int ki, int cap){
        this.ki = ki;
        this.cap = cap;
    }
    public synchronized void kidSwims() throws InterruptedException{
        while(intstructor==0 || kids >= ki * intstructor || intstructor + kids >= cap || insWaitingToRest){
            log.waitingToSwim();
            wait();
        }
        kids++;
        notifyAll();
        log.swimming();
    }
    public synchronized void kidRests(){
        log.resting();
        kids--;
        notifyAll();
    }
    public synchronized void instructorSwims() throws InterruptedException {
        while ((intstructor + kids) >= cap) {
            wait();
        }
        log.swimming();
        intstructor++;
        notifyAll();
    }
    public synchronized void instructorRests() throws InterruptedException {
        while (kids > ki * (intstructor - 1)){
            log.waitingToRest();
            insWaitingToRest = true;
            wait();
        }
        intstructor--;
        log.resting();
        notifyAll();
        insWaitingToRest = false;
    }
}
