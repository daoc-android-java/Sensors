package ute.fcii.dsii.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MotionActivity extends AppCompatActivity {

    SensorManager sensorManager;
    TextView tv, tvX, tvY, tvZ;
    Sensor accel;
    long lastSensorEvent;
    final long DELAY = TimeUnit.SECONDS.toNanos(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion);
        tv = (TextView) findViewById(R.id.tv);
        tvX = (TextView) findViewById(R.id.tvX);
        tvY = (TextView) findViewById(R.id.tvY);
        tvZ = (TextView) findViewById(R.id.tvZ);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        tv.setText(accel.toString());
    }

    public void sensores(View v) {
    }

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            tvX.setText(String.valueOf(sensorEvent.values[0]));
            tvY.setText(String.valueOf(sensorEvent.values[1]));
            tvZ.setText(String.valueOf(sensorEvent.values[2]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sel, accel, SensorManager.SENSOR_DELAY_NORMAL);
        lastSensorEvent = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sel);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.list:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.light:
                intent = new Intent(this, LightActivity.class);
                break;
            case R.id.motion:
                intent = new Intent(this, MotionActivity.class);
                break;
        }
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}

