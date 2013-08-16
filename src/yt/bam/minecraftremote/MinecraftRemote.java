package yt.bam.minecraftremote;

import java.util.Timer;
import java.util.TimerTask;

import ch.spacebase.mcprotocol.util.Util;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MinecraftRemote extends LinearLayout implements JoystickMovedListener, SensorEventListener{
	private static Bot bot;
	private double opan=0;
	private double otilt=0;
	private SensorManager mSensorManager;
	public MinecraftRemote(Context context,SensorManager mSensorManager) {
		super(context);
		
 		Button button = new Button(getContext());
 		button.setText("Connect");
 		button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(bot!=null){
            		bot.client.disconnect("");
            		}
	        		bot = new Bot("192.168.2.7", 25565);
	         		bot.login("BAMcraft", "sean99");
            }
        });
 		addView(button);
 		
 		Button btnConnect = new Button(getContext());
 		btnConnect.setText("Send");
 		btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(bot!=null)
            	bot.say("test");
            }
        });
 		addView(btnConnect);
 		
 		
 		Button btntest = new Button(getContext());
 		btntest.setText("1");
 		btntest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(bot!=null)
                	bot.setYawPitch(0, 0);
    			bot.move(1);
            }
        });
 		addView(btntest);
 		
 		Button btntest2 = new Button(getContext());
 		btntest2.setText("2");
 		btntest2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(bot!=null)
            	bot.setYawPitch(0, 90);
    			bot.move(1);
            }
        });
 		addView(btntest2);
 		
 		
 		
 		JoystickView joystick = new JoystickView(getContext());
 		joystick.setOnJostickMovedListener(this);
 		addView(joystick);

		this.mSensorManager = mSensorManager;
		Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
 		mSensorManager.registerListener(this,mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		invalidate();
	}

	@Override
	public void OnMoved(int pan, int tilt) {
		double dpan = pan;
		double dtilt = tilt;
		bot.setVars(dpan/10,0,dtilt/10);
		bot.move(1);
	}

	@Override
	public void OnReleased() {
		bot.setVars(0,0,0);
	}

	@Override
	public void OnReturnedToCenter() {
		bot.setVars(0,0,0);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		Util.logger().info(""+z);
		if(bot!=null){
			float pitch = 0;
			if(z > 0){
				pitch = (float)(z*19.2);
			}else{
				pitch = (float)(z*-6.4);
			}
			
			//bot.setYawPitch(0, y);
		}
	}

}