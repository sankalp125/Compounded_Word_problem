import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class LongestCompoundedWordsFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine(); // Taking the filename as input from the user. If you enter wrong file name it will through an error.


        System.out.println("\nOutput for Input file ( " + filename + " ):");
        System.out.println();

        // Finding the processing time using system time.
        long startTime = System.currentTimeMillis();
        TwoStrings result = longestCompoundedWords(filename);
        long endTime = System.currentTimeMillis();
        double processingTime = (endTime - startTime) / 1000.0;

        // output The Longest compound word Second-Longest compounded words.
        System.out.println("Longest compound word: " + result.getFirst());
        System.out.println("Second longest compound word: " + result.getSecond());
        System.out.println("Time taken to process the input file: " + new DecimalFormat("#.######").format(processingTime) + " seconds");
    }

    // Reading the file and adding the items of the file in a list.
    public static  TwoStrings longestCompoundedWords(String filename) {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new TwoStrings("", "");
        }

        // Sorting the items of list in ascending order and adding the sorted list into a hashset to avoid duplicacy of items.
        words.sort((a, b) -> Integer.compare(b.length(), a.length()));
        Set<String> wordDictionary = new HashSet<>(words);

        String firstLongWord = "";
        String secondLongestWord = "";

        // Searching for the two longest compounded words among all the compounded words.
        for (String word : words) {
            if (checkWordCompounded(wordDictionary, word)) {
                if (firstLongWord.isEmpty()) {
                    firstLongWord = word;
                } else if (secondLongestWord.isEmpty()) {
                    secondLongestWord = word;
                    break;
                }
            }
        }

        return new TwoStrings(firstLongWord, secondLongestWord);
    }

    // checking for the word is compounded or not.
    public static boolean checkWordCompounded(Set<String> wordDictionary, String word) {
        for (int i = 1; i < word.length(); i++) {
            String firstWord = word.substring(0, i);
            String remainingWord = word.substring(i);

            if (wordDictionary.contains(firstWord) && (wordDictionary.contains(remainingWord) || checkWordCompounded(wordDictionary, remainingWord))) {
                return true;
            }
        }
        return false;
    }
}

// custom class works as a data structure to store the two longest compounded words.
class TwoStrings {
    private String first;
    private String second;

    public TwoStrings(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }
}
