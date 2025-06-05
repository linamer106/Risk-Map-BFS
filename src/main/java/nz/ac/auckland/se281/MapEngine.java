package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  // private Map<String, List<String>> adjacentCountriesMap;
  // private Map<String, List<String>> countryContinentAndFuelMap;

  private Map<String, Country> countriesMap = new HashMap<>();

  public MapEngine() {
    // add other code here if you wan
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    for (String line : countries) {
      String[] parts = line.split(",");
      String name = parts[0].trim();
      String continent = parts[1].trim();
      int fuelCost = Integer.parseInt(parts[2].trim());
      Country country = new Country(name, continent, fuelCost);
      countriesMap.put(name, country); // key: country name
    }

    for (String line : adjacencies) {
      String[] parts = line.split(",");
      String countryName = parts[0].trim();
      Country country = countriesMap.get(countryName);

      for (int i = 1; i < parts.length; i++) {
        String neighborName = parts[i].trim();
        Country neighbor = countriesMap.get(neighborName);
        if (neighbor != null && !country.getNeighbours().contains(neighbor)) {
          country.addNeighbor(neighbor);
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {}

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
