# Prendre en compte la catégorie de voyageur

## 1. Modifer la réservation d'une expérience zéro gravité

### Mise en contexte
Pour l'instant, tous les `Traveler` ont le même comportement, peu importe leur catégorie:
1. L'expérience zéro gravité est réservée.
2. Le voyageur gagne le badge `ZERO_G`, s'il ne le possède pas déjà.

### Tâche
**Utiliser le polymorphisme** pour prendre en compte la catégorie de voyageur lors de la réservation d'une expérience zéro gravité.
* Vous pouvez modifier ou ajouter autant de méthodes et de classes que désiré.
* Vous pouvez modifier les signatures des méthodes existantes.
* Vous ne pouvez pas modifier les classes `Cruise` et `ZeroGravityExperience`.

N'oubliez pas vos tests unitaires pour les comportements des voyageurs.
Nous vous recommandons d'utiliser le TDD.

### Nouvelles règles à implémenter pour la réservation d'une expérience zéro gravité selon la catégorie de voyageur:

ADULT:
* Peut toujours réserver une expérience zéro gravité
* Gagne le badge `ZERO_G`, s'il ne le possède pas déjà.

CHILD:
* Peut réserver une expérience zéro gravité seulement si un ADULTE ou un SENIOR de sa réservation a préalablement réservé la même expérience
    * Si ce n'est pas possible, l'exception `ZeroGravityExperienceChildCriteriaException` est lancée
* Gagne le badge `MINI_ZERO_G` au lieu de `ZERO_G`, s'il ne le possède pas déjà.

SENIOR:
* Peut toujours réserver une expérience zéro gravité
* Gagne le badge `STILL_GOT_IT` (s'il ne le possède pas déjà) en plus de `ZERO_G`(s'il ne le possède pas déjà).

## 2. Modifier `TravelerFactory` pour prendre en compte la catégorie de voyageur lors de la création d'une réservation.

1. Utiliser les bonnes pratiques vues en classe pour faire les modifications nécessaires.
2. N'oubliez pas vos tests unitaires! Nous vous recommandons d'utiliser le TDD.



