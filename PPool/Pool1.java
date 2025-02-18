// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {//no kids alone
    private int intstructor, kids;
    public synchronized void init(int ki, int cap){}
    public synchronized void kidSwims() throws InterruptedException{
        while(intstructor==0){
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
        while (intstructor == 1 && kids > 0){
            log.waitingToRest();
            wait();
        }
        intstructor--;
        log.resting();
        notifyAll();
    }
}
