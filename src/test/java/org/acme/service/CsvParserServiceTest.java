package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@QuarkusTest
public class CsvParserServiceTest {

    @Inject
    CsvParserService csvParserService;

    @Test
    public void testCsvParsing() throws URISyntaxException, IOException {
        Map<Integer, List<String>> parseResult = csvParserService.parseCSV();
        Assertions.assertNotNull(parseResult);
        Assertions.assertEquals(List.of("DE", "DE-BW", "Baden-Württemberg", "Freiburg", "Breisgau-Hochschwarzwald", "Gundelfingen", "79194", "Gundelfingen", "", "", "48.04271", "7.86635", "Europe/Berlin", "UTC+1", "true", "A"), parseResult.get(79194));
        Assertions.assertEquals(8231, parseResult.size());
    }
}
