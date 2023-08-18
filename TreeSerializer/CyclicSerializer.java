import java.util.*;
/*This class throws a runtime exception when a cycle is detected*/

public class CyclicSerializer implements TreeSerializer {
    private Set<Node> visitedNodes = new HashSet<>();

    @Override
    public String serialize(Node root) {
        visitedNodes.clear(); // Reset the visited nodes
        return serializeNode(root);
    }

    private String serializeNode(Node node) {
        if (node == null) {
            return "X,";
        }

        if (visitedNodes.contains(node)) {
            throw new RuntimeException("Cyclic tree detected!");
        }

        visitedNodes.add(node);
        String serialized = node.num + ",";
        serialized += serializeNode(node.left);
        serialized += serializeNode(node.right);
        visitedNodes.remove(node);

        return serialized;
    }

    @Override
    public Node deserialize(String str) {
        visitedNodes.clear(); // Reset the visited nodes
        String[] tokens = str.split(",");
        Queue<String> values = new LinkedList<>(Arrays.asList(tokens));
        return deserializeHelper(values);
    }

    private Node deserializeHelper(Queue<String> values) {
        String val = values.poll();
        if (val.equals("X")) {
            return null;
        }

        int num = Integer.parseInt(val);
        Node node = new Node(num);

        if (visitedNodes.contains(node)) {
            throw new RuntimeException("Cyclic tree detected!");
        }

        visitedNodes.add(node);
        node.left = deserializeHelper(values);
        node.right = deserializeHelper(values);
        visitedNodes.remove(node);

        return node;
    }

 private static void printTree(Node node) {
         if (node == null) {
            return;
    }

    System.out.print(node.num + " ");
    printTree(node.left);
    printTree(node.right);
    }
   
}
