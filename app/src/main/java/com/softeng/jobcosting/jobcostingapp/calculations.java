package com.softeng.jobcosting.jobcostingapp;

public class Calculations
{
    public static void main(String[] args)
    {
        Order sampleorder = new Order();

        sampleorder.addBoard("12-6 Race", (float)1749.00);
        sampleorder.addBoard("10-6 Ride", (float)1399.00);
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

    public Order()
    {
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
    }

    public void addShippingTo(String description, float amount)
    {
        list.add(new ShippingTo(description, amount));
    }

    public void addShippingFrom(String description, float amount)
    {
        list.add(new ShippingFrom(description, amount));
    }

    public void addFees(String description, float amount)
    {
        list.add(new Fees(description, amount));
    }

    public void addAccessories(String description, float amount)
    {
        list.add(new Accessories(description, amount));
    }

    public void addPromoItems(String description, float amount)
    {
        list.add(new PromoItems(description, amount));
    }

    public float profit()
    {
        float profit = (float)2.2;
        // warning not finalized


        return profit;
    }

    public float margin()
    {
        return (float)2.2;
    }

    public float total()
    {
        // warning not finalized
        // calculates total of the order
        return (float)2.2;
    }

    public String toString()
    {
        return list.returnList();
    }
}

class TotalOrder
{
    public TotalOrder()
    {
        // empty constructor
    }

    public float totalSold()
    {
        // sum board amounts from order objects
        return (float)2.2;
    }

    public float totalProfit()
    {
        // sum profit amounts from order objects
        return (float)2.2;
    }

    public int boardsSold()
    {
        // increment when seeing boards in objects
        return 2;
    }

    public float profitPerBoard()
    {
        return totalProfit() / boardsSold();
    }

    public float totalMargin()
    {
        // sum margins from objects then divide by the # of objects
        return (float)2.2;
    }

    public float totalPST()
    {
        // sum PSTCharged from objects
        return (float)2.2;
    }

    public float totalGST()
    {
        // sum GSTCharged from objects
        return (float)2.2;
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
        return "Description: " + description + " Amount: $" + amount;
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