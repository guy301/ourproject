package com.imperialsoupgmail.tesseractexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PickingItemsActivity1 extends AppCompatActivity {

    List<User> activeUsers=new ArrayList<User>();
    List<User> users;
    Map<Item,Integer> AppItemsMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_items1);
        users = (ArrayList<User>) getIntent().getSerializableExtra("Users");
        AppItemsMap= (Map<Item,Integer>) getIntent().getSerializableExtra("Items");
        List<CheckBox> usersButtonList=addUserButtons(users);
        addItemsButtons(AppItemsMap);

    }



    public void addItemsButtons(Map<Item,Integer> itemsMap)
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pickItem);
        Item itm;
        int quantity;
        String name;
        int i=0;
        for (Map.Entry<Item, Integer> entry : itemsMap.entrySet()) {
            itm = entry.getKey();
            quantity = entry.getValue();
            name = itm.getName();
            Button btn = new Button(this);
            btn.setText(name + ": " + quantity);
            layout.addView(btn);
            if (activeUsers.size() != 0)
                itemButtonsOnclik(btn);
            btn.setY(i * 170);
            btn.setX(-1100);
            i++;
         }
    }

    public void itemButtonsOnclik(Button btn)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = ((Button) v).getText().toString();
                Scanner s = new Scanner(str).useDelimiter(" ");
                String tmp = null;
                String name = "none";
                int quantity = 0;
                if (s.hasNext())
                    name = s.next();
                if (s.hasNext())
                    quantity = Integer.parseInt(s.next().toString()) + 1;

                ((Button) v).setText(name + " " + quantity);
            }
        });

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                String str = ((Button) v).getText().toString();
                Scanner s = new Scanner(str).useDelimiter(" ");
                String name = "none";
                int quantity = 0;
                if (s.hasNext())
                    name = s.next();
                if (s.hasNext())
                    quantity = Integer.parseInt(s.next().toString()) - 1;
                if (quantity < 0)
                    quantity = 0;
                ((Button) v).setText(name + " " + quantity);
                return true;
            }
        });
    }


    public Item getItemByName(String name)
    {
        Item itm;
        String itemName;
        for (Map.Entry<Item, Integer> entry : AppItemsMap.entrySet()) {
            itm = entry.getKey();
            itemName=itm.getName();
            if(itemName.equals(name))
                return itm;
        }
        return null;
    }


    public void updateActiveUsers(boolean add,String name)
    {
        User user=null;
        Iterator<User> iter=users.iterator();
        while(iter.hasNext())
        {
            user=(User)iter.next();
            if(user.getName()==name)
            {
                if(add)
                     activeUsers.add(user);
                else
                     activeUsers.remove(user);
            }

            addItemsButtons(AppItemsMap);
        }
    }



    public List<CheckBox> addUserButtons(List<User> users)
    {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pickItem);
        String userName;
        List<CheckBox> usersButtonList=new ArrayList<CheckBox>();
        int i=0;
        for(User user : users){
            userName=user.getName();
            CheckBox Userbutton = new CheckBox(this);
            usersButtonList.add(Userbutton);
            Userbutton .setText(userName);

            Userbutton .setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            Userbutton.setY(1900);
            Userbutton.setX(-1100+350*i);
            Userbutton .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked=((CheckBox)v).isChecked();
                    String s= (String)((CheckBox)v).getText();
                    updateActiveUsers(isChecked,s);
                }
            });

            i++;
            layout.addView(Userbutton );

        }
        return usersButtonList;

    }
}


