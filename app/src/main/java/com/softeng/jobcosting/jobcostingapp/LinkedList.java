package com.softeng.jobcosting.jobcostingapp;

import java.util.Iterator;

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

    public Iterator<E> iterator()
    {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E>
    {
        private Node<E> next;

        public LinkedListIterator()
        {
            next = head;
        }

        public boolean hasNext()
        {
            return next.getNext() != null;
        }

        public E next()
        {
            E result = null;
            if(!hasNext())
            {
                result = next.getData();
                next = next.getNext();
            }
            return result;
        }

        public void remove()
        {
            // Optional implementation (won't need it for now)
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