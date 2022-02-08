import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mapping {

    public static final int INITIAL_LOCATION = 95;

    /**
     * create a static LocationMap object
     */
    static LocationMap locationMap = new LocationMap();

    /**
     * create a vocabulary HashMap to store all directions a user can go
     */
    Map<String, String> vocabulary = new HashMap<>();

    /**
     * create a FileLogger object
     */
    FileLogger fileLogger = new FileLogger();

    /**
     * create a ConsoleLogger object
     */
    ConsoleLogger consoleLogger = new ConsoleLogger();

    public Mapping() {
        //vocabulary.put("QUIT", "Q"); //example
        /**
         * complete the vocabulary HashMap <Key, Value> with all directions.
         * use the directions.txt file and crosscheck with the ExpectedInput and ExpectedOutput files to find the keys and the values
         */
        vocabulary.put("QUIT", "Q");
        vocabulary.put("NORTH", "N");
        vocabulary.put("EAST", "E");
        vocabulary.put("SOUTH", "S");
        vocabulary.put("WEST", "W");
        vocabulary.put("NORTHEAST", "NE");
        vocabulary.put("NORTHWEST", "NW");
        vocabulary.put("SOUTHEAST", "SE");
        vocabulary.put("SOUTHWEST", "SW");
        vocabulary.put("UP", "U");
        vocabulary.put("DOWN", "D");
    }

    public void mapping() {

        /**
         * create a Scanner object
         */
        Scanner sc = new Scanner(System.in);

        /**
         * initialise a location variable with the INITIAL_LOCATION
         */
        int location = INITIAL_LOCATION;

        while (true) {

            /**
             * get the location and print its description to both console and file
             * use the FileLogger and ConsoleLogger objects
             */
            String description = locationMap.get(location).getDescription();
            fileLogger.log(description);
            consoleLogger.log(description);

            /**
             * verify if the location is exit
             */
            if (location == 0) {
                break;
            }

            /**
             * get a map of the exits for the location
             */
            Map<String, Integer> exitsMap = locationMap.get(location).getExits();

            /**
             * print the available exits (to both console and file)
             * crosscheck with the ExpectedOutput files
             * Hint: you can use a StringBuilder to append the exits
             */
            StringBuilder sb = new StringBuilder();
            sb.append("Available exits are ");
            for (String key : exitsMap.keySet()) {
                sb.append(key).append(", ");
            }
            fileLogger.log(sb.toString());
            consoleLogger.log(sb.toString());

            /**
             * input a direction
             * ensure that the input is converted to uppercase
             */
            String inputDirection = sc.nextLine().trim().toUpperCase();

            /**
             * available inputs are: a letter(the HashMap value), a word (the HashMap key), a string of words that contains the key
             * if the input contains multiple words, extract each word
             * find the direction to go to using the vocabulary mapping
             * if multiple viable directions are specified in the input, choose the last one
             */
            String lastViableDirection = "";
            boolean availableInput = false;

            if (vocabulary.containsValue(inputDirection) && exitsMap.containsKey(inputDirection)) {
                lastViableDirection = inputDirection;
                availableInput = true;
            } else if (exitsMap.containsKey(vocabulary.get(inputDirection))) {
                inputDirection = vocabulary.get(inputDirection);
                lastViableDirection = inputDirection;
                availableInput = true;
            } else if (inputDirection.contains(" ") || inputDirection.contains(",")) {
                inputDirection = inputDirection.replace(",", " ");
                String[] inputSentenceAsWord = inputDirection.split(" ");
                int numOfPossibleDirections = 1;
                for (String word : inputSentenceAsWord) {
                    if (exitsMap.containsKey(vocabulary.get(word))) {
                        if (exitsMap.size() > 1 && numOfPossibleDirections > 1) {
                            exitsMap.remove(lastViableDirection);
                        }
                        lastViableDirection = vocabulary.get(word);
                        availableInput = true;
                        numOfPossibleDirections++;
                    }
                }
            }

            /**
             * if user can go in that direction, then set the location to that direction
             * otherwise print an error message (to both console and file)
             * availableInput the ExpectedOutput files
             */
            if (availableInput) {
                location = exitsMap.get(lastViableDirection);
            } else {
                consoleLogger.log("You cannot go in that direction");
                fileLogger.log("You cannot go in that direction");
            }
        }
    }

    public static void main(String[] args) {
        /**
         * run the program from here
         * create a Mapping object
         * start the game
         */
        Mapping run = new Mapping();
        run.mapping();
    }

}
