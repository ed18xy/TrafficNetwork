package Map;
import org.w3c.dom.Node;

import java.util.List;

/**
 * MapReader constructs a Map from an XML Node.
 *
 * @from Kyle Robert Harrison
 * @version 1.0
 *          06/10/16
 */
public class MapReader implements XMLNodeConverter<Map> {

    private RoadSegmentReader roadSegmentReader;

    public MapReader(){
        roadSegmentReader = new RoadSegmentReader();
    }

    @Override
    public Map convertXMLNode(Node node) {
        Map map = new Map();

        if(node.getNodeName().equals("map")){
            List<Node> nodes = XMLTools.getChildNodes(node);
            for(int i = 0; i < nodes.size(); i++){
                RoadSegment roadSegment = roadSegmentReader.convertXMLNode(nodes.get(i));
                map.addRoadSegment(roadSegment);
            }
        }

        return map;
    }
}
