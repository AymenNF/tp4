# Nouvelle fonctionnalité : Obtenir le manifeste des navettes d'urgence

1. Implémenter la fonctionnalité pour voir les détails des navettes d'urgence pour une croisière donnée selon les détails plus bas.
  **Assurez-vous de respecter les principes SOLID+T et les bonnes pratiques vues en classe.**
2. Élaborez et implémentez une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe et en suivant [les instructions](../instructions/scenarios-essais.md).
   Écrire la stratégie dans [le fichier de questions](../questions/question-emergency-shuttle.md).

## Détails de la fonctionnalité

En cas de situation d'urgence, chaque croisière USpace a des navettes d'urgence pour évacuer les passagers.
Les administrateurs de USpace veulent obtenir le manifeste pour voir les détails des navettes d'urgence pour une croisière donnée.

- La place des voyageurs sur une navette d'urgence est réservée dès la réservation.
- La place des membres d'équipage sur une navette d'urgence est réservée dès l'ajout du membre d'équipage à la croisière.

### Types de navettes d'urgence
- **Les vaisseaux de sauvetage** (RESCUE_SHIP): 
  - Il y en a un nombre limité par croisière. Pour la croisière JUPITER_MOONS_EXPLORATION_2085, il y en a 5.
  - Avec 50 places chacun.
  - 150 000$ par vaisseau.

- **Les navettes standards** (STANDARD_SHUTTLE):
  - Il y en a un nombre illimité par croisière.
  - Avec 20 places chacune.
  - 50 000$ par navette.

### Règles d'attribution des navettes
- Les vaisseaux de sauvetage sont attribués en priorité.
- Pour un même type de navette, il faut remplir la dernière navette avant d'en ouvrir une autre.
  - Il ne faut pas avoir plusieurs navettes incomplètes du même type!

### Note sur les constantes
Le coût, le nombre et la capacité des navettes doivent être facilement modifiables sans modifier les classes du code.

## Critères d'acceptation

- Les détails des navettes d'urgence (navettes avec passagers, coût total) sont obtenus.
- Le coût total correspond à la somme des coûts des navettes utilisées.
- Les navettes sont attribuées selon leur priorité.
- Pour un même type de navette, il faut remplir la dernière navette avant d'en ouvrir une autre
- La capacité des navettes est respectée.
- La place des voyageurs sur une navette d'urgence est réservée dès la réservation.
- La place des membres d'équipage sur une navette d'urgence est réservée dès l'ajout du membre d'équipage à la croisière.
- Les navettes vides ne doivent pas être obtenues dans le manifeste.
- Les erreurs appropriées doivent être retournées par l'API (Voir détails plus bas).
- L'interface utilisateur affiche les détails des navettes et un coût total.
- Le bouton "Get Manifest" de l'interface utilisateur devient rouge si le manifeste obtenu est vide.


## Routes API et Erreurs possibles

GET /cruises/{cruiseId}/emergencyShuttles

Réponses:

HTTP 200 OK
```
{
  "cost": 1000000::int,
  "emergencyShuttles": [
    {
      "type": "STANDARD_SHUTTLE"::string(STANDARD_SHUTTLE|RESCUE_SHIP|SENIOR_SHUTTLE),
      "travelers": [
        {
          "travelerId": "395e7715-74fc-468a-9795-1adc79e6148a"::string,
          "travelerName: "John Doe"::string,
        },
        ...
      ]
      crewMembers: [
        {
          "employeeId": "ABC123"::string,
          "crewMemberName: "Jane Doe"::string,
        },
        ...
      ]
    },
    ...
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


