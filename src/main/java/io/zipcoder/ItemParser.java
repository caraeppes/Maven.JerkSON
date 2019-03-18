package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser{

    private int exceptionCount = 0;

    public List<Item> parseItemList(String valueToParse) {
        String[] itemArray = valueToParse.split("##");
        List<Item> parsedList = new ArrayList<>();
        for (String s : itemArray) {
            try {
                System.out.println(s);
                Item parsedItem = parseSingleItem(s);
                parsedList.add(parsedItem);
            } catch (ItemParseException e){
                System.out.println("Invalid item found.");
                exceptionCount++;
            }
        }
        return parsedList;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        String name = findName(singleItem);
        Double price = findPrice(singleItem);
        String type = findType(singleItem);
        String expiration = findExpiration(singleItem);
        return new Item(name, price, type, expiration);

    }

    public String findName(String singleItem) throws ItemParseException {
        String name = "";
        Pattern namePattern = Pattern.compile("[N|n][A|a][M|m][E|e][@|^|*|:|%]([a-zA-Z0]+);");
        Matcher nameMatcher = namePattern.matcher(singleItem);
        if (nameMatcher.find()) {
            name = nameMatcher.group(1).toLowerCase();
            Pattern zeroPattern = Pattern.compile("0");
            Matcher zeroMatcher = zeroPattern.matcher(name);
            name = zeroMatcher.replaceAll("o");
            return name;
        }
        throw new ItemParseException();

    }

    public Double findPrice(String singleItem) throws ItemParseException {
        Double price;
        Pattern pricePattern = Pattern.compile("[P|p][R|r][I|i][C|c][E|e][@|^|*|:|%](\\d+\\.\\d+);");
        Matcher priceMatcher = pricePattern.matcher(singleItem);
        if (priceMatcher.find()) {
            price = new Double(priceMatcher.group(1));
            return price;
        }
        throw new ItemParseException();
    }

    public String findType(String singleItem) throws ItemParseException {
        String type = "";
        Pattern typePattern = Pattern.compile("type[@|^|*|:|%]([A-Za-z]+)[;|/^|/!|%|*|@]");
        Matcher typeMatcher = typePattern.matcher(singleItem);
        if (typeMatcher.find()) {
            type = typeMatcher.group(1).toLowerCase();
            return type;
        }
        throw new ItemParseException();
    }

    public String findExpiration(String singleItem) throws ItemParseException {
        String expiration = "";
        Pattern expirationPattern = Pattern.compile("expiration[@|\\^|\\*|:|%](\\d+\\/\\d+\\/\\d{4})");
        Matcher expirationMatcher = expirationPattern.matcher(singleItem);
        if (expirationMatcher.find()) {
            expiration = expirationMatcher.group(1);
            return expiration;
        }
        throw new ItemParseException();
    }

    public int getExceptionCount() {
        return exceptionCount;
    }
}
