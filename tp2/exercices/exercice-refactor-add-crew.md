# Réusinage: Ajouter un membre d'équipage à une croisière

La fonctionnalité d'ajout d'un membre d'équipage est présente dans l'application.
Cependant, les Principes SOLID ne sont pas respectés dans le code actuel.

1. **Votre tâche est de réusiner (refactor) le code pour respecter les Principes SOLID+T et les bonnes pratiques vus en classe.**
2. Implémentez les tests unitaires pour tous les comportements attendus de l'ajout d'un membre d'équipage.

## Questions à répondre

Répondez aux questions dans l'espace prévu dans le [fichier de questions](../questions/questions-refactor-add-crew.md).

## Détails de la fonctionnalité

Il est possible d'ajouter un membre d'équipage à une croisière.

Un membre d'équipage est composé d'un nom et d'un id.

### Conditions

* L'ID est unique et est composé de 3 lettres majuscules suivies de 3 chiffres.
* Il faut réserver une cabine "STANDARD" par membre d'équipage.
* Le membre d'équipage ne peut pas recevoir de badge ni participer à des activités.
* Les ID des membres d'équipages ajoutés à une croisière apparaissent dans les détails de la croisière.

### NOTES

Il est maintenant possible de voir les ids des membres d'équipage avec `GET cruises/{cruiseId} (cruises/JUPITER_MOONS_EXPLORATION_2085)`.
Voir le fichier [README.](../../README.md) pour plus de détails.

### Route API

POST /cruises/{cruiseId}/crewMembers

```
{
    "name": "John Doe"::string,
    "employeeId": "ABC123"::string(3 lettres majuscules suivi de 3 chiffres)
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
(`<parameterName>` est le nom du champ manquant: name ou employeeId)

Si la croisière n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "CRUISE_NOT_FOUND",
    "description": "Cruise not found."
}
```

Si le membre d'équipage existe déjà dans la croisière:

HTTP 400 BAD REQUEST

```
{
    "error": "CREW_MEMBER_ALREADY_EXISTS",
    "description": "Crew member already in the cruise."
}
```

Si l'ID du membre d'équipage n'est pas dans le bon format:

HTTP 400 BAD REQUEST

```
{
    "error": "INVALID_EMPLOYEE_ID_FORMAT",
    "description": "Invalid employee ID format."
}
```

Si il n'y a pas de cabine de type "STANDARD" de disponible:

HTTP 400 BAD REQUEST

```
{
    "error": "CABIN_TYPE_NOT_AVAILABLE",
    "description": "Cabin type not available."
}
```