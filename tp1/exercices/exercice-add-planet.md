# Ajouter une nouvelle planète à l'itinéraire d'une croisière spatiale

## 1. Implémenter la nouvelle fonctionnalité et les tests unitaires

1. Ajouter les tests unitaires pertinents. Nous recommandons d'utiliser le TDD.
2. Assurez-vous de respecter les bonnes pratiques vues en classe.

Implémenter la fonctionnalité selon les comportements décrits plus bas. Le guide [Créer une fonctionnalité complète](../astuces/astuce-creer-une-fonctionnalite.md) peut vous aider dans votre démarche.

### Détails de la fonctionnalité

Le gestionnaire de croisières veut pouvoir ajouter un arrêt à une nouvelle planète à l'itinéraire d'une croisière.

Chaque planète a un nom, une date d'arrivée et une date de départ. La classe `Planet` existe déjà dans l'application.

#### Conditions à respecter:
* Le nom de la planète doit être unique dans l'itinéraire de la croisière.
* La date d'arrivée à la planète doit être au moins 3 journées avant la date de départ de la planète.
* La date de départ de la planète doit être avant la date de fin de la croisière.
* La date d'arrivée à la planète doit être après la date de départ de la croisière.
* Il n'est pas possible d'être à deux planètes en même temps. C'est-à-dire :
  * La date de départ de la planète doit être après la date d'arrivée de la planète précédente.
  * La date d'arrivée de la planète doit être avant la date de départ de la planète suivante.

#### Détails de la route API

POST /cruises/{cruiseId}/planets

```
{
    "name": "Mars"::string,
    "arrivalDateTime": "2084-04-08T12:30"::string(dateTime, format ISO),
    "departureDateTime": "2084-04-10T15:15"::string(dateTime, format ISO)
}
```

Réponses:

HTTP 201 CREATED

Si un des champs est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```
(`<parameterName>` est le nom du champ manquant: name, arrivalDateTime ou departureDateTime)

Si la planète existe déjà dans l'itinéraire:

HTTP 400 BAD REQUEST

```
{
    "error": "PLANET_ALREADY_EXISTS",
    "description": "Planet already exists in the itinerary"
}
```

Si la date d'arrivée ou de départ de la planète n'est pas dans le bon format:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_DATE_FORMAT",
    "description": "Invalid date format."
}
```

Si une des dates ne répond pas aux conditions:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PLANET_DATE",
    "description": "Arrival date or departure date is invalid"
}
```
## 2. Utilisation d'un système externe de validation de planètes

USpace a décidé d'utiliser un système externe pour valider les planètes avant de les ajouter à l'itinéraire.
`PlanetValidatorVowelSystem` est le système de validation actuellement utilisé.

_Les noms des planètes suivantes sont valides:
"Terre", "Mars", "Jupiter", "Uranus", "Saturne", "Neptune", "Mercure", "Venus"_

_Exemple de nom de planète invalide: "SuperPlaneteInvalide"_

1. Intégrer `PlanetValidatorVowelSystem` dans la fonctionnalité d'ajout d'une planète à l'itinéraire.
   **Assurez-vous que le système de validation pourra être remplacé facilement en vous basant sur les concepts vus en classe.**

2. Si le système de validation externe ne valide pas la planète, retourner la réponse ci-dessous:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_PLANET",
    "description": "Invalid planet"
}
```

3. N'oubliez pas vos tests unitaires pour l'intégration dans la fonctionnalité!
(P.S. `PlanetValidatorVowelSystem` est testé dans l'exercice [Implémenter les tests unitaires de PlanetValidatorVowelSystem](exercice-tests-planet-validator.md)).