package com.marcinkaminski.aWeather.utilities;

public class IconManagerUtils {

    public static final int OTHER_INDEX = 0;
    public static final int CLEAR_SKY_INDEX = 1;
    public static final int CLOUDY_INDEX = 2;
    public static final int STORMY_INDEX = 3;
    public static final int RAINY_INDEX = 4;
    public static final int SNOWY_INDEX = 5;
    public static final int FOGGY_INDEX = 6;

    private static final int[] weatherParameterArray = {0,1,2,4,10,20,40};

    public static int[] decode(String code,int[] decodedWeather) {
        return decode(Integer.parseInt(code),decodedWeather);
    }

    public static int[] decode(int code,int[] decodedWeather){
        if(decodedWeather.length!=7){
            return decodedWeather;
        }
        if(code==0){
            decodedWeather[OTHER_INDEX] = 1;
            return decodedWeather;
        }
        else {
            int digit = code % 10;
            switch (digit){
                case 1:
                    decodedWeather[CLEAR_SKY_INDEX] = 1;
                    break;
                case 2:
                    decodedWeather[CLOUDY_INDEX] = 1;
                    break;
                case 3:
                    decodedWeather[CLEAR_SKY_INDEX] = 1;
                    decodedWeather[CLOUDY_INDEX] = 1;
                    break;
                case 4:
                    decodedWeather[STORMY_INDEX] = 1;
                    break;
                case 5:
                    decodedWeather[CLEAR_SKY_INDEX] = 1;
                    decodedWeather[STORMY_INDEX] = 1;
                    break;
                case 6:
                    decodedWeather[STORMY_INDEX] = 1;
                    decodedWeather[CLOUDY_INDEX] = 1;
                    break;
                case 7:
                    decodedWeather[CLEAR_SKY_INDEX] = 1;
                    decodedWeather[CLOUDY_INDEX] = 1;
                    decodedWeather[STORMY_INDEX] = 1;
            }

            digit = (code%100)/10;

            switch (digit){
                case 1:
                    decodedWeather[RAINY_INDEX] = 1;
                    break;
                case 2:
                    decodedWeather[SNOWY_INDEX] = 1;
                    break;
                case 3:
                    decodedWeather[RAINY_INDEX] = 1;
                    decodedWeather[SNOWY_INDEX] = 1;
                    break;
                case 4:
                    decodedWeather[FOGGY_INDEX] = 1;
                    break;
                case 5:
                    decodedWeather[RAINY_INDEX] = 1;
                    decodedWeather[FOGGY_INDEX] = 1;
                    break;
                case 6:
                    decodedWeather[SNOWY_INDEX] = 1;
                    decodedWeather[FOGGY_INDEX] = 1;
                    break;
                case 7:
                    decodedWeather[RAINY_INDEX] = 1;
                    decodedWeather[SNOWY_INDEX] = 1;
                    decodedWeather[FOGGY_INDEX] = 1;
            }
            return decodedWeather;
        }
    }

    public static int encode(int[] weatherCondition){
        if(weatherCondition.length!=7){
            return 0;
        }
        else{
            int result=0;
            for(int i=0; i<weatherParameterArray.length;++i){
                result+=(weatherCondition[i]*weatherParameterArray[i]);
            }
            return result;
        }
    }

/**
 *  ICON CODE DOCUMENTATION
 *
 *  code values and text description of an icon
 *
 *  Section 1
 *  0 - other
 *  1 - sunny/clear sky
 *  2 - cloudy
 *  3 - partly cloudy
 *  4 - stormy
 *  5 - sunny and stormy
 *  6 - stormy and cloudy   #icon: stormy
 *  7 - partly cloudy and stormy    #icon: sunny and stormy
 *
 *  Section 2
 *  10 - rainy
 *  20 - snowy
 *  30 - rainy and snowy
 *  40 - foggy
 *  50 - foggy and rainy #icon: rainy
 *  60 - foggy and snowy #icon: snowy
 *  70 - foggy, rainy and snowy #icon: rainy and snowy
 *
 *  Section 3.1
 *  11 - rainy and sunny
 *  12 - rainy and cloudy
 *  13 - rainy and partly cloudy    #icon: rainy and sunny
 *  14 - rainy and stormy
 *  15 - rainy, sunny and stormy
 *  16 - rainy, stormy and cloudy   #icon: rainy and stormy
 *  17 - rainy, partly cloudy and stormy    #icon: rainy, sunny and stormy
 *
 *  Section 3.2
 *  21 - snowy and sunny
 *  22 - snowy and cloudy
 *  23 - snowy and partly cloudy    #icon: snowy and sunny
 *  24 - snowy and stormy
 *  25 - snowy, sunny and stormy
 *  26 - snowy, stormy and cloudy   #icon: snowy and stormy
 *  27 - snowy, partly cloudy and stormy    #icon snowy, sunny and stormy
 *
 *  Section 3.3
 *
 *  31 - rainy, snowy and sunny
 *  32 - rainy, snowy and cloudy    #icon: rainy and snowy
 *  33 - rainy, snowy and partly cloudy     #icon: rainy, snowy and sunny
 *  34 - rainy, snowy and stormy
 *  35 - rainy, snowy, sunny and stormy     #icon: rainy, snowy and stormy
 *  36 - rainy, snowy, stormy and cloudy    #icon: rainy, snowy and stormy
 *  37 - rainy, snowy, sunny, cloudy and stormy #icon: rainy, snowy and stormy
 *
 *  Section 3.4
 *  41 - foggy and sunny    #icon: partly cloudy
 *  42 - foggy and cloudy   #icon: cloudy
 *  43 - foggy and partly clody     #icon: partly cloudy
 *  44 - foggy and stormy   #icon: stormy
 *  45 - foggy, sunny and stormy    #icon: sunny and stormy
 *  46 - foggy, cloudy and stormy   #icon: stormy
 *  47 - foggy, sunny, cloudy and stormy    #icon: sunny and stormy
 *
 *  Section 4.5
 *  51 - foggy, rainy and sunny
 *
 *  ...
 *
 *  For the other codes didn't meant above (sections 3.3 to 3.7)
 *  replace word 'snowy' or 'rainy' with appropriate words from
 *  section 2.
 *
 *
*/

}
