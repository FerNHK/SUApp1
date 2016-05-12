package com.example.suv2;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainWel extends Activity {
	private static final long SPLASH_SCREEN_DELAY = 3000;
	Typeface Cairo;
	TextView b;
	TextView it;
	TextView te;
	TextView ch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_bienvenida);
		Cairo = Typeface.createFromAsset(getAssets(), "font/CairotiquaFSFreestyle-Italic.ttf");
		b = (TextView)findViewById(R.id.bienvenido);

		it = (TextView)findViewById(R.id.itch);
		ch=(TextView)findViewById(R.id.itc);
		
		b.setTypeface(Cairo);
		it.setTypeface(Cairo);

		ch.setTypeface(Cairo);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

				// Start the next activity
				Intent mainIntent = new Intent().setClass(MainWel.this,
						MainPrincipal.class);
				startActivity(mainIntent);
				overridePendingTransition(R.anim.zoom_forward_in,
						R.anim.zoom_forward_out);

				// Close the activity so the user won't able to go back this
				// activity pressing Back button
				finish();
			}
		};

		// Simulate a long loading process on application startup.
		Timer timer = new Timer();
		timer.schedule(task, SPLASH_SCREEN_DELAY);
	}
}
