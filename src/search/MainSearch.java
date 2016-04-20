package search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainSearch {

    //region consts

    private final static String[] NAMES = {
            "Myong", "Latasha", "Fabian", "Joesph", "Mellissa", "Buffy", "Sheena", "Weldon", "Renate", "Nydia", "Bernard", "Alexander", "Leanne", "Shalonda", "Elenora", "Merlene", "Dick",
            "Kasie", "Cheryll", "Darius", "Sona", "Nathanael", "Brigid", "Ivy", "Raquel", "Mellisa", "Ehtel", "Larita", "Heide", "Arletha", "Terri", "Ethel", "Guadalupe", "Karyn", "Lashaunda", "Kallie", "Monserrate", "Narcisa", "Sanda", "Tamela", "Kip", "Emery", "Hwa", "Krista", "Stanton", "Kam", "Emerson", "Ilda", "Kristyn", "Zoraida",
            "Tessa", "Margery", "Karma", "Verline", "Landon", "Waneta", "Marvella", "Maximina", "Gabriela", "Enrique", "Lorena", "Courtney", "Willow", "Shaunda", "Franklyn", "An", "Cassy", "Alix", "Merrill", "Joella", "Ebonie", "Elwanda", "Ka", "Cathie",
            "Rosy", "Kerstin", "Nell", "Bree", "Mariette", "Dulce", "Lelah", "Esteban", "Barrett", "Conrad", "Collene", "Tuyet", "Elke", "Blake", "Jamal", "Regenia", "Shirlene", "Rodney", "Lillie", "Moon", "Lashaun", "Lisandra", "Delisa",
            "Tandy", "Harriett", "Fritz", "Marita", "Mendy", "Kasha", "Ricarda", "Amy", "Sharie", "Lucia", "Jaye", "Zora", "Garfield", "Dinorah", "Ester", "Maragaret", "Mavis", "Humberto", "Pearlie", "Serafina", "Lajuana", "Janay", "Marta", "Greta",
            "Johnson", "Adolph", "Sacha", "Florentina", "Claudie", "Suzan", "Hermine", "Eloisa", "Aurora", "Byron", "Anna", "Felica", "Lizbeth", "Curtis", "Katie", "Elise", "Chanell", "Damaris", "Keenan", "Simona", "Anthony", "Emiko", "Nita", "Larry", "Annalee",
            "Marilyn", "Myung", "Seema", "Gillian", "Beverlee", "Tyler", "Shantay", "Isobel", "Marcella", "Elois", "Darrick", "Kitty", "Gerardo", "Ying", "Kena", "Astrid", "Lourie", "Charity", "Anisa", "Soledad", "Kassandra", "Eilene", "Francoise", "Marylin", "Marni",
            "Magdalena", "Azalee", "Hyun", "Twila", "Verena", "Leonel", "Mollie", "Johnette", "Jule", "Rowena", "Felix", "Bobbye", "Blondell", "Vallie", "Hien", "Shanita", "Herlinda", "Andy", "Doris", "Lacresha",
            "Jamaal", "Elaine", "Teresita", "Luba", "Sheri", "Marcella", "Ruthanne", "Jess", "Willard", "Norberto", "Orlando", "Talia", "Armida", "Bernardo", "Toni", "Harold", "Tonja", "Felecia", "Amiee"
    };


    private static final String[] LASTNAMES = {
            "Mohler", "Drouin", "Mercure", "Stubbs", "Halper", "Swartwood", "Swayne", "Feehan", "Glaspie", "Trower", "Hynson", "Truong", "Toribio", "Simonton", "Carr", "Laudenslager", "Arbaugh",
            "Mitzel", "Kovacs", "Summerville", "Caspers", "Piraino", "Melby", "Nieman", "Stthomas", "Chalmers", "Toohey", "Griner", "Ahl", "Strother", "Applin", "Stamm", "Means", "Puskar", "Piccolo", "Douthit", "Sosnowski",
            "Parkey", "Weigle", "Hepp", "Huhn", "Hamlet", "Loux", "Rowden", "Lazarus", "Heth", "Rappa", "Hoff", "Deforge", "Starbird", "Wamsley", "Broughton", "Lorch", "Toon", "Bohon", "Quast",
            "Sciortino", "Etter", "Keleher", "Marrero", "Rumery", "Kegler", "Antle", "Petry", "Miesner", "Vannatta", "Lasker", "Baggs", "Rosenthal", "Paladino", "Rathbun", "Paff", "Krom", "Delao",
            "Botelho", "Gant", "Linebarger", "Healy", "Schober", "Hoadley", "Cheever", "Vaquera", "Jozwiak", "Melecio", "Bloch", "Savidge", "Ceron", "Salyers", "Zermeno", "Linwood", "Mason", "Zajac", "Troyer", "Zachery", "Lebow", "Hamblen", "Rothschild",
            "Toler", "Wrench", "Troxler", "Gothard", "Hoefer", "Profitt", "Shepler", "Mckeon", "Keeney", "Winbush", "Rone", "Lemmons", "Ungar", "Bortz", "Shreffler", "Allende", "Salva", "Jared", "Spector", "Marriott", "Cuthbertson", "Ash", "Choquette", "Huddle", "Buchmann",
            "Musselman", "Hensen", "Sinner", "Wiedman", "Sherer", "Wind", "Jines", "Bartholomew", "Madonia", "Teitelbaum", "Lipari", "Paley", "Truesdale", "Falkner", "Mayse", "Wanke", "Cammarata", "Gundlach", "Kropp", "Leyden", "Westphal", "Hinz", "Rundell", "Carreon", "Bronk", "Piche", "Kenworthy", "Cabral",
            "Cintron", "Broderick", "Dubay", "Mister", "Mizer", "Deatherage", "Sano", "Trevathan", "Donati", "Shivers", "Geissler", "Hoffmeister", "Morado", "Scala", "Bouchard", "Slaymaker", "Enz", "Pless", "Santee", "Lockett", "Trout", "Ros", "Buckland", "Winfrey", "Moncayo", "Schlachter", "Furness", "Melder",
            "Fleagle", "Leininger", "Wantz", "Mangus", "Boulton", "Ludwig", "Tallarico", "Cartledge", "Landy", "Adam", "Cashwell", "Chasteen", "Oshaughnessy", "Thieme", "Bisson", "Oswalt", "Zelinski", "Mongillo", "Sherwood", "Chretien", "Lucio", "Blanche",
            "Boyette", "Owen", "Joplin", "Liska", "Twining", "Oquin", "Whelchel", "Pilcher", "Whitener", "Zepeda"
    };

    //endregion

    public static void main(String[] args) throws IOException {
        List<Student> generate = generate(2_000_000);
//        System.out.println(generate);
        HashTable table = new HashTable(5_000_000);
        System.out.println("Creating table");
        for (Student student : generate) {
            table.insert(new Item(student));
        }
        String last;
        System.out.println("Input lastname: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //because intellij
        while (!(last = br.readLine()).equals("")) {
            long startTime = System.nanoTime();
            List<Student> found = table.find(last);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println(found);
            System.out.println("Found in " + duration / 1_000_000f + "ms.");
            System.out.println("Input lastname: ");
        }
    }

    public static List<Student> generate(int count) {
        List<Student> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String name = NAMES[random.nextInt(NAMES.length)];
            String lastname = LASTNAMES[random.nextInt(LASTNAMES.length)];
            list.add(new Student(name, lastname));
        }
        return list;
    }

}
