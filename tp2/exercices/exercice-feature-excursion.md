# Nouvelle fonctionnalité: Ajouter une excursion sur une planète

Vous devez implémenter cette nouvelle fonctionnalité dans l'application USpace.

1. Implémentez tous les comportements de la fonctionnalité.
2. Assurez-vous de respecter les Principes SOLID+T et les bonnes pratiques de programmation.
3. Implémentez les tests unitaires pour cette fonctionnalité. Nous vous recommandons le TDD.

## Questions à répondre

Répondez aux questions dans l'espace prévu dans le [fichier de questions](../questions/questions-feature-excursion.md).

## Détails de la fonctionnalité

Il est possible d'ajouter une excursion sur une planète à une croisière.

Une excursion est composée d'un nom d'excursion, d'un nom de planète, d'une description, d'une date de début, d'une durée en heures et d'un nombre de places disponibles.


### Conditions

* Le nom de la planète doit être dans l'itinéraire de la croisière.
* Le nom de l'excursion doit être unique dans la croisière.
* Le début de l'excursion doit être entre l'arrivée et le départ de la planète.
* Il ne faut pas que l'excursion finisse après le départ de la planète.
  ```
    Exemple avec Début: 2085-02-01 et Durée: 2 jours
  
    => Date de fin de l'excursion: 2085-02-03, mais la Date de départ de la planète: 2022-12-02
    Donc, l'excursion ne peut pas être ajoutée.
  ```
* Le nombre de places disponibles doit être supérieur à 0.
* La durée de l'excursion doit être supérieure à 0.

### Route API à ajouter

POST /cruises/{cruiseId}/planets/{planetName}/excursions

```
{
    "name": "Excursion sur Mars"::string,
    "description": "Découvrez la planète rouge!"::string,
    "startDateTime": "2085-02-01T12:00"::string(date, format ISO),
    "duration": 2::int, // en heures
    "capacity": 10::int
}
```

Réponses:

HTTP 201 CREATED

Si un des champs est manquant ou vide:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```

(`<parameterName>` est le nom du champ manquant: name, description, startDate, duration ou capacity)

Si duration est inférieur ou égal à 0:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Excursion duration must be greater than 0."
}
```

Si capacity est inférieur ou égal à 0:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PARAMETER",
    "description": "Excursion capacity must be greater than 0."
}
```

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "CRUISE_NOT_FOUND",
    "description": "Cruise not found."
}
```

Si le nom de la planète n'est pas dans l'itinéraire de la croisière:

HTTP 404 BAD REQUEST

```
{
    "error": "PLANET_NOT_FOUND",
    "description": "Planet not found."
}
```

Si le nom de l'excursion n'est pas unique:

HTTP 400 BAD REQUEST

```
{
    "error": "EXCURSION_ALREADY_EXISTS",
    "description": "Excursion already exists in the cruise itinerary."
}
```

Si le début de l'excursion n'est pas entre l'arrivée et le départ de la planète,
si l'excursion finit après le départ de la planète ou si le nombre de places disponibles est inférieur ou égal à 0:

HTTP 400 BAD REQUEST

```
{
      "error": "INVALID_EXCURSION",
    "description": "Invalid excursion."
}
```

### Route API à modifier

Ajouter `"excursions"` dans la réponse de la route suivante:

GET /cruises/{cruiseId}

Réponses:

```
{
    "id": "JUPITER_MOONS_EXPLORATION_2085"::string,
    "departureDateTime": "2085-01-25T12:00"::string(dateTime, format ISO),
    "endDateTime": "2085-02-01T12:00"::string(dateTime, format ISO),
    "planets": [
        {
            "name": "Mars"::string,
            "arrivalDateTime": "2085-01-26T12:00"::string(dateTime, format ISO),
            "departureDateTime": "2085-01-30T12:00"::string(dateTime, format ISO),
            "excursions": [
                {
                    "name": "Excursion sur Mars"::string,
                    "description": "Découvrez la planète rouge!"::string,
                    "startDateTime": "2085-01-27T12:00"::string(date, format ISO),
                    "duration": 2::int,
                    "capacity": 10::int
                },
                ...
            ]
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

Plus de détails sur `GET /cruises/{cruiseId}` dans le fichier [README](../../README.md).