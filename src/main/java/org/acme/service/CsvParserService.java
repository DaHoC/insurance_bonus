package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class CsvParserService {

    @ConfigProperty(name = "csv.location", defaultValue = "postcodes.csv")
    String configFileLocation;

    private static final String DELIMITER = ",";

    private static final Integer CSV_POSITION_FOR_ZIPCODE = 6;

    /**
     * Parse the internalized CSV file containing postcodes into a Map that has the zipcode as Integer key.
     *
     * @return Map containing key=zipcode to list of associated values
     * @throws URISyntaxException uri syntax exception
     * @throws IOException        io exception
     */
    @Named("regionData")
    public Map<Integer, List<String>> parseCSV() throws URISyntaxException, IOException {
        Path filePath = Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource(configFileLocation)).toURI());
        // We have a fixed schema, no need to dynamically build keys
        return Files.readAllLines(filePath)
                .stream()
                .skip(1) // First line is the header
                .map(line -> line.split(DELIMITER))
                .map(CsvParserService::removeQuotes)
                .collect(Collectors.toMap(
                        arr -> Integer.parseInt(arr[CSV_POSITION_FOR_ZIPCODE]),
                        Arrays::asList,
                        (existing, replacement) -> existing  // NOTE that there are duplicate values, i.e. 79194
                ));
    }

    private static String[] removeQuotes(String[] stringArr) {
        return Arrays.stream(stringArr)
                .map(string -> string.trim().replaceAll("^\"|\"$", "").trim())
                .toArray(String[]::new);
    }

}
