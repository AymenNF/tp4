# Scénario d'essais : Réserver une activité sportive

1. Implémenter la fonctionnalité pour réserver une activité sportive.
2. Élaborer une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe.
   Écrire la stratégie dans [le fichier de questions](../questions/question-book-sports.md).
3. Implémenter les tests décrits dans la stratégie d'essais en respectant les bonnes pratiques vues en classe.

## Critères d'acceptations

- Le champ `sport` est obligatoire.
- La croisière doit exister.
- Le voyageur doit exister dans la réservation donnée.
- L'activité sportive doit exister pour la croisière.
- Un voyageur ne peut pas réserver une activité sportive s'il ne fait pas parti du public cible.
- Un voyageur ne peut pas réserver une activité sportive s'il est déjà inscrit.
- Lorsqu'un voyageur réserve une activité sportive, il reçoit un badge spécifique selon sa catégorie de voyageur (voir détails plus bas).
- Lorsqu'un voyageur réserve une activité, il ne peut pas la réserver à nouveau.
- Les informations des sports disponibles pour les croisières sont obtenues du fichier `sports.json`.
- Les erreurs appropriées doivent être retournées par l'API (Voir détails plus bas).

## Détails de la fonctionnalité

USpace fait affaire avec une entreprise de sports pour offrir des activités sportives à ses voyageurs.

Dans le futur, les informations des sports disponibles pour les croisières seront obtenues d'un API externe.
Pour l'instant, l'entreprise de sports a fourni les informations des sports disponibles dans le fichier JSON [sports.json](../../src/main/resources/data/sports.json) contenant:
- les activités sportives
- les croisières dans lesquelles elles sont disponibles
- le public (ex: ADULT, CHILD, SENIOR, ALL, etc)

Un voyageur peut réserver une activité sportive s'il fait parti du public cible et s'il n'y est pas déjà inscrit.

- Vous devez accéder au fichier JSON à chaque requête, comme si vous appeliez un service externe. (N'oubliez pas d'appliquer les principes SOLID!)
  - "L'appel au service externe" doit retourner les informations du sport demandé selon le nom et l'ID de la croisière. C'est Uspace qui doit décider si le voyageur peut réserver ou non.
  - USpace est responsable de la lecture appropriée du fichier JSON.
- Le sport est identifié par son nom unique.
- Le voyageur doit avoir une réservation pour la croisière.

Lorsqu'un voyageur réserve une activité sportive, il reçoit un badge spécifique selon sa catégorie de voyageur:

ADULT:
- Gagne le badge `SPORTY`, s'il ne le possède pas déjà.
- S'il le possède déjà, ne gagne pas de badge.

CHILD:
- Gagne le badge `MINI_SPORTY`, s'il ne le possède pas déjà.

SENIOR:
- Gagne le badge `SPORTY`, s'il ne le possède pas déjà.
- S'il le possède déjà, gagne `SUPER_SPORTY` en plus.
- S'il possède déjà `SUPER_SPORTY`, ne gagne pas de badge.

### Route API et Erreurs possibles

POST /cruises/{cruiseId}/bookings/{bookingId}/travelers/{travelerId}/sports

```
{
    "sport": "Lunar Soccer"::"string",
}
```

Réponses:

HTTP 200 OK

Si le champ `sport` est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: sport"
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


Si la réservation ou le voyageur n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "TRAVELER_NOT_FOUND",
    "description": "Traveler not found"
}
```

Si le sport n'existe pas pour la croisière:

HTTP 404 BAD REQUEST

```
{
    "error": "SPORT_NOT_FOUND",
    "description": "Sport not found"
}
```

Si le sport n'est pas réservable pour le voyageur:

HTTP 400 BAD REQUEST

```
{
    "error": "SPORT_NOT_BOOKABLE",
    "description": "Sport not bookable for traveler"
}
```

Si le sport est déjà réservé pour le voyageur:

HTTP 400 BAD REQUEST

```
{
    "error": "SPORT_ALREADY_BOOKED",
    "description": "Sport already booked by traveler"
}
```