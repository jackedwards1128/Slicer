import java.io.FileNotFoundException;
import java.io.*;

public class Program {

    public Program() {

    }



    public static void main(String[] args) throws IOException {
        System.out.println("Program started");

        if (args.length != 1) {
            System.out.println("\n!!ERROR!! Usage: java Program.java <file>\n");
        }

        String fileName = args[0];

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();


    }
}
