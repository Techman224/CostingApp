import java.util.*;

public class Calculations2
{
	private AtomicInteger atomicInteger = new AtomicInteger();

	public static void main(String[] args)
	{
		Database db = new Database();
	}

	public static int newOrder()
	{
		int newOrderID = AtomicInteger.get();
		return newOrderID;
	}

	public static void newItem(String store, String description, String type, float price)
	{
		int newItemID = AtomicInteger.get();
		insert("costs", store + " " + description + " " + type + " " + price);
	}

	public static void editItem(String store, String description, String type, float price)
	{

	}
}