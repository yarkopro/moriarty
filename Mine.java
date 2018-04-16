import java.io.*;
import java.lang.Math;
//import java.math.BigDecimal;

public abstract class Mine {
    protected final static int DEFAULT_TEMPERATURE=15;
    protected final static int DEFAULT_ATMOSPHERIC_PRESSURE=750;
    protected long FirePositionX;//        Координата Х вогневої позиції (В системі Гауса-Крюгера)
    protected long FirePositionY;//        Координата Y вогневої позиції (Визначаються по карті)
    protected long TargetX;//              Координата Х цілі
    protected long TargetY;//              Коoрдината Y цілі
    protected static double WindSpeed;//         Швидкість вітру (м/с)
    protected static double WindAngle;//         Кут вітру (град.)
    protected static int AtmospherePressure;//   Атмосферний тиск (мм рт. ст.)
    protected static int AirTemperature;//       Температура (град. С)
    protected static int ChargeTemperature;//    Температура заряду
    protected int WeightSign;//                  Знак маси міни
    protected int VelocityDec;//                 Різниця швикості вильоту міни від табличної (знос ствола) (%)
    protected int Charge;//                      Заряд (0-Основний, 1-Перший, 2-Другий, 3-Третій, 4-Далекобійний)
    protected double Angle;//                    Кут основного напрямку стрільби (Радіан)
    protected int DirectAngle;//                 Дирекційний кут основного напрямку стрільби (горизонтальна наводка) Обл.Значень:  0 - 6000
    protected int Scope;//                       Приціл (вертикальна наводка) (Поділки) Обл.Значень: 333 - 1000
    protected int FiringRange;//                 Дальність стрільби (метрів)
    protected double VerticalCorrections;//      Вертикальні поправки (метрів)
    protected double HorizontalCorrections;//    Горизонтальні поправки (тисячні)
    protected String CorrTableFileName;//        Назва файлу з таблицею стрільб для даної міни даного заряду (ініціалізовується в конструкторах класів нащадків)
    protected double[] CorrTableRow= new double[15];//         Рядок з таблиці стрільб для заданої дальності
    protected static int[] CHARGES_MAXIMAL_FIRING_RANGES;//    Максимальні
    protected static int[] CHARGES_MINIMAL_FIRING_RANGES;

    Mine(){
        this.FirePositionX = 0;
        this.FirePositionY = 0;
        this.TargetX = 0;
        this.TargetY = 0;
    }

    Mine(long X1, long Y1, long X2, long Y2, double windSpeed, double windAngle, int atmospherePressure, int airTemperature, int chargeTemperature, int weightSign, int velocityDec){
        this.FirePositionX = X1;
        this.FirePositionY = Y1;
        this.TargetX = X2;
        this.TargetY = Y2;
        this.WindSpeed = windSpeed;
        this.WindAngle = windAngle;
        this.AtmospherePressure = atmospherePressure;
        this.AirTemperature = airTemperature;
        this.ChargeTemperature = chargeTemperature;
        this.WeightSign = weightSign;
        this.VelocityDec = velocityDec;
    }

    public double[] getTableLine(int FirstColumnValue){
        String TmpStrBuffer = new String();
        try(BufferedReader finCorrections = new BufferedReader(new FileReader(new File( this.CorrTableFileName+this.Charge+".txt")))){
            while(TmpStrBuffer!=null){
                TmpStrBuffer = finCorrections.readLine();
                if (Integer.parseInt(TmpStrBuffer.substring(0,TmpStrBuffer.indexOf(' ')))<=FirstColumnValue)break;
            }
            int i=0;
            for (String It : TmpStrBuffer.split(" ")) {
                this.CorrTableRow[i]=Double.parseDouble(It);
                i++;
            }
        }catch (FileNotFoundException e){e.printStackTrace();}
        catch (IOException f){f.printStackTrace();}
        return this.CorrTableRow;
    }

