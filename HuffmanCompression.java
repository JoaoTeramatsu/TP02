// HuffmanCompression.java

import java.io.*;
import java.util.*;

class HuffmanNode implements Serializable{
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

public class HuffmanCompression {

    private static Map<Character, String> huffmanCodes;

    public static String compress(byte[] input) {
        // Construir a árvore de Huffman e gerar os códigos de Huffman
        HuffmanNode root = buildHuffmanTree(input);
        huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        StringBuilder compressedString = new StringBuilder();

        // Converter a sequência de entrada em uma sequência compactada usando os códigos de Huffman
        for (byte b : input) {
            char c = (char) (b & 0xFF);
            compressedString.append(huffmanCodes.get(c));
        }

        return compressedString.toString();
    }

    public static byte[] decompress(String compressedString) {
        ByteArrayOutputStream decompressedData = new ByteArrayOutputStream();

        StringBuilder currentCode = new StringBuilder();
        for (char c : compressedString.toCharArray()) {
            currentCode.append(c);
            for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
                if (entry.getValue().equals(currentCode.toString())) {
                    decompressedData.write((byte) entry.getKey().charValue());
                    currentCode.setLength(0);
                    break;
                }
            }
        }

        return decompressedData.toByteArray();
    }

    private static HuffmanNode buildHuffmanTree(byte[] input) {
        Map<Character, String> huffmanCodes = new HashMap<>();

        // Calcular a frequência de cada caractere
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (byte b : input) {
            char c = (char) (b & 0xFF);
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Criar uma fila de prioridade para armazenar os nós
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new HuffmanComparator());

        // Criar um nó para cada caractere e adicioná-lo à fila de prioridade
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.left = null;
            node.right = null;
            priorityQueue.add(node);
        }

        // Construir a árvore de Huffman
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();

            HuffmanNode parentNode = new HuffmanNode();
            parentNode.character = '\0';
            parentNode.frequency = left.frequency + right.frequency;
            parentNode.left = left;
            parentNode.right = right;

            priorityQueue.add(parentNode);
        }

        return priorityQueue.peek();
    }

    private static void generateHuffmanCodes(HuffmanNode node, String code, Map<Character, String> huffmanCodes) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') {
            huffmanCodes.put(node.character, code);
        }

        generateHuffmanCodes(node.left, code + "0", huffmanCodes);
        generateHuffmanCodes(node.right, code + "1", huffmanCodes);
    }

    public static void writeCompressedFile(String compressedString) {
        try {
            FileWriter fileWriter = new FileWriter("baseHuffmanCompressao" + 0 + ".txt");
            BufferedWriter writer = new BufferedWriter(fileWriter);

            writer.write(compressedString);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readCompressedFile(int version) {
        StringBuilder compressedString = new StringBuilder();

        try {
            FileReader fileReader = new FileReader("baseHuffmanCompressao" + version + ".txt");
            BufferedReader reader = new BufferedReader(fileReader);

            String line;
            while ((line = reader.readLine()) != null) {
                compressedString.append(line);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressedString.toString();
    }
}