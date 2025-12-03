import java.io.*;

public class ConsoleToFileDebug {
    public static void main(String[] args) {
        System.out.print("Enter the name of the output file: ");
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            String outputFile = consoleReader.readLine();
            if (outputFile == null || outputFile.trim().isEmpty()) {
                System.out.println("Error: Output file name cannot be empty.");
                return;
            }

            File file = new File(outputFile);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                System.out.println("Error: Directory " + parentDir.getAbsolutePath() + " does not exist.");
                return;
            }
            if (file.exists() && !file.canWrite()) {
                System.out.println("Error: No write permission for file " + outputFile);
                return;
            }

            System.out.println("Current working directory: " + System.getProperty("user.dir"));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                System.out.println("Enter text (type 'END' to finish, or try Ctrl + Z on Windows/Ctrl + D on Linux/Mac):");

                String line;
                while ((line = consoleReader.readLine()) != null) {
                    System.out.println("Read: " + line);
                    if (line.equals("END")) {
                        break;
                    }
                    if (!line.equals("null")) {
                        writer.write(line);
                        writer.newLine();
                        writer.flush();
                    }
                }
                System.out.println("Text successfully written to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error writing to file " + outputFile + ": " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error reading from console: " + e.getMessage());
            e.printStackTrace();
        }
    }
}