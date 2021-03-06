package com.gcart.android.mralgon;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentScreen2Titip extends Activity {
	public String[] param = new String[12];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment1);
		appConf = new AppConfiguration(getApplicationContext());
		 LinearLayout container = (LinearLayout)findViewById(R.id.layoutId);
		 String[] arrorder = AppConfiguration.splitString(appConf.get("confirmorder"), ';', false);
		 String[] arrorderdetail = AppConfiguration.splitString(appConf.get("confirmorderdetail"), '~', false);
		 int totalbarang = 0;
		 double totalbayar = 0;
		 double totalweight = 0;
		 for(int i = 0; i < arrorderdetail.length; i++) {
			 String[] _rw = AppConfiguration.splitString(arrorderdetail[i], ';', false);
			totalbarang += Integer.parseInt(_rw[2]);
			totalbayar += Double.parseDouble(_rw[4]);
			totalweight += Double.parseDouble(_rw[9]);
		 }
		 final double totalongkir = AppConfiguration.tariff * Math.ceil(totalweight);
		 TextView txtTotalbarang = new TextView(this);
		 txtTotalbarang.setText("Total Barang : " + totalbarang + " Pcs");
		 TextView txtOngkir = new TextView(this);
		 txtOngkir.setText("Ongkir : " + totalongkir + " IDR");
		 TextView txtTotalbayar = new TextView(this);
		 txtTotalbayar.setText("Total Harga + Ongkir : " + AppConfiguration.formatNumber(totalbayar + totalongkir) + " IDR");
		 TextView txtTotalberat = new TextView(this);
		 txtTotalberat.setText("Total Berat : " + AppConfiguration.formatNumber(totalweight) + " Kg");
		 container.addView(txtTotalbarang);
		 container.addView(txtOngkir);
		 container.addView(txtTotalbayar);
		 container.addView(txtTotalberat);
		 Button btnSubmit = new Button(this);
		 final EditText edtBank = new EditText(this);
		 final EditText edtRekening = new EditText(this);
		 final EditText edtJumlah = new EditText(this);
		 final EditText edtPengirim = new EditText(this);
		 final EditText edtHandphone = new EditText(this);
		 final EditText edtPenerima = new EditText(this);
		 final EditText edtTelepon = new EditText(this);
		 final EditText edtAlamat = new EditText(this);
		 edtBank.setHint("A/N. BANK :");
		 edtRekening.setHint("A/N. REKENING ANDA : ");
		 edtJumlah.setHint("Total Bayar + Ongkir : ");
		 edtPengirim.setHint("Nama Pengirim : ");
		 edtHandphone.setHint("No HP Pengirim : ");
		 edtPenerima.setHint("Titip ke Toko : ");
		 edtTelepon.setHint("Nomor Telepon : ");
		 edtAlamat.setHint("Alamat Toko : ");
		 edtRekening.setInputType(InputType.TYPE_CLASS_NUMBER);
		 edtRekening.setInputType(InputType.TYPE_CLASS_NUMBER);
		 edtJumlah.setInputType(InputType.TYPE_CLASS_NUMBER);
		 edtTelepon.setInputType(InputType.TYPE_CLASS_PHONE);
		 edtHandphone.setInputType(InputType.TYPE_CLASS_PHONE);
		 //container.addView(edtJumlah);
		 //container.addView(edtBank);
		 //container.addView(edtRekening);
		 //container.addView(edtPengirim);
		 //container.addView(edtHandphone);
		 container.addView(edtPenerima);
		 //container.addView(edtTelepon);
		 container.addView(edtAlamat);
		 final DatePicker datePicker = new DatePicker(this);
		 //container.addView(datePicker);
		 btnSubmit.setText("KONFIRMASI");
		 container.addView(btnSubmit);
		 
		 btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String bank = edtBank.getText().toString();
				String rekening = edtRekening.getText().toString();
				String jumlah = edtJumlah.getText().toString();
				String pengirim = "Titip Toko";
				String nohp = edtHandphone.getText().toString();
				String penerima = edtPenerima.getText().toString();
				String telepon = edtTelepon.getText().toString();
				String alamat = edtAlamat.getText().toString();

				String username = appConf.get("loginusername");
				param[0] = username;
				param[1] = "";
				param[2] = "";
				param[3] = "";
				param[4] = alamat;
				//param[5] = "";
				param[5] = datePicker.getYear()  + "-" + checkDigit(datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth() + " 00:00:00";
				param[6] = pengirim;
				param[7] = penerima;
				param[8] = appConf.get("confirmorder");
				param[9] = telepon;
				param[10] = nohp;
				param[11] = String.valueOf(totalongkir);
				new DoPayment(v.getContext()).execute();	
			}
		});
         
		 
	}
	
	
	
	public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
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
}
