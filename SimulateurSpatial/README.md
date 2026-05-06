# Simulateur de Lancement Spatial

Un simulateur interactif en Java permettant de configurer des fusées et de simuler des lancements vers diverses destinations spatiales.

## 🚀 Fonctionnalités

- **Configuration de fusée personnalisée** : Choisissez parmi plusieurs types de lanceurs, capsules et boosters.
- **Gestion des missions** : Différents types de missions (ISS, Lune, Mars, Orbite, etc.) avec des exigences spécifiques en carburant et en charge utile.
- **Simulation réaliste** :
    - Calcul de la masse totale et du carburant nécessaire.
    - Vérification de la compatibilité pour les vols habités.
    - Gestion des contraintes de charge utile et de nombre de boosters.
    - Probabilité d'échec aléatoire (anomalies techniques).
- **Suivi et Statistiques** :
    - Historique des lancements sauvegardé en CSV.
    - Statistiques globales (taux de réussite, coûts totaux).

## 🛠️ Architecture du Projet

Le projet suit une architecture orientée objet avec les packages suivants :
- `capsule` : Contient les différents modèles de capsules (Apollo, Orion, CrewDragon, etc.).
- `lanceur` : Contient les types de lanceurs (Saturne V, Ariane 5, Falcon 9, SLS).
- `mission` : Définit les objectifs et les calculs de carburant associés.
- `modele` : Contient les classes de base comme `Fusee`, `Booster` et `Lancement`.
- `Simulateur` : Point d'entrée principal avec le menu interactif.

## 💻 Installation et Utilisation

### Prérequis
- Java JDK 17 ou supérieur.

### Compilation
```bash
javac -d bin src/*.java src/capsule/*.java src/lanceur/*.java src/mission/*.java src/modele/*.java
```

### Exécution
```bash
java -cp bin Simulateur
```

## 📊 Données
Les résultats des lancements sont automatiquement enregistrés dans le fichier `historique.csv`.
