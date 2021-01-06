package com.gcart.android.mralgon;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SendData {
	//change this webfolder only
	static String webfolder = "mralgon";
	static String server = "https://tokoalgonquins.com/" + webfolder;
	static String server2= "https://tokoalgonquins.com/" + webfolder;
	static String controller = server + "/index.php/servicehttps2/";
	static String controllerweb = server + "/index.php/web/";
	static String folderimage = server + "/product/";
	static String rajaongkirapi = "f12c4a775295a1f6ae86fea58b1a8fd8";
	static String rajaongkirserver = "https://pro.rajaongkir.com/api";
	static String originkota = "2102";

	public static String doCancelOrder(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idorder",arg[1])
				.build();
		Request request = new Request.Builder()
				.url(controller + "cancelorderandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdateProduct(String offset) {
		String ret = "";
		RequestBody formBody = new FormEncodingBuilder()
				.add("offset",offset)
				.add("cat",AppConfiguration.categoryproduct)
				.build();
		Request request = new Request.Builder()
				.url(controller + "product").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		OkHttpClient client = new OkHttpClient();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doSearchProduct(String term) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("term",term)
				.add("cat",AppConfiguration.categoryproduct)
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchproduct").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doListstock(String id) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("id", id)
				.build();
		Request request = new Request.Builder()
				.url(controller + "liststock").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doUpdateResi() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadresi").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doSearchKota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchkota").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doSearchKecamatan(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchkecamatan").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doSearchTariff(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.add("kecamatan",arg[1])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchtariff").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doOrder(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idproduct",arg[1])
				.add("qty",arg[2])
				.add("news",arg[3])
				.add("shippingaddress",arg[4])
				.add("color",arg[5])
				.build();
		Request request = new Request.Builder()
				.url(controller + "neworderandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doOrderEcer(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("idproduct",arg[1])
				.add("color",arg[2])
				.build();
		Request request = new Request.Builder()
				.url(controller + "neworderecerandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}


	public static String doRefreshOrderPaid(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshorderandropaid").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshOrderPOPaid(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshorderandropopaid").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doRefreshOrder(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshorderandrov2").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshOrderPO(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshorderandropo").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doRefreshPaymentNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshpaymentandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshnotaandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRefreshNotaHistory(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "refreshnotaandrohistory").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doUpdate() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadproduct").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdateAcc() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadproductacc").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doUpdatePO() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadproductpo").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doLogin(String [] arg) {
		String ret = "";
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("password", arg[1])
				.build();
		Request request = new Request.Builder()
				.url(controller + "login").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		OkHttpClient client = new OkHttpClient();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doRegister(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("phone", arg[1])
				.add("address",arg[3])
				.add("email",arg[4])
				.add("password",arg[4])
				.build();
		Request request = new Request.Builder()
				.url(controller + "register").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;


	}

	public static String doRegisterIMEI(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("imei", arg[0])
				.add("phone", arg[1])
				.add("name",arg[2])
				.add("address",arg[3])
				.add("email",arg[4])
				.build();
		Request request = new Request.Builder()
				.url(controller + "registerimei").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doContact() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "printcontact").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static String doPayment(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("bank", arg[1])
				.add("account", arg[2])
				.add("amount",arg[3])
				.add("news",arg[4])
				.add("paymentdate",arg[5])
				.add("namapengirim","")
				.add("namapenerima","")
				.add("idorder",arg[6])
				.add("telepon","")
				.add("nohp","")
				.build();
		Request request = new Request.Builder()
				.url(controller + "newpaymentandro").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doPaymentNota(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("username", arg[0])
				.add("bank", arg[1])
				.add("account",arg[2])
				.add("amount",arg[3])
				.add("news",arg[4])
				.add("paymentdate",arg[5])
				.add("namapengirim",arg[6])
				.add("namapenerima",arg[7])
				.add("idorder",arg[8])
				.add("telepon",arg[9])
				.add("nohp",arg[10])
				.add("ongkir",arg[11])
				.add("kota",AppConfiguration.jneKota)
				.add("kecamatan",AppConfiguration.jneKecamatan)
				.add("jnereg",AppConfiguration.jnereg)
				.add("paket",AppConfiguration.paket)
				.build();
		Request request = new Request.Builder()
				.url(controller + "newnotaandrov2").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}



	public static String doUpdatePromo() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "downloadpromo").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doCaraOrder() {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.build();
		Request request = new Request.Builder()
				.url(controller + "printcaraorder").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	//J&T
	public static String doSearchKotaJnt(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchkotajnt").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String doSearchKecamatanJnt(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchkecamatanjnt").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}

	public static String doSearchTariffJnt(String [] arg) {
		String ret = "";
		OkHttpClient client = new OkHttpClient();
		RequestBody formBody = new FormEncodingBuilder()
				.add("kota", arg[0])
				.add("kecamatan",arg[1])
				.build();
		Request request = new Request.Builder()
				.url(controller + "searchtariffjnt").post(formBody)
				.addHeader("Accept-Encoding","identity")
				.build();
		try {
			Response response = client.newCall(request).execute();
			ret = response.body().string();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;

	}
	
}
