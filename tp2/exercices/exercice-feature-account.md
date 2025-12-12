# Nouvelle fonctionnalité: Ajouter un compte utilisateur

Nous allons maintenant intégrer un autre partie à l'application USpace: Les comptes utilisateurs.
Le compte utilisateur sera utilisé pour gérer les réservations dans différentes croisières.

* **Ajouter les tests unitaires nécessaires pour cette fonctionnalité. Il n'est pas nécessaire de tester les classes du repository avec Hibernate. 
Cependant, il faut tester le repository "In Memory"**

## Questions à répondre

Répondez aux questions dans l'espace prévu dans le [fichier de questions](../questions/questions-feature-account.md).

### 1. Créer deux repository pour contenir les comptes utilisateurs: 
un "In Memory" et un avec Hibernate (voir [exercice-hibernate-h2](exercice-hibernate-h2.md)). 
Portez une attention particulière au DIP.

**Le repository avec Hibernate doit être utilisé dans l'application.**

### 2. Ajouter la fonctionnalité pour ajouter un compte utilisateur.

POST /accounts

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

### 3. Ajouter la fonctionnalité pour obtenir un compte utilisateur par son username.

GET /accounts/{username}

Réponse:

HTTP 200 OK

```
{
    "username": "johndoe",
    "homePlanet": "Earth",
    "bookingIds": []
}
```

### 4. Modifier la méthode createBooking de BookingService

1. Ajouter le `accountUsername` à la requête 

POST /cruises/{cruiseId}/bookings

```
{
    "accountUsername": "johndoe"::string, //NOUVEAU
    "travelers": [
        ...
    ],
    "cabinType": "Standard"::string(STANDARD | DELUXE | SUITE),
    "bookingDateTime": "2084-04-08T12:30"::string(dateTime, format ISO)
}
```

Si le paramètre `accountUsername` est manquant ou vide:

HTTP 400 BAD REQUEST

```
{
    "error": "MISSING_PARAMETER",
    "description": "Missing parameter: accountUsername"
}
```

2. S'assurer que le compte utilisateur existe avant d'effectuer la réservation.

S'il n'existe pas, retourner une erreur 404 NOT FOUND.

```
{
    "error": "ACCOUNT_NOT_FOUND",
    "description": "Account not found."
}
```

3. Ajouter le `bookingId` au compte utilisateur.

ATTENTION: Effectuez les actions dans le bon ordre et n'oubliez pas de sauvegarder les modifications dans les repository

