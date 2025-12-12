# Créer une fonctionnalité complète

Ce guide sert à vous aider à créer une fonctionnalité complète, de la route API au domaine.
L'ordre présenté est une suggestion, mais vous pouvez commencer par n'importe quelle couche. 

Utiliser votre jugement et assurez-vous de respecter les principes vus en classe.
N'hésitez pas à vous inspirer du code existant dans le projet pour vous aider dans votre tâche.

## 1. Créer la route API

Pour créer une route API dans l'application, vous devez vous rendre dans la couche `api` du projet.

1. Créer une nouvelle classe Resource ou ajouter une méthode à une classe existante.
2. Utiliser les annotations Jakarta pour indiquer les détails de votre route: GET, POST, PATH("/..."), etc.
3. Créer vos objets de Request ou de Response si nécessaire.
4. Assurez-vous de faire les validations nécessaires à ce niveau.

## 2. Créer le service

Un service est une classe qui contient la logique d'application, c'est-à-dire l'orchestration. 
C'est ici que vous devriez mettre le code qui manipule les objets de votre domaine.

Les services se trouvent dans la couche `application` du projet.

1. Créer une nouvelle classe Service ou ajouter une méthode à une classe existante.
2. Faites appel aux classes de votre domaine pour effectuer les opérations.
3. Assurez-vous de faire les validations nécessaires à ce niveau.

## 3. Configurer l'injection de dépendances

1. Ajouter un @Inject aux constructeurs des classes qui ont besoin d'injections de dépendances au démarrage de l'application.
2. La classe `ConfigurationServerRest` est en charge de l'injection de dépendances. Assurez-vous que les classes à injecter y soit bien présentes.

## 4. Créer le domaine

Le domaine est la couche la plus basse de l'application. C'est ici que vous devriez mettre les classes qui représentent les concepts de votre application.

Les classes de domaine se trouvent dans la couche `domain` du projet.

1. Implémenter la logique d'affaire de la fonctionnalité.



