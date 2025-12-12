# Nouvelle fonctionnalité: Voir le coût de la réservation

Vous devez implémenter cette nouvelle fonctionnalité dans l'application USpace.

**ATTENTION: Il vous sera peut-être nécessaire de modifier le code existant de la fonctionnalité d'ajout de réservation pour respecter les principes SOLID+T.**

1. Implémentez tous les comportements de la fonctionnalité.
2. Assurez-vous de respecter les Principes SOLID+T et les bonnes pratiques de programmation.
3. Implémentez les tests unitaires pour toutes vos modifications. Nous vous recommandons le TDD.

## Questions à répondre

Répondez aux questions dans l'espace prévu dans le [fichier de questions](../questions/questions-feature-cost.md).

## Détails de la fonctionnalité

Une réservation doit afficher le coût total de la réservation.

Le coût est calculé en fonction de plusieurs critères:
* Le type de cabine réservée.
* Le nombre de voyageurs.
* La date de réservation.

### Détails des coûts pour la croisière JUPITER_MOONS_EXPLORATION_2085

* Le coût de base par personne est déterminée par le type de cabine.
  * STANDARD: 1000,00$/personne
  * DELUXE: 1999,99$/personne
  * SUITE: 4500,00$/personne
* Le coût total de la réservation est calculé de cette façon:
  * Si la date de réservation est au moins 7 jours avant la date de départ, {coût de base par personne} * {nombre de voyageurs}. **Les enfants ont un rabais de 50%.**
  * Si la date de réservation est moins de 7 jours avant la date de départ, {coût de base par personne} * {nombre de voyageurs} * 1,5. (Frais tardif de 50%) **Aucun rabais.**

### Autres conditions

* Le coût doit être affiché avec 2 décimales (arrondi au centième).
* Les coûts présentés plus haut sont les coûts pour la croisière JUPITER_MOONS_EXPLORATION_2085.
  Les coûts pour les autres croisières peuvent être différents.
* Vous pouvez assumer que le pourcentage de frais tardif est le même pour toutes les croisières. Il n'a pas besoin d'être modifié au runtime.
* Il devrait être facile de changer les coûts et le pourcentage de frais tardif sans toucher à l'implémentation.
* ATTENTION: Hypothétiquement, si les coûts des cabines changent, les réservations préexistantes doivent conserver le coût original. (Sinon USpace va recevoir de nombreuses plaintes!)

### Exemples
* Réservation avec 1 adulte et 1 enfant en cabine DELUXE 8 jours avant le départ de la croisière:
  * Coût de base par personne pour la cabine DELUXE: 1999,99$
  * Coût 1 adulte + 1 enfant: 1999,99$ + 1999,99$ * 0,5 = 2999,98$

* Réservation avec 1 adulte et 1 enfant en cabine DELUXE 3 jours avant le départ de la croisière:
  * Coût de base par personne pour la cabine DELUXE: 1999,99$
  * Coût total: 1999,99$ * 2 * 1,5 = 5999,97$

### Route API à modifier

Ajouter `"cost"` dans la réponse de la route suivante:

GET cruises/{cruiseId}/bookings/{bookingId}

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
    "cost": 1000,00::float(2 décimales)
}
```

Plus de détails sur `GET cruises/{cruiseId}/bookings/{bookingId}` dans le fichier [README](../../README.md).