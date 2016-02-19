package com.softeng.jobcosting.jobcostingapp;

public class LinkedList<E>
{
    private Node<E> head;
    private Node<E> curr;
    private Node<E> prev;

    public LinkedList()
    {
        head = null;
        curr = null;
        prev = null;
    }

    public void add(E item)
    {
        if(head == null)
        {
            head = new Node<E>(item);
        }
        else
        {
            curr = head;
            while(curr != null)
            {
                prev = curr;
                curr = curr.getNext();
            }
            curr = new Node<E>(item);
            prev.setNext(curr);
        }
    }

    public String returnList()
    {
        String value = "";

        curr = head;
        while(curr != null)
        {
            value += curr.getData().toString();
            curr = curr.getNext();
        }

        return value;
    }

}
