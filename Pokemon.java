import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

class Pokemon {
    // Entender melhor esse atributo
    public boolean lapide;
    private String name, generation, specie, hiddenAbility;
    private int index, pokedexNum;
    private ArrayList<String> types, abilities;
    private Date releaseDate;

    // Seters
    public void setName(String name) {
        this.name = name;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public void setHiddenAbility(String hiddenAbility) {
        this.hiddenAbility = hiddenAbility;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPokedexNum(int pokedexNum) {
        this.pokedexNum = pokedexNum;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }

    public void setAbilities(ArrayList<String> abilities) {
        this.abilities = abilities;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setReleaseDate(long longDate) {
        Date date = new Date(longDate);
        this.releaseDate = date;
    }

    // Getters
    public String getName() {
        return this.name;
    }

    public String getGeneration() {
        return this.generation;
    }

    public String getSpecie() {
        return this.specie;
    }

    public String getHiddenAbility() {
        return this.hiddenAbility;
    }

    public int getIndex() {
        return this.index;
    }

    public int getPokedexNum() {
        return this.pokedexNum;
    }

    public ArrayList<String> getTypes() {
        return this.types;
    }

    public ArrayList<String> getAbilities() {
        return this.abilities;
    }

    public long getReleaseDate() {
        System.out.println(this.releaseDate);
        System.out.println(this.releaseDate.getTime());
        return this.releaseDate.getTime();
    }

    // Empty Constructor
    public Pokemon() {
        this.lapide = true;
        this.name = this.generation = this.specie = hiddenAbility = null;
        this.index = this.pokedexNum = -1;
        this.types = abilities = new ArrayList<String>();
        this.releaseDate = null;
    }

    // CSV: [id, pokedexId, name, generation, specie, abilityHidden, releaseDate,
    // types, abilities]
    public void parseCSV(String csvLine) {
        String[] cells = csvLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        this.lapide = true;
        this.setIndex(Integer.parseInt(cells[0]));
        this.setPokedexNum(Integer.parseInt(cells[1]));
        this.setName(cells[2]);
        this.setGeneration(cells[3]);
        this.setSpecie(cells[4]);
        this.setHiddenAbility(cells[5]);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(format.parse(cells[6]));
            this.setReleaseDate(format.parse(cells[6]));
        } catch (Exception e) {
            System.out.println(e);
        }
        this.setTypes(formatArray(cells[7]));
        this.setAbilities(formatArray(cells[8]));
    }

    public ArrayList<String> formatArray(String arrayField) {

        String[] array = (arrayField.replaceAll("\"", "")).split(", ");
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            arrayList.add(array[0]);
            if (array.length > 1) {
                arrayList.add(array[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayList;
    }

    public String arrayToString(ArrayList<String> array) {
        String str = array.size() == 2 ? array.get(0) + ", " + array.get(1) : array.get(0);
        return str;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bs);

        // Converter dados da Classe Pokemon para um array de Bytes

        dos.writeBoolean(this.lapide);
        dos.writeInt(getIndex());
        dos.writeInt(getPokedexNum());
        dos.writeInt(getName().getBytes(Charset.forName("UTF-8")).length);
        dos.writeUTF(getName());
        dos.writeInt(getGeneration().getBytes(Charset.forName("UTF-8")).length);
        dos.writeUTF(getGeneration());
        dos.writeInt(getSpecie().getBytes(Charset.forName("UTF-8")).length);
        dos.writeUTF(getSpecie());
        dos.writeInt(getHiddenAbility().getBytes(Charset.forName("UTF-8")).length);
        dos.writeUTF(getHiddenAbility());
        dos.writeLong(getReleaseDate());
        dos.writeInt(getTypes().size());
        for (String type : getTypes()) {
            System.out.println(type);
            dos.writeInt(type.getBytes(Charset.forName("UTF-8")).length);
            dos.writeUTF(type);
        }
        dos.writeInt(getAbilities().size());
        for (String abilities : getAbilities()) {
            System.out.println(abilities);
            dos.writeInt(abilities.getBytes(Charset.forName("UTF-8")).length);
            dos.writeUTF(abilities);
        }

        return bs.toByteArray();
    }

    public String toString() {
        return "\nIndex: " + getIndex() +
                "\nPokedex Number: " + getPokedexNum() +
                "\nGeneration: " + getGeneration() +
                "\nNome: " + getName() +
                "\nSpecie: " + getSpecie() +
                "\nTypes: " + this.arrayToString(getTypes()) +
                "\nAbilities: " + this.arrayToString(getAbilities()) +
                "\nHidden Ability: " + getHiddenAbility();
    }
}