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
        for(Item i : parsedList){
            System.out.println(i.getName());
        }
    }

    @Override
    public String toString() {
        String result = "";
        int nameCounter = 0;
        for(String name : getItemNames()){
            if(nameCounter != 0){
                result += "\n";
            }
            result += String.format("name:%8s       seen: %d times\n", capitalizeFirstLetter(name), getItemCount(name));
            result += "=============       =============\n";
            int counter = 0;
            for(Double d : getItemPrices(name)) {
                counter++;
                result += String.format("Price:   %.2f       seen: %d times\n", d, getPriceCount(name, d));
                if (counter == 1) {
                    result += "-------------       -------------\n";
                }
            }
            nameCounter++;
        }
        result += String.format("\nErrors              seen: %d times\n", itemParser.getExceptionCount());
        return result;
    }


    public List<String> getItemNames(){
        List<String> itemNames = new ArrayList<>();
        for(Item i : parsedList){
            if(!(itemNames.contains(i.getName()))){
                itemNames.add(i.getName());
            }
        }
        return itemNames;
    }

    public List<Double> getItemPrices(String name){
        List<Double> prices = new ArrayList<>();
        for(Item i : parsedList){
            if((i.getName().equals(name)) && (!(prices.contains(i.getPrice())))) {
                prices.add(i.getPrice());
            }
        }
        return prices;
    }

    public int getItemCount(String name){
        int itemCount = 0;
        for(Item i : parsedList){
            if(i.getName().equals(name)){
                itemCount++;
            }
        }
        return itemCount;
    }

    public int getPriceCount(String name, Double price){
        int priceCount = 0;
        for(Item i : parsedList){
            if(name.equals(i.getName()) && (price.equals(i.getPrice()))){
                priceCount++;
            }
        }
        return priceCount;
    }

    public String capitalizeFirstLetter(String input){
        return input.toUpperCase().charAt(0) + input.substring(1);
    }
}
