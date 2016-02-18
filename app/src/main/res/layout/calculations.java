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

    }

    public float profit()
    {
        float profit;

        profit = board + shippingTo - fees - shippingFrom - GSTPaid - promoItems

        return profit;
    }

    public float margin()
    {

    }

    public float total()
    {

    }

    public float totalSold()
    {

    }

    public float totalProfit()
    {

    }

    public int boardsSold()
    {

    }

    public float profitPerBoard()
    {

    }

    public float totalMargin()
    {

    }

    public float totalPST()
    {

    }

    public float totalGST()
    {

    }
}