package no.hiof.stianmag.javalin.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.hiof.stianmag.javalin.model.Planet;
import no.hiof.stianmag.javalin.model.PlanetSystem;
import no.hiof.stianmag.javalin.model.Star;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniverseJSONRepository implements IUniverseRepository {
    private String fileName;
    public ArrayList<PlanetSystem> planetSystems = new ArrayList<>();

    public UniverseJSONRepository(String fileName) {
        this.fileName = fileName;
    }

    public UniverseJSONRepository() {
        ObjectMapper objectMapper = new ObjectMapper();

        Star sun = new Star("Sun",1.9885E30,695342,5777);

        ArrayList<Planet> planets = new ArrayList<>();

        planets.add(new Planet("Mercury", 3.283E23,2439.7,
                0.387, 0.206, 88, sun,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/" +
                        "Mercury_in_color_-_Prockter07-edit1.jpg/330px-Mercury_in_color_-_Prockter07-edit1.jpg"));
        planets.add(new Planet("Venus",4.867E24,6051.8, 0.723, 0.007,
                225, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/" +
                "e/e5/Venus-real_color.jpg/375px-Venus-real_color.jpg"));
        planets.add(new Planet("Earth",5.972E24,6371, 1, 0.017,
                365, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/" +
                "NASA_Earth_America_2002.jpg/480px-NASA_Earth_America_2002.jpg"));
        planets.add(new Planet("Mars",6.39E23,3389.5, 1.524, 0.093,
                687, sun, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56" +
                "/Mars_Valles_Marineris.jpeg/375px-Mars_Valles_Marineris.jpeg"));
        planets.add(new Planet("Jupiter",1.898E27,69911, 5.20440, 0.049,
                4380, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/" +
                "Jupiter_and_its_shrunken_Great_Red_Spot.jpg/480px-Jupiter_and_its_shrunken_Great_Red_Spot.jpg"));
        planets.add(new Planet("Saturn",5.683E26,58232, 9.5826, 0.057,
                10585, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/1/1c/" +
                "Saturn_from_Cassini_Orbiter_-_Square_%282004-10-06%29.jpg/" +
                "480px-Saturn_from_Cassini_Orbiter_-_Square_%282004-10-06%29.jpg"));
        planets.add(new Planet("Uranus",8.681E25,25362, 19.2184, 0.046,
                30660, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/" +
                "3/3d/Uranus2.jpg/480px-Uranus2.jpg"));
        planets.add(new Planet("Neptune",1.024E26,24622, 30.11, 0.010,
                60225, sun,"https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/" +
                "Neptune_Full_%28cropped%29.jpg/480px-Neptune_Full_%28cropped%29.jpg"));

        PlanetSystem solarSystem = new PlanetSystem("Solar System", sun, planets,
                "https://upload.wikimedia.org/wikipedia/commons/c/c3/Solar_sys8.jpg");

        planetSystems.add(solarSystem);
        System.out.println(planetSystems);

        writeToJson("solarsystem.json", planetSystems);

        List<PlanetSystem> newList = readFromJson("solarsystem.json");
        System.out.println("*****READ FROM FILE*****");
        System.out.println(newList);
    }

    public static void writeToJson(String fileName, ArrayList<PlanetSystem> planetSystems) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), planetSystems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<PlanetSystem> readFromJson(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<PlanetSystem> planetSystemsFromFile = new ArrayList<>();

        try {
            PlanetSystem[] planetSystemsArray = objectMapper.readValue(new File(fileName), PlanetSystem[].class);

            planetSystemsFromFile.addAll(Arrays.asList(planetSystemsArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return planetSystemsFromFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ArrayList<PlanetSystem> getAllPlanetSystems() {
        return planetSystems;
    }

    @Override
    public PlanetSystem getSpecificPlanetSystem(String planetSystemName) {
        for (PlanetSystem planetsystem : planetSystems) {
            if (planetsystem.getName().equals(planetSystemName))
                return planetsystem;
        }
        return null;
    }

    @Override
    public ArrayList<Planet> getAllPlanetsInPlanetSystem(String planetSystemName) {
        PlanetSystem thePlanetSystem = getSpecificPlanetSystem(planetSystemName);

        if (thePlanetSystem != null)
            return thePlanetSystem.getPlanetsInPlanetSystem();

        return new ArrayList<>();
    }

    @Override
    public Planet getSpecificPlanetInPlanetSystem(String planetSystemName, String planetName) {
        PlanetSystem thePlanetSystem = getSpecificPlanetSystem(planetSystemName);

        if (thePlanetSystem != null) {
            for (Planet aPlanet : thePlanetSystem.getPlanetsInPlanetSystem()) {
                if (aPlanet.getName().equals(planetName))
                    return aPlanet;
            }
        }
        return null;
    }
}
