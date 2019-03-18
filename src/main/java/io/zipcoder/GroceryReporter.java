package io.zipcoder;

import io.zipcoder.utils.FileReader;

public class GroceryReporter {
    private final String originalFileText;
    private String[] itemArray;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
        itemArray = originalFileText.split("##");
        for(String s : itemArray){
            System.out.println(s);
        }
    }

    @Override
    public String toString() {
        return null;
    }

}
