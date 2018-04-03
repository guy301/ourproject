package com.imperialsoupgmail.tesseractexample;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;


/**
 * Created by Guy on 29/03/2018.
 */

public class PickingItemsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picking_items);
        Bundle extras = getIntent().getExtras();
        List<User> users=null;
        if (extras != null) {
            users = (List<User>) extras.get("Users");
        }
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pickItems);
        String userName;
        int i=0;
        for(User user : users){
            userName=user.getName();
            Button Userbutton = new Button(this);
            Userbutton .setText(userName);

            Userbutton .setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            Userbutton.setY(300);
            Userbutton.setX(i*300);
            i++;//s
            layout.addView(Userbutton );
        }

    }
}
