package Map;

import org.w3c.dom.Node;

import java.util.List;

/**
 * RoadSegmentReader constructs a RoadSegment from an XML Node
 * @from Kyle Robert Harrison
 * @version 1.0
 *          06/10/16
 */
public class RoadSegmentReader implements XMLNodeConverter<RoadSegment>{
    @Override
    public RoadSegment convertXMLNode(Node node) {
        RoadSegment roadSegment = null;

        if(node.getNodeName().equals("roadSegment")){
            String id = XMLTools.getStringAttribute(node, "id");
            List<Node> children = XMLTools.getChildNodes(node);

            String from = children.get(0).getTextContent();
            String to = children.get(1).getTextContent();
            String direction = children.get(2).getTextContent();
            int lanes = Integer.valueOf(children.get(3).getTextContent());
            roadSegment = new RoadSegment(id, from, to, direction, lanes);
        }

        return roadSegment;
    }
}
