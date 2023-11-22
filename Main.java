import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
   public static int version = 0;

   public static void main(String args[]) throws IOException, ClassNotFoundException {
      Scanner fetch = new Scanner(System.in);
      CRUD crud = new CRUD("pokemonDB");
      String basefile = "pokemonSample.csv";
      System.out.println("Deseja carregar o arquivo?");
      System.out.println("1-Sim \n2-Nao");
      try {
         if (fetch.nextInt() == 1) {
            FileInputStream fs = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String csvLine = br.readLine();
            while ((csvLine = br.readLine()) != null) {
               Pokemon pokemon = new Pokemon();
               pokemon.parseCSV(csvLine);
               crud.create(pokemon);
            }
            br.close();
         }
      } catch (Exception e) {
         System.out.println("error");
      }
      int choice = 0;
      while (choice != 7) {
         System.out.println("Escolha uma operação: ");
         System.out.println("1- Create");
         System.out.println("2- Read");
         System.out.println("3- Update");
         System.out.println("4- Delete");
         System.out.println("5- Compress");
         System.out.println("6- Decompress");
         System.out.println("7- Sair");
         choice = fetch.nextInt();

         switch (choice) {

            // CREATE
            case 1:
               try {
                  String gen = "";
                  String hiddenAbility = "";
                  String date = "";
                  System.out.println("Digite o ID: ");
                  int readID = fetch.nextInt();
                  String lixo = fetch.nextLine();
                  if (crud.search(readID)) {
                     // Personalizar para meu CSV e campos de Pokemon
                     System.out.println("Digite o nome do Pokemon: ");
                     String pokemonName = fetch.nextLine();
                     System.out.println("Digite o número da pokedex: ");
                     int pokedexID = fetch.nextInt();
                     lixo = fetch.nextLine();
                     System.out.println("Escreva o número da geração: ");
                     gen = fetch.nextLine();

                     System.out.println("Escreva o nome da especie de Pokemon: ");
                     String specie = fetch.nextLine();

                     System.out.println("Escreva o nome da habilidade escodida: ");
                     hiddenAbility = fetch.nextLine();
                     // data
                     System.out.println("Digite a data de lançamento do Pokemon: ");
                     System.out.println("Ex.: yyyy-MM-dd");
                     date = fetch.nextLine();

                     // arrays types
                     System.out.println("Escreva os tipos do pokemon (separado por virgulas)");
                     System.out.println("Ex: Fire, Rock");
                     String typesStr = fetch.nextLine();
                     String[] types = typesStr.split(",");
                     ArrayList<String> typesAL = new ArrayList();
                     for (int i = 0; i < types.length; i++) {
                        typesAL.add(types[i]);
                     }
                     // abilities
                     System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
                     System.out.println("Keen Eye, Tangled Feet");
                     String abilitiesStr = fetch.nextLine();
                     String[] abilities = abilitiesStr.split(",");
                     ArrayList<String> abilitiesAL = new ArrayList();
                     for (int i = 0; i < abilities.length; i++) {
                        abilitiesAL.add(types[i]);
                     }
                     Pokemon pokemon = new Pokemon();
                     String line = readID + "," + pokedexID + "," + pokemonName + "," + gen + "," + specie + ","
                           + hiddenAbility + "," + date + ",\"" + typesStr + "\"," + "\"" + abilitiesStr + "\"";
                     System.out.println(line);
                     pokemon.parseCSV(line);
                     crud.create(pokemon);
                     // long pos = crud.getIndex(pokemon);
                     // listaGeneros.addDocument((int) pos, mus.getGenres());
                     // listaNomes.addDocument((int) pos, mus.getReleaseName());
                     System.out.println("\nArquivo atualizado!\n");

                     System.out.print(
                           "ID: " + pokemon.getIndex() + ", Pokedex ID: " + pokemon.getPokedexNum()
                                 + ", Nome do Pokemon: " + pokemon.getName()
                                 + ", Geração: " + pokemon.getGeneration()
                                 + ", Especie: " + pokemon.getSpecie()
                                 + ", Hidden Hability: " + pokemon.getHiddenAbility() + ", ");

                     System.out.print("Tipos: ");
                     for (int i = 0; i < pokemon.getTypes().size(); i++) {
                        System.out.print(pokemon.getTypes().get(i) + " ");
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < pokemon.getAbilities().size(); i++) {
                        System.out.print(pokemon.getAbilities().get(i) + " ");
                     }
                     System.out.println("");
                     System.out.println("");
                  } else
                     System.out.println("ID já existente.");
               } catch (Exception e) {
                  System.out.println("Erro ao criar Arquivo.");
               }

               break;
            case 2:
               try {
                  System.out.println("Digite o ID: ");
                  int readID = fetch.nextInt();
                  Pokemon pokemon = crud.Read(readID);
                  if (pokemon.lapide) {
                     System.out.print(
                           "ID: " + pokemon.getIndex() + ", Pokedex ID: " + pokemon.getPokedexNum()
                                 + ", Nome do Pokemon: " + pokemon.getName()
                                 + ", Geração: " + pokemon.getGeneration()
                                 + ", Especie: " + pokemon.getSpecie()
                                 + ", Hidden Hability: " + pokemon.getHiddenAbility() + ", ");

                     System.out.print("Tipos: ");
                     for (int i = 0; i < pokemon.getTypes().size(); i++) {
                        System.out.print(pokemon.getTypes().get(i) + " ");
                     }
                     System.out.print(" Habilidades: ");
                     for (int i = 0; i < pokemon.getAbilities().size(); i++) {
                        System.out.print(pokemon.getAbilities().get(i) + " ");
                     }
                     System.out.println("");
                  } // Teste para ver se pokemon existe (teste de pointer)
                  else
                     System.out.println("Arquivo não Encontrado!");
                  ;
               } catch (Exception e) {
                  System.out.println("\nArquivo não encontrado!");
               }

               break;

            // Update
            case 3:
               System.out.print("Digite o ID do Pokemon que deseja atualizar no arquivo: ");
               int updateID = fetch.nextInt();
               String lixo = fetch.nextLine();
               boolean checkArq;
               try {
                  crud.delete(updateID);
                  checkArq = true;
               } catch (Exception e) {
                  checkArq = false;
               }
               if (!checkArq) {
                  System.out.println("\nO arquivo não existe!");
                  break;
               }
               System.out.println("Digite o nome do Pokemon: ");
               String pokemonName = fetch.nextLine();
               System.out.println("Digite o número da pokedex: ");
               int pokedexID = fetch.nextInt();
               lixo = fetch.nextLine();
               System.out.println("Escreva o número da geração: ");
               String gen = fetch.nextLine();

               System.out.println("Escreva o nome da especie de Pokemon: ");
               String specie = fetch.nextLine();

               System.out.println("Escreva o nome da habilidade escodida: ");
               String hiddenAbility = fetch.nextLine();
               // data
               System.out.println("Digite a data de lançamento do Pokemon: ");
               System.out.println("Ex.: yyyy-MM-dd");
               String date = fetch.nextLine();

               // arrays types
               System.out.println("Escreva os tipos do pokemon (separado por virgulas)");
               System.out.println("Ex: Fire, Rock");
               String typesStr = fetch.nextLine();
               String[] types = typesStr.split(",");
               ArrayList<String> typesAL = new ArrayList();
               for (int i = 0; i < types.length; i++) {
                  typesAL.add(types[i]);
               }
               // abilities
               System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
               System.out.println("Keen Eye, Tangled Feet");
               String abilitiesStr = fetch.nextLine();
               String[] abilities = abilitiesStr.split(",");
               ArrayList<String> abilitiesAL = new ArrayList();
               for (int i = 0; i < abilities.length; i++) {
                  abilitiesAL.add(types[i]);
               }
               Pokemon pokemon = new Pokemon();
               String line = pokedexID + "," + pokedexID + "," + pokemonName + "," + gen + "," + specie + ","
                     + hiddenAbility + "," + date + ",\"" + typesStr + "\"," + "\"" + abilitiesStr + "\"";
               System.out.println(line);
               pokemon.parseCSV(line);
               crud.create(pokemon);
               System.out.println("\nRegistro atualizado com sucesso!");
               // Enviar para Update no CRUD
               break;

            // Delete

            case 4:
               System.out.print("Digite o ID do Pokemon que deseja deletar no arquivo: ");
               int deleteID = fetch.nextInt();
               try {
                  Pokemon pokemonDeleted = crud.Read(deleteID); // Teste para ver se existe Pokemon
                  System.out.print(
                        "ID: " + pokemonDeleted.getIndex() + ", Pokedex ID: " + pokemonDeleted.getPokedexNum()
                              + ", Nome do pokemon deletado: " + pokemonDeleted.getName()
                              + ", Geração: " + pokemonDeleted.getGeneration()
                              + ", Especie: " + pokemonDeleted.getSpecie()
                              + ", Hidden Hability: " + pokemonDeleted.getHiddenAbility() + ", ");

                  System.out.print("Tipos: ");
                  for (int i = 0; i < pokemonDeleted.getTypes().size(); i++) {
                     System.out.print(pokemonDeleted.getTypes().get(i) + " ");
                  }
                  System.out.print(" Habilidades: ");
                  for (int i = 0; i < pokemonDeleted.getAbilities().size(); i++) {
                     System.out.print(pokemonDeleted.getAbilities().get(i) + " ");
                  }
                  System.out.println("");
                  crud.delete(deleteID); // Deleta o Pokemon
                  System.out.println("\nArquivo deletado com sucesso!");
               } catch (Exception e) {
                  System.out.println("\nArquivo não encontrado!");
               }
               // Enviar para Delete no CRUD
               break;
            case 5:
               String inputFileName = "pokemonDB";

               // Huffman Compression
               long huffmanCompressStart = System.currentTimeMillis();
               File pokemonDB = new File("./pokemonDB");
               byte[] bytes = Files.readAllBytes(pokemonDB.toPath());
               HuffmanCompression.compress(bytes);

               /*
                * ----------------- //
                * // "Código do gabigol"
                * long iLzwDesc = System.currentTimeMillis();
                * try {
                * String fileName = "baseLzwCompressao";
                * byte[] compressedFileContent = Files.readAllBytes(Paths.get(fileName));
                * int[] compressedData = new int[compressedFileContent.length / 2];
                * for (int i = 0; i < compressedData.length; i++) {
                * compressedData[i] = ((compressedFileContent[2 * i] & 0xFF) << 8) |
                * (compressedFileContent[2 * i + 1] & 0xFF);
                * }
                * 
                * byte[] decompressed = LZW.decompress(compressedData);
                * Files.write(Paths.get(fileName), decompressed);
                * } catch (Exception e){
                * System.out.println("Erro na descompressão LZW");
                * }
                * long fLzwDesc = System.currentTimeMillis()-iLzwDesc;
                * System.out.println("Tempo de descompressão LZW: "+fLzwDesc+"ms");
                * 
                * break;
                */
               break;
            case 6:
               try {
                  String compressedString = HuffmanCompression.readCompressedFile(0);
                  byte[] decompressedData = HuffmanCompression.decompress(compressedString);
                  Files.write(Paths.get("baseHuffmanDecompressao" + 0 + ".txt"), decompressedData);
                  System.out.println("Arquivo da sequência descompactada gerado: baseHuffmanDecompressao" + 0 + ".txt");
                  // CompressionUtility.decompressFile("pokemonDB", "LZW", version);
               } catch (Exception e) {
                  System.out.println("Erro na descompressão Huffman");
                  // TODO: handle exception
               }
               // byte[] decompressed = LZW.decompress(compressedData);
               // Files.write(Paths.get(fileName), decompressed);
               break;
            case 7:
               System.out.println("qual a versão do arquivo que deseja descomprimir?");
               inputFileName = fetch.nextLine();
               // Huffman Decompression
               long huffmanDecompressStart = System.currentTimeMillis();
               try {
                  // HuffmanCompression.decompressFile(inputFileName, "Huffman", 0);
               } catch (Exception e) {
                  System.out.println("Error in Huffman decompression");
               }
               long huffmanDecompressTime = System.currentTimeMillis() - huffmanDecompressStart;
               System.out.println("Huffman decompression time: " + huffmanDecompressTime + "ms");
               // sair
               break;
         }

      }
      fetch.close();
   }
}
