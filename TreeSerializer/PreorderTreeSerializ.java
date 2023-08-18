import java.util.*;

public class PreorderTreeSerializ implements TreeSerializer {

    @Override
    public String serialize(Node root) {
        if (root == null) {
            return "X,";
        }
        String serialized = root.num + ",";
        serialized += serialize(root.left);
        serialized += serialize(root.right);
        return serialized;
    }

    @Override
    public Node deserialize(String str) {
        String[] tokens = str.split(",");
        Queue<String> values = new LinkedList<>(Arrays.asList(tokens));
        return deserializeHelper(values);
    }
    
    private Node deserializeHelper(Queue<String> values) {
        String val = values.poll();
        if (val.equals("X")) {
            return null;
        }
        Node node = new Node(Integer.parseInt(val));
        node.left = deserializeHelper(values);
        node.right = deserializeHelper(values);
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

    public static void main(String[] args) {
        Node root = new Node(1);
        root.left = new Node(2);
        root.right = new Node(1);
        root.left.left = new Node(7);
        root.left.right = new Node(5);
        root.right.right = new Node(28);
        root.left.left.left = new Node(4);

        TreeSerializer serializer = new PreorderTreeSerializ();
    /*Basic Tree Serializer using PreOrder*/
    
        String serializedTree = serializer.serialize(root);
        System.out.println("Serialized tree: " + serializedTree);

        Node deserializedTree = serializer.deserialize(serializedTree);
        System.out.println("Deserialized tree: ");
        printTree(deserializedTree);

    /*TreeSerializer that takes into account a “cyclic tree”. implementation throws a RuntimeException when a cyclic connection is found in the Tree.*/
        
     
        TreeSerializer cyclicSerializer = new CyclicSerializer();

        String serializedCyclicTree = cyclicSerializer.serialize(root);
        System.out.println("\nSerialized cyclic tree: " + serializedCyclicTree);

        try {
            Node deserializedCyclicTree = cyclicSerializer.deserialize(serializedCyclicTree);
            System.out.println("Deserialized cyclic tree: ");
            printTree(deserializedCyclicTree);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    /* TreeSerializer that works even with cyclic Tree*/
        try {
        TreeSerializer cyclicSerializerTree = new WithCyclicTree();

        String serializedCyclicTree2 = cyclicSerializerTree.serialize(root);
        System.out.println("\nSerialized cyclic tree: " + serializedCyclicTree2);

        
            Node deserializedCyclicTree2 = cyclicSerializerTree.deserialize(serializedCyclicTree2);
            System.out.println("Deserialized cyclic tree: ");
            printTree(deserializedCyclicTree2);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
//For part III of the question please go through Readme