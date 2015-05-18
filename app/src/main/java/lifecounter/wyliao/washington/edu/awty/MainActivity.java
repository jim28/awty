package lifecounter.wyliao.washington.edu.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    Button str;
    EditText msg;
    EditText phoneNum;
    EditText minTime;
    PendingIntent alarmIntent = null;
    private AlarmManager manager;
    String alertInfo;


    BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            alertInfo = phoneNum.getText().toString() + ": " + msg.getText().toString();
            Toast.makeText(MainActivity.this,alertInfo,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = (Button) findViewById(R.id.start);
        msg = (EditText) findViewById(R.id.sendMsg);
        phoneNum = (EditText) findViewById(R.id.telNum);
        minTime = (EditText) findViewById(R.id.timeEachNag);

        str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str.getText().toString().equals("Start")) {

                    if ((!msg.getText().toString().equals("")) && (!phoneNum.getText().toString().equals("")) && (!minTime.getText().toString().equals(""))) {
                        str.setText("Stop");
                        int interval = Integer.parseInt(minTime.getText().toString());

                        //register receiver
                        registerReceiver(alarmReceiver, new IntentFilter("lifecounter.wyliao.washington.edu.awty"));
                        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                        Intent i = new Intent();
                        i.setAction("lifecounter.wyliao.washington.edu.awty");
                        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);
                        manager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 3000, interval*1000, alarmIntent);
                    }
                } else if (str.getText().toString().equals("Stop")) {
                    str.setText("Start");
                    manager.cancel(alarmIntent);
                    alarmIntent.cancel();
                }
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
