package yt.bam.minecraftremote;

import ch.spacebase.mcprotocol.util.Util;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private MinecraftRemote mDebugView;
	private SensorManager mSensorManager;
	private PowerManager mPowerManager;
	private WindowManager mWindowManager;
	private Display mDisplay;
	private WakeLock mWakeLock;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
		mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		mDisplay = mWindowManager.getDefaultDisplay();
		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
		mDebugView = new MinecraftRemote(this,mSensorManager);
		setContentView(mDebugView);
	}

		
		
}
