# Real Estate Platform – Jakarta EE & JSP

Application web développée avec Jakarta EE permettant d’assurer les fonctionnalités essentielles d’une plateforme immobilière :
- Inscription et connexion utilisateur
- Création et gestion d’annonces
- Consultation des annonces
- Ajout d’annonces en favoris

Le projet suit une structure MVC avec JSP, Servlets et DAO.

## Technologies utilisées

- Jakarta EE (Servlets, JSP)
- Java 8+
- JDBC
- MySQL
- Tomcat
- HTML / CSS
- JSTL

## Architecture du projet

``` 
src/
 ├─ controller/    → Servlets (auth, annonces, favoris…)
 ├─ dao/           → Accès aux données
 ├─ model/         → Classes métier (User, Annonce…)
 └─ utils/         → Helpers, connexion DB
webapp/
 ├─ WEB-INF/
 ├─ pages/         → JSP (connexion, inscription, annonces…)
 └─ assets/        → CSS, images…
```

## Fonctionnalités

### Authentification
- Inscription
- Connexion
- Gestion de session

### Annonces immobilières
- Création d’une annonce
- Consultation de la liste des annonces
- Affichage détaillé

### Favoris
- Ajout en favoris
- Suppression
- Liste personnelle des favoris

## Lancement

1. Importer le projet dans un IDE compatible (Eclipse, IntelliJ…)
2. Configurer la connexion à la base MySQL
3. Déployer sur Tomcat ou un serveur Jakarta EE équivalent
4. Accéder à l’application via :
   ```
   http://localhost:8080/nom-du-projet
   ```

## Évolutions possibles

- Recherche avancée
- Upload d’images pour les annonces
- API REST
- Interface modernisée (ex. Bootstrap, Vue.js…)

