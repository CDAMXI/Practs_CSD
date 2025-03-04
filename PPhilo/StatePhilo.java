// CSD Mar 2013 Juansa Sendra


public class StatePhilo {
    //              0        1       2      3     4       5       6        7      8      9           10          11   12    13
    //StatePhilos philo: inactive ponder, wtalk, talk, wtakeL, wtakeR, wtakeLR, talkR, talkL, wtakeLhasR, wtakeRhasL, eat, rest, restL
    final int[] e; final boolean[] f; //5 StatePhilos philo, 5 StatePhilos fork (free)

    public StatePhilo() {e=new int[5]; f=new boolean[5]; for (int i=0; i<5; i++) {e[i]=0; f[i]=true;}}
    public StatePhilo(StatePhilo est) {e=new int[5]; f=new boolean[5]; for (int i=0; i<5; i++) {e[i]=est.e[i]; f[i]=est.f[i];}}
    public StatePhilo(StatePhilo est, int idx, int x, boolean[] f) {this(est); e[idx]=x; for (int i=0; i<5; i++) this.f[i]=f[i];}
    public String toString() {
        String s="   .P..*..T.*T..T**T*.T][T.*T][T*[E].R.[R.";
        StringBuffer sb=new StringBuffer();
        for (int i=4; i>=0; i--) sb.append(s.substring(3*e[i],3*e[i]+3));
        return sb.toString();
    }
    public void draw(BoxPhilo b) {b.StatePhilo(this);}
    public int get(int i) {return e[i];}
    public boolean free(int i) {return f[i];}
}


