package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Country {
  private String continent;
  private int fuelCost;
  private List<Country> neighbours;
  private String name;

  public Country(String name, String continent, int fuelCost) {
    this.name = Utils.capitalizeFirstLetterOfEachWord(name);
    this.continent = continent;
    this.fuelCost = fuelCost;
    this.neighbours = new ArrayList<>();
  }

  public void addNeighbor(Country neighbour) {
    neighbours.add(neighbour);
  }

  public List<Country> getNeighbours() {
    // return a copy of the neighbours list to avoid external modification??
    return neighbours;
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
