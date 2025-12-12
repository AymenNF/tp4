# Scénario d'essais : Supprimer un compte utilisateur

1. Implémenter la fonctionnalité pour supprimer un compte utilisateur.
2. Élaborer une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe.
   Écrire la stratégie dans [le fichier de questions](../questions/question-delete-account.md).
3. Implémenter les tests décrits dans la stratégie d'essais en respectant les bonnes pratiques vues en classe.

## Critères d'acceptations

- Le compte à supprimer doit exister.
- Le compte ne peut pas être supprimé si des réservations y sont associées.
- Le compte ne peut pas être supprimé si la HomePlanet est "Earth" (peu importe les majuscules).
- Si le compte est supprimé, il n'existe plus et ne peut plus être utilisé.
- Les erreurs appropriées doivent être retournées par l'API (Voir détails plus bas).

## Détails de la fonctionnalité

Un compte utilisateur peut être supprimé si aucune réservation n'est associé à ce compte.
- Suite à la suppression, le compte n'existe plus et ne peut plus être utilisé.
- Si la HomePlanet est "Earth" (peu importe les majuscules), le compte ne peut pas être supprimé.

### Route API et Erreurs possibles

DELETE /accounts/{username}

Réponses:

HTTP 200 OK

Si le compte n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "ACCOUNT_NOT_FOUND",
    "description": "Account not found."
}
```

Si le compte a des réservations ou si la HomePlanet est "Earth" (peu importe les majuscules):

HTTP 400 BAD REQUEST

```
{
    "error": "ACCOUNT_CANNOT_BE_DELETED",
    "description": "Account cannot be deleted."
}
```

