package buddy;

import java.util.List;

/**
 * The FuzzySearch class implements fuzzy search using the Levenshtein Distance algorithm.
 * It helps find strings similar to a given search term, allowing for typos or partial matches.
 *
 * <p>Useful for forgiving search functionality when user input may be misspelled or incomplete.</p>
 *
 */
public class FuzzySearch {

    /**
     * Calculates the Levenshtein distance between two strings.
     * @param s1 the first string
     * @param s2 the second string
     * @return the Levenshtein distance between the two strings
     */
    public static int getLevenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * Fuzzy search for tasks based on a keyword and Levenshtein distance.
     * @param tasks the list of tasks
     * @param keyword the search term
     * @return the tasks that are most similar to the search term
     */
    public static String fuzzySearch(List<Task> tasks, String keyword) {
        List<Task> foundTasks = tasks.stream()
                .filter(task -> {
                    String[] taskWords = task.getDescription().toLowerCase().split("\\s+");
                    for (String taskWord : taskWords) {
                        int distance = getLevenshteinDistance(taskWord, keyword.toLowerCase());
                        if (distance <= 2) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        if (foundTasks.isEmpty()) {
            return "No tasks found matching that keyword.";
        }

        StringBuilder result = new StringBuilder("Found tasks:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            result.append(i + 1).append(". ").append(foundTasks.get(i).toString()).append("\n");
        }
        return result.toString();
    }
}
