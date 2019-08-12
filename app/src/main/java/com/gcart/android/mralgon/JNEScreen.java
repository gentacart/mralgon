package com.gcart.android.mralgon;

import com.gcart.android.mralgon.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class JNEScreen extends Activity {
	public String[] param = new String[10];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jne1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		
		 
		 Button btnSubmit = new Button(this);
		 final EditText edtKota = new EditText(this);
		 edtKota.setHint("Masukan Kota Tujuan :");
		 container.addView(edtKota);
		 btnSubmit.setText("Submit");
		 container.addView(btnSubmit);
		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String kota = edtKota.getText().toString();
				if (kota.equalsIgnoreCase("")) {
					Toast.makeText(v.getContext(), "Isi semua field", Toast.LENGTH_SHORT).show();
				} else {
					param[0] = kota;
					new DoSearchKota(v.getContext()).execute();
				}
				
			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	class DoSearchKota extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoSearchKota(Context context) {
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
	    	String ret = SendData.doSearchKota(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        AppConfiguration.searchKota = result;
	        finish();
	        Intent jne = new Intent(context,JNEScreen2.class);
       	 	startActivity(jne);
	    }
	}
}
