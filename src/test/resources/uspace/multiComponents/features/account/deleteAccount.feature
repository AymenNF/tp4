#language: fr

Fonctionnalité: Supprimer un compte utilisateur

    Scénario: Le compte supprimé n'existe plus
        Etant donné un compte utilisateur avec le nom d'utilisateur "bob"
        Quand un compte utilisateur avec le nom d'utilisateur "bob" est supprimé
        Alors le code de statut 200 est obtenu
        Et le compte utilisateur avec le nom d'utilisateur "bob" n'existe pas