package sql;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SQLReader {
    public static String readSQL(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
