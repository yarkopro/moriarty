

public class FragMine3O12 extends FragMineO832DU {
    FragMine3O12(long X1, long Y1, long X2, long Y2, double windSpeed, double windAngle, int atmospherePressure, int airTemperature, int chargeTemperature, int weightSign, int velocityDec){
        super(X1, Y1, X2, Y2, windSpeed, windAngle, atmospherePressure, airTemperature, chargeTemperature, weightSign, velocityDec);
    }
    void findVerticalCorrections() {
        super.findVerticalCorrections();
        if(this.Charge==3) this.VerticalCorrections *= 0.99; // На 1% менша
    }
}