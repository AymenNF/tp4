# Scénario d'essais : Créer un compte utilisateur

La fonctionnalité de création de compte utilisateur existe déjà.

1. Élaborer une stratégie d'essais pour cette fonctionnalité en respectant les bonnes pratiques vues en classe.
   Écrire la stratégie dans [le fichier de questions](../questions/question-create-account.md).
2. Implémenter les tests décrits dans la stratégie d'essais en respectant les bonnes pratiques vues en classe.

## Critères d'acceptations

- Le champ `username` est obligatoire.
- Le champ `homePlanetName` est obligatoire.
- Un compte utilisateur ne peut pas être créé si le `username` est déjà utilisé.
- Un compte utilisateur créé doit être accessible par son `username`.
- Les erreurs appropriées doivent être retournées par l'API (Voir détails plus bas).

## Détails de la fonctionnalité

### Routes API et Erreurs possibles

Les routes API ci-dessous sont déjà implémentées depuis le TP2.

#### POST /accounts

```
{
  "username": "johndoe",
  "homePlanetName": "Earth"
}
```

Réponse:

HTTP 201 CREATED

Si un des champs est manquant:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: <parameterName>"
}
```

(`<parameterName>` est le nom du champ manquant: username ou homePlanetName)

Si le username est déjà utilisé:

HTTP 400 BAD REQUEST

```
{
    "error": "USERNAME_ALREADY_EXISTS",
    "description": "Username already exists."
}
```

#### GET /accounts/{username}

Réponse:

HTTP 200 OK

```
{
    "username": "johndoe",
    "homePlanet": "Earth",
    "bookingIds": []
}
```

Si le compte n'existe pas:

HTTP 404 NOT FOUND

```
{
    "error": "ACCOUNT_NOT_FOUND",
    "description": "Account not found."
}
```