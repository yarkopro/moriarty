

public class FragMineO832DU extends Mine  {
    FragMineO832DU() {
        super();
        CorrTableFileName = "frag";
        CHARGES_MAXIMAL_FIRING_RANGES = new int[] {538, 1478, 2336, 3107, 3922};
        CHARGES_MINIMAL_FIRING_RANGES = new int[] {91, 245, 376, 488, 640} ;
    }
    FragMineO832DU(long X1, long Y1, long X2, long Y2, double windSpeed, double windAngle, int atmospherePressure, int airTemperature, int chargeTemperature, int weightSign, int velocityDec){
        super(X1,Y1,X2,Y2,windSpeed,windAngle,atmospherePressure,airTemperature,chargeTemperature,weightSign,velocityDec);
        CorrTableFileName = "D:\\Я\\Проекти\\Балістичний калькулятор\\mortar_calculator\\src\\frag";
        CHARGES_MAXIMAL_FIRING_RANGES = new int[] {538, 1478, 2336, 3107, 3922};
        CHARGES_MINIMAL_FIRING_RANGES = new int[] {91, 245, 376, 488, 640} ;
    }
}
