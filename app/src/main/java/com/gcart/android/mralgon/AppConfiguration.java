package com.gcart.android.mralgon;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import gentalib.Product;
import gentalib.StockProduct;

public class AppConfiguration {
	public static ArrayList province = new ArrayList();
	public static ArrayList city = new ArrayList();
	public static ArrayList subdistrict = new ArrayList();
	public static ArrayList ekspedisi = new ArrayList();
	public static Double ttlweight;
	public static String totalongkir;
	public static String paket;
	public static String ctName;
	public static String customername;
	public static String listproduct;
	public static String ctAddress;
	public static String ctPhone;
	public static String ctHandphone;
	public static String ctPinbb;
	public static String ctEmail;
	public static String ctAccount1;
	public static String ctAccnumber1;
	public static String ctBank1;
	public static String ctAccount2;
	public static String ctAccnumber2;
	public static String ctBank2;
	public static String ctNews;
	public static String biodataName;
	public static String biodataPhone;
	public static String biodataAddress;
	public static String biodataEmail;
	public static String searchKota;
	public static String searchKecamatan;
	public static String searchTariff;
	public static String jneKota;
	public static String jneKecamatan;
	public static String jnereg;
	public static int cekongkir;
	public static String idorder;
	public static double tariff;
	public static double totalweight;
	public static double totalpayment;
	public static double totalbarang;
	
	public static String orderproductname;
	public static String orderqty;
	public static String ordercolor;
	public static String orderserian;
	public static String orderminorder;
	public static String orderidproduct;

	public static ArrayList productdetail = new ArrayList();
	public static String categoryproduct;
	public static String listcategory;

	public static Product product;
	public static List<StockProduct> listStock = new ArrayList<>();

	
	public SharedPreferences prefs;
	public Context mc;
	
	public AppConfiguration(Context c) {
		mc = c;
		prefs = PreferenceManager.getDefaultSharedPreferences(c);
	}
	
	public String get(String key) {
		String ret = prefs.getString(key, "");
		return ret;
	}
	
	public void set(String key,String value) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key,value);
		editor.commit(); 
	}  
	
	
	public static String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String eventDateString = null;

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT")); // GMT will always be supported by getTimeZone
		eventDateString = dateFormat.format(calendar.getTime());
		
		return eventDateString;
	}
	
	public static String[] splitString(final String data, final char splitChar, final boolean allowEmpty) {
        Vector v = new Vector();
        int indexStart = 0;
        int indexEnd = data.indexOf(splitChar);
        if (indexEnd != -1)
        {
            while (indexEnd != -1)
            {
                String s = data.substring(indexStart, indexEnd);
                if (allowEmpty || s.length() > 0)
                {
                    v.addElement(s);
                }
                indexStart = indexEnd + 1;
                indexEnd = data.indexOf(splitChar, indexStart);
            }

            if (indexStart != data.length())
            {
                // Add the rest of the string
                String s = data.substring(indexStart);
                if (allowEmpty || s.length() > 0)
                {
                    v.addElement(s);
                }
            }
        }
        else
        {
            if (allowEmpty || data.length() > 0)
            {
                v.addElement(data);
            }
        }

        String[] result = new String[v.size()];
        v.copyInto(result);
        return result;
    }
	
	public static String formatNumber(double amount) {
		String ret = NumberFormat.getInstance(Locale.US).format(amount);
		String ret2 = ret.replace(',', '.');
		return ret2;
	}
	
	public static String formatNumber(String amount) {
		double amt = Double.parseDouble(amount);
		String ret = NumberFormat.getInstance(Locale.US).format(amt);
		String ret2 = ret.replace(',', '.');
		return ret2;
	}
	
	public static boolean isEqualToString(String source, String pattern) {    
	    if (source == null || pattern==null)
	    	return false;
	    if(source.length()==0||pattern.length()==0||source.length()<pattern.length())
	    	return false;
	    source = source.toLowerCase();
	    pattern = pattern.toLowerCase();
	    try {
	    	 int index=0;
	 	    while(index<source.length() )
	 	    {
	 	    	if(source.charAt(index)==pattern.charAt(0))
	 	    	{
	 	    		String str=source.substring(index,index+pattern.length() );
	 	    		if(str.equalsIgnoreCase(pattern))//If you don't want case sensitive then use "str.equalsIgnoreCase(pattern)"
	 	    		{
	 	    			return true;
	 	    		}
	 	    	}
	 	    	index++;
	 	    }
	 	    return false;
	    } catch (StringIndexOutOfBoundsException e) {
	    	return false;
	    }
	   
	}

	//JSON Parser
	public static ArrayList parseJSON(String json) {
		ArrayList data = new ArrayList();
		try {
			JSONArray jsonArray = new JSONArray(json);
			if (jsonArray.length() == 0 ) {
				data.clear();
				return data;
			} else {
				data.clear();
				for (int i=0; i < jsonArray.length(); i++) {
					data.add(jsonArray.getJSONObject(i));
				}
			}
		} catch (JSONException e) {
			Log.d("please try again ", e.toString());
			data.clear();
			return data;
		}
		return data;
	}
	
}
