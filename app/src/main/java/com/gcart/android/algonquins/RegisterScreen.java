package com.gcart.android.algonquins;

import com.gcart.android.algonquins.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterScreen extends Activity {
	public String[] param = new String[5];
	public String imei = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		final Button button = (Button) findViewById(R.id.regSubmit);
	     button.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	EditText regPhone = (EditText)findViewById(R.id.regPhone);
	        	EditText regName = (EditText)findViewById(R.id.regName);
	        	EditText regAddress = (EditText) findViewById(R.id.regAddress);
	        	EditText regEmail = (EditText) findViewById(R.id.regEmail);
	        	if (regPhone.getText().toString().equalsIgnoreCase("") || regName.getText().toString().equalsIgnoreCase("")) {
	        		Toast.makeText(v.getContext(), "Lengkapi biodata anda untuk diaktifkan", Toast.LENGTH_SHORT).show();
	        	} else {
	        		param[0] = imei;
		        	param[1] = regPhone.getText().toString();
		        	param[2] = regName.getText().toString();
		        	param[3] = regAddress.getText().toString();
		        	param[4] = regEmail.getText().toString();
		        	new GetTask(v.getContext()).execute();
	        	}
	         }
	     });
	}
	
	class GetTask extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    GetTask(Context context) {
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
	    	String ret = SendData.doRegisterIMEI(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
        	Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();
	    }
	}
	
}


