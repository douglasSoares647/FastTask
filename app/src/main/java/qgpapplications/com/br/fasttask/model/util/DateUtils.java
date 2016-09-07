package qgpapplications.com.br.fasttask.model.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by c1284521 on 18/11/2015.
 */
public final class DateUtils {

    public static String convertDateToString(Date date){
        String convertedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            convertedDate = dateFormat.format(date);
        }
        catch(Exception e){
            Log.i("ERRO", "ERRO AO CONVERTER DATA PARA STRING");
        }
        return convertedDate;
    }


    public static String getFormatedDate(Integer day, Integer month, Integer year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,day);

        return new SimpleDateFormat("EEE, d MMM yyyy").format(calendar.getTime());
    }
}
