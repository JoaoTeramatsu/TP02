import java.io.*;

public class Sort {

    private File file;
    private RandomAccessFile fileReader;
    private long position;

    File file_1 = new File("file1.db");
    RandomAccessFile file1 = new RandomAccessFile("file1.db", "rw");

    File file_2 = new File("file2.db");
    RandomAccessFile file2 = new RandomAccessFile("file2.db", "rw");

    File file_3 = new File("file3.db");
    RandomAccessFile file3 = new RandomAccessFile("file3.db", "rw");

    File file_4 = new File("file4.db");
    RandomAccessFile file4 = new RandomAccessFile("file4.db", "rw");

    Sort(String file) throws FileNotFoundException {
        this.file = new File(file);
        fileReader = new RandomAccessFile(file, "rw");
    }

    public void intercalacaoBalanceadaComum() throws Exception {
        int fileControl = 0;
        fileReader.seek(0);
        fileReader.readUTF();

        Pokemon[] array = new Pokemon[20];

        int arq = 0;

        while (arq < 40) {
            int currentElement = 0;
            while (currentElement < 20) {
                array[currentElement] = new Pokemon();
                int sizePokemon = fileReader.readInt();
                position = fileReader.getFilePointer();
                setPokemon(array[currentElement]);
                fileReader.seek(position);
                fileReader.skipBytes(sizePokemon);
                currentElement++;
                arq++;
            }

            quicksort(array);

            if (fileControl % 2 == 0) {
                for (int i = 0; i < 20; i++) {
                    writePokemon(array[i], file1);
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    writePokemon(array[i], file2);
                }
            }
            fileControl++;
        }

        Pokemon pokemon1 = new Pokemon();
        Pokemon pokemon2 = new Pokemon();

        file1.seek(0);
        file2.seek(0);

        int cont1 = 0;
        int cont2 = 0;

        for (int u = 0; u < 4; u++) {

            if (u % 2 == 0) {
                for (int i = 0; i < 8; i++) {

                    String id1 = "";
                    String id2 = "";
                    int sizePokemon1 = 0;
                    int sizePokemon2 = 0;
                    long position1 = 0;
                    long position2 = 0;
                    long firstPosition1 = 0;
                    long firstPosition2 = 0;

                    if (file1.getFilePointer() < file1.length()) {
                        firstPosition1 = file1.getFilePointer();
                        sizePokemon1 = file1.readInt();
                        position1 = file1.getFilePointer();
                        boolean b1 = file1.readBoolean();
                        file1.readInt();
                        id1 = file1.readUTF();
                        pokemon1 = readPokemon(sizePokemon1, id1, b1, file1);

                    }

                    if (file2.getFilePointer() < file2.length()) {
                        firstPosition2 = file2.getFilePointer();
                        sizePokemon2 = file2.readInt();
                        position2 = file2.getFilePointer();
                        boolean b2 = file2.readBoolean();
                        file2.readInt();
                        id2 = file2.readUTF();
                        pokemon2 = readPokemon(sizePokemon2, id2, b2, file2);

                    }

                    if (cont1 < 20 && cont2 < 20) {
                        if (id1.compareTo(id2) < 0) {
                            writePokemon(pokemon1, file3);
                            file1.seek(position1);
                            file1.skipBytes(sizePokemon1);
                            file2.seek(firstPosition2);
                            cont1++;

                        } else {
                            writePokemon(pokemon2, file3);
                            file2.seek(position2);
                            file2.skipBytes(sizePokemon2);
                            file1.seek(firstPosition1);
                            cont2++;
                        }
                    } else if (cont1 < 20) {
                        writePokemon(pokemon1, file3);
                        file1.seek(position1);
                        file1.skipBytes(sizePokemon1);
                        file2.seek(firstPosition2);
                        cont1++;

                    } else if (cont2 < 20) {
                        writePokemon(pokemon2, file3);
                        file2.seek(position2);
                        file2.skipBytes(sizePokemon2);
                        file1.seek(firstPosition1);
                        cont2++;

                    }
                }
            } else {
                for (int i = 0; i < 8; i++) {

                    String id1 = "";
                    String id2 = "";
                    int sizePokemon1 = 0;
                    int sizePokemon2 = 0;
                    long position1 = 0;
                    long position2 = 0;
                    long firstPosition1 = 0;
                    long firstPosition2 = 0;

                    if (file1.getFilePointer() < file1.length()) {
                        firstPosition1 = file1.getFilePointer();
                        sizePokemon1 = file1.readInt();
                        position1 = file1.getFilePointer();
                        boolean b1 = file1.readBoolean();
                        file1.readInt();
                        id1 = file1.readUTF();
                        pokemon1 = readPokemon(sizePokemon1, id1, b1, file1);

                    }

                    if (file2.getFilePointer() < file2.length()) {
                        firstPosition2 = file2.getFilePointer();
                        sizePokemon2 = file2.readInt();
                        position2 = file2.getFilePointer();
                        boolean b2 = file2.readBoolean();
                        file2.readInt();
                        id2 = file2.readUTF();
                        pokemon2 = readPokemon(sizePokemon2, id2, b2, file2);

                    }

                    if (cont1 < 20 && cont2 < 20) {
                        if (id1.compareTo(id2) < 0) {
                            writePokemon(pokemon1, file4);
                            file1.seek(position1);
                            file1.skipBytes(sizePokemon1);
                            file2.seek(firstPosition2);
                            cont1++;

                        } else {
                            writePokemon(pokemon2, file4);
                            file2.seek(position2);
                            file2.skipBytes(sizePokemon2);
                            file1.seek(firstPosition1);
                            cont2++;
                        }
                    } else if (cont1 < 20) {
                        writePokemon(pokemon1, file4);
                        file1.seek(position1);
                        file1.skipBytes(sizePokemon1);
                        cont1++;

                    } else if (cont2 < 20) {
                        writePokemon(pokemon2, file4);
                        file2.seek(position2);
                        file2.skipBytes(sizePokemon2);
                        file1.seek(firstPosition1);
                        cont2++;

                    }
                }
            }
        }

        pokemon1 = new Pokemon();
        pokemon2 = new Pokemon();

        file1.setLength(0);
        file2.setLength(0);

        file3.seek(0);
        file4.seek(0);

        cont1 = 0;
        cont2 = 0;

        for (int u = 0; u < 4; u++) {

            if (u % 2 == 0) {
                for (int i = 0; i < 16; i++) {

                    String id1 = "";
                    String id2 = "";
                    int sizePokemon1 = 0;
                    int sizePokemon2 = 0;
                    long position1 = 0;
                    long position2 = 0;
                    long firstPosition1 = 0;
                    long firstPosition2 = 0;

                    if (file3.getFilePointer() < file3.length()) {
                        firstPosition1 = file3.getFilePointer();
                        sizePokemon1 = file3.readInt();
                        position1 = file3.getFilePointer();
                        boolean b1 = file3.readBoolean();
                        file3.readInt();
                        id1 = file3.readUTF();
                        pokemon1 = readPokemon(sizePokemon1, id1, b1, file3);

                    }

                    if (file4.getFilePointer() < file4.length()) {
                        firstPosition2 = file4.getFilePointer();
                        sizePokemon2 = file4.readInt();
                        position2 = file4.getFilePointer();
                        boolean b2 = file4.readBoolean();
                        file4.readInt();
                        id2 = file4.readUTF();
                        pokemon2 = readPokemon(sizePokemon2, id2, b2, file4);

                    }

                    if (cont1 < 20 && cont2 < 20) {
                        if (id1.compareTo(id2) < 0) {
                            writePokemon(pokemon1, file1);
                            file3.seek(position1);
                            file3.skipBytes(sizePokemon1);
                            file4.seek(firstPosition2);
                            cont1++;

                        } else {
                            writePokemon(pokemon2, file1);
                            file4.seek(position2);
                            file4.skipBytes(sizePokemon2);
                            file3.seek(firstPosition1);
                            cont2++;
                        }
                    } else if (cont1 < 20) {
                        writePokemon(pokemon1, file1);
                        file3.seek(position1);
                        file3.skipBytes(sizePokemon1);
                        file4.seek(firstPosition2);
                        cont1++;

                    } else if (cont2 < 20) {
                        writePokemon(pokemon2, file1);
                        file4.seek(position2);
                        file4.skipBytes(sizePokemon2);
                        file3.seek(firstPosition1);
                        cont2++;

                    }
                }
            } else {
                for (int i = 0; i < 16; i++) {

                    String id1 = "";
                    String id2 = "";
                    int sizePokemon1 = 0;
                    int sizePokemon2 = 0;
                    long position1 = 0;
                    long position2 = 0;
                    long firstPosition1 = 0;
                    long firstPosition2 = 0;

                    if (file3.getFilePointer() < file3.length()) {
                        firstPosition1 = file3.getFilePointer();
                        sizePokemon1 = file3.readInt();
                        position1 = file3.getFilePointer();
                        boolean b1 = file3.readBoolean();
                        file3.readInt();
                        id1 = file3.readUTF();
                        pokemon1 = readPokemon(sizePokemon1, id1, b1, file3);

                    }

                    if (file4.getFilePointer() < file4.length()) {
                        firstPosition2 = file4.getFilePointer();
                        sizePokemon2 = file4.readInt();
                        position2 = file4.getFilePointer();
                        boolean b2 = file4.readBoolean();
                        file4.readInt();
                        id2 = file4.readUTF();
                        pokemon2 = readPokemon(sizePokemon2, id2, b2, file4);

                    }

                    if (cont1 < 20 && cont2 < 20) {
                        if (id1.compareTo(id2) < 0) {
                            writePokemon(pokemon1, file2);
                            file3.seek(position1);
                            file3.skipBytes(sizePokemon1);
                            file4.seek(firstPosition2);
                            cont1++;

                        } else {
                            writePokemon(pokemon2, file2);
                            file4.seek(position2);
                            file4.skipBytes(sizePokemon2);
                            file3.seek(firstPosition1);
                            cont2++;
                        }
                    } else if (cont1 < 20) {
                        writePokemon(pokemon1, file2);
                        file3.seek(position1);
                        file3.skipBytes(sizePokemon1);
                        cont1++;

                    } else if (cont2 < 20) {
                        writePokemon(pokemon2, file2);
                        file4.seek(position2);
                        file4.skipBytes(sizePokemon2);
                        file3.seek(firstPosition1);
                        cont2++;

                    }
                }
            }
        }
    }

