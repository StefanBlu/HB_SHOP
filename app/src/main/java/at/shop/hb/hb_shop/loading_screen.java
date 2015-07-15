package at.shop.hb.hb_shop;

import android.app.Activity;
import android.os.Bundle;


public class loading_screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);


        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    finish();
                }
            }
        };
        welcomeThread.start();


    }

}
