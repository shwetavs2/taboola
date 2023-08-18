import java.util.*;
/*This class handles the cycles in the tree*/

public class WithCyclicTree implements TreeSerializer {
    private Set<Node> visitedNodes = new HashSet<>();
    private Set<Node> serializedNodes = new HashSet<>();

    @Override
    public String serialize(Node root) {
        visitedNodes.clear(); // Reset the visited nodes
        serializedNodes.clear(); // Reset the serialized nodes
        return serializeNode(root);
    }

    private String serializeNode(Node node) {
        if (node == null) {
            return "X,";
        }

        if (visitedNodes.contains(node)) {
            if (!serializedNodes.contains(node)) {
                throw new RuntimeException("Cyclic tree detected during serialization!");
            }
            return "X,"; // Node already serialized
        }

        visitedNodes.add(node);
        serializedNodes.add(node);

        String serialized = node.num + ",";
        serialized += serializeNode(node.left);
        serialized += serializeNode(node.right);

        visitedNodes.remove(node);

        return serialized;
    }

    @Override
    public Node deserialize(String str) {
        visitedNodes.clear(); // Reset the visited nodes
        return deserializeHelper(new LinkedList<>(Arrays.asList(str.split(","))));
    }

    private Node deserializeHelper(Queue<String> values) {
        String val = values.poll();
        if (val.equals("X")) {
            return null;
        }

        int num = Integer.parseInt(val);
        Node node = new Node(num);

        if (visitedNodes.contains(node)) {
            throw new RuntimeException("Cyclic tree detected during deserialization!");
        }

        visitedNodes.add(node);
        node.left = deserializeHelper(values);
        node.right = deserializeHelper(values);
        visitedNodes.remove(node);

        return node;
    }

    // Rest of the code remains the same
}
