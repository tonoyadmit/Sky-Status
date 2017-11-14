package in.digitechlab.skystatus;

/**
 * Created by DELL on 10/15/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread t = new Thread(){

            public void run(){

                try{
                    sleep(4000);
                }catch (InterruptedException e){
                }
                finally{
                    Intent i = new Intent ("android.intent.action.MAINSKYACTIVITY");
                    startActivity(i);
                    finish();
                }
            }
        };
        t.start();

    }
}