    int getCharge(int range){
        int i;
        for (i=0; i < CHARGES_MAXIMAL_FIRING_RANGES.length; i++) {
            if (range < CHARGES_MAXIMAL_FIRING_RANGES[i] && range > CHARGES_MINIMAL_FIRING_RANGES[i]) break;
        }
        //this.Charge=(byte)i;
        return i;
    }

    public void findFiringRange(){
        // sqrt((X1-X2)^2+(Y1-Y2)^2)
        this.FiringRange = (int)Math.round(Math.sqrt((FirePositionX - TargetX)*(FirePositionX - TargetX)+
                (FirePositionY-TargetY)*(FirePositionY-TargetY)));
    }

    public double findAngle(){
        //atan(|Y2-Y1|/|X2-X1|) - з. Зворотня геодезична задача
        // 1ша чверть координатної площини
        if ((TargetX == FirePositionX) && (TargetY > FirePositionY))
            this.Angle = 0;
        else if ((TargetX > FirePositionX) && (TargetY > FirePositionY))
            this.Angle = Math.atan(Math.abs((FirePositionY-TargetY) /(double) (FirePositionX-TargetX)));
            // 2га чверть
        else if ((TargetX > FirePositionX) && (TargetY == FirePositionY))
            this.Angle = Math.PI/2;
        else if ((TargetX > FirePositionX) && (TargetY < FirePositionY))
            this.Angle = Math.PI - Math.atan(Math.abs((FirePositionY-TargetY) / (double)(FirePositionX-TargetX)));
            // 3тя чверть
        else if ((TargetX == FirePositionX) && (TargetY < FirePositionY))
            this.Angle = Math.PI;
        else if ((TargetX < FirePositionX) && (TargetY < FirePositionY))
            this.Angle = Math.PI + Math.atan(Math.abs((FirePositionY-TargetY) / (FirePositionX-TargetX)));
            // 4та чверть
        else if ((TargetX < FirePositionX) && (TargetY == FirePositionY))
            this.Angle = Math.PI*3/2;
        else if ((TargetX < FirePositionX) && (TargetY > FirePositionY))
            this.Angle = Math.PI*2 - Math.atan(Math.abs((FirePositionY-TargetY) / (FirePositionX-TargetX)));
        return this.Angle;
    }

    void findHorizontalCorrections() {
        this.Charge = getCharge((int) this.FiringRange);
        getTableLine(FiringRange);
        //final BigDecimal constantArythmetic = newBigDecimal(-0.1);
        //final BigDecimal extraPrecisionHorizontalCorrection = constantArythmetic.multiply(new BigDecimal(CorrTableRow[3])).multiply(new Big);
        this.HorizontalCorrections = (-0.1) * CorrTableRow[3] * WindSpeed * Math.sin(Angle - WindAngle);
    }

    void findVerticalCorrections(){
        this.VerticalCorrections = ((-0.1) * CorrTableRow[4] * WindSpeed * Math.cos(Angle - WindAngle)) +
                ((0.1) * (-CorrTableRow[5] + CorrTableRow[6] * (AtmospherePressure - DEFAULT_ATMOSPHERIC_PRESSURE)) * (AtmospherePressure - DEFAULT_ATMOSPHERIC_PRESSURE)) +
                ((-0.1) * CorrTableRow[7] * (AirTemperature - DEFAULT_TEMPERATURE)) + ((-0.1) * CorrTableRow[8] * (ChargeTemperature - DEFAULT_TEMPERATURE)) -
                (CorrTableRow[9] * VelocityDec) - (CorrTableRow[10] * WeightSign);

        if (this.Charge != getCharge(FiringRange + (int)VerticalCorrections)) ;
    }

    void findScope() {
        getTableLine(this.FiringRange + (int) this.VerticalCorrections);
        this.Scope = (int) Math.round(CorrTableRow[1] + CorrTableRow[2] * (this.FiringRange + (int) Math.round(this.VerticalCorrections)) % 100 / 50.0);
    }

    void findDirectAngle(){
        this.Angle=Math.round((this.Angle+this.HorizontalCorrections)*3000/Math.PI);
    }

}