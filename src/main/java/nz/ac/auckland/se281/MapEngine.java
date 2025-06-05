package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

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

  private List<Country> shortestPathToDestination(
      Country sourceCountry, Country destinationCountry) {

    // BFS setup
    Queue<Country> queue = new LinkedList<>();
    Set<Country> visited = new HashSet<>();
    Map<Country, Country> parentMap = new HashMap<>();

    queue.add(sourceCountry);
    visited.add(sourceCountry);

    while (!queue.isEmpty()) {
      Country current = queue.poll();

      // Check if we've reached the destination
      if (current.equals(destinationCountry)) {
        // Reconstruct path
        List<Country> path = new ArrayList<>();
        Country step = destinationCountry;
        while (step != null) {
          path.add(0, step); // insert at front
          step = parentMap.get(step); // follow parent link
        }
        return path; // this will be the shortest path
      }

      // BFS neighbors
      for (Country neighbor : current.getNeighbours()) {
        if (!visited.contains(neighbor)) {
          queue.add(neighbor);
          visited.add(neighbor);
          parentMap.put(neighbor, current); // track how we reached this node
        }
      }
    }

    return null; // if no path found
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();

    Country sourceCountry = countryInputCheck();

    MessageCli.INSERT_DESTINATION.printMessage();

    Country destinationCountry = countryInputCheck();

    if (sourceCountry.equals(destinationCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      MessageCli.FUEL_INFO.printMessage(Integer.toString(0));
      return;
    }
    List<Country> shortestPath = shortestPathToDestination(sourceCountry, destinationCountry);

    // Format path
    StringBuilder formattedRoute = new StringBuilder("[");
    for (int i = 0; i < shortestPath.size(); i++) {
      formattedRoute.append(shortestPath.get(i).getName());
      if (i < shortestPath.size() - 1) {
        formattedRoute.append(", ");
      }
    }
    formattedRoute.append("]");

    int fuelCostTotal = 0;
    // Calculate total fuel cost
    for (int i = 1; i < shortestPath.size() - 1; i++) {
      fuelCostTotal += shortestPath.get(i).getFuelCost();
    }

    Set<String> continentsVisited = new LinkedHashSet<>();
    Map<String, Integer> fuelPerContinent = new LinkedHashMap<>();

    // Step 1: Collect continents in order of appearance, dont need a continent class right?
    for (Country country : shortestPath) {
      continentsVisited.add(country.getContinent());
    }

    // Step 2: Sum fuel cost for intermediate countries only
    for (int i = 1; i < shortestPath.size() - 1; i++) {
      Country intermediate = shortestPath.get(i);
      String continent = intermediate.getContinent();
      int fuelCost = intermediate.getFuelCost();

      fuelPerContinent.put(continent, fuelPerContinent.getOrDefault(continent, 0) + fuelCost);
    }

    // Format continents
    StringBuilder formattedContinents = new StringBuilder("[");
    for (String continent : continentsVisited) {
      int fuel = fuelPerContinent.getOrDefault(continent, 0);
      formattedContinents.append(continent).append(" (").append(fuel).append("), ");
    }
    // Remove trailing comma and space
    if (formattedContinents.length() > 1) {
      formattedContinents.setLength(formattedContinents.length() - 2);
    }
    formattedContinents.append("]");

    // Final message
    MessageCli.ROUTE_INFO.printMessage(formattedRoute.toString());
    MessageCli.FUEL_INFO.printMessage(Integer.toString(fuelCostTotal));
    MessageCli.CONTINENT_INFO.printMessage(formattedContinents.toString());
  }
}
