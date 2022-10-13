package Map;

import org.w3c.dom.Node;

/**
 * An XMLNodeConverter provides a method to convert an XML node
 * to it's corresponding (generic) object type.
 *
 * @from Michal Winter
 * @param <E> The type of object this node represents.
 */
public interface XMLNodeConverter<E> {
	
	public E convertXMLNode(Node node);
	
}