package it.pgp.killprocessexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RestarterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarter);

        Bundle extras = getIntent().getExtras();
        int targetPid = extras.getInt("PID");
        android.os.Process.sendSignal(targetPid,2); // SIGINT

        // start activity again
        Intent i = new Intent(RestarterActivity.this,MainActivity.class);

        // prevent infinite restarting
        i.putExtra(MainActivity.RESTARTED,true);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        // self kill process
        android.os.Process.sendSignal(android.os.Process.myPid(),2); // SIGINT
    }
}
