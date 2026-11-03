# Banque API : Service REST avec Spring Boot (JSON et XML)

## Étape 1 : Création du projet avec Spring Initializr

Projet créé avec Spring Initializr (https://start.spring.io/).

Dépendances : Spring Web, Spring Data JPA, H2 Database, PostgreSQL Driver, Lombok, DevTools.

- Group : `ma.banque`
- Artifact : `banque-api`

<img width="445" height="811" alt="Capture d&#39;écran 2026-11-03 004713" src="https://github.com/user-attachments/assets/e1d4ac5f-7cdf-4e33-9b7e-758c3215cb2e" />

---

## Étape 2 : Configuration de la base de données

### a) Lancer PostgreSQL avec Docker

```bash
docker compose up -d
```

<img width="1507" height="699" alt="Capture d&#39;écran 2026-11-03 001521" src="https://github.com/user-attachments/assets/e4681d57-1c37-496d-8a9e-069a2209adec" />

### b) Fichier `application.properties`

```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://localhost:5432/banque
spring.datasource.username=banque_user
spring.datasource.password=banque_pass
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
springdoc.swagger-ui.path=/swagger-ui.html
```

L'option `ddl-auto=update` permet à Hibernate de créer et de mettre à jour le schéma automatiquement.

### c) Connexion avec DBeaver

| Paramètre | Valeur |
|---|---|
| Host | `localhost` |
| Port | `5432` |
| Database | `banque` |
| Username | `banque_user` |
| Password | `banque_pass` |

<img width="1474" height="926" alt="Capture d&#39;écran 2026-11-03 001228" src="https://github.com/user-attachments/assets/c2441153-89de-474b-9309-8eff4ba8d29d" />

---

## Étape 3 : Entité JPA `Account`

Classe `Account` annotée avec `@Entity` : identifiant, solde, date de création et type de compte.

## Étape 4 : Énumération `AccountType`

Valeurs : `COURANT` et `EPARGNE`. `@Enumerated(EnumType.STRING)` stocke les valeurs sous forme de texte.

## Étape 5 : Dépôt JPA `AccountRepository`

Interface qui étend `JpaRepository<Account, Long>`.

## Étape 6 : Initialisation des données

Un `CommandLineRunner` dans `BankingApplication` insère 3 comptes au démarrage.

<img width="1784" height="866" alt="Capture d&#39;écran 2026-11-03 001422" src="https://github.com/user-attachments/assets/5219f3e3-7c0c-4654-9656-cff55b850cea" />

<img width="1816" height="805" alt="Capture d&#39;écran 2026-11-03 001433" src="https://github.com/user-attachments/assets/73a48574-ab55-4200-bd3e-2dfbe52795bc" />

<img width="1526" height="789" alt="image" src="https://github.com/user-attachments/assets/c29ac390-3bc3-4f66-9951-135b45bb6851" />

---

## Étape 7 : Contrôleur REST `AccountController`

Contrôleur annoté `@RestController` et `@RequestMapping("/banque")`, avec les endpoints CRUD :

| Méthode | URL | Rôle |
|---|---|---|
| GET | `/banque/comptes` | Liste des comptes |
| GET | `/banque/comptes/{id}` | Un compte par id |
| POST | `/banque/comptes` | Création |
| PUT | `/banque/comptes/{id}` | Mise à jour |
| DELETE | `/banque/comptes/{id}` | Suppression |

---


## Étape 8 : Intégration de Swagger

Dépendance `springdoc-openapi-starter-webmvc-ui` ajoutée dans le `pom.xml`.

Lancement :

```bash
mvn spring-boot:run
```

Accès : http://localhost:8082/swagger-ui.html

<img width="1902" height="1025" alt="Capture d&#39;écran 2026-11-03 003544" src="https://github.com/user-attachments/assets/bac1899b-2f6d-4a24-8f00-d64bf45f6907" />

<img width="1888" height="365" alt="image" src="https://github.com/user-attachments/assets/5e877a88-1788-479a-bb36-fae052648ae4" />

---

## Étape 9 : Support JSON et XML

Ajout de la dépendance `jackson-dataformat-xml` dans le `pom.xml`. Les attributs `produces` et `consumes` des méthodes du contrôleur acceptent `application/json` et `application/xml`.

---

## Étape 10 : Lancement de l'application

Exécuter la classe `BankingApplication`. L'application démarre sur le port 8082.

---

## Étape 11 : Tests avec Postman

Collection importée depuis `postman/banque-api.postman_collection.json`.

<img width="615" height="741" alt="image" src="https://github.com/user-attachments/assets/98f7755e-16fb-4fa4-a0a0-be3358ac2b2d" />

### a) GET tous les comptes (JSON)
`GET http://localhost:8082/banque/comptes`, header `Accept: application/json`

<img width="1911" height="1079" alt="image" src="https://github.com/user-attachments/assets/67fcb877-c434-4aaf-9b58-278fd3b06502" />

<img width="1221" height="660" alt="image" src="https://github.com/user-attachments/assets/4866ecbe-c9f5-4ece-8a65-f7d8b033e76e" />

### b) GET tous les comptes (XML)
`GET http://localhost:8082/banque/comptes`, header `Accept: application/xml`

<img width="1215" height="876" alt="image" src="https://github.com/user-attachments/assets/83658ecb-c754-4dd1-bf45-df707cc38195" />

<img width="1227" height="626" alt="image" src="https://github.com/user-attachments/assets/eea96a21-2d13-496b-b772-3f9b9b83f99b" />

### c) POST créer un compte (JSON)
Header `Content-Type: application/json`

```json
{"solde": 1500.0, "dateCreation": "2024-11-01", "type": "COURANT"}
```
<img width="1216" height="777" alt="image" src="https://github.com/user-attachments/assets/125c3216-e279-4da8-a931-e8c25b4f03c0" />

### d) POST créer un compte (XML)
Header `Content-Type: application/xml`

```xml
<compte><solde>1500.0</solde><dateCreation>2024-11-01</dateCreation><type>COURANT</type></compte>
```

<img width="1226" height="696" alt="image" src="https://github.com/user-attachments/assets/6bf76703-fd8e-436c-a30f-fc11f7fa00c0" />

### e) PUT modifier un compte (JSON)
`PUT http://localhost:8082/banque/comptes/1`


```json
{"solde": 2000.0, "dateCreation": "2024-11-01", "type": "EPARGNE"}
```

<img width="1230" height="674" alt="image" src="https://github.com/user-attachments/assets/81d30f97-ba61-45d7-a154-4bde663209fc" />

### f) PUT modifier un compte (XML)

```xml
<compte><solde>2000.0</solde><dateCreation>2024-11-01</dateCreation><type>EPARGNE</type></compte>
```

<img width="1232" height="709" alt="image" src="https://github.com/user-attachments/assets/54d4ceb2-7733-4576-8060-695ab0cfd796" />

### g) DELETE supprimer un compte
`DELETE http://localhost:8082/banque/comptes/1`

<img width="1243" height="551" alt="image" src="https://github.com/user-attachments/assets/d1ebae2f-564d-4613-ac79-d6c935bd8224" />

### h) Compte inexistant
`GET http://localhost:8082/banque/comptes/9999`

<img width="1227" height="546" alt="image" src="https://github.com/user-attachments/assets/52f100eb-4f21-4c3f-b304-42b30f0338e7" />

### i) Vérification dans DBeaver
Table `account` après les opérations CRUD.

<img width="1494" height="592" alt="image" src="https://github.com/user-attachments/assets/bd7ee047-0bb9-4af9-9771-70806e135e95" />

---

## Étape 12 : À retenir

- `@RestController` simplifie la création de services RESTful avec Spring Boot.
- Une entité JPA et un `JpaRepository` suffisent pour le CRUD sans implémentation manuelle.
- `jackson-dataformat-xml` permet de répondre en JSON ou en XML selon les en-têtes `Accept` et `Content-Type`.
- `ResponseEntity` permet de contrôler le code de statut (200, 201, 404) et les en-têtes.
- Swagger documente et teste l'API automatiquement.
- Postman permet de vérifier les opérations CRUD dans les deux formats.
