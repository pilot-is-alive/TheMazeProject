package accse.datastructure;

public class Node<E> implements Position<E>{

	private E element;
	private Node<E>prev;
	private Node<E> next;
	//Constructor
	
	/**
	 * @param e
	 * @param p
	 * @param n
	 */
	public Node(E e, Node<E> p, Node<E> n)
	{
		this.element=e;
		this.next=n;
		this.prev=p;
	}
	/**
	 * @return the prev
	 * Getting the previous node
	 */
	public Node<E> getPrev() {
		return prev;
	}
	/**
	 * @param prev the prev to set
	 * 
	 */
	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}
	/**
	 * @return the next
	 */
	public Node<E> getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(Node<E> next) {
		this.next = next;
	}
	/**
	 * @return the element
	 */
	@Override
	public E getElement() throws IllegalStateException {
		// TODO Auto-generated method stub
		if(next==null)
			throw new IllegalStateException("Position no longer valid");
		return element;
		
	}
	public void setElement(E e)
	{
		element=e;
	}
	
}