    public void writePokemon(Pokemon pokemon, RandomAccessFile file) throws IOException {
        byte[] ba = pokemon.toByteArray();
        file.seek(file.length());
        file.writeInt(ba.length);
        file.write(ba);
    }

    private void setPokemon(Pokemon p) throws Exception {
    }

    public void quicksort(Pokemon[] vetor) {
        quicksort(vetor, 0, vetor.length - 1);
    }

    private void quicksort(Pokemon[] vetor, int inicio, int fim) {
        if (fim > inicio) {
            int pivo = dividir(vetor, inicio, fim);
            quicksort(vetor, inicio, pivo - 1);
            quicksort(vetor, pivo + 1, fim);
        }
    }

    private int dividir(Pokemon[] vetor, int inicio, int fim) {
        String pivo;
        int esq, dir = fim;
        esq = inicio + 1;

        swap(vetor, inicio, dir);
        return dir;
    }

    private void swap(Pokemon[] vetor, int i, int j) {
        Pokemon temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

    public void clear() {
        file_1.delete();
        file_2.delete();
        file_3.delete();
        file_4.delete();
    }

    private Pokemon readPokemon(int fileSize, String id, boolean lapide, RandomAccessFile file) throws Exception {
        Pokemon pokemon = new Pokemon();

        int x = file.readInt();
        pokemon.setName(file.readUTF());

        int n = file.readInt();
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            file.readInt();
            s[i] = file.readUTF();
        }

        return pokemon;
    }

}
