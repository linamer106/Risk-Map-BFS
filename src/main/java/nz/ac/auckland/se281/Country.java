package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Country {
  private String continent;
  private int fuelCost;
  private List<Country> neighbours;
  private String name;

  public Country(String name, String continent, int fuelCost) {
    this.name = getCountryNameCapsFirstLetter(name);
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.neighbours = new ArrayList<>();
  }

  private String getCountryNameCapsFirstLetter(String name) {
    // code for taking country name and making the first letters of it caps
    if (name == null || name.isEmpty()) {
      return name;
    }
    // need to split string by space and then capitalize the first letter of each word and then
    // return together with a single space inbewteen
    String[] words = name.split(" ");
    StringBuilder capitalizedName = new StringBuilder();
    for (String word : words) {
      if (!word.isEmpty()) {
        capitalizedName
            .append(Character.toUpperCase(word.charAt(0)))
            .append(word.substring(1).toLowerCase())
            .append(" ");
      }
    }
    return capitalizedName.toString().trim();
  }

  public void addNeighbor(Country neighbour) {
    neighbours.add(neighbour);
  }

  public List<Country> getNeighbours() {
    // return a copy of the neighbours list to avoid external modification??
    return neighbours;
  }

  // Getters, equals(), hashCode(), toString() ...
}
