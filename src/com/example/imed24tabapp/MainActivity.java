package com.example.imed24tabapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
		StrictMode.setThreadPolicy(policy);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button buttonSend = (Button) findViewById(R.id.buttonSend);
		
		buttonSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				BufferedReader in = null;
				String data = null;
				try{
					HttpClient client = new DefaultHttpClient();
					//URI website = new URI("http://10.132.25.164:8081/workshops/rest/examination/bp?patientID=1234567&systolic=555&diastolic=777&puls=1234");
					//URI website = new URI("http://192.168.0.150:8080/workshops/rest/examination/bp?patientID=000&systolic=555&diastolic=777&puls=1234");
					
					
					String address = "http://192.168.0.150:8080/workshops/rest/examination/bp?";
					EditText addressing = (EditText) findViewById(R.id.adressing);
					URI website = null;
					
					if((addressing.getText().toString().equals("")) || (addressing.getText().toString().equals("brak")))
					{
						EditText patientID = (EditText) findViewById(R.id.patientID);
						EditText systolic = (EditText) findViewById(R.id.systolic);
						EditText diastolic = (EditText) findViewById(R.id.diastolic);
						EditText puls = (EditText) findViewById(R.id.puls);
						
						address += ("patientID=" + patientID.getText() + "&");
						address += ("diastolic=" + diastolic.getText() + "&");
						address += ("systolic=" + systolic.getText() + "&");
						address += ("puls=" + puls.getText());
						
						website = new URI(address);
					}
					else {
						website = new URI(addressing.getText().toString());
					}
					
					
					HttpGet request = new HttpGet();
					request.setURI(website);
					
					HttpResponse response = client.execute(request);
					in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					
					// strings
					StringBuffer sb = new StringBuffer("");
					String l = "";
					String nl = System.getProperty("line.separator");
					
					while((l = in.readLine()) != null){
						sb.append(l + nl);
					}
					
					in.close();
					data = sb.toString();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (in != null){
						try{
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
				TextView txtback = (TextView) findViewById(R.id.txtback);
				txtback.setText(data);
			}
 
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
