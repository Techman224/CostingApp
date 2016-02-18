public class calculations
{
    public static void main(String[] args)
    {
        System.out.println("hello world!");
    }
}

class order
{
    private int day;
    private int month;
    private int year;
    private int id;
    private string store;

    private float board;
    private float boardDemo;
    private float accessories;
    private float promoItems;
    private float shippingTo;
    private float shipppingFrom;
    private float fees;
    private float GSTPaid;
    private float PSTPaid;
    private float GSTCharged;
    private float PSTCharged;

    public order()
    {
        // empty constructor
    }

    public float profit()
    {
        float profit;
        // warning not finalized
        profit = board + shippingTo - fees - shippingFrom - GSTPaid - promoItems

        return profit;
    }

    public float margin()
    {
        return profit / board;
    }

    public float total()
    {
        // warning not finalized
        // calculates total of the order
    }
}

class totalOrder
{
    public totalOrder()
    {
        // empty constructor
    }

    public float totalSold()
    {
        // sum board amounts from order objects
    }

    public float totalProfit()
    {
        // sum profit amounts from order objects
    }

    public int boardsSold()
    {
        // increment when seeing boards in objects
    }

    public float profitPerBoard()
    {
        return totalProfit() / boardsSold();
    }

    public float totalMargin()
    {
        // sum margins from objects then divide by the # of objects
    }

    public float totalPST()
    {
        // sum PSTCharged from objects
    }

    public float totalGST()
    {
        // sum GSTCharged from objects
    }
}