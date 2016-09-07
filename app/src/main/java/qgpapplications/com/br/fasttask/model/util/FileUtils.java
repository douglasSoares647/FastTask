package qgpapplications.com.br.fasttask.model.util;

import java.io.File;

/**
 * Created by dhb_s on 9/3/2016.
 */
public class FileUtils {

    public static void deletePhotoFromPath(String path){
        File fdelete = new File(path);
        if (fdelete.exists()) {
           fdelete.delete();
        }
    }

}
