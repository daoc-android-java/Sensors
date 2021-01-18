package ute.fcii.dsii.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class LightActivity extends AppCompatActivity {

    SensorManager sensorManager;
    TextView tv;
    Sensor light;
    long lastSensorEvent;
    final long DELAY = TimeUnit.SECONDS.toNanos(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        tv.setText(light.toString());
    }

    public void sensores(View v) {
    }

    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.timestamp > (lastSensorEvent + DELAY)) {
                lastSensorEvent = sensorEvent.timestamp;
                float val = sensorEvent.values[0];
                tv.append("\n" + val);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sel, light, SensorManager.SENSOR_DELAY_NORMAL);
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

