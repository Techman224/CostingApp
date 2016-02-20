package com.softeng.jobcosting.jobcostingapp;

import java.util.*;

public class Calculations
{
    public static void main(String[] args)
    {
        Order sampleorder = new Order(11, 06, 2015, 1006, "Shopify");

        sampleorder.addBoard("12-6 Race", (float) 1749.00);
        sampleorder.addShippingTo("Shipping", (float) 100.00);
        sampleorder.addFees("Shopify Fees", (float) -53.92);
        sampleorder.addBrand("Red Paddle", (float) -1175.00);
        sampleorder.addShippingFrom("Shipping", (float) -55.00);
        sampleorder.addShippingTo("Drop Ship", (float) -75.00);
        sampleorder.addGSTPaid((float) -65.25);
        sampleorder.addPromoItems("Paddle & Leash", (float) -141.53);
        sampleorder.addShippingTo("UPS Shipping", (float) -54.29);

        Order[] arrayorder = new Order[5];
        arrayorder[0] = new Order(11, 06, 215,115,"ets");

        LinkedList<Order> listoforders = new LinkedList<Order>();

        TotalOrder allorders = new TotalOrder(listoforders);


        System.out.println(sampleorder.toString());
    }
}

class Order
{
    private int day;
    private int month;
    private int year;
    private int id;
    private String store;
    private float profit;
    private float boardTotal;
    private float orderTotal;
    private float PSTTotal;
    private float GSTTotal;
    private int numberBoardSold;

    private Board board;
    private Accessories accessories;
    private PromoItems promoItems;
    private ShippingTo shippingTo;
    private ShippingFrom shippingFrom;
    private Fees fees;
    private GSTPaid GSTPaid;
    private PSTPaid PSTPaid;
    private GSTCharged GSTCharged;
    private PSTCharged PSTCharged;
    private LinkedList<OrderDetailType> list;

    public Order(int day, int month, int year, int id, String store)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.id = id;
        this.store = store;
        this.profit = 0;
        this.boardTotal = 0;
        this.orderTotal = 0;
        this.PSTTotal = 0;
        this.GSTTotal = 0;
        this.numberBoardSold = 0;

