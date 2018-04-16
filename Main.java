
public class Main {
    public static void main(String[] args){
    Mine P = new FragMine3O12(150333, 480120, 151150, 480212, 2.7, 1.5, 758, 9, 9, 1, 0);
        long time = System.currentTimeMillis();
        P.findFiringRange();
        P.findAngle();
        P.findHorizontalCorrections();
        P.findVerticalCorrections();
        P.findDirectAngle();
        P.findScope();
        System.out.println(System.currentTimeMillis()-time);
    }
}