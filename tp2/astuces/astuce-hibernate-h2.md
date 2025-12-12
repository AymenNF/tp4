# Guide d'utilisation d'Hibernate avec la base de données H2

Ce guide met en lumière les étapes nécessaires pour utiliser Hibernate avec une base de données H2 dans le cadre de ce TP.

## Ce qui est présent

- Un fichier `hibernate.cfg.xml` qui contient la configuration de Hibernate.
- Les dépendances Maven nécessaires pour Hibernate et H2.
- Les annotations nécessaires pour les entités déjà présentes dans le projet.
- La classe `HibernateUtil` qui contient une méthode utilitaire permettant de créer une session Hibernate.

## Accès aux données

Pour accéder aux données de l'application, vous devez utiliser Hibernate. Voici un exemple de code pour sauvegarder et récupérer un objet de la base de données.

### Récupérer un objet

```
try(Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.get(Objet.class, objetId);
  } catch (Exception e) {
      // Gérer l'exception
  }
```

### Sauvegarder un objet

```
Transaction transaction = null;

try(Session session = HibernateUtil.getSessionFactory().openSession()) {
    transaction = session.beginTransaction();

    session.saveOrUpdate(objet); // Méthode Obsolete qui fera l'affaire pour ce TP

    transaction.commit();
} catch (Exception e) {
    if (transaction != null) {
        transaction.rollback();
    }

    // Gérer l'exception
}
```

## Annotations

Les annotations permettent d'indiquer à Hibernate comment gérer les entités. Voici quelques annotations utiles:

- `@Entity`: Indique que la classe est une entité.
- `@Id`: Indique la clé primaire de l'entité.
- `@Embeddable`: Indique que la classe est un objet intégré (Value object).
- `@OneToMany`: Indique une relation un-à-plusieurs. Souvent sur une collection.
- `@Enumerated`: Indique que le champ est un ENUM.
- `@Inheritance`: Indique une relation d'héritage.
- `@Fetch(FetchMode.SELECT)`: Indique à Hibernate de charger les données en utilisant une requête SELECT à part.

### Mapper une collection

Il est nécessaire d'utiliser fetch = FetchType.EAGER pour mapper une collection.
Dans un but de simplification, l'application n'utilise pas de Lazy Loading.

## Mappage des entités

Il est important d'ajouter les nouvelles entités à mapper dans le fichier `hibernate.cfg.xml`. Voici un exemple de mappage:

```
<mapping class="uspace.domain.cruise.Cruise" />
```

## Connexion à la base de données H2

Pour utiliser Hibernate avec une base de données H2, vous devez spécifier l'URL de connexion appropriée dans le fichier `hibernate.cfg.xml`.

### Base de données en mémoire (Configuration par défaut pour ce TP)

Dans le fichier `hibernate.cfg.xml`, spécifiez l'URL de connexion comme suit:

```
<property name="hibernate.connection.username">sa</property>
<property name="hibernate.connection.password"></property>
<property name="hibernate.connection.url">jdbc:h2:mem:cruise</property>
```

Il ne sera pas possible de visualiser les données de la base de données H2.

### Base de données avec Docker

Pour visualiser les données de la base de données H2, vous pouvez utiliser Docker. Voici comment vous pouvez démarrer un conteneur Docker pour la base de données H2:

**IMPORTANT:** Il faut démarrer le Docker en même temps que l'application pour accéder à la base de données.

#### Utilisation de Docker

1. Installer Docker Desktop sur votre machine: [suivre les instructions de téléchargement selon votre OS](https://www.docker.com/)

2. À la première utilisation, pour créer le conteneur:
```shell
docker pull buildo/h2database
```
```shell
docker run --name uspace -p 8082:8082 -p 9092:9092 -v h2-data:/opt/h2-data buildo/h2database
```

3. Pour arrêter le conteneur:
```shell
docker stop uspace
```

4. Pour le redémarrer:
```shell
docker start uspace
```

#### Configuration de la connexion

Dans le fichier `hibernate.cfg.xml`, spécifiez l'URL de connexion comme suit:

```
<property name="hibernate.connection.username">sa</property>
<property name="hibernate.connection.password"></property>
<property name="hibernate.connection.url">jdbc:h2:tcp://localhost:9092/uspace</property>
```

#### Visualiser les données avec VSCode

**Assurez-vous que le docker est démarré!**

1. Dans la fenêtre "Database" de VSCode, cliquez sur le bouton "+" pour ajouter une nouvelle connexion.
2. Sélectionnez "H2" comme type de base de données.
3. Remplissez les champs suivants:
   - Hostname: localhost
   - Port: 9092
   - Database: uspace
   - User: sa
   - Password: (laissez vide)
   - URL: jdbc:h2:tcp://localhost:9092/uspace
- Cliquez sur "Test Connection". SI tout est beau, APPLY et OK.
