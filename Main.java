
// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class Main {
   public static int version = 0;

   public static void main(String args[]) throws IOException, ClassNotFoundException {
      Scanner fetch = new Scanner(System.in);
      CRUD crud = new CRUD("pokemonDB");
      String basefile = "pokemonSample.csv";
      HuffmanCoding huffman = new HuffmanCoding();
      KMP kmp = new KMP();
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
         System.out.println("7- Pesquisar Padrão");
         System.out.println("8- Sair");

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
                     ArrayList<String> typesAL = new ArrayList<String>();
                     for (int i = 0; i < types.length; i++) {
                        typesAL.add(types[i]);
                     }
                     // abilities
                     System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
                     System.out.println("Keen Eye, Tangled Feet");
                     String abilitiesStr = fetch.nextLine();
                     String[] abilities = abilitiesStr.split(",");
                     ArrayList<String> abilitiesAL = new ArrayList<String>();
                     for (int i = 0; i < abilities.length; i++) {
                        abilitiesAL.add(types[i]);
                     }
                     Pokemon pokemon = new Pokemon();
                     String line = readID + "," + pokedexID + "," + pokemonName + "," + gen + "," + specie + ","
                           + hiddenAbility + "," + date + ",\"" + typesStr + "\"," + "\"" + abilitiesStr + "\"";
                     System.out.println(line);
                     pokemon.parseCSV(line);
                     crud.create(pokemon);
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
               ArrayList<String> typesAL = new ArrayList<String>();
               for (int i = 0; i < types.length; i++) {
                  typesAL.add(types[i]);
               }
               // abilities
               System.out.println("Escreva as habilidades do Pokemon (separado por virgulas)");
               System.out.println("Keen Eye, Tangled Feet");
               String abilitiesStr = fetch.nextLine();
               String[] abilities = abilitiesStr.split(",");
               ArrayList<String> abilitiesAL = new ArrayList<String>();
               for (int i = 0; i < abilities.length; i++) {
                  abilitiesAL.add(types[i]);
               }
               Pokemon pokemon = new Pokemon();
               String line = updateID + "," + pokedexID + "," + pokemonName + "," + gen + "," + specie + ","
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
               System.out.println("Comprimindo arquivo...\n");
               // Comprimir arquivos
               // Huffman
               long iHuffComp = System.currentTimeMillis();
               try {
                  String arquivo = new Scanner(new File(basefile)).useDelimiter("\\\\Z").next();
                  String compressedString = huffman.compress(arquivo);
                  huffman.writeCompressedFile(compressedString);
                  System.out.println(
                        "Arquivo da sequência compactada gerado: baseHuffmanCompressao" + Main.version + ".txt");
               } catch (Exception e) {
                  System.out.println("Erro na compressão Huffman");
               }
               long fHuffComp = System.currentTimeMillis() - iHuffComp;
               System.out.println("Tempo de compressão Huffman: " + fHuffComp + "ms");
               /*
                * -----------------
                */
               // "Código do gabigol"
               long iLzwComp = System.currentTimeMillis();
               try {
                  String arqComprimido = "baseLzwCompressao" + version++;
                  byte[] fileContent = Files.readAllBytes(Paths.get(basefile));
                  int[] compressed = LZW.compress(fileContent);
                  byte[] compressedBytes = new byte[compressed.length * 2];
                  for (int i = 0; i < compressed.length; i++) {
                     compressedBytes[2 * i] = (byte) (compressed[i] >> 8);
                     compressedBytes[2 * i + 1] = (byte) (compressed[i] & 0xFF);
                  }
                  Files.write(Paths.get(arqComprimido), compressedBytes);
                  System.out
                        .println("Arquivo da sequência compactada gerado: baseLzwCompressao" + (version - 1) + ".txt");
               } catch (Exception e) {
                  System.out.println("Erro na compressão LZW");
               }
               long fLzwComp = System.currentTimeMillis() - iLzwComp;
               System.out.println("Tempo de compressão LZW: " + fLzwComp + "ms");

               // Comparar Tempo de Compressão dos dois Algoritmos

               if (fHuffComp < fLzwComp) {
                  System.out.print("Compressão Huffman foi ");
                  System.out.printf("%.2f%% mais eficiente%n", (1.0 - ((float) fHuffComp / (float) fLzwComp)) * 100);
                  // System.out.println("% mais eficiente");
               } else {
                  System.out.print("Compressão LZW foi ");
                  System.out.printf("%.2f%% mais eficiente%n", (1.0 - ((float) fLzwComp / (float) fHuffComp)) * 100);
                  // System.out.println("% mais eficiente");
               }
               System.out.println("\nArquivo comprimido com sucesso!");
               break;
            case 6:
               System.out.print("Qual a versão do arquivo que deseja descomprimir? ");
               int versao = fetch.nextInt();
               System.out.println("Descomprimindo arquivo...\n");
               // Descomprimir arquivo da versão escolhida
               // Huffman
               long iHuffDesc = System.currentTimeMillis();
               try {
                  String readString = huffman.readCompressedFile(versao);
                  String decompressedString = huffman.decompress(readString);
                  FileWriter fw = new FileWriter("baseHuffmanCompressao" + versao + ".txt");
                  BufferedWriter writer = new BufferedWriter(fw);
                  writer.write(decompressedString);
                  writer.close();
               } catch (Exception e) {
                  System.out.println("Erro na descompressão Huffman");
               }
               long fHuffDesc = System.currentTimeMillis() - iHuffDesc;
               System.out.println("Tempo de descompressão Huffman: " + fHuffDesc + "ms");
               // LZW
               long iLzwDesc = System.currentTimeMillis();
               try {
                  String fileName = "baseLzwCompressao" + versao;
                  byte[] compressedFileContent = Files.readAllBytes(Paths.get(fileName));
                  int[] compressedData = new int[compressedFileContent.length / 2];
                  for (int i = 0; i < compressedData.length; i++) {
                     compressedData[i] = ((compressedFileContent[2 * i] & 0xFF) << 8)
                           | (compressedFileContent[2 * i + 1] & 0xFF);
                  }

                  byte[] decompressed = LZW.decompress(compressedData);
                  Files.write(Paths.get(fileName), decompressed);
               } catch (Exception e) {
                  System.out.println("Erro na descompressão LZW");
               }
               long fLzwDesc = System.currentTimeMillis() - iLzwDesc;
               System.out.println("Tempo de descompressão LZW: " + fLzwDesc + "ms");
               if (fHuffDesc < fLzwDesc) {
                  System.out.print("Descompressão Huffman foi ");
                  System.out.printf("%.2f%% mais eficiente%n", (1.0 - ((float) fHuffDesc / (float) fLzwDesc)) * 100);
                  // System.out.println("% mais eficiente");
               } else {
                  System.out.print("Descompressão LZW foi ");
                  System.out.printf("%.2f%% mais eficiente%n", (1.0 - ((float) fLzwDesc / (float) fHuffDesc)) * 100);
                  // System.out.println("% mais eficiente");
               }
               System.out.println("\nArquivo descomprimido com sucesso!");
               break;
            case 7:
               fetch.nextLine();
               System.out.print("Digite o nome do Pokemon que deseja encontrar: ");
               String pattern = fetch.nextLine();
               // Procura do game
               long iKmpComp = 0;
               long iBoyerComp = 0;
               try {
                  String caminhoArquivo = basefile;
                  String conteudoBanco = lerArquivoBD(caminhoArquivo);
                  // KMP.
                  iKmpComp = System.currentTimeMillis();
                  // Pesquisa no KMP
                  kmp.search(conteudoBanco, pattern);
                  iKmpComp = System.currentTimeMillis() - iKmpComp;
                  // Boyer Moore.
                  BoyerMoore bm = new BoyerMoore();
                  System.out.print(
                        "Use a heurística do mau caractere ou do sufixo bom? (1 - Sufixo bom; 2 - Mau caractere): ");
                  int heuristica = fetch.nextInt();
                  boolean charOrSufix = false;
                  if (heuristica == 2) {
                     charOrSufix = true;
                  }
                  iBoyerComp = System.currentTimeMillis();
                  // Pesquisa no Boyer Moore.
                  List<Integer> occurrences = bm.search(conteudoBanco, pattern, charOrSufix);
                  if (occurrences.isEmpty()) {
                     System.out.println("Padrão não encontrado no texto.");
                  } else {
                     System.out.println("Padrão encontrado nos índices: " + occurrences);
                  }
                  iBoyerComp = System.currentTimeMillis() - iBoyerComp;
               } catch (Exception e) {
                  // System.out.println(e.getLocalizedMessage());
                  System.out.println("Erro no padrão");
               }
               // Comparação no tempo de pesquisa.
               System.out.println("Tempo de pesquisa KMP: " + iKmpComp + "ms");
               System.out.println("Tempo de pesquisa BoyerMoore: " + iBoyerComp + "ms");
               System.out.println();
               if (iKmpComp < iBoyerComp) {
                  System.out.println("KMP foi mais eficiente");
               } else {
                  System.out.println("BoyerMoore foi mais eficiente");
               }
            case 8:
               System.out.print("\nSalvando arquivo e encerrando programa...");
               break;
            default:
               System.out.println("Digite uma opção válida!\n\n");
         }

      }
      fetch.close();
   }

   public static String lerArquivoBD(String caminhoArquivo) throws IOException {
      StringBuilder conteudo = new StringBuilder();

      BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
      String linha;
      while ((linha = leitor.readLine()) != null) {
         conteudo.append(linha);
         conteudo.append("\n"); // Adicione uma quebra de linha, se necessário
      }
      leitor.close();

      return conteudo.toString();
   }

}
