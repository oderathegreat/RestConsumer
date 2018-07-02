package com.valentishealth.app.restconsumer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

public class NextActivity extends AppCompatActivity {
    private static final String TAG ="POST_DATA" ;
    EditText inputcounty,inputward,inputresidence,inputkin,inputkincontact;
    Button registerBtn;
    ProgressDialog progressBar;

    String firstName,lastName,idNumber,phoneNumber;
    String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        inputcounty = findViewById(R.id.inputCounty);
        inputward = findViewById(R.id.inputWard);
        inputresidence = findViewById(R.id.inputAreaOfResidence);
        inputkin = findViewById(R.id.inputNextOfKin);
        inputkincontact = findViewById(R.id.inputNextKinContact);
        registerBtn = findViewById(R.id.btnRegister);
        firstName=getIntent().getStringExtra("firstName");
        lastName=getIntent().getStringExtra("lastName");
        idNumber=getIntent().getStringExtra("idNumber");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        imgPath=getIntent().getStringExtra("profile");

        progressBar=new ProgressDialog(this);
        progressBar.setMessage("Registering.....");

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting user input
                String county = inputcounty.getText().toString().trim();
                String ward = inputward.getText().toString().trim();
                final String residence = inputresidence.getText().toString().trim();
                String kin = inputkin.getText().toString().trim();
                String kinContact = inputkincontact.getText().toString().trim();
                String url = "http://jistymarketer.com/data/RequestHandler.php";
                //Async Task operation
                AsyncHttpClient httpClient = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("county", county);
                params.put("ward", ward);
                params.put("residence", residence);
                params.put("kin", kin);
                params.put("kinContact", kinContact);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("idNumber",idNumber);
                params.put("phoneNumber",phoneNumber);
                File myFile = new File(imgPath);
                try {
                    params.put("fileToUpload", myFile);
                } catch(FileNotFoundException e) {}

                progressBar.show();
                httpClient.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(NextActivity.this, "Failed To Connect", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.d(TAG, "onSuccess: "+responseString);
                        progressBar.dismiss();
                        try {
                            JSONObject object=new JSONObject(responseString);
                            String result = object.getString("result");
                            if (result.toLowerCase().contains("success"))
                                Toast.makeText(NextActivity.this, "Your registration was succesfull", Toast.LENGTH_SHORT).show();

                            else
                                Toast.makeText(NextActivity.this, "Your registration failed", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
