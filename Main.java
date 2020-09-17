package com.kingapawlowska;

// https://regexr.com/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static String patternToSearch = "(?:\\((?:0010)\\,(?:0010)\\))\\s(?:[A-Z]{2})\\s\\[([a-zA-Z ]*)\\](\\s*)\\s\\#\\s+(?:\\d+)\\,\\s\\d\\s\\w+";
    private static String patternToSearchConfidentialData = "(?:\\((?:0010)\\,(?:0010)\\)|\\((?:0010)\\,(?:0030)\\)|\\((?:0010)\\,(?:0032)\\)|\\((?:0010)\\,(?:0033)\\)|\\((?:0010)\\,(?:0034)\\)|\\((?:0010)\\,(?:0040)\\)|\\((?:0010)\\,(?:1001)\\)|\\((?:0010)\\,(?:1005)\\)|\\((?:0010)\\,(?:1010)\\)|\\((?:0010)\\,(?:1020)\\)|\\((?:0010)\\,(?:1030)\\)|\\((?:0010)\\,(?:1040)\\)|\\((?:0010)\\,(?:1060)\\)|\\((?:0010)\\,(?:2154)\\)|\\((?:0010)\\,(?:2155)\\)|\\((?:0010)\\,(?:21F0)\\)|\\((?:0010)\\,(?:2203)\\)|\\((?:0038)\\,(?:0300)\\)|\\((?:0038)\\,(?:0400)\\))\\s(?:[A-Z]{2})\\s((\\[([a-zA-Z ]*)\\])|(([a-zA-Z ]*)))(\\s*)\\s\\#\\s+(?:\\d+)\\,\\s\\d\\s\\w+";
    private static String anonymizedPatternSpacesPart1 = "((?:\\((?:0010)\\,(?:0010)\\))\\s(?:[A-Z]{2})\\s\\[([a-zA-Z\\# ]*)\\])";
    private static String anonymizedPatternSpacesPart2 = "(\\s\\#\\s+(?:\\d+)\\,\\s\\d\\s\\w+)";


    public static void main(String[] args) {
//        System.out.println("Podaj nazwe pliku do wczytania: ");
        Scanner in = new Scanner(System.in);
//        String fileName = in.next();

        String fileName = "wynik.txt";

        System.out.println("File name: " + fileName);

        List linesOfFile = new ArrayList();
        Scanner input = new Scanner(System.in);
        File file = new File("" + fileName);
        try {
            input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                linesOfFile.add(line);
            }
            input.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        String lineWithName = getLineWithName(linesOfFile);
//        String lineWithAnonymousName = anonymizationOfConfidentialDataName(lineWithName);
//        String nameFromLine = getNameFromLine(lineWithName);
//
//        saveToFile(fileName,nameFromLine,lineWithName,lineWithAnonymousName);

        List linesOfFileWithAnonymousData = new ArrayList();
        linesOfFileWithAnonymousData = anonymizationOfConfidentialData(linesOfFile);
        saveAnonymousDataToFile(fileName, linesOfFileWithAnonymousData);

    }

    private static List anonymizationOfConfidentialData(List linesOfFile) {

        String anonymizationOfTheDataResult = null;

        Pattern p = Pattern.compile(patternToSearchConfidentialData);   // the pattern to search for

        String confidentialDataStr = "#CONFIDENTIAL#DATA#";

        List linesOfFileNewList = new ArrayList();
        for (int i = 0; i < linesOfFile.size(); i++) {

            Matcher m = p.matcher(linesOfFile.get(i).toString());

            if (m.find()) {
                String patientData = m.group(1);

                anonymizationOfTheDataResult = linesOfFile.get(i).toString();
                anonymizationOfTheDataResult = anonymizationOfTheDataResult.replace(patientData, confidentialDataStr);
                linesOfFileNewList.add(anonymizationOfTheDataResult);


            } else {
                linesOfFileNewList.add(linesOfFile.get(i).toString());
            }

        }
        return linesOfFileNewList;
    }

    private static String getLineWithName(List<String> linesOfFile) {

        String lineWithName = "";

        for (int i = 0; i < linesOfFile.size(); i++) {
            String stringToSearch = linesOfFile.get(i);

            Pattern p = Pattern.compile(patternToSearch);   // the pattern to search for
            Matcher m = p.matcher(stringToSearch);

            if (m.find()) {
                lineWithName = linesOfFile.get(i);
            }
        }

        return lineWithName;
    }

    private static String getNameFromLine(String lineWithName) {

        String nameFromLine = null;

        Pattern p = Pattern.compile(patternToSearch);   // the pattern to search for
        Matcher m = p.matcher(lineWithName);

        if (m.find()) {
            String patientName = m.group(1);
            nameFromLine = patientName;
        }

        return nameFromLine;
    }

    private static String anonymizationOfConfidentialDataName(String lineWithName) {

        String anonymizationOfTheNameResult = null;

        Pattern p = Pattern.compile(patternToSearch);   // the pattern to search for
        Matcher m = p.matcher(lineWithName);

        if (m.find()) {

            String patientName = m.group(1);
            String spaces = m.group(2);

            String confidentialDataStr = "#CONFIDENTIAL#DATA#";

            anonymizationOfTheNameResult = lineWithName;
            anonymizationOfTheNameResult = anonymizationOfTheNameResult.replace(patientName, confidentialDataStr);


            int maximumLengthOfName = patientName.length() + spaces.length();
            int lengthOfConfidentialDataStr = confidentialDataStr.length();
            int lengthOfSpacesStr = maximumLengthOfName - lengthOfConfidentialDataStr;

            StringBuilder sbResult = new StringBuilder();

            Pattern pSpace1 = Pattern.compile(anonymizedPatternSpacesPart1);
            Matcher mSpace1 = pSpace1.matcher(anonymizationOfTheNameResult);

            if (mSpace1.find()) {
                sbResult.append(mSpace1.group(1));
            }

            for (int i = 0; i < lengthOfSpacesStr; i++) {
                sbResult.append(" ");
            }

            Pattern pSpace2 = Pattern.compile(anonymizedPatternSpacesPart2);
            Matcher mSpace2 = pSpace2.matcher(anonymizationOfTheNameResult);

            if (mSpace2.find()) {
                sbResult.append(mSpace2.group(1));
            }

//            System.out.println(lineWithName);
//            System.out.println(anonymizationOfTheNameResult);
//            System.out.println(sbResult.toString());
            anonymizationOfTheNameResult = sbResult.toString();

        }

        return anonymizationOfTheNameResult;
    }

    public static boolean saveToFile(String filename, String nameFromLine, String lineWithName, String lineWithAnonymousName) {

        Path path = Paths.get("" + filename + "_result.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            try {
                writer.write(filename + "\n");
                writer.write(nameFromLine + "\n");
                writer.write("\n");
                writer.write(lineWithName + "\n");
                writer.write(lineWithAnonymousName + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean saveAnonymousDataToFile(String filename, List linesOfFileWithAnonymousData) {

        Path path = Paths.get("" + filename + "_result.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            try {

                for (int i = 0; i < linesOfFileWithAnonymousData.size(); i++) {
                    writer.write(linesOfFileWithAnonymousData.get(i) + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
