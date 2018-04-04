package com.imperialsoupgmail.tesseractexample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Guy on 29/03/2018.
 */

public class User implements Serializable {
    private int id;
    private String name;
    private static int numOfUsers=0;
    private double totalPayment;
    HashMap<Item,Integer> items;
    List<Integer> shareGrups;

    public User()
    {
        this.name=null;
        numOfUsers++;
        this.id=numOfUsers;
        this.totalPayment=0;
        this.items=new HashMap<Item,Integer>();
        this.shareGrups = new ArrayList<Integer>();
    }

    public User(String name,HashMap<Item,Integer> items)
    {
        this.items=items;
        this.name=name;
        numOfUsers++;
        this.id=numOfUsers;
        this.totalPayment=0;
        this.shareGrups = new ArrayList<Integer>();

    }


    public double getTotalPayment()
    {
        return this.totalPayment;
    }
    public HashMap<Item,Integer> getItems()
    {
        return this.items;
    }

    public List<Item> getItemsList()
    {
        List<Item> itemsList = new ArrayList<Item>();

        for (Map.Entry<Item, Integer> entry : this.items.entrySet()) {
            itemsList.add(entry.getKey());
        }
        return itemsList;
    }



    public void updateName(String name)
    {
        this.name=name;
    }

    public void insertItem(Item itm, int amount)
    {
        int val=this.items.get(itm);
        this.items.put(itm,val+amount);
        totalPayment+=amount*itm.getPrice();

    }

    public void updateItem(Item itm, int amount)
    {
        int val=this.items.get(itm);
        this.items.put(itm,amount);
        totalPayment+=(amount-val)*itm.getPrice();
    }

    public void removeItem(Item itm)
    {
        int val=this.items.get(itm);
        this.items.put(itm,0);
        totalPayment-=val*itm.getPrice();
    }

    public String getName()
    {
        return  this.name;
    }

    public int getId()
    {
        return this.id;
    }

    public void addToshareGrups(int newGroupUser)
    {
        this.shareGrups.add(newGroupUser);
    }

    public GroupUser inTheGruop(List<User> usersList,int[] gruop)
    {
        Iterator<User> iter=usersList.iterator();
        User usr;
        GroupUser gUser;
        int id;
        for(int i=0 ; i < this.shareGrups.size() ;i++) {
            id=this.shareGrups.get(i);
            gUser=(GroupUser)findUserById(id, usersList);
            if(gUser.isMyGroup(gruop))
                return  gUser;
        }
        return null;
    }

    protected User findUserById(int id,List<User> users)
    {
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getId()==id)
                return users.get(i);
        }
        return null;
    }

    protected void addToTotalPayment(double sum)
    {
        this.totalPayment+=sum;
    }





}
