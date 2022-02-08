import java.io.*;
import java.util.*;

//class that behaves like a map
public class LocationMap implements Map<Integer, Location> {

    private static final String LOCATIONS_FILE_NAME = "locations.txt";
    private static final String DIRECTIONS_FILE_NAME = "directions.txt";

    /**
     * create a static locations HashMap
     */
    static HashMap<Integer, Location> locations = new HashMap<>();

    static {
        /**
         * create a FileLogger object
         */
        FileLogger fileLogger = new FileLogger();

        /**
         * create a ConsoleLogger object
         */
        ConsoleLogger consoleLogger = new ConsoleLogger();

        /**
         * Read from LOCATIONS_FILE_NAME so that a user can navigate from one location to another
         * use try-with-resources/catch block for the FileReader
         * extract the location and the description on each line
         * print all locations and descriptions to both console and file
         * check the ExpectedOutput files
         * put the location and a new Location object in the locations HashMap, using temporary empty hashmaps for exits
         */
        try (BufferedReader readLocationsFile = new BufferedReader(new FileReader(LOCATIONS_FILE_NAME))) {
            int location = 0;
            String description = "";
            String locationsLine;
            HashMap<String, Integer> tempLocations = new HashMap<>();
            String keyLocation = "";
            String valueDescription = "";

            String instructions = "Available locations:";
            consoleLogger.log(instructions);
            fileLogger.log(instructions);

            while ((locationsLine = readLocationsFile.readLine()) != null) {
                for (int x = 0; x < locationsLine.length(); x++) {
                    if (locationsLine.charAt(x) == ',') {
                        location = Integer.parseInt(locationsLine.substring(0, x));
                        description = locationsLine.substring(x + 1);
                        break;
                    }
                }
                locations.put(location, new Location(location, description, tempLocations));

                for (int locationIndex : locations.keySet()) {
                    keyLocation = Integer.toString(locationIndex);
                }
                for (Location locationValue : locations.values()) {
                    valueDescription = locationValue.getDescription();
                }
                consoleLogger.log(keyLocation + ": " + valueDescription);
                fileLogger.log(keyLocation + ": " + valueDescription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Read from DIRECTIONS_FILE_NAME so that a user can move from A to B, i.e. current location to next location
         * use try-with-resources/catch block for the FileReader
         * extract the 3 elements  on each line: location, direction, destination
         * print all locations, directions and destinations to both console and file
         * check the ExpectedOutput files
         * add the exits for each location
         */
        try (BufferedReader readDirectionsFile = new BufferedReader(new FileReader(DIRECTIONS_FILE_NAME))) {

            int location;
            String direction;
            int destination;
            String lines;
            String[] takeaway;
            int locationAsKey = 0;
            String directionAsValue = "";
            int destinationAsValue = 0;
            Map<String, Integer> tempExits = new HashMap<>();

            String instruction = "Available directions:";
            consoleLogger.log(instruction);
            fileLogger.log(instruction);

            while ((lines = readDirectionsFile.readLine()) != null) {
                takeaway = lines.split(",");
                location = Integer.parseInt(takeaway[0]);
                direction = takeaway[1];
                destination = Integer.parseInt(takeaway[2]);

                tempExits.clear();
                tempExits.put(direction, destination);

                new Location(locations.get(location).getLocationId(), locations.get(location).getDescription(), tempExits);
                locations.get(location).addExit(direction, destination);

                for (int x = 0; x < locations.get(location).getExits().size(); x++) {
                    locationAsKey = locations.get(location).getLocationId();
                    for (Entry<String, Integer> exit : tempExits.entrySet()) {
                        directionAsValue = exit.getKey();
                        destinationAsValue = exit.getValue();
                    }
                }
                consoleLogger.log(locationAsKey + ": " + directionAsValue + ": " + destinationAsValue);
                fileLogger.log(locationAsKey + ": " + directionAsValue + ": " + destinationAsValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * implement all methods for Map
     *
     * @return
     */
    @Override
    public int size() {

        return locations.size();
    }

    @Override
    public boolean isEmpty() {

        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {

        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {

        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {

        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {

        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

        locations.putAll(m);
    }

    @Override
    public void clear() {

        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {

        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {

        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {

        return locations.entrySet();
    }
}
