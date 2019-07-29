package com.gcart.android.algonquins;


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

public class JNTScreen4 extends Activity {
	public String[] param = new String[10];
	public AppConfiguration appConf;
	public String jneKota;
	double[] price = new double[3];
	String[] reg = new String[3];
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jne1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 Button btnSubmit = new Button(this);
		 final Spinner spinner = new Spinner(this);
		 if (AppConfiguration.searchTariff.equalsIgnoreCase("")) {
			 TextView txt = new TextView(this);
			 txt.setText("Pencarian tariff tidak ditemukan");
			 container.addView(txt);
		 } else {
			 int idx = 0;
			 String[] tariff = AppConfiguration.splitString(AppConfiguration.searchTariff, ';', false);
			 String[] array = new String[tariff.length];
			 String paket = "";
			 for (int i = 0; i < tariff.length; i++) {
				if (i == 0) paket = "J&T";
     			if (i == 1) paket = "J&T";
     			if (i == 2) paket = "J&T";
     			array[idx] = paket + " : " + tariff[i];
     			price[i] = Double.parseDouble(tariff[i]);
     			reg[i] = paket;
     			idx++;
			 }
			 SpinnerAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
			 spinner.setAdapter(adapter);
			 container.addView(spinner);
		 }
		 btnSubmit.setText("Submit");
		 if (AppConfiguration.cekongkir == 0)
		 container.addView(btnSubmit);
		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!AppConfiguration.searchTariff.equalsIgnoreCase("")) {
					AppConfiguration.tariff = price[spinner.getSelectedItemPosition()];
					AppConfiguration.jnereg = reg[spinner.getSelectedItemPosition()];
					finish();
					Intent ps = new Intent(v.getContext(),PaymentScreen2.class);
		       	 	startActivity(ps);
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
	    	String ret = SendData.doSearchTariff(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        finish();
	        Intent jne = new Intent(context,JNTScreen4.class);
       	 	startActivity(jne);
	    }
	}
}
