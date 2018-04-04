package com.imperialsoupgmail.tesseractexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PickingItemsActivity extends AppCompatActivity {

    User activeUser=null;
    boolean groupUserActive=false;
    List<User> users;
    Map<Item,Integer> AppItemsMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_items);
        users = (ArrayList<User>) getIntent().getSerializableExtra("Users");
        AppItemsMap= (Map<Item,Integer>) getIntent().getSerializableExtra("Items");
        List<CheckBox> usersButtonList=addUserButtons(users);
        addItemsButtons(AppItemsMap, null);


    }



    public void addItemsButtons(Map<Item,Integer> itemsMap,User user)
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
            if (activeUser != null)
                itemButtonsOnclik(btn,user);
            btn.setY(i * 170);
            btn.setX(-1050);
            i++;
         }
    }

    public void itemButtonsOnclik(Button btn,final User user)
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

                if(user!=null)
                {
                    Map<Item,Integer> userItemsMap=user.getItems();
                    Scanner s1 = new Scanner(str).useDelimiter(":");
                    String itemName="None";
                    if (s1.hasNext())
                        itemName = s1.next();
                    Item itm=getItemByName(itemName);
                    int userQuantity=userItemsMap.get(itm);
                    //userItemsMap.remove(itm);

                    int appQuantity=AppItemsMap.get(itm);
                    if(appQuantity ==0)
                        quantity--;
                    else {
                        AppItemsMap.put(itm, appQuantity - 1);
                        //userItemsMap.put(itm,userQuantity+1);
                        user.updateItem(itm,userQuantity+1);
                    }
                }

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
                if(user!=null)
                {
                    Map<Item,Integer> userItemsMap=user.getItems();
                    Scanner s1 = new Scanner(str).useDelimiter(":");
                    String itemName="None";
                    if (s1.hasNext())
                        itemName = s1.next();
                    Item itm=getItemByName(itemName);
                    int userQuantity=userItemsMap.get(itm);
                    //userItemsMap.remove(itm);

                    int appQuantity=AppItemsMap.get(itm);
                    if(quantity<0)
                        quantity=0;
                    else {
                        AppItemsMap.put(itm, appQuantity + 1);
                       // userItemsMap.put(itm,userQuantity-1);
                        user.updateItem(itm,userQuantity-1);
                    }
                }
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
            {
                return itm;
            }
        }
        return null;
    }


    public void updateActiveUsers(boolean add,String name)
    {
        boolean flag=false; // if the activelist user not ampty
        boolean added=false; // if we add new user to the activelist
        User user=null;
        Iterator<User> iter=users.iterator();
        Map<Item,Integer> userItemsMap=null;

        while(iter.hasNext()) // looking for active user with name <name>
        {
            user=(User)iter.next();
            if(user.getName().equals(name))
            {
                int id=user.getId();
                if(add && (activeUser==null)) {// adding the user to the active users
                    activeUser=user;
                    added = true;
                    groupUserActive=false;
                    break;
                }
                else if(add && (activeUser != null)) // gruop of users
                {
                    added = true;
                    int[] group=getActiveUsersIds(id);
                    groupUserActive=true;
                    GroupUser gUser=user.inTheGruop(users,group);
                    if(gUser==null)
                        gUser=createNewGruopUser(user,group);
                    activeUser=gUser;
                    break;
                }
                else // removing the user
                {
                    if(groupUserActive==true)
                    {
                        GroupUser gUser=(GroupUser)activeUser;
                        if(gUser.getNumOfMembers()>2)
                        {
                            int[] newGruop=createGroupWithoutCurrnetUser(user.getId());
                            //int randomUserId=gUser.getRandomMemberId(user.getId());
                            User randomUser=findUserById(newGruop[0]);
                            GroupUser newGropUser=randomUser.inTheGruop(users,newGruop);
                            if(newGropUser==null)
                                newGropUser=createNewGruopUser(user,newGruop);
                            activeUser=newGropUser;
                            groupUserActive=true;
                        }
                        else if(gUser.getNumOfMembers()==2)
                        {
                            int randomUserId=gUser.getRandomMemberId(user.getId());
                            activeUser=findUserById(randomUserId);
                            groupUserActive=false;
                        }
                    }
                    else
                    {
                        activeUser=null;
                        groupUserActive=false;
                    }
                    break;
                }
            }
        }
        if(activeUser==null)
            addItemsButtons(AppItemsMap,null);
        else
        {
            userItemsMap=activeUser.getItems();
            addItemsButtons(userItemsMap,activeUser);
        }
    }

    private GroupUser createNewGruopUser(User user, int[] group)
    {
        HashMap<Item,Integer> items=user.getItems();
        HashMap<Item,Integer> newItems= new HashMap<Item,Integer>();
        Item itm;
        for (Map.Entry<Item, Integer> entry : items.entrySet())
        {
            itm=entry.getKey();
            newItems.put(itm,0);
        }
        GroupUser gUser=new GroupUser(users,group,newItems);
        users.add(gUser);
        return gUser;
    }

    private int[] createGroupWithoutCurrnetUser(int id)
    {
        GroupUser gUser=(GroupUser)activeUser;
        int size=gUser.getNumOfMembers();
        int[] tmp=new int[size-1];
        int[] members=gUser.getMembers();
        boolean flag=false;
        int j=-1;
        for(int i=0;i<size;i++)
        {
            if(members[i]==id) {
                j = i;
                break;
            }
        }
        if( j == -1)
            return null;
        else
        {
            for(int i=0;i<size-1;i++)
            {
                if(i==j)
                    flag=true;
                if(flag==false)
                    tmp[i]=members[i];
                else
                    tmp[i]=members[i+1];
            }
        }
        return tmp;


    }
    private GroupUser findUserByGrup(int[] group)
    {
        User usr;
        GroupUser gUser;
        for(int i=0;i<users.size();i++)
        {
            usr=users.get(i);
            if(usr instanceof GroupUser)
            {
                gUser=(GroupUser)usr;
                if(gUser.isMyGroup(group)==true)
                    return gUser;
            }
        }
        return null;
    }
    private User findUserById(int id)
    {
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getId()==id)
                return users.get(i);
        }
        return null;
    }

    public int[] getActiveUsersIds(int id)
    {
        // two users
        if( (activeUser != null) && (groupUserActive==false))
        {
            int [] tmp=new int[2];
            tmp[0]=activeUser.getId();
            tmp[1]=id;
            return tmp;
        }
        //group user of more then two users
        if( (activeUser != null) && (groupUserActive==true))
        {

            int [] tmp=new int[((GroupUser)(activeUser)).getMembers().length+1];
            for(int i=0;i<((GroupUser)(activeUser)).getMembers().length;i++)
            {
                tmp[i]=((GroupUser)(activeUser)).getMembers()[i];
            }
            tmp[((GroupUser)(activeUser)).getMembers().length]=id;
            return tmp;
        }
        return null;
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
            Userbutton.setX(-1150+300*i);
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

    public void goToPayment(View view)
    {
        Intent intent = new Intent(PickingItemsActivity.this, PaymentActivity.class);
        ArrayList<User> usersList=(ArrayList<User>)users;
        User usr;
        for(int i=0;i<usersList.size();i++)
        {
            usr=usersList.get(i);
            if(usr instanceof  GroupUser) {
                usersList.remove(usr);
                i=0;
            }
        }
        intent.putExtra("Users",usersList);
        startActivity(intent);
    }
}


