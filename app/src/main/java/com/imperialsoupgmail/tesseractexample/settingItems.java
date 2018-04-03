package com.imperialsoupgmail.tesseractexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class settingItems extends AppCompatActivity {
    private Button go_back_b, EditItems, Continue;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_items);
        go_back_b = (Button) findViewById(R.id.goBack_b);
        EditItems = (Button) findViewById(R.id.Edit_Items);
        Continue = (Button) findViewById(R.id.Continue);
        text = getIntent().getStringExtra("OCR_text");
        addButtons(text);
    }

    public void goBack(View view){
        finish();
    }

    public void addButtons(String s)
    {
        String[] words = s.split("\\s+");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ImageContainer);
        int i=0;
        for(String word : words){

            Button yourButton = new Button(this);
            yourButton.setText(word);
            //here can set to button OnClickListener if you want
            yourButton.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            yourButton.setY(300+i*150);
            i++;//s
            layout.addView(yourButton );
        }
    }
}
