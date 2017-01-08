package com.company;

import java.util.ArrayList;

public class CustomerMember extends Customer
{
    public int aquatics;
    public ArrayList<Double> visits = new ArrayList<>();

    public CustomerMember(String n, double w, boolean e)
    {
        super(n, w, e);
        aquatics = 0;
    }

    public double calcBill()
    {
        double bill = super.calcBill();
        if(isEmployee)
        {
            bill += 1;
            bill += aquatics * 7;
        }
        else
        {
            bill += 65;
            bill += aquatics * 10;
        }
        return bill;
    }
}
