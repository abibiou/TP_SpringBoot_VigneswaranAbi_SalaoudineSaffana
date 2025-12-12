# API REST MMI - Gestion de Livres et Auteurs

Ce projet implémente une API REST complète pour gérer des livres et des auteurs en utilisant Spring Boot, Spring Data JPA, et MySQL.

## Le démarrage de l'application

Il faut avoir :
- Java 17+
- Maven
- Une instance MySQL fonctionnelle (XAMPP)

## La configuration

- **Base de données** : Assurez-vous que MySQL est en cours d'exécution avec Apache Web Server.
- **Fichier "application.properties"** : Vérifiez et ajustez les paramètres de connexion localhost (Note : `spring.jpa.hibernate.ddl-auto=update` est configuré pour gérer la création des tables au démarrage.)

## Lancement

Exécutez l'application via votre IDE (Run "MainApplication.java").  
L'API sera disponible sur `http://localhost:8080`.

## Endpoints Disponibles et Fonctionnalités

### 1. Authors (`http://localhost:8080/authors`)

Les méthodes POST et PUT doivent se faire en JSON sur Postman.

| MÉTHODE | ROUTE | EXEMPLE | DESCRIPTION |
|---------|-------|---------|-------------|
| GET | `http://localhost:8080/authors` | | Liste tous les auteurs |
| GET | `http://localhost:8080/authors/{id}` | `http://localhost:8080/authors/1` | Détails d'un auteur, si l'id est non trouvé erreur 404 |
| POST | `http://localhost:8080/authors` | (voir en dessous) | Créer un nouvel auteur (nécessite un body JSON valide) |
| PUT | `http://localhost:8080/authors/{id}` | (identique au POST, juste pas la même route) | Modifie complètement l'auteur (nécessite un body JSON valide, et si l'id est non trouvé erreur 404) |
| DELETE | `http://localhost:8080/authors/{id}` | `http://localhost:8080/authors/1` | Supprime l'auteur et retourne 204 si succès |

**Exemple de POST (Création) :**

```
http://localhost:8080/authors
```

Body (JSON):
```json
{
  "firstName": "Victor",
  "lastName": "Hugo",
  "birthYear": 1802
}
```

### 2. Books (`http://localhost:8080/books`)

Les méthodes POST et PUT doivent se faire en JSON sur Postman.

| MÉTHODE | ROUTE | EXEMPLE | DESCRIPTION |
|---------|-------|---------|-------------|
| GET | `http://localhost:8080/books` | | Liste paginée et filtrée des livres |
| GET | `http://localhost:8080/books/{id}` | `http://localhost:8080/books/1` | Détails d'un livre, si l'id est non trouvé erreur 404 |
| POST | `http://localhost:8080/books` | (voir en dessous) | Créer un nouveau livre (nécessite un body JSON valide, erreur 409 si isbn déjà existant dans la BDD) |
| PUT | `http://localhost:8080/books/{id}` | (identique au POST, juste pas la même route) | Modifie le livre (nécessite un body JSON valide, et si l'id est non trouvé erreur 404, 409 si isbn existant) |
| DELETE | `http://localhost:8080/books/{id}` | `http://localhost:8080/books/1` | Supprime le livre et retourne 204 si succès |

**Exemple de POST (Création) :**

```
http://localhost:8080/books
```

Body (JSON):
```json
{
  "authorId": 1,
  "title": "Les Misérables",
  "isbn": "9782070364107",
  "year": 1862,
  "category": "NOVEL"
}
```

**Remarque** : le POST nécessite l'id d'un auteur existant (`authorId`).

### 3. Statistiques (`http://localhost:8080/stats`)

| MÉTHODE | ROUTE | DESCRIPTION |
|---------|-------|-------------|
| GET | `http://localhost:8080/stats/books-per-category` | Nombre de livres pour chaque catégorie |
| GET | `http://localhost:8080/stats/top-authors` | Liste les auteurs avec le plus de livres (par défaut 3) |

**Exemple de GET (Top Auteurs) :**

```
http://localhost:8080/stats/top-authors?limit=5
```

(`limit` définit le nombre d'auteurs que vous voulez).

## Filtres Avancés et Pagination

Les filtres s'appliquent via des paramètres de requête (Query).

| PARAMÈTRES | TYPE | EXEMPLE | DESCRIPTION |
|------------|------|---------|-------------|
| page | int | `page=1` | Numéro de page (par df 0) |
| size | int | `size=10` | Taille de la page (par df 20) |
| sort | string | `sort=year,desc` | Champ de tri et direction (field,desc ou field,asc) |
| title | string | `title=misérables` | Recherche partielle sur le titre |
| authorId | long | `authorId=1` | Filtre par l'id de l'auteur |
| category | enum | `category=NOVEL` | Filtre par catégorie (NOVEL, POETRY, ESSAY, OTHER) |
| yearFrom | int | `yearFrom=1900` | Année minimale de publication |
| yearTo | int | `yearTo=2000` | Année maximale de publication |

**Exemple de GET (Filtres) :**

```
http://localhost:8080/books?authorId=1&category=NOVEL&sort=year,desc&page=0&size=5
```
