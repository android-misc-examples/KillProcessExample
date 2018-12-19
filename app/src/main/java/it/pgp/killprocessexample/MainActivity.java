package it.pgp.killprocessexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String RESTARTED = "RESTARTED";

    public static String a = "111";

    public void selfShutdown() {
        new Thread(()->{
            Log.e("SHUTDOWN","SHUTDOWN THREAD STARTED");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
//                finishAffinity();
//                android.os.Process.killProcess(android.os.Process.myPid());
            android.os.Process.sendSignal(android.os.Process.myPid(),2); // SIGINT
        }).start();
    }

    public void prepareRestart() {
        new Thread(()->{
            Log.e("RESTART","RESTART THREAD STARTED");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            Intent i = new Intent(MainActivity.this,RestarterActivity.class);
            i.putExtra("PID",android.os.Process.myPid()); // pid of this activity's process, to be killed by the other activity
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        selfShutdown();

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.getBoolean(RESTARTED,false))
            prepareRestart();


        Toast.makeText(this,"Static: "+a,Toast.LENGTH_LONG).show();

        a = "222"; // on kill process the variable is reset to "111";
    }
}
