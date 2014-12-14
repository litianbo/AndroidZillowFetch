package com.example.zillowsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private Button search;
	private EditText addressBox;
	private EditText cityBox;
	private Spinner state;
	TextView viewnodata,space3,space4,space5;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addItemsOnState();
		search = (Button) findViewById(R.id.searchbutton);
		search.setBackgroundColor(Color.GRAY);
		viewnodata = (TextView)findViewById (R.id.nodatafound);
		space3 = (TextView)findViewById (R.id.space3);
		space4 = (TextView)findViewById (R.id.space4);
		space5 = (TextView)findViewById (R.id.space5);
	}
	public void sendData(View view) {
		intent = new Intent(this, SearchActivity.class);
		addressBox = (EditText) findViewById(R.id.address2);
		String address = addressBox.getText().toString();
		cityBox = (EditText) findViewById(R.id.city2);
		String city = cityBox.getText().toString();
		String stateText = state.getSelectedItem().toString();
		String url = "http://litianbo.elasticbeanstalk.com/?";
		String[] addressSeparated = address.split(" ");
		String[] citySeparated = city.split(" ");
		String addressParse = "";
		String cityParse = "";
		String result= "";
		if (city.length() < 1)
			space4.setText("This field is required");
		else
			space4.setText("");
		if (stateText.length() < 1)
			space5.setText("This field is required");
		else
			space5.setText("");
		if (address.length() < 1)
			space3.setText("This field is required");
		else
			space3.setText("");
		if (city.length() < 1 || stateText.length() < 1 || address.length() < 1){
			return;
			
		}
		for (int i = 0; i < addressSeparated.length; i++) {
			addressParse += addressSeparated[i];
			if (i != addressSeparated.length - 1)
				addressParse += "+";

		}
		for (int i = 0; i < citySeparated.length; i++) {
			cityParse += citySeparated[i];
			if (i != citySeparated.length - 1)
				cityParse += "+";

		}
		url += "streetaddress=" + addressParse + "&city=" + cityParse
				+ "&state=" + stateText;
		
		new MyAsyncTask().execute(url, null, null);
		
	}
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();

		return result;

	}
	class MyAsyncTask extends AsyncTask<String, Void, Void> {
		//private ProgressDialog progressDialog = new ProgressDialog(
		//		MainActivity.this);
		InputStream inputStream = null;
		String url = "";
		String result = "";
		
		@Override
		protected Void doInBackground(String... arg0) {
			url = arg0[0];
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				// Convert response to string using String Builder
				if (inputStream != null) {
					result = convertInputStreamToString(inputStream);

				} else {
					result = "Error";
				}

			} catch (Exception e) {
				Log.d("InputStream", e.getLocalizedMessage());
			}
			return null;
		}
		protected void onPostExecute(Void v) {
			if (result.equals("nodatafound")){
				viewnodata.setText("No Exact Match Found--Verify that the given address is correct");
			}else{
				intent.putExtra("result", result); // Optional parameters
				startActivity(intent);
			}
		}
	}
	public void addItemsOnState() {
		state = (Spinner) findViewById(R.id.state2);
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("AK");
		list.add("AL");
		list.add("AZ");
		list.add("AR");
		list.add("CA");
		list.add("CO");
		list.add("CT");
		list.add("DE");
		list.add("FL");
		list.add("GA");
		list.add("HI");
		list.add("ID");
		list.add("IL");
		list.add("IN");
		list.add("IA");
		list.add("KS");
		list.add("KY");
		list.add("LA");
		list.add("ME");
		list.add("MD");
		list.add("MA");
		list.add("MI");
		list.add("MN");
		list.add("MS");
		list.add("MO");
		list.add("MT");
		list.add("NE");
		list.add("NV");
		list.add("NH");
		list.add("NJ");
		list.add("NM");
		list.add("NY");
		list.add("NC");
		list.add("ND");
		list.add("OH");
		list.add("OK");
		list.add("OR");
		list.add("PA");
		list.add("RI");
		list.add("SC");
		list.add("SD");
		list.add("TN");
		list.add("TX");
		list.add("UT");
		list.add("VT");
		list.add("VA");
		list.add("WA");
		list.add("WV");
		list.add("WI");
		list.add("WY");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		state.setAdapter(dataAdapter);

	}

	public void addListenerOnSpinnerItemSelection() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
