package com.softeng.jobcosting.jobcostingapp.BusinessLogic;

public class Node<E>
{
    private E data;
    private Node<E> next;

    public Node()
    {
        data = null;
        next = null;
    }

    public Node(E head)
    {
        this.data = head;
        this.next = null;
    }

    public E getData()
    {
        return data;
    }

    public Node getNext()
    {
        return next;
    }

    public void setData(E head)
    {
        this.data = head;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }
}