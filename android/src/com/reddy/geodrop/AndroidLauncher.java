package com.reddy.geodrop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private static final String TAG = "AndroidLauncher";
	private final int SHOW_AD = 1;
	private final int REQUEST_NEW_AD = 0;
	protected InterstitialAd interstitialAd;
	protected AdView adView;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case SHOW_AD:
					interstitialAd.show();
					break;

				case REQUEST_NEW_AD:
					requestIA();
					break;
			}
		}
	};


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(), config);

		//interstitials
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-7214932880723751/5082836421");

		//banners

	}

	public void requestIA() {
		AdRequest ar = new AdRequest.Builder().build();
		interstitialAd.loadAd(ar);
	}

	public void showInterstitial() {
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				interstitialAd.show();
			}
		});
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				interstitialAd.show();
			}
		});
	}

	/*public void showBanner() {
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				adView.show();
			}
		});
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				adView.show();
			}
		});
	}*/

	@Override
	public void show(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_AD : REQUEST_NEW_AD);
	}
}
