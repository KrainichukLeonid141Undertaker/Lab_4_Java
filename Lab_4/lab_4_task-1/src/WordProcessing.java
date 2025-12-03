import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordProcessing {
    private static Set<String> readWords(String filePath) {
        Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.toLowerCase());
                while (matcher.find()) {
                    words.add(matcher.group());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + filePath + " not found.");
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
        }
        return words;
    }

    private static List<String> readText(String filePath) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.toLowerCase());
                while (matcher.find()) {
                    words.add(matcher.group());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + filePath + " not found.");
        } catch (IOException e) {
            System.out.println("Error reading text file " + filePath + ": " + e.getMessage());
        }
        return words;
    }

    private static void writeMissingWords(String outputFile, List<String> textWords, Set<String> wordList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            Set<String> missingWords = new TreeSet<>();
            for (String word : textWords) {
                if (!wordList.contains(word)) {
                    missingWords.add(word);
                }
            }
            for (String word : missingWords) {
                writer.println(word);
            }
            System.out.println("Missing words written to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing to file " + outputFile + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of the file with the word list: ");
        String wordFile = scanner.nextLine();
        System.out.print("Enter the name of the file with the text: ");
        String textFile = scanner.nextLine();
        System.out.print("Enter the name of the output file: ");
        String outputFile = scanner.nextLine();

        Set<String> wordList = readWords(wordFile);
        List<String> textWords = readText(textFile);

        if (wordList.isEmpty() || textWords.isEmpty()) {
            System.out.println("No data to process. Exiting.");
            return;
        }

        Map<String, Integer> wordCounts = new HashMap<>();
        for (String word : textWords) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> frequentWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() > 1) {
                frequentWords.add(entry);
            }
        }
        frequentWords.sort(Map.Entry.comparingByKey());

        System.out.println("\nWords appearing more than once in the text (sorted):");
        for (Map.Entry<String, Integer> entry : frequentWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        writeMissingWords(outputFile, textWords, wordList);

        scanner.close();
    }
}
