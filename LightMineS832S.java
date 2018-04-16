

public class LightMineS832S extends Mine {

    int FuzeGraduation;
    final private static int RECOMENDED_EXPLOSION_ATTITUDE = 300;

    LightMineS832S() {
        super();
        CorrTableFileName = "light";
        CHARGES_MAXIMAL_FIRING_RANGES = new int [] {538, 1478, 2336, 3107, 3922};
        CHARGES_MINIMAL_FIRING_RANGES = new int [] {91, 245, 376, 488, 640} ;
    }

    LightMineS832S(long X1, long Y1, long X2, long Y2, int windSpeed, int windAngle, int atmospherePressure, byte airTemperature, byte chargeTemperature, byte weightSign, byte velocityDec){
        super(X1,Y1,X2,Y2,windSpeed,windAngle,atmospherePressure,airTemperature,chargeTemperature,weightSign,velocityDec);
        CorrTableFileName = "light";
        CHARGES_MAXIMAL_FIRING_RANGES = new int [] {538, 1478, 2336, 3107, 3922};
        CHARGES_MINIMAL_FIRING_RANGES = new int [] {91, 245, 376, 488, 640} ;
    }

    void findFuzeGrad(){
        this.FuzeGraduation=(int)this.CorrTableRow[12]-(int)(this.CorrTableRow[13]-RECOMENDED_EXPLOSION_ATTITUDE)/(int)this.CorrTableRow[14];
    }
}