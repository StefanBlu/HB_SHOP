package at.shop.hb.hb_shop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class product_detail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
    }

    public void cl_onclose(View view)
    {
        this.finish();
    }
}
