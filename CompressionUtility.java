import java.io.*;
import java.util.*;

public class CompressionUtility {

    public static void compressFile(String inputFileName, String algorithm) throws IOException {
       if (algorithm.equalsIgnoreCase("Huffman")) {
            // Read the input file
            byte[] fileData = readFile(inputFileName);

            // Calculate character frequencies
            Map<Byte, Integer> frequencyMap = calculateFrequency(fileData);

            // Build Huffman Tree
            HuffmanNode root = buildHuffmanTree(frequencyMap);

            // Generate Huffman codes
            Map<Byte, String> huffmanCodes = new HashMap<>();
            generateHuffmanCodes(root, "", huffmanCodes);

            // Compress the input data
            String compressedData = compressInput(fileData, huffmanCodes);

            // Save the compressed data to a file
            String outputFileName = inputFileName + "HuffmanCompressed";
            saveCompressedFile(outputFileName, compressedData, root);
        } else if (algorithm.equalsIgnoreCase("LZW")) {
            // Implement LZW compression here
        } else {
            throw new IllegalArgumentException("Invalid compression algorithm");
        }
    }

    private static String compressInput(byte[] inputData, Map<Byte, String> huffmanCodes) {
        StringBuilder compressedData = new StringBuilder();
        for (byte b : inputData) {
            compressedData.append(huffmanCodes.get(b));
        }
        return compressedData.toString();
    }

    private static HuffmanNode buildHuffmanTree(Map<Byte, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(new FrequencyComparator());

        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.data = entry.getKey();
            node.frequency = entry.getValue();
            node.left = null;
            node.right = null;
            queue.add(node);
        }

        while (queue.size() > 1) {
            HuffmanNode x = queue.poll();
            HuffmanNode y = queue.poll();

            HuffmanNode newNode = new HuffmanNode();
            newNode.frequency = x.frequency + y.frequency;
            newNode.left = x;
            newNode.right = y;

            queue.add(newNode);
        }

        return queue.poll();
    }

    private static void generateHuffmanCodes(HuffmanNode node, String code, Map<Byte, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.data, code);
        }

        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    public static void decompressFile(String inputFileName, String algorithm, int version)
            throws IOException, ClassNotFoundException {
        if (algorithm.equalsIgnoreCase("Huffman")) {
            // Read the compressed file and reconstruct the Huffman Tree
            String compressedFileName = inputFileName + "HuffmanCompressed";
            Pair<String, HuffmanNode> compressedDataAndRoot = readCompressedFile(compressedFileName);
            String compressedData = compressedDataAndRoot.first;
            HuffmanNode root = compressedDataAndRoot.second;

            // Decompress the data
            byte[] decompressedData = decompressInput(compressedData, root);

            // Save the decompressed data to a file
            String outputFileName = inputFileName + "HuffmanDecompressed";
            saveDecompressedFile(outputFileName, decompressedData);
        } else if (algorithm.equalsIgnoreCase("LZW")) {
            // Implement LZW decompression here
        } else {
            throw new IllegalArgumentException("Invalid decompression algorithm");
        }
    }

    private static byte[] decompressInput(String compressedData, HuffmanNode root) {
        StringBuilder decompressedData = new StringBuilder();
        HuffmanNode currentNode = root;

        for (int i = 0; i < compressedData.length(); i++) {
            char bit = compressedData.charAt(i);

            if (bit == '0') {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }

            if (currentNode.left == null && currentNode.right == null) {
                decompressedData.append((char) currentNode.data);
                currentNode = root;
            }
        }

        // Convert the decompressed data string to a byte array
        byte[] decompressedBytes = new byte[decompressedData.length()];
        for (int i = 0; i < decompressedData.length(); i++) {
            decompressedBytes[i] = (byte) decompressedData.charAt(i);
        }

        return decompressedBytes;
    }
    // Helper methods for Huffman Coding

    private static byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] fileData = new byte[(int) file.length()];
        fis.read(fileData);
        fis.close();
        return fileData;
    }

    private static Map<Byte, Integer> calculateFrequency(byte[] fileData) {
        Map<Byte, Integer> frequencyMap = new HashMap<>();
        for (byte b : fileData) {
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }
        return frequencyMap;
    }

    // ... (other Huffman Coding helper methods from the previous answer)

    private static void saveCompressedFile(String fileName, String compressedData, HuffmanNode root)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            System.out.println("teste");
            // Write the compressed data as a string
            oos.writeObject(compressedData);
            System.out.println("teste");
            // Serialize the Huffman Tree
            oos.writeObject(root);
        }
    }

    private static Pair<String, HuffmanNode> readCompressedFile(String fileName)
            throws IOException, ClassNotFoundException {
        String compressedData;
        HuffmanNode root;

        try (FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Read the compressed data as a string
            compressedData = (String) ois.readObject();

            // Deserialize the Huffman Tree
            root = (HuffmanNode) ois.readObject();
            return new Pair<>(compressedData, root);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class not found");
            return null; // Return a default value or handle the error in some other way
        }
    }

    private static void saveDecompressedFile(String fileName, byte[] decompressedData) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(decompressedData);
        fos.close();
    }
}

class Pair<T, U> {
    public final T first;
    public final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}