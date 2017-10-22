package me.rishabhkhanna.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etNumber;
    Button btnCall;
    int CALL_REQCODE = 123;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNumber = (EditText) findViewById(R.id.etNumber);
        btnCall = (Button) findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etNumber.getText().toString();
                int perm = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE);
                if (perm == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                            Manifest.permission.CALL_PHONE
                    },CALL_REQCODE);
                }
                makeCall(phone);
            }
        });
    }


    public  void makeCall(String phone){
        Intent i = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            Log.d(TAG, "makeCall: ");
            return;
        }
        startActivity(i);
    }
//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_REQCODE){
            Log.d(TAG, "onRequestPermissionsResult: ");
            for (int i =0 ; i < permissions.length ; i++){
                Log.d(TAG, "onRequestPermissionsResult: " + permissions[i]);
                if(permissions[i].equals(Manifest.permission.CALL_PHONE)){
                    Log.d(TAG, "onRequestPermissionsResult: ");
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: ");
                        makeCall(etNumber.getText().toString());
                    }else if (grantResults[i] == PackageManager.PERMISSION_DENIED){
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
