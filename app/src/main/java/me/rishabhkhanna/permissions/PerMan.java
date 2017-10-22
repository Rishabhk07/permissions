package me.rishabhkhanna.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by rishabhkhanna on 22/10/17.
 */

public class PerMan {
    static int REQ_CODE = 123;
    interface onPermisionResultListener{
        void onSuccess();
        void onDenied();
    }
    static onPermisionResultListener resultListener;
    static void askForPermissions(Activity activity,String permission, onPermisionResultListener oprl){
        if(ContextCompat.checkSelfPermission(activity,permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,new String[]{
                    permission
            },REQ_CODE);
        }else{
            oprl.onSuccess();
        }

        resultListener = oprl;
    }

    static void onResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if(requestCode == 123){
            for (int i =0; i < permissions.length ;i++){
                if(permissions[i].equals(Manifest.permission.CALL_PHONE)){
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        resultListener.onSuccess();
                    }else{
                        resultListener.onDenied();
                    }
                }
            }
        }

    }
}
