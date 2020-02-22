package com.gcart.android.mralgon;

import com.gcart.android.mralgon.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeScreen extends Activity {
	public AppConfiguration appConf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		appConf = new AppConfiguration(this);
		final TextView tx = (TextView) findViewById(R.id.txtName);
		final Button btnProduct = (Button) findViewById(R.id.btnHomeProduct);
		final Button btnLogout = (Button) findViewById(R.id.btnHomeLogout);
		//final Button btnProductAcc = (Button) findViewById(R.id.btnHomeProductAcc);
		final Button btnProductPO = (Button) findViewById(R.id.btnHomeProductPO);
		final Button btnOrder = (Button) findViewById(R.id.btnHomeOrder);
		final Button btnOrderPO = (Button) findViewById(R.id.btnHomeOrderPO);
		final Button btnOngkir = (Button) findViewById(R.id.btnOngkir);
		final Button btnNota = (Button) findViewById(R.id.btnHomeNota);
		final Button btnNotaHistory = (Button) findViewById(R.id.btnHomeNotaHistory);
		final Button btnPaymentNota = (Button) findViewById(R.id.btnHomePaymentNota);
		//final Button btnBiodata = (Button) findViewById(R.id.btnHomeBiodata);
		//final Button btnPromo = (Button) findViewById(R.id.btnHomePromo);
		//final Button btnSaran = (Button) findViewById(R.id.btnHomeSaran);
		//final Button btnCaraOrder = (Button) findViewById(R.id.btnHomeCaraOrder);
		final Button btnContact = (Button) findViewById(R.id.btnHomeContact);
		final Button btnResi = (Button) findViewById(R.id.btnHomeResi);
		tx.setText("Hi " + appConf.get("loginusername") + ", Selamat berbelanja");
		//button click
		btnProduct.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 AppConfiguration.categoryproduct = "0";
	        	new DoUpdateProduct(v.getContext()).execute();
	         }
		});

		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				appConf.set("loginusername", "");
				finish();
				Intent main = new Intent(v.getContext(),MainActivity.class);
				startActivity(main);
			}
		});
		
		btnOngkir.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 1;
				 new DoSearchProvince(v.getContext()).execute();
	         }
		});
		
		btnResi.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	new DoUpdateResi(v.getContext()).execute();
	         }
		});
		
		
		btnProductPO.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
				 AppConfiguration.categoryproduct = "0";
				 new DoUpdateProduct(v.getContext()).execute();
	         }
		});
		
		btnOrder.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoRefreshOrder(v.getContext()).execute();
	         }
		});
		
		btnOrderPO.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoRefreshOrderPO(v.getContext()).execute();
	         }
		});
		/*
		btnPromo.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	new DoUpdatePromo(v.getContext()).execute();
	         }
		});
		*/
		
		btnNota.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoRefreshNota(v.getContext()).execute();
	         }
		});
		
		btnNotaHistory.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoRefreshNotaHistory(v.getContext()).execute();
	         }
		});
		
		btnPaymentNota.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoRefreshPaymentNota(v.getContext()).execute();
	         }
		});
		

		btnContact.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View v) {
	        	 AppConfiguration.cekongkir = 0;
	        	 new DoContact(v.getContext()).execute();
	         }
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			this.moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
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

	class DoUpdateProduct extends AsyncTask<Object, Void, String> {
		Context context;
		ProgressDialog mDialog;

		DoUpdateProduct(Context context) {
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
			String ret = SendData.doUpdateProduct("0");
			return ret;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.dismiss();
			appConf.set("product", result);
			AppConfiguration.listproduct = result;
			Intent productList = new Intent(context,ProductList.class);
			startActivity(productList);
		}
	}

	class DoCaraOrder extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoCaraOrder(Context context) {
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
	    	String ret = SendData.doCaraOrder();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        AppConfiguration.ctNews = result;
	        Intent caraorderScreen = new Intent(context,CaraOrderScreen.class);
       	 	startActivity(caraorderScreen);
	    }
	}
	
	class DoUpdatePromo extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoUpdatePromo(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Download...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doUpdatePromo();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        AppConfiguration appConf = new AppConfiguration(context);
	        mDialog.dismiss();
	        appConf.set("product", result);
	        Intent resiList = new Intent(context,PromoList.class);
       	 	startActivity(resiList);
	    }
	}
	
	
	class DoContact extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoContact(Context context) {
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
	    	String ret = SendData.doContact();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        mDialog.dismiss();
	        String[] arr = AppConfiguration.splitString(result, ';', false);
	        AppConfiguration.ctName = arr[0];
	        AppConfiguration.ctAddress = arr[1];
	        AppConfiguration.ctPhone = arr[2];
	        AppConfiguration.ctHandphone = arr[3];
	        AppConfiguration.ctPinbb = arr[4];
	        AppConfiguration.ctEmail = arr[5];
	        AppConfiguration.ctAccount1 = arr[6];
	        AppConfiguration.ctAccnumber1 = arr[7];
	        AppConfiguration.ctBank1 = arr[8];
	        AppConfiguration.ctAccount2 = arr[9];
	        AppConfiguration.ctAccnumber2 = arr[10];
	        AppConfiguration.ctBank2 = arr[11];
	        AppConfiguration.ctNews = arr[12];
	        Intent contactScreen = new Intent(context,ContactScreen.class);
       	 	startActivity(contactScreen);
	    }
	}
	

	class DoRefreshOrder extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoRefreshOrder(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("download your order...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String[] arg = new String[1];
			String username = appConf.get("loginusername");
	    	arg[0] = username;
	    	String ret = SendData.doRefreshOrder(arg);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        appConf.set("order", result);
	        mDialog.dismiss();
	        Intent orderScreen = new Intent(context,PaymentScreen.class);
       	 	startActivity(orderScreen);
	    }
	} 
	
	class DoRefreshOrderPO extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoRefreshOrderPO(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("download your order...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String[] arg = new String[1];
			String username = appConf.get("loginusername");
	    	arg[0] = username;
	    	String ret = SendData.doRefreshOrderPO(arg);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        appConf.set("order", result);
	        mDialog.dismiss();
	        Intent orderScreen = new Intent(context,PaymentScreenPO.class);
       	 	startActivity(orderScreen);
	    }
	} 
	
	class DoRefreshNota extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoRefreshNota(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("download your nota...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String[] arg = new String[1];
			String username = appConf.get("loginusername");
	    	arg[0] = username;
	    	String ret = SendData.doRefreshNota(arg);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        appConf.set("paymentproses", result);
	        mDialog.dismiss();
	        Intent pScreen = new Intent(context,PaymentScreen3.class);
       	 	startActivity(pScreen);
	    }
	} 
	
	class DoRefreshNotaHistory extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoRefreshNotaHistory(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("download your nota...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String[] arg = new String[1];
			String username = appConf.get("loginusername");
	    	arg[0] = username;
	    	String ret = SendData.doRefreshNotaHistory(arg);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        appConf.set("paymentproses", result);
	        mDialog.dismiss();
	        Intent pScreen = new Intent(context,PaymentScreen3History.class);
       	 	startActivity(pScreen);
	    }
	} 
	
	
	class DoRefreshPaymentNota extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoRefreshPaymentNota(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("download your nota...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String[] arg = new String[1];
			String username = appConf.get("loginusername");
	    	arg[0] = username;
	    	String ret = SendData.doRefreshPaymentNota(arg);
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        appConf.set("paymentproses", result);
	        mDialog.dismiss();
	        Intent pScreen = new Intent(context,PaymentHistoryNotaScreen.class);
       	 	startActivity(pScreen);
	    }
	} 
	
	class DoUpdate extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoUpdate(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Download catalog...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doUpdate();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        AppConfiguration appConf = new AppConfiguration(context);
	        mDialog.dismiss();
	        appConf.set("product", result);
	        AppConfiguration.listproduct = result;
	        Intent productList = new Intent(context,ProductList.class);
       	 	startActivity(productList);
	    }
	}
	
	class DoUpdateAcc extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoUpdateAcc(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Download catalog...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doUpdateAcc();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        AppConfiguration appConf = new AppConfiguration(context);
	        mDialog.dismiss();
	        appConf.set("product", result);
	        Intent productList = new Intent(context,ProductList.class);
       	 	startActivity(productList);
	    }
	}
	
	class DoUpdatePO extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoUpdatePO(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Download catalog...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doUpdatePO();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        AppConfiguration appConf = new AppConfiguration(context);
	        mDialog.dismiss();
	        appConf.set("product", result);
	        AppConfiguration.listproduct = result;
	        Intent productList = new Intent(context,ProductList.class);
       	 	startActivity(productList);
	    }
	}
	
	class DoUpdateResi extends AsyncTask<Object, Void, String> {
	    Context context;
	    ProgressDialog mDialog;
	    
	    DoUpdateResi(Context context) {
	        this.context = context;
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        mDialog = new ProgressDialog(this.context);
	        mDialog.setMessage("Download...");
	        mDialog.show();
	    }

	    @Override
	    protected String doInBackground(Object... params) {
	    	String ret = SendData.doUpdateResi();
	    	return ret;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	        AppConfiguration appConf = new AppConfiguration(context);
	        mDialog.dismiss();
	        appConf.set("product", result);
	        AppConfiguration.listproduct = result;
	        Intent resiList = new Intent(context,ResiList.class);
       	 	startActivity(resiList);
	    }
	}

}
