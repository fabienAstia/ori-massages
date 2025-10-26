# Ori Massages

> Site (en construction) de réservation de **massages** et **soins du visage**, avec espace utilisateur et praticienne.

---

## ✅ To-Do

- [ ] Interface UI (React)
- [X] Modélisation Merise (MCD, MLD)
- [ ] API Java (Spring Boot)
- [ ] Authentification & gestion des rôles (client / praticienne)
- [ ] Système de réservation (créneaux, calendrier)
- [ ] Tableau de bord & historique
- [ ] Intégration PostgreSQL
- [ ] Déploiement Docker

---

## 🧱 1. Architecture

| Couche | Technologie | Version |
|---------|--------------|----------|
| Frontend | React.js | 19 |
| Backend | Java (Spring Boot) | 21 |
| Base de données | PostgreSQL | 16 |

---

## 🧩 2. Conception

### 2.1. Modélisation Merise

- **MCD (Modèle Conceptuel de Données)** :  
  Entités principales prévues :  
  - `Utilisateur` (client ou praticienne)  
  - `Massage`  
  - `SoinVisage`  
  - `Réservation`  
  - `CréneauHoraire`
  - `Prix`
  - `Message`  

- **MLD (Modèle Logique de Données)** :  
  Traduction prévue vers PostgreSQL via ORM (JPA / Hibernate).  

### 2.2. Cas d’utilisation (Use Cases)

- Réservation d’un massage ou soin par un utilisateur  
- Consultation du planning d’une praticienne  
- Gestion des créneaux disponibles  
- Consultation de l’historique de rendez-vous  
- Gestion des comptes et profils utilisateurs  

---

## ⚙️ 3. Installation (prévision)

```bash
# Clone du projet
git clone https://github.com/ton-profil/ori-massages.git

# Installation des dépendances front
cd frontend
npm install

# Lancement du front
npm run dev

# Lancement du backend (depuis IntelliJ / Maven)
mvn spring-boot:run
