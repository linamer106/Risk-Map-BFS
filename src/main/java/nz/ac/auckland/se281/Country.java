package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Country {
  private String continent;
  private int fuelCost;

  private String name;

  public Country(String name, String continent, int fuelCost) {
    this.name = Utils.capitalizeFirstLetterOfEachWord(name);
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  public Map<Country, List<Country>> addNeighbor(
      Country neighbour, Map<Country, List<Country>> neighboursHashMap) {
    neighboursHashMap.putIfAbsent(this, new ArrayList<>());
    List<Country> currentNeighbors = neighboursHashMap.get(this);

    if (!currentNeighbors.contains(neighbour)) {
      currentNeighbors.add(neighbour);
    }
    return neighboursHashMap;
  }

  public String getContinent() {
    return continent;
  }

  public int getFuelCost() {
    return fuelCost;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  // Getters, equals(), hashCode(), toString() ...

}
