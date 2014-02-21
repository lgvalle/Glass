package com.igz.glass.sample1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.TimelineManager;

public class GlassSample1Service extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		publishStaticCard();
		return START_STICKY;

	}
	
	/**
	 * Sample 1: Static Card
	 */
	private void publishStaticCard() {
		Card card1 = new Card(this);
		card1.setText("Esto es una static card");
		card1.setFootnote("Y esto es su footer!");

		// A–adir card al timeline
		TimelineManager.from(this).insert(card1);
		
		
	}
}
