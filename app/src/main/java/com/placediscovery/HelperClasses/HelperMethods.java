package com.placediscovery.HelperClasses;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Dhawan Gupta on 10-10-2015.
 */
public class HelperMethods {

    private static final String TAG = HelperMethods.class.getSimpleName();
    static private Context context;


    /*
   * use this method to save any object to Cache memory
   * */

    /*
    * use to check internet connectivity
    */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isInternetAvailable(Context context)
    {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            Network[] networks = connectivity.getAllNetworks();
            if (networks != null) {
                for (Network net : networks) {
                    NetworkInfo anInfo = connectivity.getNetworkInfo(net);
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

    public static void saveObjectToCache(String filename, Object data) {
        File cacheDir = new File(context.getCacheDir(), "app_directory");
        cacheDir.mkdir();
        try {
            FileOutputStream fos = new FileOutputStream(new File(cacheDir, filename));
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * use this to read any object from Cache using filename
    * */
    public static Object readObjectFromFile(String filename){
        Object obj=null;
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

}
