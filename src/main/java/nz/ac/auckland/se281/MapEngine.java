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

  private Country getCountryInfo(String name) throws InvalidCountryNameException {
    String capitalizedName = getCountryNameCapsFirstLetter(name);
    Country country = countriesMap.get(capitalizedName);
    if (country == null) {
      throw new InvalidCountryNameException(capitalizedName);
    }
    return country;
  }

  private Country countryInputCheck() {
    while (true) {
      String input = Utils.scanner.nextLine().trim();
      try {
        return getCountryInfo(input);
      } catch (InvalidCountryNameException e) {
        MessageCli.INVALID_COUNTRY.printMessage(e.getMessage());
      }
    }
  }

  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage(); // Show prompt once at start

    Country country = countryInputCheck();

    // // Handle empty input
    // if (countryName.isEmpty()) {
    //   MessageCli.INVALID_COUNTRY.printMessage(countryName);
    //   continue;
    // }

    // if (country != null) {//why no need anymore?

    List<Country> neighbours = country.getNeighbours();
    StringBuilder names = new StringBuilder("[");
    for (int i = 0; i < neighbours.size(); i++) {
      names.append(neighbours.get(i).getName());
      if (i < neighbours.size() - 1) {
        names.append(", ");
      }
    }
    names.append("]");

    MessageCli.COUNTRY_INFO.printMessage(
        country.getName(),
        country.getContinent(),
        String.valueOf(country.getFuelCost()),
        names.toString()); // Exit loop after successful output
    // }
  }

  public String getCountryNameCapsFirstLetter(String name) {
    // code for taking country name and making the first letters of it caps
    if (name == null || name.isEmpty()) {
      return null;
    }
    // need to split string by space and then capitalize the first letter of each word and then
    // return together with a single space inbewteen
    String[] words = name.split(" ");
    StringBuilder capitalizedName = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        capitalizedName
            .append(Character.toUpperCase(word.charAt(0)))
            .append(word.substring(1))
            .append(" ");
      }
    }
    return capitalizedName.toString().trim();
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();

    String sourceName = countryInputCheck().getName(); // do i want it in string or country?

    MessageCli.INSERT_DESTINATION.printMessage();

    String destinationName = countryInputCheck().getName(); // same Q here...

    if (sourceName.equals(destinationName)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
  }
}
