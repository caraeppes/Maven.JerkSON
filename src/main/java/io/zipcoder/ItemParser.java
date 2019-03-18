package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser{

    private int exceptionCount;
    private Pattern namePattern, pricePattern, typePattern, expirationPattern;

    public ItemParser() {
        exceptionCount = 0;
        namePattern = Pattern.compile("[N|n][A|a][M|m][E|e][@|^|*|:|%]([a-zA-Z0]+);");
        pricePattern = Pattern.compile("[P|p][R|r][I|i][C|c][E|e][@|^|*|:|%](\\d+\\.\\d+);");
        typePattern = Pattern.compile("type[@|^|*|:|%]([A-Za-z]+)[;|/^|/!|%|*|@]");
        expirationPattern = Pattern.compile("expiration[@|^|*|:|%](\\d+/\\d+/\\d{4})");
    }

    public List<Item> parseItemList(String valueToParse) {
        List<Item> parsedList = new ArrayList<>();
        for (String s : valueToParse.split("##")) {
            try {
                parsedList.add(parseSingleItem(s));
            } catch (ItemParseException e){
                System.out.println("Invalid item found.");
                exceptionCount++;
            }
        }
        return parsedList;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        return new Item(replace0withO(findItemField(singleItem, namePattern)),
                new Double(findItemField(singleItem, pricePattern)),
                findItemField(singleItem, typePattern),
                findItemField(singleItem, expirationPattern));
    }

    public Matcher getMatcher(String singleItem, Pattern pattern){
        return pattern.matcher(singleItem);
    }

    public String findItemField(String singleItem, Pattern pattern) throws ItemParseException {
        Matcher matcher = getMatcher(singleItem, pattern);
        if(matcher.find()){
            return matcher.group(1).toLowerCase();
        }
        throw new ItemParseException();
    }

    public String replace0withO(String string){
        return Pattern.compile("0").matcher(string).replaceAll("o");
    }

    public int getExceptionCount() {
        return exceptionCount;
    }
}
