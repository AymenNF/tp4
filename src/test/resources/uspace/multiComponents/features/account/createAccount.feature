#language: fr

Fonctionnalité: Créer un compte utilisateur

    Scénario: Le compte est créé
        Quand un compte utilisateur avec le nom d'utilisateur "bob" est créé
        Alors le code de statut 201 est obtenu
        Et le compte utilisateur avec le nom d'utilisateur "bob" existe