package com.imperialsoupgmail.tesseractexample;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guy on 29/03/2018.
 */

public class User implements Serializable {
    private int id;
    private String name;
    private static int numOfUsers=0;
    private int totalPayment;
    HashMap<Item,Integer> items;

    public User()
    {
        this.name=null;
        numOfUsers++;
        this.id=numOfUsers;
        this.totalPayment=0;
        this.items=new HashMap<Item,Integer>();
    }

    public User(String name,HashMap<Item,Integer> items)
    {
        this.items=items;
        this.name=name;
        numOfUsers++;
        this.id=numOfUsers;
        this.totalPayment=0;
        this.items=new HashMap<Item,Integer>();
    }
    public HashMap<Item,Integer> getItems()
    {
        return this.items;
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

}
