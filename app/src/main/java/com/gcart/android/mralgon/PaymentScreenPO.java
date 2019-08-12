package com.gcart.android.mralgon;

import com.gcart.android.mralgon.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentScreenPO extends Activity {
	public String[] param = new String[20];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kirim1);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration.tariff = 0;
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 final String[] arrorder = AppConfiguration.splitString(appConf.get("order"), '~', false);
		 if (arrorder.length == 0) {
			 TextView title = new TextView(this);
			 title.setText("Your order is empty");
			 container.addView(title);
		 } else {
			 	int totalqty = 0;
			 	double totalpayment = 0;
			 	double totalweight = 0;
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				totalqty += Integer.parseInt(row[2]);
				totalpayment += Double.parseDouble(row[4]);
				totalweight += Double.parseDouble(row[9]);
			 }
			 TextView info = new TextView(this);
			 info.setText("HARAP CENTANG DAN KLIK TOMBOL DI BAWAH UNTUK MELAKUKAN PENGIRIMAN");
			 TextView txtqty = new TextView(this);
			 txtqty.setText("Total Barang: " + totalqty);
			 TextView txtpayment = new TextView(this);
			 txtpayment.setText("Total Harga : " + totalpayment);
			 container.addView(txtqty);
			 container.addView(txtpayment);
			 final CheckBox[] chk = new CheckBox[arrorder.length];
			 final String[] chkstr = new String[arrorder.length];
			 
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				 CheckBox ch = new CheckBox(this);
				 ch.setText(row[1] + " " + row[6] + "\nJumlah : " + row[2] + " Warna : " + row[6] + "\nNews : "  + row[12]);
				 container.addView(ch);
				 chk[i] = ch;
				 chkstr[i] = arrorder[i] + ";";
			 }
			 Button btnSubmit = new Button(this);
			 btnSubmit.setText("Ekspedisi Lain");
			 Button btnKirim = new Button(this);
			 btnKirim.setText("Kirim JNE");
			 Button btnAmbil = new Button(this);
			 btnAmbil.setText("Ambil Toko");

			 
			 btnAmbil.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String ret = "";
						String ret2 = "";
						int totalqty = 0;
					 	double totalpayment = 0;
					 	double totalweight = 0;
						for(int i = 0; i < arrorder.length; i++) {
							if(chk[i].isChecked()) {
								String[] _rw = AppConfiguration.splitString(chkstr[i], ';', false);
								ret += chkstr[i] + "~";
								ret2 += _rw[11] + ",";
								totalqty += Integer.parseInt(_rw[2]);
								totalpayment += Double.parseDouble(_rw[4]);
								totalweight += Double.parseDouble(_rw[9]);
							}
							AppConfiguration.totalbarang = totalqty;
							AppConfiguration.totalpayment = totalpayment;
							AppConfiguration.totalweight = totalweight;
						}
						if(ret.equalsIgnoreCase("")) {
							Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
						} else {
							String bank = "-";
							String rekening = "-";
							String jumlah = "-";
							String pengirim = "Ambil Toko";
							String nohp = "-";
							String penerima = "Ambil Toko";
							String telepon = "-";
							String alamat = "-";
							
							TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
							String imei = telephonyManager.getDeviceId();
							param[0] = imei;
							param[1] = "-";
							param[2] = "-";
							param[3] = "-";
							param[4] = alamat;
							param[5] = "-";
							param[6] = pengirim;
							param[7] = penerima;
							param[8] = ret2;
							param[9] = telepon;
							param[10] = nohp;
							param[11] = String.valueOf(0);
							new DoPayment(v.getContext()).execute();
						}
					}
				});
			 
			 btnSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String ret = "";
					String ret2 = "";
					int totalqty = 0;
				 	double totalpayment = 0;
				 	double totalweight = 0;
					for(int i = 0; i < arrorder.length; i++) {
						if(chk[i].isChecked()) {
							String[] _rw = AppConfiguration.splitString(chkstr[i], ';', false);
							ret += chkstr[i] + "~";
							ret2 += _rw[11] + ",";
							totalqty += Integer.parseInt(_rw[2]);
							totalpayment += Double.parseDouble(_rw[4]);
							totalweight += Double.parseDouble(_rw[9]);
						}
						AppConfiguration.totalbarang = totalqty;
						AppConfiguration.totalpayment = totalpayment;
						AppConfiguration.totalweight = totalweight;
					}
					if(ret.equalsIgnoreCase("")) {
						Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
					} else {
						appConf.set("confirmorder",ret2);
						appConf.set("confirmorderdetail", ret);
						finish();
						Intent pscreen = new Intent(v.getContext(),PaymentScreen2.class);
						startActivity(pscreen);
					}
				}
			});
			 
			 btnKirim.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String ret = "";
						String ret2 = "";
						int totalqty = 0;
					 	double totalpayment = 0;
					 	double totalweight = 0;
						for(int i = 0; i < arrorder.length; i++) {
							if(chk[i].isChecked()) {
								String[] _rw = AppConfiguration.splitString(chkstr[i], ';', false);
								ret += chkstr[i] + "~";
								ret2 += _rw[11] + ",";
								totalqty += Integer.parseInt(_rw[2]);
								totalpayment += Double.parseDouble(_rw[4]);
								totalweight += Double.parseDouble(_rw[9]);
							}
							AppConfiguration.totalbarang = totalqty;
							AppConfiguration.totalpayment = totalpayment;
							AppConfiguration.totalweight = totalweight;
						}
						if(ret.equalsIgnoreCase("")) {
							Toast.makeText(v.getContext(), "Pilih Order anda", Toast.LENGTH_SHORT).show();
						} else { 
							appConf.set("confirmorder",ret2);
							appConf.set("confirmorderdetail", ret);
							finish();
							Intent pscreen = new Intent(v.getContext(),JNEScreen.class);
							startActivity(pscreen);
						}
					}
				});
			 
		 }
	}
	
	class DoPayment extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoPayment(Context context) {
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
	    	String ret = SendData.doPaymentNota(param);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	    	Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
        	finish();
	    }
	}
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

}
