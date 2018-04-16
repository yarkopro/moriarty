
public class AgitationMineA832A extends Mine {

    int FuzeGraduation;
    final private static int RECOMENDED_EXPLOSION_ATTITUDE = 110;

    AgitationMineA832A() {
        super();
        CorrTableFileName = "agitation";
        CHARGES_MAXIMAL_FIRING_RANGES = new int[]{935, 1545, 2025};
        CHARGES_MINIMAL_FIRING_RANGES = new int[]{155, 240, 307};
    }

    AgitationMineA832A(long X1, long Y1, long X2, long Y2, int windSpeed, int windAngle, int atmospherePressure, byte airTemperature, byte chargeTemperature, byte weightSign, byte velocityDec) {
        super(X1, Y1, X2, Y2, windSpeed, windAngle, atmospherePressure, airTemperature, chargeTemperature, weightSign, velocityDec);
        CorrTableFileName = "agitation";
        CHARGES_MAXIMAL_FIRING_RANGES = new int[]{935, 1545, 2025};
        CHARGES_MINIMAL_FIRING_RANGES = new int[]{155, 240, 307};
    }

    @Override
    int getCharge(int range) {
        int i;
        for (i = 0; i < CHARGES_MAXIMAL_FIRING_RANGES.length; i++) {
            if (range < CHARGES_MAXIMAL_FIRING_RANGES[i] && range > CHARGES_MINIMAL_FIRING_RANGES[i]) break;
        }
        i = (i + 1) * 2;
        return  i;
    }

    void findFuzeGrad() {
        this.FuzeGraduation = (int) this.CorrTableRow[12] - (int) (this.CorrTableRow[13] - RECOMENDED_EXPLOSION_ATTITUDE) / (int) this.CorrTableRow[14];
    }
}