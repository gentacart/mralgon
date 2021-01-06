package com.gcart.android.mralgon;

import com.gcart.android.mralgon.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	public String[] param = new String[2];

	public Context ct;
	public AppConfiguration appConf;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration appConf = new AppConfiguration(this);
		if (appConf.get("loginusername").equalsIgnoreCase("")) {
			finish();
			Intent login = new Intent(this,LoginScreen.class);
			startActivity(login);
		} else {
			finish();
			Intent home = new Intent(this,HomeScreen.class);
			startActivity(home);
		}
		ct = this;
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	 
	
}
