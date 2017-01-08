package com.company;

import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
    public static ArrayList<Customer> people = new ArrayList<>();

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        top: while(true)
        {
            boolean upgrade;
            System.out.println("What is your name?");
            String name = input.nextLine();
            for(int i = 0; i < people.size(); i++)
            {
                if(name.equalsIgnoreCase(people.get(i).name))
                {
                    upgrade = enterSession(people.get(i));
                    if(upgrade)
                        people.remove(i);
                    continue top;
                }
            }
            if(name.equalsIgnoreCase("exit"))
                break;
            double weight = 0;
            while(weight == 0)
            {
                System.out.println("What is your weight?");
                try
                {
                    String wt = input.nextLine();
                    weight = Double.parseDouble(wt);
                }
                catch(Exception e)
                {
                    System.out.println("Invalid input.");
                }
            }
            boolean emp;
            String response = null;
            while(response == null)
            {
                System.out.println("Are you an employee? (Y/N)");
                response = input.nextLine();
                if(!(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("n")))
                {
                    System.out.println("Invalid input.");
                    response = null;
                }
            }
            emp = response.equalsIgnoreCase("y");

            people.add(new Customer(name, weight, emp));
            upgrade = enterSession(people.get(people.size() - 1));
            if(upgrade)
                people.remove(people.size() - 2);
        }
    }

    public static boolean enterSession(Customer cust)
    {
        Scanner input = new Scanner(System.in);
        boolean upgraded = false;

        if(!(cust instanceof CustomerMember))
            System.out.println(
                    "You are not a member, you may become a membership for " + (cust.isEmployee ? "$1" : "$65") + " per month.");
        System.out.println("You have been to " + cust.classCount + " classes this month.");
        System.out.println("You have spent " + cust.trainerHours + " hours with a trainer this month.");
        System.out.println("You have $" + cust.retailTotal + " worth of retail items on this month's bill.");
        if(cust instanceof CustomerMember)
            System.out.println("You have been to " + ((CustomerMember) cust).aquatics + " aquatics sessions this month.");
        String respond = "";
        while(!(respond.equalsIgnoreCase("exit")))
        {
            System.out.println("Would you like to ");
            if(!(cust instanceof CustomerMember))
            {
                System.out.print("view your monthly BILL for this month, become a MEMBER, go to CLASS, work with a TRAINER, purchase RETAIL items, or EXIT?\n");
                do
                    respond = input.nextLine();
                while(!(respond.equalsIgnoreCase("bill") || respond.equalsIgnoreCase("member") || respond.equalsIgnoreCase("class") || respond.equalsIgnoreCase("trainer") || respond.equalsIgnoreCase("retail") || respond.equalsIgnoreCase("exit")));
                if(respond.equalsIgnoreCase("bill"))
                    System.out.println("Your bill for this month is $" + (int)(cust.calcBill()*100)/100.0 + ".");
                if(respond.equalsIgnoreCase("member"))
                {
                    people.add(cust.upgrade());
                    cust = people.get(people.size() - 1);
                    upgraded = true;
                    System.out.println("You are now a member.");
                }
                if(respond.equalsIgnoreCase("class"))
                {
                    cust.classCount++;
                    System.out.println("You have now been to " + cust.classCount + " classes this month.");
                }
                if(respond.equalsIgnoreCase("trainer"))
                {
                    System.out.println("How many hours?");
                    int hours;
                    while(true)
                    {
                        try
                        {
                            String hr = input.nextLine();
                            hours = Integer.parseInt(hr);
                            if(hours < 1)
                                throw new Exception();
                            break;
                        }
                        catch(Exception e)
                        {
                            System.out.println("Hours must be a positive integer.");
                        }
                    }
                    cust.trainerHours += hours;
                    System.out.println("You have now spent " + cust.trainerHours + " hours with a trainer this month.");
                }
                if(respond.equalsIgnoreCase("retail"))
                {
                    System.out.println("Would you like to purchase (Enter 1, 2, 3, or 4)\n1. Gatorade ($5)\n2. Energy bar ($4)\n3. Water ($2)\n4. Nothing");
                    String item = "";
                    while(!(item.equalsIgnoreCase("1") || item.equalsIgnoreCase("2") || item.equalsIgnoreCase("3") || item.equalsIgnoreCase("4")))
                    {
                        item = input.nextLine();
                        if(!(item.equalsIgnoreCase("1") || item.equalsIgnoreCase("2") || item
                                .equalsIgnoreCase("3") || item.equalsIgnoreCase("4")))
                            System.out.println("Please enter 1, 2, 3, or 4.");
                    }
                    switch(item)
                    {
                        case "1": cust.retailTotal += cust.isEmployee ? 5 * .9 : 5;
                        case "2": cust.retailTotal += cust.isEmployee ? 4 * .9 : 4;
                        case "3": cust.retailTotal += cust.isEmployee ? 2 * .9 : 2;
                    }
                }
            }
            if(cust instanceof CustomerMember)
            {
                System.out.print("view your monthly BILL for this month, WORK OUT, go to CLASS, work with a TRAINER, purchase RETAIL items, have an AQUATICS session, or EXIT?\n");
                do
                    respond = input.nextLine();
                while(!(respond.equalsIgnoreCase("bill") || respond.equalsIgnoreCase("work out") || respond.equalsIgnoreCase("class") || respond.equalsIgnoreCase("trainer") || respond.equalsIgnoreCase("retail") || respond.equalsIgnoreCase("aquatics") || respond.equalsIgnoreCase("exit")));
                if(respond.equalsIgnoreCase("bill"))
                    System.out.println("Your bill for this month is $" + cust.calcBill() + ".");
                if(respond.equalsIgnoreCase("work out"))
                {
                    System.out.println("How many hours will you work out for?");
                    double hours;
                    while(true)
                    {
                        try
                        {
                            String hr = input.nextLine();
                            hours = Double.parseDouble(hr);
                            if(hours <= 0)
                                throw new Exception();
                            break;
                        }
                        catch(Exception e)
                        {
                            System.out.println("Hours must be positive.");
                        }
                    }
                    ((CustomerMember) cust).visits.add(hours);
                }
                if(respond.equalsIgnoreCase("class"))
                {
                    cust.classCount++;
                    System.out.println("You have now been to " + cust.classCount + " classes this month.");
                }
                if(respond.equalsIgnoreCase("trainer"))
                {
                    System.out.println("How many hours?");
                    int hours;
                    while(true)
                    {
                        try
                        {
                            String hr = input.nextLine();
                            hours = Integer.parseInt(hr);
                            if(hours < 1)
                                throw new Exception();
                            break;
                        }
                        catch(Exception e)
                        {
                            System.out.println("Hours must be a positive integer.");
                        }
                    }
                    cust.trainerHours += hours;
                    System.out.println("You have now spent " + cust.trainerHours + " hours with a trainer this month.");
                }
                if(respond.equalsIgnoreCase("retail"))
                {
                    System.out.println("Would you like to purchase (Enter 1, 2, 3, or 4)\n1. Gatorade ($5)\n2. Energy bar ($4)\n3. Water ($2)\n4. Nothing");
                    String item = "";
                    while(!(item.equalsIgnoreCase("1") || item.equalsIgnoreCase("2") || item.equalsIgnoreCase("3") || item.equalsIgnoreCase("4")))
                    {
                        item = input.nextLine();
                        if(!(item.equalsIgnoreCase("1") || item.equalsIgnoreCase("2") || item
                                .equalsIgnoreCase("3") || item.equalsIgnoreCase("4")))
                            System.out.println("Please enter 1, 2, 3, or 4.");
                    }
                    switch(item)
                    {
                        case "1": cust.retailTotal += 5 * .8;
                        case "2": cust.retailTotal += 4 * .8;
                        case "3": cust.retailTotal += 2 * .8;
                    }
                }
                if(respond.equalsIgnoreCase("aquatics"))
                {
                    ((CustomerMember) cust).aquatics++;
                    System.out.println("You have been to " + ((CustomerMember) cust).aquatics + " aquatics sessions this month.");
                }
            }
        }
        return upgraded;
    }
}

