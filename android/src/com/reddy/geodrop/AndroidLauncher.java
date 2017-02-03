package com.reddy.geodrop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private static final String TAG = "AndroidLauncher";
	private final int SHOW_AD = 1;
	private final int REQUEST_NEW_AD = 0;
	private final int SHOW_BANNER = 1;
	private final int HIDE_BANNER = 0;
	protected InterstitialAd interstitialAd;
	protected AdView adView;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_AD:
					interstitialAd.show();
					break;

				case REQUEST_NEW_AD:
					requestIA();
					break;
			}
		}
	};

	Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_BANNER:
					adView.setVisibility(View.VISIBLE);
					break;

				case HIDE_BANNER:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RelativeLayout layout = new RelativeLayout(this);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new Main(this), config);
		layout.addView(gameView);

		//interstitials
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-7214932880723751/5082836421");

		adView = new AdView(this);
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visibility = adView.getVisibility();
				adView.setVisibility(AdView.GONE);
				adView.setVisibility(visibility);
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-7214932880723751/3606103220");

		AdRequest ar = new AdRequest.Builder().build();
		RelativeLayout.LayoutParams adParams =  new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		layout.addView(adView, adParams);
		adView.loadAd(ar);

		setContentView(layout);
	}

	public void requestIA() {
		AdRequest ar = new AdRequest.Builder().build();
		interstitialAd.loadAd(ar);
	}

	@Override
	public void showI(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_AD : REQUEST_NEW_AD);
	}

	public void showB(boolean show) {
		handler2.sendEmptyMessage(show ? SHOW_BANNER : HIDE_BANNER);
	}
}
