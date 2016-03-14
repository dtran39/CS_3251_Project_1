import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;

/**
 * This class handle the request from client, hit the database and generate the response back.
 */
public class InputHandler {
    /*
        This function handle the query request, call the database and generate a response
     */
    public static String handleArgs(String[] args) throws FileNotFoundException {
        // Precheck the argument number
        if (args.length < 2) return "Not enough argument to query";
        // Generate the db and lookup table
        HashMap<String, String[]> db = getDB("db.csv");
        HashMap<String, Integer> lookupTable = getLookupTable();
        // Get the ID, query on the database to see if there is a match
        String id = args[0];
        if (!db.containsKey(id)) return "ID not found";
        String output = "From server: ";
        // Get the result from the columns in the query. Return Not found if the column name is invalid.
        for (int i = 1; i < args.length; i++) {
            String column = args[i], value = "Not found. ";
            output += column + " : ";
            if (lookupTable.containsKey(column))
                value = db.get(id)[lookupTable.get(column)] + ", ";
            output += value;
        }
        // Return as a string
        return output.substring(0, output.length() - 2);
    }
    /*
        This function generate the lookup table for the student database.
        @return the lookupTable as a hashmap
     */
    private static HashMap<String, Integer> getLookupTable(){
        HashMap<String, Integer> lookupTable = new HashMap<String, Integer>();
        lookupTable.put("first_name", 0);  lookupTable.put("last_name", 1);   lookupTable.put("quality_points", 2);
        lookupTable.put("gpa_hours", 3);  lookupTable.put("gpa", 4);
        return lookupTable;
    }
    /*
        This function read the database file and generate a database/hashmap
     */
    private static HashMap<String, String[]> getDB(String fileName) throws FileNotFoundException{
        // Create the hash and the file scanner
        HashMap<String, String[]> hash = new HashMap<String, String[]>();
        Scanner fileScanner = new Scanner(new File(fileName));
        // Read each line of the file using the scanner, extract information and put in the hashmap
        while (fileScanner.hasNextLine()) {
            String[] student= fileScanner.nextLine().split(",");
            int n = student.length;
            String[] studentInfo = new String[n - 1];
            for (int i = 1; i < n; i++) {
                studentInfo[i - 1] = student[i];
            }
            hash.put(student[0], studentInfo);// student[0] is the student ID
        }
        return hash;
    }
}