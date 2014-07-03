package com.example.buttonwork;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: RAVI
 * Date: 28/6/14
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class Serv extends Service implements SensorEventListener {

    public SensorManager mSensorManager;
    public Sensor mSensor;
    public SensorEventListener listen;
    public TelephonyManager telephonyMgr;
    AudioManager am;
    public String currentState = "far";
    public String callState = "no";
    int flag=0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart(final Intent intent, int startid) {
        Toast.makeText(this, "in on start", Toast.LENGTH_SHORT).show();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        telephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyMgr.listen(new TeleListener(),PhoneStateListener.LISTEN_CALL_STATE);
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onDestroy() {
        mSensorManager.unregisterListener(this);
        super.onDestroy();

    }

    protected void onResume() {
        this.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        this.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {  // this is to do when object is near

            if(callState.equals("yes") && flag == 1){
                flag = 2;
            }

              if(callState.equals("yes") && flag==2){
                  am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
              }

            if(callState.equals("yes") && flag==1){
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
            currentState = "near";

            Toast.makeText(this, "near", Toast.LENGTH_SHORT).show();

        } else {
            currentState = "far";
            Toast.makeText(this, "far", Toast.LENGTH_SHORT).show();
        }
    }


    class TeleListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // CALL_STATE_IDLE;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",Toast.LENGTH_LONG).show();
                    callState = "no";
                    if(flag == 2)
                        flag = 0;
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // CALL_STATE_OFFHOOK;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // CALL_STATE_RINGING
                    Toast.makeText(getApplicationContext(), incomingNumber, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",Toast.LENGTH_LONG).show();
                    callState = "yes";
                    if(currentState.equals("far"))
                        flag = 2;
                    if(currentState.equals("near"))
                        flag = 1;
                    break;
                default:
                    break;
            }
        }

    }
}
