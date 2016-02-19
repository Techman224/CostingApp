package com.softeng.jobcosting.jobcostingapp;

public class Calculations
{
    public static void main(String[] args)
    {
        System.out.println("hello world!");
    }
}

class Order
{
    private int day;
    private int month;
    private int year;
    private int id;
    private String store;

    private float board;
    private float boardDemo;
    private float accessories;
    private float promoItems;
    private float shippingTo;
    private float shippingFrom;
    private float fees;
    private float GSTPaid;
    private float PSTPaid;
    private float GSTCharged;
    private float PSTCharged;

    public Order()
    {
        // empty constructor
    }

    public float profit()
    {
        float profit;
        // warning not finalized
        profit = board + shippingTo - fees - shippingFrom - GSTPaid - promoItems;

        return profit;
    }

    public float margin()
    {
        return profit() / board;
    }

    public float total()
    {
        // warning not finalized
        // calculates total of the order
        return (float)2.2;
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
}

class Node
{
    private Order head;
    private Node next;

    public Node()
    {
        head = null;
        next = null;
    }

    public Node(Order head)
    {
        this.head = new Order();
        this.head = head;
        next = null;
    }

    public Order getNode()
    {
        return head;
    }

    public Node getNext()
    {
        return next;
    }

    public void setNode(Order head)
    {
        this.head = head;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }
}