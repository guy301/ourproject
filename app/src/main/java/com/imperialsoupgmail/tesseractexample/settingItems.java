package com.imperialsoupgmail.tesseractexample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class settingItems extends AppCompatActivity {
    private Button go_back_b, Continue, addFriends;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_items);
        go_back_b = (Button) findViewById(R.id.goBack_b);
        addFriends = (Button) findViewById(R.id.add_friends);
        Continue = (Button) findViewById(R.id.Continue);
        text = getIntent().getStringExtra("OCR_text");
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriends();
            }
        });
        addButtons(text);

    }

    public void goBack(View view){
        finish();
    }

    public void addFriends(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How many friends are you?");
        final EditText friends_qntty = new EditText(this);
        friends_qntty.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(friends_qntty);
        builder.setNegativeButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EnterFriendsName();
            }
        });
        builder.show();
    }


    public void EnterFriendsName() {


    }
    public void addButtons(String s)
    {
        final String[] words = s.split("\\s+");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.ImageContainer);
        int i=0;
        for(final String word : words){

            final Button yourButton = new Button(this);
            yourButton.setText(word);
            //here can set to button OnClickListener if you want
            yourButton.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            yourButton.setY(300+i*150);
            i++;//s
            layout.addView(yourButton );
            //final EditText edit = findViewById(R.id.edit_dialog);
            //edit.setVisibility(View.INVISIBLE);
            yourButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showDialog(word, yourButton);
                    return true;
                }
            });
        }
    }

    private void showDialog(String str, final Button btn) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("input text");
        //final View view = LayoutInflater.from(this).inflate(R.layout.activity_setting_items, null);
        //final EditText edit_dialog = findViewById(R.id.edit_dialog);
        final EditText edit = new EditText(this);
        edit.setText(str);
        builder.setView(edit);
        builder.setNegativeButton("cancel",null);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn.setText(edit.getText().toString());
            }
        });
        builder.show();
    }
}
