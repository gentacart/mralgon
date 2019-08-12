package com.gcart.android.mralgon;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class JNTScreen3 extends Activity {
	public String[] param = new String[10];
	public AppConfiguration appConf;
	public String jneKota;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jne1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 Button btnSubmit = new Button(this);
		 final Spinner spinner = new Spinner(this);
		 if (AppConfiguration.searchKecamatan.equalsIgnoreCase("")) {
			 TextView txt = new TextView(this);
			 txt.setText("Pencarian kecamatan tidak ditemukan");
			 container.addView(txt);
		 } else {
			 String[] array = AppConfiguration.splitString(AppConfiguration.searchKecamatan, ';', false);
			 SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
			 spinner.setAdapter(adapter);
			 container.addView(spinner);
			 btnSubmit.setText("Submit");
			 container.addView(btnSubmit);
		 }
		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!AppConfiguration.searchKecamatan.equalsIgnoreCase("")) {
					AppConfiguration.jneKecamatan = spinner.getSelectedItem().toString();
					param[0] = AppConfiguration.jneKota;
					param[1] = AppConfiguration.jneKecamatan;
					new DoSearchTariff(v.getContext()).execute();
				} 
				
			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
	
	
	class DoSearchTariff extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoSearchTariff(Context context) {
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
	    	String ret = SendData.doSearchTariffJnt(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        finish();
	        AppConfiguration.searchTariff = result;
	        Intent jne = new Intent(context,JNTScreen4.class);
       	 	startActivity(jne);
	    }
	}
}
