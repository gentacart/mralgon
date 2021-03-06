package com.gcart.android.mralgon;

import com.gcart.android.mralgon.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Boolean.TRUE;

public class PaymentScreen extends Activity {
	public String[] param = new String[20];
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kirim2);
		appConf = new AppConfiguration(getApplicationContext());
		AppConfiguration.tariff = 0;
		AppConfiguration.jneKecamatan = "";
		AppConfiguration.jneKota = "";
		AppConfiguration.jnereg = "";
		AppConfiguration.totalongkir = "0";
		AppConfiguration.paket = "";
		LinearLayout container = (LinearLayout)findViewById(R.id.layoutIdx);
		LinearLayout container3 = (LinearLayout)findViewById(R.id.layoutId2);
		TableLayout container2 = (TableLayout)findViewById(R.id.androtable);
		container2.setStretchAllColumns(TRUE);
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
			 container.addView(info);
			 container.addView(txtqty);
			 container.addView(txtpayment);
			 final CheckBox[] chk = new CheckBox[arrorder.length];
			 final String[] chkstr = new String[arrorder.length];
			 final String[] idorders = new String[arrorder.length];
			 for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);
				 TableRow tr = new TableRow(this);
				 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						 LinearLayout.LayoutParams.MATCH_PARENT,
						 LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
				 tr.setLayoutParams(params);

				 Button bt = new Button(this);
				 bt.setText("CANCEL");
				 bt.setMaxWidth(20);
				 tr.setGravity(Gravity.CENTER);
				 tr.setMinimumHeight(300);


				 idorders[i] = row[11];
				 final String _idorder = row[11];
				 CheckBox ch = new CheckBox(this);
				 ch.setText(row[1] + " " + row[6] + "\nJumlah : " + row[2] + " Warna : " + row[6] + "\nNews : "  + row[12]);
				 ch.setWidth(240);

				 bt.setOnClickListener(new View.OnClickListener() {
					 @Override
					 public void onClick(View view) {
						 String username = appConf.get("loginusername");
						 param[0] = username;
						 param[1] = _idorder;
						 new DoCancel(view.getContext()).execute();
					 }
				 });

				 if(i % 2 == 0) {
					 tr.setBackgroundColor(Color.parseColor("#dcdee2"));
					 //ch.setBackgroundColor(Color.parseColor("#dcdee2"));
				 }
				 tr.setPadding(0,0,10,0);
				 tr.addView(ch);
				 tr.addView(bt);


				 container2.addView(tr);
				 chk[i] = ch;
				 chkstr[i] = arrorder[i] + ";";
			 }
			/* for(int i = 0; i < arrorder.length; i++) {
				 String[] row = AppConfiguration.splitString(arrorder[i], ';', false);

				 TableRow tr = new TableRow(this);
				 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						 LinearLayout.LayoutParams.MATCH_PARENT,
						 LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
				 tr.setLayoutParams(params);

				 Button bt = new Button(this);
				 bt.setText("CANCEL");
				 bt.setMaxWidth(60);
				 tr.setGravity(Gravity.CENTER);
				 //tr.setMinimumHeight(300);

				 idorders[i] = row[11];
				 final String _idorder = row[11];

				 bt.setOnClickListener(new View.OnClickListener() {
					 @Override
					 public void onClick(View view) {
						 String username = appConf.get("loginusername");
						 param[0] = username;
						 param[1] = _idorder;
						 new DoCancel(view.getContext()).execute();
					 }
				 });

				 if(i % 2 == 0) {
					 tr.setBackgroundColor(Color.parseColor("#dcdee2"));
					 //ch.setBackgroundColor(Color.parseColor("#dcdee2"));
				 }
				 tr.setPadding(0,0,10,0);



				 CheckBox ch = new CheckBox(this);
				 ch.setText(row[1] + " " + row[6] + "\nJumlah : " + row[2] + " Warna : " + row[6] + "\nNews : "  + row[12]);
				 ch.setTextSize(0,50);
				 tr.addView(ch);
				 tr.addView(bt);

				 container2.addView(tr);
				 chk[i] = ch;
				 chkstr[i] = arrorder[i] + ";";
			 }*/
			 Button btnSubmit = new Button(this);
			 btnSubmit.setText("Ekspedisi Lain");
			 Button btnKirim = new Button(this);
			 btnKirim.setText("Kirim JNE / JNT / POS");
			 Button btnAmbil = new Button(this);
			 btnAmbil.setText("Ambil Toko");
			 Button btnTitip = new Button(this);
			 btnTitip.setText("Titip Toko");
			 Button btnJnt = new Button(this);
			 btnJnt.setText("Kirim J&T");
			 Button btnLain = new Button(this);
			 btnLain.setText("Kirim Ekspedisi Lain");
			 container3.addView(btnAmbil);
			 container3.addView(btnSubmit);
			 container3.addView(btnKirim);
			 //container.addView(btnJnt);
			 //container.addView(btnLain);
			 container3.addView(btnTitip);
			 
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

							String username = appConf.get("loginusername");
							param[0] = username;
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
			 
			 btnTitip.setOnClickListener(new View.OnClickListener() {
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
							Intent pscreen = new Intent(v.getContext(),PaymentScreen2Titip.class);
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
							new DoSearchProvince(v.getContext()).execute();
						}
					}
				});
			 
			 btnJnt.setOnClickListener(new View.OnClickListener() {
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
							Intent pscreen = new Intent(v.getContext(),JNTScreen.class);
							startActivity(pscreen);
						}
					}
				});
			 
		 }
	}

	class DoCancel extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoCancel(Context context) {
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
			String ret = SendData.doCancelOrder(param);
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

	class DoSearchProvince extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoSearchProvince(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(this.context);
			mDialog.setMessage("please wait...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("https://api.rajaongkir.com/starter/province")
					.get()
					.addHeader("key", SendData.rajaongkirapi)
					.build();
			try {
				Response response = client.newCall(request).execute();
				return response.body().string();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			AppConfiguration appConf = new AppConfiguration(context);
			mDialog.dismiss();
			Log.d("province",result);
			try {
				JSONObject jo = new JSONObject(result);
				JSONObject jo2 = new JSONObject(jo.getString("rajaongkir"));
				AppConfiguration.province = AppConfiguration.parseJSON(jo2.getString("results"));
			} catch (JSONException e){
				Log.d("jsonerror",e.toString());
			}

			//appConf.set("product", result);
			//AppConfiguration.listproduct = result;
			finish();
			Intent productList = new Intent(context,Province.class);
			startActivity(productList);
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
