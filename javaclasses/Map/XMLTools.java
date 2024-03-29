package Map;

import java.util.*;
import org.w3c.dom.*;

/**
 * A set of tools to assist with XML parsing.
 *
 * @from Michal Winter
 *
 * Modified by Kyle Robert Harrison to add support for double attibutes (which wasn't actually necessary!).
 */
public class XMLTools {
	
	public static List<Node> getChildNodes(Node node) {
		List<Node> result = new LinkedList<Node>();
		NodeList list = node.getChildNodes();
		for(int i=0;i<list.getLength();i++) 
			if (!list.item(i).getNodeName().equals("#text")) result.add(list.item(i));
		return result;		
	}
	
	public static int getIntAttribute(Node node, String name) {
		Attr attr = (Attr) node.getAttributes().getNamedItem(name);
		return Integer.valueOf(attr.getValue());
	}

	public static double getDoubleAttribute(Node node, String name){
		Attr attr = (Attr) node.getAttributes().getNamedItem(name);
		return Double.valueOf(attr.getValue());
	}
	
	public static boolean getBoolAttribute(Node node, String name) {
		Attr attr = (Attr) node.getAttributes().getNamedItem(name);
		return Boolean.valueOf(attr.getValue());
	}
	
	public static String getStringAttribute(Node node, String name) {
		Attr attr = (Attr) node.getAttributes().getNamedItem(name);
		return attr.getValue();
	}
	
	public static Enum getEnumAttribute(Class c, Node node, String name) {
		Attr attr = (Attr) node.getAttributes().getNamedItem(name);
		Class[] array = new Class[1];
		array[0] = String.class;
		Object obj = null;
		try {
			obj = c.getMethod("valueOf",array).invoke(null,attr.getValue());
		} catch (Exception e) { 
			e.printStackTrace();
		};
		if (obj instanceof Enum) return (Enum) obj;
		return null;
	}
	
}