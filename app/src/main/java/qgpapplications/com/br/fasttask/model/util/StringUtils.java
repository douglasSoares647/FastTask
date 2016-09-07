package qgpapplications.com.br.fasttask.model.util;

/**
 * Created by dhb_s on 9/3/2016.
 */
public class StringUtils {


    public static boolean validateString(String message){
        if(message.trim().isEmpty()){
            return false;
        }
        return true;
    }
}
