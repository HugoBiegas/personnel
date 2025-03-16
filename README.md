# Application de Gestion du Personnel

## Description

Cette application Java permet la gestion complète du personnel pour des ligues sportives. Elle offre une interface graphique conviviale ainsi qu'une interface en ligne de commande, permettant de gérer les ligues et leurs employés avec un système de droits d'accès hiérarchique.

## Fonctionnalités

### Gestion des utilisateurs
- **Système de droits hiérarchiques**:
  - Root (super administrateur)
  - Administrateurs de ligues
  - Employés standard
- **Gestion des comptes**:
  - Création, modification et suppression de comptes
  - Historisation des employés (dates d'arrivée/départ)
  - Changement des informations personnelles (nom, prénom, email, mot de passe)

### Gestion des ligues
- Création, modification et suppression de ligues
- Attribution/retrait du statut d'administrateur
- Vue historique des employés d'une ligue

### Sécurité
- Système d'authentification par email et mot de passe
- Gestion des permissions selon le rôle de l'utilisateur
- Protection des données sensibles (mots de passe masqués)

## Architecture du projet

### Structure des packages
- **commandLine**: Interface en ligne de commande
  - `PersonnelConsole`: Point d'entrée pour l'interface console
  - `LigueConsole`, `EmployeConsole`: Gestion des ligues et employés en mode console
  - Exceptions personnalisées: `DateInvalideException`, `InvalidAddEmploye`

- **InterfaceApplication**: Interface graphique (GUI)
  - `Connexions`: Écran de connexion
  - `PageAcceuil`: Page principale des ligues
  - `listEmployesLigue`, `InfoEmploye`: Gestion des employés
  - `AddChangeEmploye`, `editEmploye`: Formulaires d'ajout/modification
  - `RootData`, `EditRoot`: Gestion du compte root

- **personnel**: Classes métier
  - `Employe`, `Ligue`: Entités principales
  - `GestionPersonnel`: Contrôleur central de l'application

- **jdbc**: Persistance des données
  - Connexion à la base de données

### Modèle de données
Le modèle conceptuel de données (MCD) comprend :
- Une entité **Employe** avec id, nom, prénom, email, droits, mot de passe, date de début/fin
- Une entité **Ligue** avec id et nom
- Relations:
  - Un employé appartient à une ligue (0,1 - 0,n)
  - Un employé peut administrer une ligue (0,n - 0,n)

## Technologies utilisées

- **Java** (JDK 8 minimum)
- **Swing** pour l'interface graphique
- **JDBC** pour la connexion à la base de données
- **Looping** pour la modélisation conceptuelle

## Installation et utilisation

### Prérequis
- Java JDK 8 ou supérieur
- Base de données compatible JDBC (MySQL, PostgreSQL, etc.)

### Installation
1. Cloner le dépôt
2. Configurer la connexion à la base de données dans les fichiers appropriés
3. Compiler le projet avec votre IDE ou via la ligne de commande

### Lancement
- **Mode graphique**: Exécuter la classe `Connexions` du package `InterfaceApplication`
- **Mode console**: Exécuter la classe `PersonnelConsole` du package `commandLine`

### Connexion
- Compte root par défaut disponible au premier lancement
- Il est recommandé de changer le mot de passe du compte root après la première connexion

## Captures d'écran

L'interface graphique comprend les écrans suivants:
- Écran de connexion
- Liste des ligues
- Gestion des employés d'une ligue
- Profil utilisateur
- Formulaires d'ajout/modification

## Guide d'utilisation

### Pour le root (administrateur système)
- Gérer toutes les ligues
- Créer de nouvelles ligues
- Gérer tous les employés
- Nommer des administrateurs de ligues

### Pour les administrateurs de ligues
- Gérer les employés de leur ligue
- Ajouter/supprimer des employés
- Consulter l'historique des employés

### Pour les employés standard
- Consulter les informations personnelles
- Modifier certaines informations de leur profil
