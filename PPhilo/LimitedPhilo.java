// CSD Mar 2013 Juansa Sendra

public class LimitedPhilo extends Philo {
    public LimitedPhilo(int id, int cycles, int delay, Table table) {super(id, cycles, delay, table);}
    public void run(){
        try{
            for(int i = 0; i < 4; i++){
                table.takeL(id);delay();table.takeR(id);
                table.eat(id); randomDelay();
                table.dropR(id); delay(); table.dropL(id);
                table.ponder(id); randomDelay();
            }
            table.end(id);
        }catch(Exception e){}
    }
}
