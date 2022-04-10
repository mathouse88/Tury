/*
Problem „Tury”
    Program uruchamia 2 wątki o numerach 1 i 2 (Szkielet)
    procesy wypisują na ekran swoje numery
    Proszę spowodować, żeby kolejność wypisywania numerów była: 1,2,1,2, ...
 */

class Sync {
    public int tura = 1;
}

class T1 extends Thread {
    private Sync s;
    public T1(Sync s) { this.s = s; }
    public void run() {
        for (int i = 1; i <= 10; ++i) {
            synchronized(s) {
                while (s.tura != 1)
                    try { s.wait(); } catch(InterruptedException e) { };
                System.out.println("1");
                s.tura = 2;
                s.notifyAll();
            }
        }
    }
}

class T2 extends Thread {
    private Sync s;
    public T2(Sync s) { this.s = s; }
    public void run() {
        for (int i = 1; i <= 10; ++i) {
            synchronized(s) {
                while (s.tura != 2)
                    try { s.wait(); } catch(InterruptedException e) { };
                s.tura = 1;
                System.out.println("2");
                s.notifyAll();
            }
        }
    }
}

public class Tury {
    public static void main(String args[]) {
        Sync s = new Sync();
        T1 t1 = new T1(s);
        T2 t2 = new T2(s);
        t1.start();
        t2.start();
    }
}
