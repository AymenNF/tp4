# Réservation d'une expérience zéro gravité

### Mise en contexte
La méthode `bookZeroGravityExperience` de la classe `Cruise` permet à un voyageur de réserver une expérience zéro gravité lors de la croisière.

Prenez le temps de bien comprendre le code existant avant de commencer les exercices.

### 1. Implémenter les tests unitaires de `bookZeroGravityExperience` en respectant les bonnes pratiques vues en cours.

### 2. Implémenter les tests unitaires des méthodes de `ZeroGravityExperience` en respectant les bonnes pratiques vues en cours.

### 3. Compléter la fonctionnalité en ajoutant la route API:

1. Ajouter les tests unitaires pertinents. Nous recommandons d'utiliser le TDD.
2. Assurez-vous de respecter les bonnes pratiques vues en classe.
3. Ajouter le code manquant pour compléter la fonctionnalité. Le guide [Créer une fonctionnalité complète](../astuces/astuce-creer-une-fonctionnalite.md) peut vous aider dans votre démarche.
4. Assurez-vous que tous les comportements sont fonctionnels.

#### Détails de la route API

POST /cruises/{cruiseId}/bookings/{bookingId}/travelers/{travelerId}/zeroGravityExperiences

```
{
    "experienceBookingDateTime": "2084-04-08T12:30:00"::string(dateTime, format ISO)
}
```

Réponses:

HTTP 200 OK

Si un des champs est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```

Si la date n'est pas dans le bon format:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_DATE_FORMAT",
    "description": "Invalid date format"
}
```

Si la date de réservation est après le départ de la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_BOOKING_TIME",
    "description": "Zero gravity experience booking time must be before the cruise departure time."
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

Si le voyageur n'existe pas dans la réservation:

HTTP 404 NOT FOUND

```
{
    "error": "TRAVELER_NOT_FOUND",
    "description": "Traveler not found"
}
```

Si le voyageur a déjà réservé une expérience zéro gravité:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_ALREADY_BOOKED",
    "description": "Zero gravity experience already booked by traveler"
}
```

Si l'expérience zéro gravité est complète:

HTTP 400 BAD REQUEST

```
{
    "error": "ZERO_GRAVITY_EXPERIENCE_FULL",
    "description": "Zero gravity experience is full"
}
```

