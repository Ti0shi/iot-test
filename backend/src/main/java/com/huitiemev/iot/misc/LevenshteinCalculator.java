package com.huitiemev.iot.misc;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Math.*;

public class LevenshteinCalculator {
    @Operation (summary = "Compute the Levenshtein distance between all tokens of two strings, " +
            "return the minimum distance if ALL tokens of x string are at least partially matching with ALL tokens " +
            "of y string")
    @Parameter (name = "x", description = "The first string to be compared")
    @Parameter (name = "y", description = "The second string to be compared")
    public static int isMatching(String x, String y) {
        ArrayList<String> xWords = new ArrayList<>(Arrays.asList(x.toLowerCase().trim().split(" ")));
        ArrayList<String> yWords = new ArrayList<>(Arrays.asList(y.toLowerCase().trim().split(" ")));
        ArrayList<Integer> distanceValues = new ArrayList<>();

        for (String xWord : xWords) {
            int minDistance = Integer.MAX_VALUE;
            for (String yWord : yWords) {
                int minWordLength = min(xWord.length(), yWord.length());
                String subYWords = StringUtils.substring(yWord, 0, minWordLength);

                int distance = getDistance(xWord, subYWords);
                double threshold = ceil((double)minWordLength / 4);
                if (distance <= threshold && distance < minDistance) {
                    minDistance = distance;
                }
            }
            distanceValues.add(minDistance);
        }

        return distanceValues.contains(Integer.MAX_VALUE) ? Integer.MAX_VALUE : Collections.min(distanceValues);
    }

    @Operation(summary = "Calculate the Levenshtein distance between two strings")
    @Parameter(name = "x", description = "The first string")
    @Parameter(name = "y", description = "The second string")
    public static int getDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];
        for (int i = 0; i <= x.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= y.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= x.length(); i++) {
            char xc = x.charAt(i - 1);
            for (int j = 1; j <= y.length(); j++) {
                char yc = y.charAt(j - 1);
                int cost = xc == yc ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j - 1] + cost,
                        Math.min(dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1));
            }
        }
        return dp[x.length()][y.length()];
    }


}
