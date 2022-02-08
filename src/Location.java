import java.util.LinkedHashMap;
import java.util.Map;

public class Location {

    /**
     * declare private final locationId, description, exits
     */
    private final int LOCATIONID;
    private final String DESCRIPTION;
    private final LinkedHashMap<String, Integer> EXITS = new LinkedHashMap<>();


    public Location(int locationId, String description, Map<String, Integer> exits) {
        /**
         * set the locationId and the description
         */
        LOCATIONID = locationId;
        DESCRIPTION = description;

        /**
         * if exits are not null, set the exit
         * Exits should be of type LinkedHashMap to maintain the insertion order
         * otherwise, set the exits LinkedHashMap to (Q,0)
         */
        if (!exits.isEmpty()) {
            EXITS.putAll(exits);
        } else {
            EXITS.put("Q", 0);
        }
    }

    protected void addExit(String direction, int location) {
        /**
         * put the direction and the location in the exits LinkedHashMap
         */
        EXITS.put(direction, location);
    }

    public int getLocationId() {
        /**
         * complete getter to return the location id
         */
        return LOCATIONID;
    }

    public String getDescription() {
        /**
         * complete getter to return the description
         */
        return DESCRIPTION;
    }

    public Map<String, Integer> getExits() {
        /**
         * complete getter to return a copy of exits
         * (preventing modification of exits from outside the Location instance)
         */
        return new LinkedHashMap<>(EXITS);
    }
}
