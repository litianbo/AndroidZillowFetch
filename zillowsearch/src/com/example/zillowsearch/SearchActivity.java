package com.example.zillowsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class SearchActivity extends ActionBarActivity {
	TextView view1, view3, view4, view5, view6, view7, view8, view9, view10,
			view11, view12, view13, view14, view15, view16, view17, view18,
			view19, view20, view21, view22, view23, view24, view25, view26,
			view27, view28, view29, view30, view31, view32, view33, graphText,
			addressText, view131, view132, zillowdisclaimer1,
			zillowdisclaimer2;
	String homeurl1, homeaddress1, lastsoldprice, overallchange, chartURL;
	TableLayout layout1;
	FragmentPagerAdapter adapter;
	ViewPager mPager;
	private ImageSwitcher imageSwitcher;
	Button btnNext, btnPrev;
	ImageButton share;
	List<String> charturls = new ArrayList<String>();
	int imageIds[] = { R.drawable.image1, R.drawable.image2, R.drawable.image3 };
	// to keep current Index of ImageID array
	int currentIndex = 0;
	Intent intent;
	private UiLifecycleHelper uiHelper;
	private static final String TAG = "MainFragment";
	FacebookDialog shareDialog;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Basic Info");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Basic Info");
		tabHost.addTab(spec1);

		TabSpec spec2 = tabHost.newTabSpec("Graph");
		spec2.setIndicator("Graph");
		spec2.setContent(R.id.tab2);
		tabHost.addTab(spec2);
		// change tab textcolor
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.parseColor("#ffffff"));
		}
		view1 = (TextView) findViewById(R.id.textview1);
		view3 = (TextView) findViewById(R.id.textview3);
		view4 = (TextView) findViewById(R.id.textview4);
		view5 = (TextView) findViewById(R.id.textview5);
		view6 = (TextView) findViewById(R.id.textview6);
		view7 = (TextView) findViewById(R.id.textview7);
		view8 = (TextView) findViewById(R.id.textview8);
		view9 = (TextView) findViewById(R.id.textview9);
		view10 = (TextView) findViewById(R.id.textview10);
		view11 = (TextView) findViewById(R.id.textview11);
		view12 = (TextView) findViewById(R.id.textview12);
		view13 = (TextView) findViewById(R.id.textview13);
		view14 = (TextView) findViewById(R.id.textview14);
		view15 = (TextView) findViewById(R.id.textview15);
		view16 = (TextView) findViewById(R.id.textview16);
		view17 = (TextView) findViewById(R.id.textview17);
		view18 = (TextView) findViewById(R.id.textview18);
		view19 = (TextView) findViewById(R.id.textview19);
		view20 = (TextView) findViewById(R.id.textview20);
		view21 = (TextView) findViewById(R.id.textview21);
		view22 = (TextView) findViewById(R.id.textview22);
		view23 = (TextView) findViewById(R.id.textview23);
		view24 = (TextView) findViewById(R.id.textview24);
		view25 = (TextView) findViewById(R.id.textview25);
		view26 = (TextView) findViewById(R.id.textview26);
		view27 = (TextView) findViewById(R.id.textview27);
		view28 = (TextView) findViewById(R.id.textview28);
		view29 = (TextView) findViewById(R.id.textview29);
		view30 = (TextView) findViewById(R.id.textview30);
		view31 = (TextView) findViewById(R.id.textview31);
		view32 = (TextView) findViewById(R.id.textview32);
		view33 = (TextView) findViewById(R.id.textview33);
		view131 = (TextView) findViewById(R.id.textview131);
		view132 = (TextView) findViewById(R.id.textview132);
		zillowdisclaimer1 = (TextView) findViewById(R.id.zillowdisclaimer1);
		zillowdisclaimer2 = (TextView) findViewById(R.id.zillowdisclaimer2);
		zillowdisclaimer1
				.setText(Html
						.fromHtml("&copy Zillow, Inc., 2006-2014. <br>"
								+ "Use is subject to <a href= 'http://www.zillow.com/corp/Terms.htm'> "
								+ "Terms of Use</a> <br>"));
		zillowdisclaimer1.append(Html
				.fromHtml("<a href='http://www.zillow.com/zestimate/'>"
						+ "What's a Zestimate?</a>"));
		zillowdisclaimer2
				.setText(Html
						.fromHtml("&copy Zillow, Inc., 2006-2014. <br>"
								+ "Use is subject to <a href= 'http://www.zillow.com/corp/Terms.htm'> "
								+ "Terms of Use</a> <br>"));
		zillowdisclaimer2.append(Html
				.fromHtml("<a href='http://www.zillow.com/zestimate/'>"
						+ "What's a Zestimate?</a>"));
		zillowdisclaimer2.setMovementMethod(LinkMovementMethod.getInstance());
		zillowdisclaimer1.setMovementMethod(LinkMovementMethod.getInstance());
		zillowdisclaimer1.setGravity(Gravity.CENTER);
		zillowdisclaimer2.setGravity(Gravity.CENTER);
		layout1 = (TableLayout) findViewById(R.id.layout1);
		btnNext = (Button) findViewById(R.id.buttonNext);
		btnPrev = (Button) findViewById(R.id.buttonPrev);
		share = (ImageButton) findViewById(R.id.share);

		// LoginButton authButton = (LoginButton)findViewById(R.id.authButton);
		// authButton.setReadPermissions(Arrays.asList("user_likes",
		// "user_status"));
		graphText = (TextView) findViewById(R.id.graphText);
		addressText = (TextView) findViewById(R.id.addressText);
		intent = getIntent();
		String result = intent.getStringExtra("result");
		callZillow(result);
		imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		setImageSwitcher();
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				openFacebookSession();
				// publishFeedDialog();

				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
						SearchActivity.this)
						.setLink(homeurl1)
						.setCaption("Property Information form Zillow")
						.setName(homeaddress1)
						.setDescription(
								"Last Sold Price: " + view19.getText()
										+ " 30 Days \nOverall Change: "
										+ overallchange).setPicture(chartURL)
						.build();

				uiHelper.trackPendingDialogCall(shareDialog.present());

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}
					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
						boolean didCancel = false;
						didCancel = FacebookDialog.getNativeDialogDidComplete(data);
						String completionGesture = FacebookDialog.getNativeDialogCompletionGesture(data);
						String postId = FacebookDialog.getNativeDialogPostId(data);
					
						if (didCancel){
							Toast.makeText(getApplicationContext(), 
		                            "Posted Successfully", 
		                            Toast.LENGTH_SHORT).show();
							Log.i("Posted", "Success!");
						}
						if (postId == null){
							Toast.makeText(getApplicationContext(), 
		                            "Post Cancelled", 
		                            Toast.LENGTH_SHORT).show();
							
						}
					}
				});

	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", homeaddress1);
		params.putString("caption", "Property Information form Zillow");
		params.putString("description", "Last Sold Price: " + view19.getText()
				+ " 30 Days \nOverall Change: " + overallchange);
		params.putString("link", homeurl1);
		params.putString("picture", chartURL);

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(this,
				Session.getActiveSession(), params)).setOnCompleteListener(
				new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(getApplicationContext(),
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();
	}

	private void openFacebookSession() {
		openActiveSession(SearchActivity.this, true,
				Arrays.asList("email", "user_birthday", "user_hometown", ""),
				new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						if (exception != null) {
							Log.d("Facebook", exception.getMessage());
						}
						Log.d("Facebook",
								"Session State: " + session.getState());
						// you can make request to the /me API or do other stuff
						// like post, etc. here
					}
				});
	}

	private static Session openActiveSession(Activity activity,
			boolean allowLoginUI, List permissions, StatusCallback callback) {
		OpenRequest openRequest = new OpenRequest(activity).setPermissions(
				permissions).setCallback(callback);
		Session session = new Session.Builder(activity).build();
		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())
				|| allowLoginUI) {
			Session.setActiveSession(session);
			session.openForRead(openRequest);
			return session;
		}
		return null;
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	public void setImageSwitcher() {

		imageSwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				ImageView imageView = new ImageView(getApplicationContext());
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				return imageView;
			}
		});
		graphText.setText("Historical Zestimate for the past 1 year");
		new downloadImage().execute(charturls.get(0));
		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex++;
				// If index reaches maximum reset it
				if (currentIndex == imageIds.length)
					currentIndex = 0;
				String url = charturls.get(currentIndex);
				new downloadImage().execute(url);
				if (currentIndex == 0)
					graphText
							.setText("Historical Zestimate for the past 1 year");
				else if (currentIndex == 1)
					graphText
							.setText("Historical Zestimate for the past 5 year");
				else
					graphText
							.setText("Historical Zestimate for the past 10 year");
			}
		});
		btnPrev.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentIndex--;
				// If index reaches maximum reset it
				if (currentIndex == -1)
					currentIndex = imageIds.length - 1;
				if (currentIndex == 0)
					graphText
							.setText("Historical Zestimate for the past 1 year");
				else if (currentIndex == 1)
					graphText
							.setText("Historical Zestimate for the past 5 year");
				else
					graphText
							.setText("Historical Zestimate for the past 10 year");
				String url = charturls.get(currentIndex);
				new downloadImage().execute(url);
			}
		});

	}

	public void callZillow(String result1) {
		// parse JSON data
		// String homeurl = "";
		try {
			// view1.setText("test");
			JSONObject root = new JSONObject(result1);
			if (root.has("response")) {
				JSONObject response = root.getJSONObject("response");
				JSONObject results = response.getJSONObject("results");
				JSONObject result = results.getJSONObject("result");
				view1.setText("See more detail on Zillow ");
				if (result.has("address")) {
					JSONObject address = result.getJSONObject("address");
					String street = address.getString("street");
					String link = "<u>" + street;
					String city = address.getString("city");
					link += " " + city;
					String state = address.getString("state");
					link += " " + state;
					String zip = address.getString("zipcode");
					link += " " + zip + "<u>";
					homeaddress1 = street + " " + state + " " + zip;
					view3.setText(Html.fromHtml(link));
					// copy view3 string to addressText
					addressText.setText(street + " " + city + " " + state + " "
							+ zip);
					JSONObject links = result.getJSONObject("links");
					if (links.has("homedetails")) {
						final String homeurl = links.getString("homedetails");
						homeurl1 = links.getString("homedetails");
						view3.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_VIEW);
								intent.addCategory(Intent.CATEGORY_BROWSABLE);
								intent.setData(Uri.parse(homeurl));
								startActivity(intent);
							}
						});
					}

				} else {
					view1.append("N/A");
				}
				view4.setText("Property Type: ");
				if (result.has("useCode")) {
					String useCode = result.getString("useCode");
					view5.setText(useCode);
				} else {
					view5.setText("N/A");
				}
				view6.setText("Year Built: ");
				if (result.has("yearBuilt")) {
					String yearBuilt = result.getString("yearBuilt");
					view7.setText(yearBuilt);
				} else {
					view7.setText("N/A");
				}
				view8.setText("Lot Size: ");
				if (result.has("lotSizeSqFt")) {
					int lotSizeSqFt = result.getInt("lotSizeSqFt");
					String lotSizeSqFtModified = String.format("%,d",
							lotSizeSqFt);
					view9.setText(lotSizeSqFtModified + " sq. ft.");
				} else {
					view9.setText("N/A");
				}
				view10.setText("Finished Area: ");
				if (result.has("finishedSqFt")) {
					int finishedSqFt = result.getInt("finishedSqFt");
					String finishedSqFtModified = String.format("%,d",
							finishedSqFt);
					view11.setText(finishedSqFtModified + " sq. ft.");
				} else {
					view11.setText("N/A");
				}
				view12.setText("Bathrooms: ");
				if (result.has("bathrooms")) {
					String bathrooms = result.getString("bathrooms");
					view13.setText(bathrooms);
				} else {
					view13.setText("N/A");
				}
				view131.setText("Bedrooms: ");
				if (result.has("bedrooms")) {
					String bedrooms = result.getString("bedrooms");
					view132.setText(bedrooms);
				} else {
					view132.setText("N/A");
				}
				view14.setText("Tax Accessment Year: ");
				if (result.has("taxAssessmentYear")) {
					String taxYear = result.getString("taxAssessmentYear");
					view15.setText(taxYear);
				} else {
					view15.setText("N/A");
				}
				view16.setText("Tax Accessment: ");
				if (result.has("taxAssessment")) {
					double tax = result.getDouble("taxAssessment");
					String taxModified = String.format("%,f", tax);
					if (taxModified.indexOf(".") == (-1))
						view17.setText("$" + taxModified + ".00");
					else {

						int decimalIndex = taxModified.indexOf(".");
						if (taxModified.length() - decimalIndex > 2) {
							view17.setText("$"
									+ taxModified
											.substring(0, decimalIndex + 3));
						} else if (taxModified.length() - decimalIndex > 1)
							view17.setText("$"
									+ taxModified
											.substring(0, decimalIndex + 2)
									+ "0");
					}
				} else {
					view17.setText("N/A");
				}
				view18.setText("Last Sold Price: ");
				if (result.has("lastSoldPrice")) {
					int lastSoldPrice = result.getInt("lastSoldPrice");
					String lastSoldPriceModified = String.format("%,d",
							lastSoldPrice);
					view19.setText("$" + lastSoldPriceModified + ".00");
				} else {
					view19.setText("N/A");
				}
				view20.setText("Last Sold Date: ");
				if (result.has("lastSoldDate")) {
					String lastSoldDate = result.getString("lastSoldDate");
					String[] datelist = lastSoldDate.split("/");
					String month = "";
					int year = Integer.parseInt(datelist[2]);
					if (year < 1970)
						view21.setText("N/A");
					else {
						if (datelist[0].equals("01"))
							month = "Jan";
						else if (datelist[0].equals("02"))
							month = "Feb";
						else if (datelist[0].equals("03"))
							month = "Mar";
						else if (datelist[0].equals("04"))
							month = "April";
						else if (datelist[0].equals("05"))
							month = "May";
						else if (datelist[0].equals("06"))
							month = "June";
						else if (datelist[0].equals("07"))
							month = "Jul";
						else if (datelist[0].equals("08"))
							month = "Aug";
						else if (datelist[0].equals("09"))
							month = "Sep";
						else if (datelist[0].equals("10"))
							month = "Oct";
						else if (datelist[0].equals("11"))
							month = "Nov";
						else if (datelist[0].equals("12"))
							month = "Dec";
						view21.setText(datelist[1] + "-" + month + "-"
								+ datelist[2]);
					}
				} else {
					view21.setText("N/A");
				}
				view22.setText("Zestimate \u00AE Property Estimate as of ");

				if (result.has("zestimate")) {
					JSONObject zestimate = result.getJSONObject("zestimate");
					if (zestimate.has("last-updated")) {
						String lastUpdated = zestimate
								.getString("last-updated");
						String[] datelist = lastUpdated.split("/");
						String month = "";
						int year = Integer.parseInt(datelist[2]);
						if (year < 1970)
							view22.append("N/A");
						else {
							if (datelist[0].equals("01"))
								month = "Jan";
							else if (datelist[0].equals("02"))
								month = "Feb";
							else if (datelist[0].equals("03"))
								month = "Mar";
							else if (datelist[0].equals("04"))
								month = "April";
							else if (datelist[0].equals("05"))
								month = "May";
							else if (datelist[0].equals("06"))
								month = "June";
							else if (datelist[0].equals("07"))
								month = "Jul";
							else if (datelist[0].equals("08"))
								month = "Aug";
							else if (datelist[0].equals("09"))
								month = "Sep";
							else if (datelist[0].equals("10"))
								month = "Oct";
							else if (datelist[0].equals("11"))
								month = "Nov";
							else if (datelist[0].equals("12"))
								month = "Dec";
							view22.append(datelist[1] + "-" + month + "-"
									+ datelist[2]);
						}
					} else {
						view22.append("N/A");
					}
				} else {
					view22.append("N/A");
				}
				if (result.has("zestimate")) {
					JSONObject zestimate = result.getJSONObject("zestimate");
					if (zestimate.has("amount")) {
						double amount = zestimate.getDouble("amount");
						String lastUpdated = String.format("%,f", amount);
						if (lastUpdated.indexOf(".") == (-1))
							view23.setText("$" + lastUpdated + ".00");
						else {

							int decimalIndex = lastUpdated.indexOf(".");
							if (lastUpdated.length() - decimalIndex > 2) {
								view23.setText("$"
										+ lastUpdated.substring(0,
												decimalIndex + 3));
							} else if (lastUpdated.length() - decimalIndex > 1)
								view23.setText("$"
										+ lastUpdated.substring(0,
												decimalIndex + 2) + "0");
						}
					} else
						view23.setText("N/A");
				} else {
					view23.setText("N/A");
				}
				view24.setText("30 Days Overall Change: ");
				if (result.has("zestimate")) {
					JSONObject zestimate = result.getJSONObject("zestimate");

					if (zestimate.has("valueChange")) {
						String valueChange = zestimate.getString("valueChange");
						if (valueChange.substring(0, 1).equals("-")) {
							overallchange = "-";
							view25.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.down_r, 0, 0, 0);
							double currency = Double.parseDouble(valueChange
									.substring(1, valueChange.length()));
							String currencyModified = String.format("%,f",
									currency);
							if (currencyModified.indexOf(".") == (-1))
								view25.setText("$" + currencyModified + ".00");
							else {
								int decimalIndex = currencyModified
										.indexOf(".");
								if (currencyModified.length() - decimalIndex > 2) {
									view25.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 3));
								} else if (currencyModified.length()
										- decimalIndex > 1)
									view25.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 2) + "0");
								overallchange += view25.getText();
							}
						} else {
							overallchange = "+";
							view25.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.up_g, 0, 0, 0);
							double currency = Double.parseDouble(valueChange
									.substring(0, valueChange.length()));
							String currencyModified = String.format("%,f",
									currency);
							if (currencyModified.indexOf(".") == (-1))
								view25.setText("$" + currencyModified + ".00");
							else {
								int decimalIndex = currencyModified
										.indexOf(".");
								if (currencyModified.length() - decimalIndex > 2) {
									view25.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 3));
								} else if (currencyModified.length()
										- decimalIndex > 1)
									view25.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 2) + "0");
							}

							overallchange += view25.getText();
						}
					} else {
						view25.setText("N/A");
					}
				} else {
					view25.setText("N/A");
				}
				view26.setText("All Time Property Range: ");
				if (result.has("zestimate")) {
					JSONObject zestimate = result.getJSONObject("zestimate");
					if (zestimate.has("valuationRange")) {
						JSONObject valuationRange = zestimate
								.getJSONObject("valuationRange");
						double high = valuationRange.getDouble("high");
						double low = valuationRange.getDouble("low");
						String highModified = String.format("%,f", high);
						String lowModified = String.format("%,f", low);
						if (highModified.indexOf(".") == (-1))
							highModified = "$" + highModified + ".00";
						else {
							int decimalIndex = highModified.indexOf(".");
							if (highModified.length() - decimalIndex > 2) {
								highModified = "$"
										+ highModified.substring(0,
												decimalIndex + 3);
							} else if (highModified.length() - decimalIndex > 1)
								highModified = "$"
										+ highModified.substring(0,
												decimalIndex + 2) + "0";
						}
						if (lowModified.indexOf(".") == (-1))
							lowModified = "$" + highModified + ".00";
						else {
							int decimalIndex = lowModified.indexOf(".");
							if (lowModified.length() - decimalIndex > 2) {
								lowModified = "$"
										+ lowModified.substring(0,
												decimalIndex + 3);
							} else if (lowModified.length() - decimalIndex > 1)
								lowModified = "$"
										+ lowModified.substring(0,
												decimalIndex + 2) + "0";
						}
						view27.setText(lowModified + "-" + highModified);
					} else {
						view27.setText("N/A");
					}
				} else {
					view27.setText("N/A");
				}
				view28.setText("Rent Zestimate \u00AE Property Estimate as of ");

				if (result.has("rentzestimate")) {
					JSONObject rentzestimate = result
							.getJSONObject("rentzestimate");
					if (rentzestimate.has("last-updated")) {
						String lastUpdated = rentzestimate
								.getString("last-updated");
						String[] datelist = lastUpdated.split("/");
						String month = "";
						int year = Integer.parseInt(datelist[2]);
						if (year < 1970)
							view28.append("N/A");
						else {
							if (datelist[0].equals("01"))
								month = "Jan";
							else if (datelist[0].equals("02"))
								month = "Feb";
							else if (datelist[0].equals("03"))
								month = "Mar";
							else if (datelist[0].equals("04"))
								month = "April";
							else if (datelist[0].equals("05"))
								month = "May";
							else if (datelist[0].equals("06"))
								month = "June";
							else if (datelist[0].equals("07"))
								month = "Jul";
							else if (datelist[0].equals("08"))
								month = "Aug";
							else if (datelist[0].equals("09"))
								month = "Sep";
							else if (datelist[0].equals("10"))
								month = "Oct";
							else if (datelist[0].equals("11"))
								month = "Nov";
							else if (datelist[0].equals("12"))
								month = "Dec";
							view28.append(datelist[1] + "-" + month + "-"
									+ datelist[2]);
						}
					} else {
						view28.append("N/A");
					}
				} else {
					view28.append("N/A");
				}

				if (result.has("rentzestimate")) {

					JSONObject rentzestimate = result
							.getJSONObject("rentzestimate");
					if (rentzestimate.has("amount")) {
						double amount = rentzestimate.getDouble("amount");
						String lastUpdated = String.format("%,f", amount);
						if (lastUpdated.indexOf(".") == (-1))
							view29.setText("$" + lastUpdated + ".00");
						else {

							int decimalIndex = lastUpdated.indexOf(".");
							if (lastUpdated.length() - decimalIndex > 2) {
								view29.setText("$"
										+ lastUpdated.substring(0,
												decimalIndex + 3));
							} else if (lastUpdated.length() - decimalIndex > 1)
								view29.setText("$"
										+ lastUpdated.substring(0,
												decimalIndex + 2) + "0");
						}

					} else
						view29.setText("N/A");
				} else {
					view29.setText("N/A");
				}

				view30.setText("30 Days Rent Change");
				if (result.has("rentzestimate")) {
					JSONObject rentzestimate = result
							.getJSONObject("rentzestimate");
					if (rentzestimate.has("valueChange")) {
						String valueChange = rentzestimate
								.getString("valueChange");
						if (valueChange.substring(0, 1).equals("-")) {
							view31.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.down_r, 0, 0, 0);
							double currency = Double.parseDouble(valueChange
									.substring(1, valueChange.length()));
							String currencyModified = String.format("%,f",
									currency);
							if (currencyModified.indexOf(".") == (-1))
								view31.setText("$" + currencyModified + ".00");
							else {
								int decimalIndex = currencyModified
										.indexOf(".");
								if (currencyModified.length() - decimalIndex > 2) {
									view31.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 3));
								} else if (currencyModified.length()
										- decimalIndex > 1)
									view31.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 2) + "0");
							}
						} else {
							view31.setCompoundDrawablesWithIntrinsicBounds(
									R.drawable.up_g, 0, 0, 0);
							double currency = Double.parseDouble(valueChange
									.substring(0, valueChange.length()));
							String currencyModified = String.format("%,f",
									currency);
							if (currencyModified.indexOf(".") == (-1))
								view31.setText("$" + currencyModified + ".00");
							else {
								int decimalIndex = currencyModified
										.indexOf(".");
								if (currencyModified.length() - decimalIndex > 2) {
									view31.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 3));
								} else if (currencyModified.length()
										- decimalIndex > 1)
									view31.setText("$"
											+ currencyModified.substring(0,
													decimalIndex + 2) + "0");
							}
						}
					} else {
						view31.setText("N/A");
					}
				} else {
					view31.setText("N/A");
				}
				view32.setText("All Time Rent Range: ");
				if (result.has("rentzestimate")) {
					JSONObject rentzestimate = result
							.getJSONObject("rentzestimate");
					if (rentzestimate.has("valuationRange")) {
						JSONObject valuationRange = rentzestimate
								.getJSONObject("valuationRange");
						double high = valuationRange.getDouble("high");
						double low = valuationRange.getDouble("low");
						String highModified = String.format("%,f", high);
						String lowModified = String.format("%,f", low);
						if (highModified.indexOf(".") == (-1))
							highModified = "$" + highModified + ".00";
						else {
							int decimalIndex = highModified.indexOf(".");
							if (highModified.length() - decimalIndex > 2) {
								highModified = "$"
										+ highModified.substring(0,
												decimalIndex + 3);
							} else if (highModified.length() - decimalIndex > 1)
								highModified = "$"
										+ highModified.substring(0,
												decimalIndex + 2) + "0";
						}
						if (lowModified.indexOf(".") == (-1))
							lowModified = "$" + highModified + ".00";
						else {
							int decimalIndex = lowModified.indexOf(".");
							if (lowModified.length() - decimalIndex > 2) {
								lowModified = "$"
										+ lowModified.substring(0,
												decimalIndex + 3);
							} else if (lowModified.length() - decimalIndex > 1)
								lowModified = "$"
										+ lowModified.substring(0,
												decimalIndex + 2) + "0";
						}
						view33.setText(lowModified + "-" + highModified);
					} else {
						view33.setText("N/A");
					}
				} else {
					view33.setText("N/A");
				}
				if (result.has("charturl1")) {
					charturls.clear();
					chartURL = result.getString("charturl1");
					charturls.add((result.getString("charturl1")));
					charturls.add((result.getString("charturl2")));
					charturls.add((result.getString("charturl3")));
				}
			}
			// this.progressDialog.dismiss();
		} catch (JSONException e) {
			Log.e("JSONException", "Error: " + e.toString());
		} // catch (JSONException e)

	}

	class downloadImage extends AsyncTask<String, Void, Void> {
		InputStream inputStream = null;
		String url = "";
		Bitmap bitmap = null;

		@Override
		protected Void doInBackground(String... arg0) {
			url = arg0[0];
			try {
				InputStream input = new java.net.URL(url).openStream();
				bitmap = BitmapFactory.decodeStream(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			Drawable drawable = new BitmapDrawable(getApplicationContext()
					.getResources(), bitmap);
			imageSwitcher.setImageDrawable(drawable);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
