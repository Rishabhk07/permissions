package me.rishabhkhanna.permissions;

/**
 * Created by rishabhkhanna on 22/10/17.
 */

public class PerMan {
    interface onPermisionResultListener{
        void onSuccess();
        void onDenied();
    }
    static void askForPermissions(String permission, onPermisionResultListener oprl){

    }
}
