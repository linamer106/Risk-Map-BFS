# Risk-Map-BFS

A Java project simulating route planning across the Risk board game map using graph algorithms and object-oriented programming principles.

---

## Overview

This project uses a simplified version of the classic board game Risk map, featuring **42 countries across 6 continents**: Europe, North America, South America, Africa, Asia, and Australia. The map represents countries as **nodes in a graph**, with edges connecting neighboring countries. Each country also has a **fuel cost** associated with crossing it.

The goal is **not to play Risk**, but to implement a system that:

- Finds the **shortest path** between two countries (in terms of number of countries crossed).  
- Calculates **total fuel cost** for intermediate countries along that path.  
- Tracks the **continents visited** and identifies the continent with the **highest fuel consumption**.  

This simulates a real-world scenario for a company planning international deliveries using **graph-based route optimization**.

---

## Features

- **INFO-COUNTRY command**: Displays the continent, fuel cost, and neighboring countries for any valid country.  
- **ROUTE command**: Computes the fastest route between two countries, showing:
  - Ordered path of countries  
  - Total fuel consumption (**excluding source and destination**)  
  - List of continents visited, with fuel spent in each  
  - Continent where the **most fuel is consumed**  
- **Input validation**: Handles invalid country names via custom exceptions and re-prompts users until valid input is provided.  
- **Object-Oriented Design**: Fully uses Java OOP principles, including:
  - Graph representation with `HashMap` / `LinkedHashMap`  
  - `HashSet` / `LinkedHashSet`, `LinkedList` / `ArrayList`, and `Queue`  
  - At least **two custom classes** and meaningful data structures  
- **Test coverage**: Includes automated test cases for both commands using Maven.

---

## CLI Commands

- `INFO-COUNTRY` → Get info about a country  
- `ROUTE` → Find the fastest route between two countries  
- `HELP` → Show all available commands  
- `EXIT` → Close the program  

> Commands are **case-insensitive**, and underscores/hyphens are interchangeable.

---

## Example Usage

```text
281-map> info-country
Insert the name of the country:
Japan
Japan => continent: Asia, fuel cost: 5, neighbours: [Kamchatka, Mongolia]

281-map> route
Insert the name of the country where you start the journey:
Japan
Insert the name of the country of destination:
Alberta
The fastest route is: [Japan, Kamchatka, Alaska, Alberta]
You will spend this amount of fuel for your journey: 7 units
You will visit the following continents: [Asia (6), North America (1)]
The continent where you will spend the most fuel is: Asia (6)
