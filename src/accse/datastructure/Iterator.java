package accse.datastructure;

import java.util.NoSuchElementException;

public interface Iterator<E> {

	/**
	 * 
	 * @return true if there is at least one additional element in the sequence and false otherwise
	 */
	public boolean hasNext();
	/**
	 * 
	 * @return the next element in the sequence
	 */
	public E next() throws NoSuchElementException;
	
	
}
