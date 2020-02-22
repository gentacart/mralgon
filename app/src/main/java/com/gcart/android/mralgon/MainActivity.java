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
	public String imei = "";
	public Context ct;
	public AppConfiguration appConf;
	public static final int REQUEST_READ_PHONE_STATE = 112;

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
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case REQUEST_READ_PHONE_STATE:
				if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
					//TODO
					Toast.makeText(ct,"Terima kasih",Toast.LENGTH_LONG).show();
					TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
					imei = telephonyManager.getDeviceId();
					new CheckImei(this).execute();
				}
				break;

			default:
				break;
		}
	}
	
	class CheckImei extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    CheckImei(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Please wait...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String [] p = {imei};
	    	String ret = SendData.doCheckIMEI(p);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
			String [] res = AppConfiguration.splitString(result, '~', false);
			if(res[0].equalsIgnoreCase("1")) {
				AppConfiguration.customername = res[1];
				finish();
				Intent homeScreen = new Intent(context,HomeScreen.class);
				startActivity(homeScreen);
			} else if (res[0].equalsIgnoreCase("0")) {
				finish();
				Intent registerScreen = new Intent(context,RegisterScreen.class);
				startActivity(registerScreen);
			} else {
				finish();
				Toast.makeText(context, "Ada masalah dalam koneksi jaringan internet", Toast.LENGTH_SHORT).show();
			}
	    }
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	 
	
}