        board = null;
        accessories = null;
        promoItems = null;
        shippingTo = null;
        shippingFrom = null;
        fees = null;
        GSTPaid = null;
        PSTPaid = null;
        GSTCharged = null;
        PSTCharged = null;
        list = new LinkedList<OrderDetailType>();
    }

    public void addBoard(String description, float amount)
    {
        list.add(new Board(description, amount));
        orderTotal += amount;
        profit += amount;
        boardTotal += amount;
        numberBoardSold++;
    }

    public float boardAmount()
    {
        return boardTotal;
    }

    public int getNumberBoardSold()
    {
        return numberBoardSold;
    }

    public void addShippingTo(String description, float amount)
    {
        list.add(new ShippingTo(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addShippingFrom(String description, float amount)
    {
        list.add(new ShippingFrom(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addFees(String description, float amount)
    {
        list.add(new Fees(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addAccessories(String description, float amount)
    {
        list.add(new Accessories(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addPromoItems(String description, float amount)
    {
        list.add(new PromoItems(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addGSTPaid(float amount)
    {
        list.add(new GSTPaid(amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addPSTPaid(float amount)
    {
        list.add(new PSTPaid(amount));
        orderTotal += amount;
        profit += amount;
    }

    public void addGSTCharged(float amount)
    {
        list.add(new GSTCharged(amount));
        orderTotal += amount;
        profit += amount;
        GSTTotal += amount;
    }

    public float GSTTotal()
    {
        return GSTTotal;
    }

    public void addPSTCharged(float amount)
    {
        list.add(new PSTCharged(amount));
        orderTotal += amount;
        PSTTotal += amount;
    }

    public float PSTTotal()
    {
        return PSTTotal;
    }

    public void addBrand(String description, float amount)
    {
        list.add(new Brand(description, amount));
        orderTotal += amount;
        profit += amount;
    }

    public float profit()
    {
        return profit;
    }

    public float margin()
    {
        return profit / boardTotal;
    }

    public float total()
    {
        return orderTotal;
    }

    public String toString()
    {
        String value = "";

        value += list.returnList();
        value += total() + "\n";
        value += profit() + "\n";
        value += margin() + "\n";

        return value;
    }
}

class TotalOrder
{
    private LinkedList<Order> allOrders;

    public TotalOrder(LinkedList<Order> allOrders)
    {
        this.allOrders = allOrders;
    }

    public float totalSold()
    {
        // sum board amounts from order objects
        Iterator<Order> itr = allOrders.iterator();
        float totalSold = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalSold += item.boardAmount();
        }
        System.out.println(totalSold);
        return totalSold;
    }

    public float totalProfit()
    {
        // sum profit amounts from order objects
        Iterator<Order> itr = allOrders.iterator();
        float totalProfit = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalProfit += item.profit();
        }
        System.out.println(totalProfit);
        return totalProfit;
    }

    public int totalBoardSold()
    {
        // increment when seeing boards in objects
        Iterator<Order> itr = allOrders.iterator();
        int totalBoardSold = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalBoardSold += item.getNumberBoardSold();
        }
        System.out.println(totalBoardSold);
        return totalBoardSold;
    }

    public float profitPerBoard()
    {
        return totalProfit() / totalBoardSold();
    }

    public float totalMargin()
    {
        // sum margins from objects then divide by the # of objects
        Iterator<Order> itr = allOrders.iterator();
        float totalMargin = 0;
        int numberOfOrders = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalMargin += item.margin();
            numberOfOrders++;
        }
        totalMargin /= numberOfOrders;
        System.out.println(totalMargin);
        return totalMargin;
    }

    public float totalPST()
    {
        // sum PSTCharged from objects
        Iterator<Order> itr = allOrders.iterator();
        float totalPST = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalPST += item.PSTTotal();
        }
        System.out.println(totalPST);
        return totalPST;
    }

    public float totalGST()
    {
        // sum GSTCharged from objects
        Iterator<Order> itr = allOrders.iterator();
        float totalGST = 0;

        while(itr.hasNext())
        {
            Order item = itr.next();
            totalGST += item.GSTTotal();
        }
        System.out.println(totalGST);
        return totalGST;
    }

    public float grandTotal()
    {
        // sum of all orders
        return (float)2.2;
    }
}

abstract class OrderDetailType
{
    private String description;
    private float amount;

    public OrderDetailType(String description, float amount)
    {
        this.description = description;
        this.amount = amount;
    }

    public String toString()
    {
        return "Description: " + description + " Amount: $" + amount + "\n";
    }
}

class Board extends OrderDetailType
{
    public Board(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class ShippingTo extends OrderDetailType
{
    public ShippingTo(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class ShippingFrom extends OrderDetailType
{
    public ShippingFrom(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class Fees extends OrderDetailType
{
    public Fees(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class Accessories extends OrderDetailType
{
    public Accessories(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class PromoItems extends OrderDetailType
{
    public PromoItems(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class GSTPaid extends OrderDetailType
{
    public GSTPaid(float amount)
    {
        super("GST", amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class PSTPaid extends OrderDetailType
{
    public PSTPaid(float amount)
    {
        super("PST", amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class GSTCharged extends OrderDetailType
{
    public GSTCharged(float amount)
    {
        super("GST", amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class PSTCharged extends OrderDetailType
{
    public PSTCharged(float amount)
    {
        super("PST", amount);
    }

    public String toString()
    {
        return super.toString();
    }
}

class Brand extends OrderDetailType
{
    public Brand(String description, float amount)
    {
        super(description, amount);
    }

    public String toString()
    {
        return super.toString();
    }
}