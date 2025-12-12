[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/DWIyYUvx)
# USpace

Le projet **USpace** vous servira de base pour vos travaux pratiques de la session.
Vous trouverez les instructions des différents travaux pratiques dans les répertoires `tpX` (ex: `tp1`, `tp2`, etc.).

## Prérequis

* Java JDK (au minimum la version 18)
* [Maven 3](https://maven.apache.org/download.cgi)
* Node.js (au minimum la version 18.3) et npm (au minimum la version 10) pour l'interface utilisateur du TP4 (le téléchargement de Node.js inclut npm)

## Démarrer le projet

### Démarrer le serveur backend
Démarrer le serveur backend en exécutant `UspaceMain`.

Vous pouvez aussi démarrer le serveur via maven:

```bash
mvn clean install
mvn exec:java
```

Le serveur sera accessible à l'adresse suivante: [http://localhost:8181](http://localhost:8181).
Vous pouvez valider que le serveur est démarré avec [http://localhost:8181/health](http://localhost:8181/health).

### Démarrer le serveur frontend

Pour démarrer le serveur frontend, exécutez les commandes suivantes:

1. Déplacez-vous dans le dossier `webapp`:
```bash
cd webapp
```

2. Installez les dépendances:
```bash
npm install
```

3. Démarrez le serveur:
```bash
npm run dev
```

L'application web sera accessible à l'adresse suivante: [http://localhost:5173](http://localhost:5173)

## Rouler les tests

Pour rouler tous les tests du backend, exécuter la commande suivante:

```bash
mvn test
```

Pour rouler tous les tests unitaires de l'interface, exécuter la commande suivante:
```bash
npm run test:unit
```

Pour rouler les tests bout en bout avec Cypress, exécuter la commande suivante:

Avec Windows:
```bash
./run-e2e.sh
```
Ou avec Linux:
```bash
source run-e2e.sh
```

Ce script démarre le serveur backend avec les bases de données en mémoire, puis démarre le serveur frontend et finalement exécute les tests Cypress.

Une fenêtre de navigateur s'ouvrira pour exécuter les tests.


## Fonctionnalités

Certaines fonctionnalités sont déjà implémentées dans le projet.

### 1. Réserver une croisière

Pour l'instant, seule la croisière "JUPITER_MOONS_EXPLORATION_2085" est disponible:
* Date et heure de départ: 2085-01-25, 12:00

**Conditions:**
* La réservation d'une croisière est possible selon la disponibilité des cabines.
* La réservation d'une croisière est possible si la date de réservation est avant la date de départ de la croisière.
* La réservation d'une croisière est possible avec au moins un voyageur.

POST cruises/{cruiseId}/bookings (cruises/JUPITER_MOONS_EXPLORATION_2085/bookings)

```
{
    "travelers": [
        {
            "name": "John Doe"::string,
            "category": "child"::string(CHILD | ADULT | SENIOR)
        },
        {
            "name": "Jane Doe"::string,
            "category": "adult"::string(CHILD | ADULT | SENIOR)
        }
    ],
    "cabinType": "Standard"::string(STANDARD | DELUXE | SUITE),
    "bookingDateTime": "2084-04-08T12:30"::string(dateTime, format ISO)
}
```

Réponses:

HTTP 201 CREATED

Headers: `Location: cruises/<cruiseId>/bookings/<bookingId>`

Si un des champs est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```

Si la bookingDate n'est pas dans le bon format:

HTTP 400 BAD REQUEST

```
{
"error": "INVALID_DATE_FORMAT",
"description": "Invalid date format"
}
```

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "CRUISE_NOT_FOUND",
    "description": "Cruise not found"
}
```

Si la date de réservation n'est pas avant le départ de la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Invalid booking date."
}
```

Si le type de cabine n'est pas valide (STANDARD, DELUXE, SUITE):

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Invalid cabin type."
}
```

Si la catégorie d'un voyageur n'est pas valide (CHILD, ADULT, SENIOR):

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Invalid traveler category."
}
```

Si la réservation ne contient aucun voyageur:

HTTP 400 BAD REQUEST

```
{
    "error": "NO_TRAVELER",
    "description": "No traveler to book."
}
```

### 2. Consulter une réservation

GET cruises/{cruiseId}/bookings/{bookingId} (cruises/JUPITER_MOONS_EXPLORATION_2085/bookings/1)

Réponses:

HTTP 200 OK

```
{
    "cruiseId": "JUPITER_MOONS_EXPLORATION_2085"::string,
    "bookingId": "a6d1a050-e4e2-451a-a315-45e2d551ed33"::string,
    "travelers": [
        {
            "id": ""395e7715-74fc-468a-9795-1adc79e6148a"::string,
            "name": "John Doe"::string,
            "category": "CHILD"::string(CHILD | ADULT | SENIOR),
            "badges": ["ZERO_G"]::list(string(ZERO_G | MINI_ZERO_G | STILL_GOT_IT)) | []
        },
        {
            "id": "093f9a8d-f7c6-4d3d-b878-90452922e768"::string,
            "name": "Jane Doe"::string,
            "category": "ADULT"::string(CHILD | ADULT | SENIOR),
            "badges": ["ZERO_G"]::list(string(ZERO_G | MINI_ZERO_G | STILL_GOT_IT)) | []
        }
    ],
    "cabinType": "STANDARD"::string(STANDARD | DELUXE | SUITE),
    "bookingDateTime": "2084-04-08T12:30"::string(dateTime, format ISO)
}
```

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "CRUISE_NOT_FOUND",
    "description": "Cruise not found"
}
```

Si la réservation n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "BOOKING_NOT_FOUND",
    "description": "Booking not found"
}
```

### 3. Consulter les détails d'une croisière

GET cruises/{cruiseId} (cruises/JUPITER_MOONS_EXPLORATION_2085)

Réponses:

HTTP 200 OK

```
{
    "id": "JUPITER_MOONS_EXPLORATION_2085"::string,
    "departureDateTime": "2085-01-25T12:00"::string(dateTime, format ISO),
    "endDateTime": "2085-02-01T12:00"::string(dateTime, format ISO),
    "planets": [
        {
            "name": "Jupiter"::string,
            "arrivalDateTime": "2085-01-26T12:00"::string(dateTime, format ISO),
            "departureDateTime": "2085-01-30T12:00"::string(dateTime, format ISO),
        },
        ...
    ],
    "crewMembersIds": [
        "ABB328"::string,
        "ABC128"::string,
        ...
    ]
}
```

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "CRUISE_NOT_FOUND",
    "description": "Cruise not found"
}
```