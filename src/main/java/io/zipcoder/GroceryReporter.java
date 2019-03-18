package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.ArrayList;
import java.util.List;

public class GroceryReporter {
    private final String originalFileText;
    private ItemParser itemParser;
    private List<Item> parsedList;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
        itemParser = new ItemParser();
        parsedList = itemParser.parseItemList(originalFileText);
    }

    @Override
    public String toString() {
        return  getNamesAndPricesString() + getErrorsString();
    }

    public String getNamesAndPricesString(){
        String result = "";
        boolean printNewLine = false;
        for (String name : getItemNames()) {
            if (printNewLine) {
                result += "\n";
            }
            result += String.format("name:%8s       seen: %d times\n", capitalizeFirstLetter(name), getItemCount(name)) +
                    "=============       =============\n" + getPriceString(name);
            printNewLine = true;
        }
        return result;
    }

    public String getPriceString(String name) {
        boolean printDashes = true;
        String result = "";
        for (Double d : getItemPrices(name)) {
            result += String.format("Price:   %.2f       seen: %d times\n", d, getPriceCount(name, d));
            if (printDashes) {
                result += "-------------       -------------\n";
            }
            printDashes = false;
        }
        return result;
    }

    public String getErrorsString(){
        return String.format("\nErrors              seen: %d times\n", itemParser.getExceptionCount());
    }


    public List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (Item i : parsedList) {
            if (!(itemNames.contains(i.getName()))) {
                itemNames.add(i.getName());
            }
        }
        return itemNames;
    }

    public List<Double> getItemPrices(String name) {
        List<Double> prices = new ArrayList<>();
        for (Item i : parsedList) {
            if ((i.getName().equals(name)) && (!(prices.contains(i.getPrice())))) {
                prices.add(i.getPrice());
            }
        }
        return prices;
    }

    public int getItemCount(String name) {
        int itemCount = 0;
        for (Item i : parsedList) {
            if (i.getName().equals(name)) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public int getPriceCount(String name, Double price) {
        int priceCount = 0;
        for (Item i : parsedList) {
            if (name.equals(i.getName()) && (price.equals(i.getPrice()))) {
                priceCount++;
            }
        }
        return priceCount;
    }

    public String capitalizeFirstLetter(String input) {
        return input.toUpperCase().charAt(0) + input.substring(1);
    }
}