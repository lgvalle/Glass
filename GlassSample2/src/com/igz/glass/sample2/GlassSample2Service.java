package com.igz.glass.sample2;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

public class GlassSample2Service extends Service {

	private static final String LIVE_CARD_SAMPLE_1_TAG = "S1";
	private LiveCard mLiveCard;
	private RemoteViews mLiveCardRemoveViews;
	private int mCounter;
	private Handler mHandler;
	private Runnable mRunnable;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		publishLiveCard();
		return START_STICKY;

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler = null;
		unPublishCard();
	}

	/**
	 * Sample 2: Si la liveCard no existe la crea y la publica 
	 */
	private void publishLiveCard() {
		if (mLiveCard == null) {
			mLiveCard = TimelineManager.from(this).createLiveCard(LIVE_CARD_SAMPLE_1_TAG);

			// Referencia a la RemoveView de la LiveCard para poder modificarla luego
			mLiveCardRemoveViews = new RemoteViews(getPackageName(), R.layout.livecard1);
			mLiveCard.setViews(mLiveCardRemoveViews);
			
			
			Intent intent = new Intent(this, MenuActivity.class);
			mLiveCard.setAction(PendingIntent.getActivity(this, 0, intent, 0));
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
			
			// Init card ui update
			initCardUpdate();
		}

	}

	/**
	 * Si la liveCard existe la despublica y destruye
	 */
	private void unPublishCard() {
		if (mLiveCard != null) {
			mLiveCard.unpublish();
			mLiveCard = null;
			mLiveCardRemoveViews = null;
		}
	}
	
	
	
	/**
	 * Actualiza la interfaz de la LiveCard
	 */
	public void updateCard() {
		if (mLiveCardRemoveViews != null) {
			mLiveCardRemoveViews.setTextViewText(R.id.tvLiveCard1, String.valueOf(mCounter));	
			mLiveCard.setViews(mLiveCardRemoveViews);
			mHandler.postDelayed(mRunnable, 1000);
		}
	}

	private void initCardUpdate() {
		mHandler = new Handler();
		mRunnable = new Runnable() {
			@Override
			public void run() {
				mCounter++;
				updateCard();
			}
		};
		mHandler.postDelayed(mRunnable, 1000);
		
	}
}
