import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class WordCounter {

    public static void main(String[] args) {
        String filePath = "path_to_your_text_file.txt";
        Map<String, Integer> wordCount = new HashMap<>();
        int totalWords = 0;
        int totalLength = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\W+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        totalWords++;
                        totalLength += word.length();
                        word = word.toLowerCase();
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print word frequencies
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Print total words and average word length
        System.out.println("Total words: " + totalWords);
        System.out.println("Average word length: " + (totalWords == 0 ? 0 : (double) totalLength / totalWords));
    }
}
