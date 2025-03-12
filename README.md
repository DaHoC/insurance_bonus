# Service zur Berechnung einer Versicherungsprämie

## Run instructions

Run in CLI:

`./mvnw clean install quarkus:dev`

Wait for the service to be up, then open 

* GET http://localhost:8080/insurancebonus for the user-facing UI
* POST http://localhost:8080/insurancebonus (`application/x-www-form-urlencoded`) for the user submission
* POST http://localhost:8080/insurancebonus (`application/JSON`) for the M2M communication

## Run tests

./mvnw test


## Improvements

For a full-blown software, I would improve:

* Add bean validators for input sanitizing
* Proper exception handling
* Add dedicated repository classes, change accessors of entity
* Dedicated library for CSV parsing
* Persist lookup-tables in the database instead of CSV
* Monitoring & Reporting
* Security (i.e. IdP like Keycloak, mTLS for M2M)
* Add lombok and mapstruct for convenience (getter/setter/equals/ctors, properties mapping, etc.)


## Considerations

- Einträge werden in einer Datenbank gespeichert. Welche würdest du nutzen? Begründe deine Entscheidung.
> Standard SQL-Datenbank (Postgres, MariaDB, Sqlite) wurde benutzt, da keine spezifischen Anforderungen. NoSQL nicht ganz passend, da struktuierte Daten.
- Erstelle die notwendingen Services, mindestens zwei! Entscheide, wo die Services anhand der fachlichen Domäne und Anforderungen aufgeteilt werden.
> Ich habe das CSV-Parsing in einen Service gepackt (normalerweise wäre das eher eine Utility), und die fachliche Berechnung in den anderen Service
- Verwende ein Test-Framework und erläutere dein Konzept zur Wahrung der Softwarequalität.
> Junit 5 + Restassured wurde benutzt, um eine exemplarische Testabdeckung von Unit- und Integrationstests aufzuzeigen. Idealerweise decken die Tests einen großteil der Funktionalität ab, und die UI sollte auch durch E2E-Tests abgedeckt werden.
- Wie erfolgt die Kommunikation zwischen den Services?
> Die M2M Kommunikation erfolgt mittels JSON-Format (ohne Authentifizierung, ohne Circuit-Braker, ohne SLAs, ...)
- Erstelle sowohl Code als auch Dokumentation.
> Der Java 21 code benutzt das Quarkus-Framework mit Maven als Build-Tool, Docker zum Starten einer Postgres Datenbank, Dokumentation ist als Markdown in dieser Readme.md als auch als JavaDoc im SourceCode
- (Optional) erstelle eine Web-basierte Oberfläche.
> Die Oberfläche ist aufgrund SSR inbegriffen

## Aufgabenstellung:

Eine Versicherung berechnet die Versicherungsprämie auf Basis von:

- Jährliche Kilometerleistung
- Fahrzeugtyp
- Region der Fahrzeugzulassung

Für die regionale Zuordnung wird eine CSV "postcodes.csv" verwendet. Die wichtigsten Felder in der CSV sind:

- REGION1 Bundesland
- REGION3 Land
- REGION4 Stadt/Ort
- POSTLEITZAHL Postleitzahl
- LOCATION Bezirk

Interessenten sollen eine Anwendung zur Berechnung der Versicherungsprämie nutzen. Nutzereingaben und die berechnete Prämie sollen persistiert werden.
Der Antragsteller soll die geschätzte Kilometerleistung, Postleitzahl der Zulassungsstelle und den Fahrzeugtyp eingeben.

Zur Berechnung der Prämie wird folgende Formel verwendet:

    Kilometerleistung-Faktor * Fahrzeugtyp-Faktor * Region-Faktor

Der Faktor für die Kilometerleistung ist wie folgt festgelegt:

- 0 bis 5.000 km: 0.5
- 5.001 km bis 10.000 km: 1.0
- 10.001 km bis 20.000 km: 1.5
- ab 20.000km: 2.0

Der Faktor für die Region kann anhand des Bundeslandes gewählt werden. Der Faktor für die Fahrzeugtyp kann frei definiert werden.

Neben der Anwendung für Antragsteller soll eine Integration von Drittanbietern ermöglicht werden.
Dazu soll eine HTTP-API zur Berechnung der Prämie angeboten werden.

## Deine Aufgabe

Erstelle eine Anwendung mit folgenden Anforderungen:

- Einträge werden in einer Datenbank gespeichert. Welche würdest du nutzen? Begründe deine Entscheidung.
- Erstelle die notwendingen Services, mindestens zwei! Entscheide, wo die Services anhand der fachlichen Domäne und Anforderungen aufgeteilt werden.
- Verwende ein Test-Framework und erläutere dein Konzept zur Wahrung der Softwarequalität.
- Wie erfolgt die Kommunikation zwischen den Services?
- Erstelle sowohl Code als auch Dokumentation.
- (Optional) erstelle eine Web-basierte Oberfläche.

Wir legen Wert auf Einfachheit, Testbarkeit und Wartbarkeit.

## Ablauf/Evaluation

Wir setzen keine zeitlichen Einschränkungen. Die Umsetzung soll vielmehr als Gesprächsgrundlage dienen und du musst in der Lage sein, deine Entscheidungen zu erläutern.
Es gibt kein Richtig oder Falsch.

Die Anwendung muss bei uns lokal lauffähig sein.

Abgabe ist entweder per .zip-Datei oder per GitHub-Link möglich.

## Viel Erfolg!
