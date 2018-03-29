package com.imperialsoupgmail.tesseractexample;

/**
 * Created by Guy on 29/03/2018.
 */

public class Item {

    private int price;
    private String name;

    public Item(String name, int price)
    {
        this.name=name;
        this.price=price;
    }

    public void updatePrice(int price)
    {
        this.price=price;
    }

    public void updateName(String name)
    {
        this.name=name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getPrice()
    {
        return  this.price;
    }


}
