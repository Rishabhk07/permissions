package me.rishabhkhanna.permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    EditText etNumber;
    Button btnCall;
    public static final String TAG = "Pemrission Activity";
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = (EditText) findViewById(R.id.etNumber);
        btnCall = (Button) findViewById(R.id.btnCall);

        final PerMan.onPermisionResultListener onPermisionResultListener  = new PerMan.onPermisionResultListener() {
            @Override
            public void onSuccess() {
                makeCall(phone);
            }
            @Override
            public void onDenied() {
                Toast.makeText(PermissionActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        };
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = etNumber.getText().toString();
                PerMan.askForPermissions(PermissionActivity.this,Manifest.permission.CALL_PHONE, onPermisionResultListener);
            }
        });
    }

    public  void makeCall(String phone){
        Intent i = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
        if(ContextCompat.checkSelfPermission(PermissionActivity.this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED){
            Log.d(TAG, "makeCall: ");
            return;
        }
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PerMan.onResult(requestCode,permissions,grantResults);
    }
}
