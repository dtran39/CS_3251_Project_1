import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
// 1. Query the DB by hand first
    // Used hash from id to array of values
// 2. Establish client/server connection
public class dbengineTCP{
    public static void main (String[] args) throws IOException {
        handleArgs(args);
    }
    private static void handleArgs(String[] args) throws FileNotFoundException {
        if (args.length < 3) {
             System.out.println("Not enough argument to query");
             return;
        }
        HashMap<String, String[]> db = getDB("db.csv");
        HashMap<String, Integer> lookupTable = getLookupTable();
        String id = args[1];
        if (!db.containsKey(id)) {
            System.out.println("ID not found");
            return;
        }
        String output = "From server: ";
        for (int i = 2; i < args.length; i++) {
            String column = args[i], value = "Not found. ";
            output += column + " : ";
            if (lookupTable.containsKey(column))
                value = db.get(id)[lookupTable.get(column)] + ", ";
            output += value;
        }
        System.out.println(output.substring(0, output.length() - 2));
    }
    private static HashMap<String, Integer> getLookupTable(){
        HashMap<String, Integer> lookupTable = new HashMap<String, Integer>();
        lookupTable.put("first_name", 0);  lookupTable.put("last_name", 1);   lookupTable.put("quality_points", 2);
        lookupTable.put("gpa_hours", 3);  lookupTable.put("gpa", 4);
        return lookupTable;
    }
    private static HashMap<String, String[]> getDB(String fileName) throws FileNotFoundException{
        HashMap<String, String[]> hash = new HashMap<String, String[]>();
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNextLine()) {
            String[] cells= fileScanner.nextLine().split(",");
            int n = cells.length;
            String[] personValues = new String[n - 1];
            for (int i = 1; i < n; i++) {
                personValues[i - 1] = cells[i];
            }
            hash.put(cells[0], personValues);
        }
        return hash;
>>>>>>> df42ed376967c8a6897254adfe5c84f8057b771a
    }
}