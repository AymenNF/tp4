# Nouvelle fonctionnalité : Voir l'attribution des cabines

1. Implémenter la fonctionnalité pour voir l'attribution des cabines pour une croisière donnée selon les détails plus bas.
  **Assurez-vous de respecter les principes SOLID+T et les bonnes pratiques vues en classe.**
2. Élaborer et implémenter une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe et en suivant [les instructions](../instructions/scenarios-essais.md).
   Écrire la stratégie dans [le fichier de questions](../questions/question-cabins-attribution.md).

## Détails de la fonctionnalité

Les administrateurs de USpace veulent voir l'attribution des cabines **pour les voyageurs** pour une croisière donnée.
**Cette fonctionnalité ne prend PAS en compte les membres de l'équipage.**

L'attribution des cabines peut être faite selon **différentes méthodes de tri des réservations**:
- Par ordre de date de réservation
  - Exemple: Avec les cabines A1, A2, A3, A4 et A5, et les réservations R1 à R5 (en ordre de date de réservation):
    - A1: R1
    - A2: R2
    - A3: R3
    - A4: R4
    - A5: R5
- Par nombre de voyageurs, puis par ordre de date de réservation
  - Exemple: Avec les cabines A1, A2, A3, A4 et A5, et les réservations suivantes (en ordre de date de réservation):
    - R1: 1 adult
    - R2: 2 adults
    - R3: 1 adult, 1 child
    - R4: 1 adult, 1 child, 1 senior
  
    Voici l'ordre d'attribution des cabines:
    - A1: R4
    - A2: R2
    - A3: R3
    - A4: R1
    - A5: vide

Dans tous les cas, la catégorie de la cabine réservée doit être respectée. Les exemples plus haut sont pour des cabines de mêmes catégories.

### Numérotation des cabines

Le fichier [cabins.json](../../src/main/resources/data/cabins.json) contient les informations des cabines disponibles pour les croisières.
- Les cabines sont identifiées par leur ID unique formé de la lettre de la section et d'un chiffre.
- Les cabines sont regroupées par catégorie (STANDARD, DELUXE, SUITE).
- Les cabines sont dans l'ordre de priorité d'attributions.
  Exemple: La catégorie STANDARD contient les ids de cabines A1, A2, B1 et B2. Vous devez attribuer A1, A2, B1 et B2 dans cet ordre.

Ce fichier doit être lu à chaque requête.

### IMPORTANT
**Vous n'avez PAS à lire ce fichier lors de la réservation d'une cabine.**
Dans le cadre de cet exercice, les cabines sont encore réservées selon la disponibilité donnée au démarrage. 
Dans un monde idéal, les cabines seraient réservées selon la disponibilité fournie par le fichier json pour que tout soit cohérent.

Assurez-vous que le nombre de cabines disponibles fourni à votre application est bien:
- 100 cabines STANDARD
- 75 cabines DELUXE
- 25 cabines SUITE

Validez les constantes dans [UspaceMain](../../src/main/java/uspace/UspaceMain.java).

## Critères d'acceptation

- Le fichier [cabins.json](../../src/main/resources/data/cabins.json) est lu à chaque requête.
- Les cabines sont retournées selon la méthode d'attribution choisie.
- Toutes les cabines attribuées sont retournées.
- Les cabines non attribuées ne sont pas retournées.
- Les erreurs appropriées doivent être retournées par l'API (Voir détails plus bas).
- L'interface utilisateur doit afficher les cabines dans l'ordre reçu, dans un tableau.
- S'il n'y a pas de cabines attribuées, l'interface utilisateur ne doit pas afficher de tableau.


## Routes API et Erreurs possibles

GET /cruises/{cruiseId}/cabins?criteria=(bookingDateTime|travelers)

Réponses:

HTTP 200 OK

```
{
    "cabins": [
        {
            "cabinId": "A1",
            "bookingId": "123456",
            "category": "STANDARD"
        },
        {
            "cabinId": "A2",
            "bookingId": "654321",
            "category": "DELUXE"
        }
    ]
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

Si le critère n'est pas valide:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_CABIN_ATTRIBUTION_CRITERIA",
    "description": "Invalid cabin attribution criteria. It must be one of: bookingDateTime, travelers."
}
```
