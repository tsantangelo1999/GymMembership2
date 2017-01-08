package com.company;

public class Customer
{
    public String name;
    public boolean isEmployee;
    public double weight;
    public int classCount;
    public int trainerHours;
    public double retailTotal;

    public Customer(String n, double w, boolean e)
    {
        name = n;
        weight = w;
        isEmployee = e;
        classCount = 0;
        trainerHours = 0;
    }

    public double calcBill()
    {
        double bill = 0;
        if(isEmployee)
        {
            bill += classCount * 3;
            bill += trainerHours * 15;
        }
        else
        {
            bill += (this instanceof CustomerMember) ? classCount * 8 : classCount * 10;
            bill += (this instanceof CustomerMember) ? trainerHours * 20 : trainerHours * 25;
        }
        bill += retailTotal;
        return bill;
    }

    public CustomerMember upgrade()
    {
        CustomerMember up = new CustomerMember(name, weight, isEmployee);
        up.classCount = classCount;
        up.trainerHours = trainerHours;
        up.retailTotal = retailTotal;
        return up;
    }
}