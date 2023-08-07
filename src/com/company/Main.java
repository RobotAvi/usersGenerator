package com.company;

import java.io.*;
import java.util.*;

public class Main {
    private static final String CSV_PATH = "users.csv";
    private static final int NUMBER_OF_RECORDS = 300;
    private static final String FIRSTNAMES_FILE = "Resources/firstnames.txt";
    private static final String LASTNAMES_FILE = "Resources/lastnames.txt";
    private static final String USERNAME_PREFIX = "GeneratedUser";
    private static final String DESCRIPTION = "Generated user";
    private static final String TYPE = "INTERNAL";

    public static void main(String[] args) {

        List<String> firstNames = getFirstnamesFromTXT();
        List<String> lastNames = getLastnamesFromTXT();
        List<String[]> generatedUsersCsvContent = generateCsvContent(lastNames, firstNames);
        saveCSV(generatedUsersCsvContent);
    }

    private static void saveCSV(List<String[]> csvContent) {
        try (PrintWriter writer = new PrintWriter(Main.CSV_PATH)) {
            StringBuilder sb = new StringBuilder();
            for (String[] record : csvContent) {
                for (int i = 0; i < record.length; i++) {
                    sb.append(record[i]);
                    if (i < record.length - 1) {
                        sb.append(",");
                    }
                }
                sb.append("\n");
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> generateCsvContent(List<String> lastNames, List<String> firstNames) {
        List<String[]> csvContent = new ArrayList<>();
        String indexFormat = "%0" + String.valueOf(Main.NUMBER_OF_RECORDS).length() + "d";
        final int PASSWORD_LENGTH = 10;

        for (int i = 1; i <= Main.NUMBER_OF_RECORDS; i++) {
            String index = String.format(indexFormat, i);
            String username = Main.USERNAME_PREFIX + index;
            String password = generateRandomPassword();
            String[] record = {username, Main.DESCRIPTION, Main.TYPE, getRandomElement(lastNames), getRandomElement(firstNames), password};
            csvContent.add(record);
        }
        return csvContent;
    }

    private static List<String> getLastnamesFromTXT() {
        return readFromFile(FIRSTNAMES_FILE);
    }

    private static List<String> getFirstnamesFromTXT() {
        return readFromFile(LASTNAMES_FILE);
    }

    private static List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static String getRandomElement(List<String> list) {
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}