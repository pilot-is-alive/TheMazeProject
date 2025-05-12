package accse.datastructure;

public interface Position <E>{
	/**
	 * Returns the element stored at this position
	 * 
	 * @return the Stored element
	 * @throws IllegalStateException
	 */
	E getElement() throws IllegalStateException;

}
