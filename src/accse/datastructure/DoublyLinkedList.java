package accse.datastructure;

import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements PositionList<E>{

	private Node<E> header;
	private Node<E> trailer;
	private int size=0;
	//private int cursor;
	//Constructor
	public DoublyLinkedList()
	{
		header= new Node<>(null,null,null);
		trailer= new Node<>(null, header,null);
		header.setNext(trailer);
	}

	@Override
	public int size() {return this.size;}
	
	@Override
	public boolean isEmpty() {return size==0;}
//	
//	public E first() {
//		if(isEmpty()) return null;
//		return header.getNext().getElement();
//	}
	
//	public E last(){
//		if(isEmpty())return null;
//		return trailer.getPrev().getElement();
//		
//	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E next() throws NoSuchElementException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		else
		//Returning the node as a position(or null if it is a sentinel
			if(node==header|| node==trailer)
			{
				return null;
			}else
		return node.getPrev();
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		else
		//Returning the node as a position(or null if it is a sentinel
			if(node==header|| node==trailer)
			{
				return null;
			}else
		return node.getNext();
		
	}

	private Position<E> addBetween(E e, Node<E>pred, Node<E>succ)
	{
		Node<E> newest= new Node<>(e,pred, succ);
		pred.setNext(newest);
		succ.setPrev(newest);
		size++;
		return newest;
	}
	@Override
	public Position<E> addFirst(E e) {
		// TODO Auto-generated method stub
		return addBetween(e,header,header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		// TODO Auto-generated method stub
		return addBetween(e,trailer.getPrev(),trailer);
	}

	@Override
	public Position<E> first() {
		// TODO Auto-generated method stub
		if(header.getNext()==trailer)
			return null;
		return header.getNext();
	}

	@Override
	public Position<E> last() {
		// TODO Auto-generated method stub
		if(trailer.getPrev()==header)
			return null;
		return trailer.getPrev();
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		
		return addBetween(e,node.getPrev(),node);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		
		return addBetween(e,node,node.getNext());
		
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		E answer = node.getElement();
		node.setElement(e);
		return answer;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(!(p instanceof Node))throw new IllegalArgumentException("Invalid p");
		Node<E> node= (Node<E>)p;
		if(node.getNext()==null)throw new IllegalArgumentException("p is no longer in the list");
		Node<E>predecessor=node.getPrev();
		Node<E>successor=node.getNext();
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		E answer= node.getElement();
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		return answer;
	}
}
