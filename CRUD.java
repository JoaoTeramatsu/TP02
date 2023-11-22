import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

public class CRUD {
   private RandomAccessFile file;

   CRUD(String filename) throws IOException {
      this.file = new RandomAccessFile(filename, "rw");
   }

   // public long getPos(RandomAccessFile file, Pokemon pokemon) throws IOException
   // {
   // file.seek(0); // Ponteiro vai para o inicio do arquivo
   // file.writeInt(pokemon.getId()); // Escreve no inicio do registro o seu ID
   // file.seek(file.length()); // Ponteiro vai para o final do arquivo
   // long pos = file.getFilePointer();
   // byte[] byteArr = pokemon.toByteArray(); // Vetor de Bytes populado com os
   // dados do CSV já filtrados
   // file.writeInt(pokemon.toByteArray().length); // Escreve o tamanho desse vetor
   // de Bytes
   // file.write(byteArr); // Escreve o vetor de Bytes
   // return pos;
   // }

   // public long getPos(Musica mus) throws IOException {
   // return getPos(file, mus);
   // }


   public void create(RandomAccessFile file, Pokemon pokemon) throws IOException {

      file.seek(0);
      file.writeInt(pokemon.getIndex());
      file.seek(file.length());
      byte[] byteArr = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArr);
   }
   public void create(Pokemon pokemon) throws IOException {
      create(this.file, pokemon);
   }

   public long getIndex(RandomAccessFile file, Pokemon pokemon) throws IOException {
      file.seek(0); // Ponteiro vai para o inicio do arquivo
      file.writeInt(pokemon.getIndex()); // Escreve no inicio do registro o seu ID
      file.seek(file.length()); // Ponteiro vai para o final do arquivo
      long index = file.getFilePointer();
      byte[] byteArr = pokemon.toByteArray(); // Vetor de Bytes populado com os dados do CSV já filtrados
      file.writeInt(pokemon.toByteArray().length); // Escreve o tamanho desse vetor de Bytes
      file.write(byteArr); // Escreve o vetor de Bytes
      return index;
   }

   public Boolean getDeletedIndex(int entryIndex) throws IOException {
      long index;
      int qntBytesInic, id;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            index = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a
                                           // quantidade
            // de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Pokemon específico
            id = file.readInt(); // Armazena o ID do registro Pokemon específico
            if (id == entryIndex) { // Verifica se o id é o mesmo que o selecionado
               return false;
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro ( int + boolean)
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      return true;
   }

   public long getIndex(Pokemon pokemon) throws IOException {
      return getIndex(file, pokemon);
   }

   public Pokemon Read(int entradaID) throws IOException {
      long pos;
      int qntBytesInic, id;
      Pokemon pokemonLido = null;
      boolean lap;

      file.seek(0);
      file.seek(0);
      int indexID = file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            pos = file.getFilePointer();
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro pokemon específico
            id = file.readInt(); // Armazena o ID do registro pokemon específico
            if (id == entradaID) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  try {
                     pokemonLido = binToPokemon(file, pos); // Gera uma instância de pokemon e popula com as informações
                                                            // do
                     // banco de dados
                     break;
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               } else {
                  file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro ( int + boolean)
               }
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro ( int + boolean)
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
         pokemonLido = null;
      }

      return pokemonLido;
   }

   public Pokemon delete(int pokemonID) throws IOException {
      long pos;
      int bytes, id;
      Pokemon pokemon = null;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            pos = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a quantidade
                                         // de bytes no registo).
            bytes = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean(); // Armazena o valor da lápide do registro Pokemon específico
            id = file.readInt();
            if (id == pokemonID) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  try {
                     file.seek(pos); // Volta para começo do registro
                     file.readInt(); // Pula primeira parte do registro
                     file.writeBoolean(false); // Acessa posição da lápide e deixa ela falsa
                     break;
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               } else {
                  file.skipBytes(bytes - 5); // Pula para o próximo registro ( int + boolean)
               }
            } else {
               file.skipBytes(bytes - 5); // Pula para o próximo registro ( int + boolean)
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
         pokemon = null;
      }

      return pokemon;
   }

   public void update(Pokemon pokemon) throws IOException, ParseException {
      file.seek(0);
      file.writeInt(pokemon.getIndex());
      file.seek(file.length());
      byte[] byteArray = pokemon.toByteArray();
      file.writeInt(pokemon.toByteArray().length);
      file.write(byteArray);
   }

   public Pokemon binToPokemon(RandomAccessFile file, long pos) throws IOException {
      Pokemon pokemon = new Pokemon();

      ArrayList<String> typeArrList = new ArrayList<String>();
      ArrayList<String> abilitiesArrList = new ArrayList<String>();

      file.seek(pos);
      file.readInt(); // pula id do registro
      pokemon.lapide = file.readBoolean();
      pokemon.setIndex(file.readInt());
      pokemon.setPokedexNum(file.readInt());
      // file.readInt(); // pula tam. do nome
      pokemon.setName(file.readUTF());
      // file.readInt(); // pula tam. da geração
      pokemon.setGeneration(file.readUTF());
      // file.readInt(); // pula tam. da especie
      pokemon.setSpecie(file.readUTF());
      // file.readInt(); // pula tam. da hidden ability
      pokemon.setHiddenAbility(file.readUTF());
      pokemon.setReleaseDate(file.readLong());
      String[] types = new String[file.readInt()];
      for (int i = 0; i < types.length; i++) {
         // file.readInt(); // pula tam. de cada tipo
         types[i] = file.readUTF();
         typeArrList.add(types[i]);
      }
      pokemon.setTypes(typeArrList);
      String[] abilities = new String[file.readInt()];
      for (int i = 0; i < abilities.length; i++) {
         // file.readInt(); // pula tam. de cada habilidade
         abilitiesArrList.add(file.readUTF());
      }
      pokemon.setAbilities(abilitiesArrList);

      return pokemon;
   }

   public Boolean search(int entradaID) throws IOException {
      long index;
      int qntBytesInic, id;
      boolean lap;

      file.seek(0);
      file.readInt();// pula o ID do registro, pois o mesmo ID será lido mais para frente
      try {
         while (file.getFilePointer() < file.length()) {
            index = file.getFilePointer(); // Pega a posição do ponteiro no momento atual(está apontando para a
                                           // quantidade
                                           // de bytes no registo).
            qntBytesInic = file.readInt(); // Pega o tamanho do registo que será selecionado
            lap = file.readBoolean();
            id = file.readInt();
            if (id == entradaID) { // Verifica se o id é o mesmo que o selecionado
               if (lap) { // Verifica se a lápide é válida, ou seja, se o registro foi apagado ou não
                  return false;
               } else {
                  file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro ( int + boolean)
               }
            } else {
               file.skipBytes(qntBytesInic - 5); // Pula para o próximo registro ( int + boolean)
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return true;
   }
}
