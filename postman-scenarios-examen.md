# Tests Postman - Scénarios d'Examen Spring Boot

## Configuration de base
- URL de base: `http://localhost:8080`
- Headers pour toutes les requêtes POST/PUT:
  - `Content-Type: application/json`

---

## 1. Ajouter un utilisateur (1.5 pts)

### Méthode: POST
### URL: `/api/utilisateurs/ajouter`

### Test 1.1 - Ajouter Sara Bahria
```json
{
    "nomComplet": "Sara Bahria",
    "email": "sara.bahria@gmail.com"
}
```

### Test 1.2 - Ajouter Lamia Salhi
```json
{
    "nomComplet": "Lamia Salhi",
    "email": "lamia.salhi@gmail.com"
}
```

### Test 1.3 - Ajouter Ahmed Salmi
```json
{
    "nomComplet": "Ahmed Salmi",
    "email": "ahmed.salmi@gmail.com"
}
```

---

## 2. Créer des Animaux (Prérequis pour les demandes)

### Méthode: POST
### URL: `/api/animaux`

### Animal 1 - Medor (Chien, Sain)
```json
{
    "nom": "Medor",
    "espece": "Chien",
    "etat": "SAIN"
}
```

### Animal 2 - Minette (Chat, Blessé)
```json
{
    "nom": "Minette",
    "espece": "Chat",
    "etat": "BLESSE"
}
```

### Animal 3 - Lucky (Chien, Sain)
```json
{
    "nom": "Lucky",
    "espece": "Chien",
    "etat": "SAIN"
}
```

### Animal 4 - Bella (Chat, Blessé)
```json
{
    "nom": "Bella",
    "espece": "Chat",
    "etat": "BLESSE"
}
```

### Animal 5 - Pika (Chien, Sain)
```json
{
    "nom": "Pika",
    "espece": "Chien",
    "etat": "SAIN"
}
```

### Animal 6 - Michelou (Chien, Sain)
```json
{
    "nom": "Michelou",
    "espece": "Chien",
    "etat": "SAIN"
}
```

---

## 3. Ajouter une demande d'adoption et ses animaux (2 pts)

### Méthode: POST
### URL: `/api/demandes-adoption/ajouter-avec-animaux`

### Test 3.1 - Demande C1 (Sara Bahria)
```json
{
    "code": "C1",
    "dateDemande": "2025-04-01T00:00:00",
    "commentaire": "Demande urgente",
    "statut": "DEMANDEE",
    "utilisateur": {
        "id": 1
    },
    "animaux": [
        {
            "idAnimal": 1
        }
    ]
}
```

### Test 3.2 - Demande C2 (Sara Bahria)
```json
{
    "code": "C2",
    "dateDemande": "2025-03-10T00:00:00",
    "commentaire": "Famille d'accueil",
    "statut": "VALIDEE",
    "utilisateur": {
        "id": 1
    },
    "animaux": [
        {
            "idAnimal": 2
        },
        {
            "idAnimal": 3
        }
    ]
}
```

### Test 3.3 - Demande C3 (Lamia Salhi)
```json
{
    "code": "C3",
    "dateDemande": "2024-12-14T00:00:00",
    "commentaire": "Famille réunie",
    "statut": "REFUSEE",
    "utilisateur": {
        "id": 2
    },
    "animaux": [
        {
            "idAnimal": 4
        },
        {
            "idAnimal": 5
        },
        {
            "idAnimal": 6
        }
    ]
}
```

---

## 4. Affecter plusieurs demandes à un utilisateur (2 pts)

### Méthode: POST
### URL: `/api/demandes-adoption/affecter-utilisateur`

### Test 4.1 - Affecter C1 et C2 à Sara Bahria
```json
{
    "codeAdoptions": ["C1", "C2"],
    "email": "sara.bahria@gmail.com"
}
```

### Test 4.2 - Affecter C3 à Lamia Salhi
```json
{
    "codeAdoptions": ["C3"],
    "email": "lamia.salhi@gmail.com"
}
```

---

## 5. Rechercher animaux blessés par état et date (2.5 pts)

### Méthode: GET
### URL: `/api/animaux/rechercher-par-etat-date`

### Test 5.1 - Rechercher animaux BLESSE après 2024-01-01
### URL: `/api/animaux/rechercher-par-etat-date?etat=BLESSE&dateD=2024-01-01`

### Test 5.2 - Rechercher animaux SAIN après 2025-01-01
### URL: `/api/animaux/rechercher-par-etat-date?etat=SAIN&dateD=2025-01-01`

---

## 6. Vérifier si un animal blessé a une adoption demandée/validée (2.5 pts)

### Méthode: GET
### URL: `/api/animaux/animal-adopte/{nomAnimal}`

### Test 6.1 - Vérifier si Minette est adoptée
### URL: `/api/animaux/animal-adopte/Minette`

### Test 6.2 - Vérifier si Bella est adoptée
### URL: `/api/animaux/animal-adopte/Bella`

### Test 6.3 - Vérifier si Medor est adopté
### URL: `/api/animaux/animal-adopte/Medor`

---

## 7. Vérification du logging (Aspect)

### Test 7.1 - Vérifier le log lors de l'ajout d'utilisateur
### Méthode: POST
### URL: `/api/utilisateurs/ajouter`

```json
{
    "nomComplet": "Test Aspect",
    "email": "test.aspect@gmail.com"
}
```

**Résultat attendu dans les logs:** 
```
Bienvenue dans le service ajouterUtilisateur
```

---

## 8. Tâche automatique et utilisateurs multi-adoption (Vérification)

### La tâche automatique s'exécute tous les 20 secondes chaque mardi
### Pour vérifier manuellement les utilisateurs avec multi-adoption:

### Méthode: GET
### URL: `/api/utilisateurs/utilisateurs-multi-adoption`

**Résultat attendu:**
```json
[
    {
        "id": 1,
        "nomComplet": "Sara Bahria",
        "email": "sara.bahria@gmail.com"
    }
]
```

**Résultat attendu dans les logs de la tâche automatique:**
```
=== Tâche automatique - Mardi ===
Utilisateurs avec au moins 2 adoptions contenant des animaux SAINS:
- Sara Bahria (sara.bahria@gmail.com)
Total trouvé: 1
```

---

## Ordre d'exécution des tests (selon l'examen):

1. **Ajouter les utilisateurs** (Tests 1.1, 1.2, 1.3) - Méthode 1
2. **Créer les animaux** (Prérequis - utiliser les JSONs ci-dessus)
3. **Ajouter demandes d'adoption avec animaux** (Tests 3.1, 3.2, 3.3) - Méthode 2
4. **Affecter demandes à utilisateur** (Tests 4.1, 4.2) - Méthode 3
5. **Rechercher animaux par état et date** (Tests 5.1, 5.2) - Méthode 4
6. **Vérifier aspect logging** (Test 7.1) - Méthode 5
7. **Vérifier animal adopté** (Tests 6.1, 6.2, 6.3) - Méthode 6
8. **Tester utilisateurs multi-adoption** (Test 8) - Méthode 7

---

## Vérifications supplémentaires:

### Récupérer tous les utilisateurs
### Méthode: GET
### URL: `/api/utilisateurs`

### Récupérer toutes les demandes d'adoption
### Méthode: GET  
### URL: `/api/demandes-adoption`

### Récupérer tous les animaux
### Méthode: GET
### URL: `/api/animaux`

### Récupérer utilisateurs avec adoptions multi-état (Méthode 7)
### Méthode: GET
### URL: `/api/utilisateurs/utilisateurs-multi-adoption`

---

## Codes de réponse attendus:

- **201 Created** : Pour les créations réussies
- **200 OK** : Pour les récupérations et affectations réussies
- **400 Bad Request** : Pour les erreurs de validation
- **404 Not Found** : Pour les ressources non trouvées

---

## Notes importantes:

1. Assurez-vous que MySQL est démarré et la base de données `adoption_animals` existe
2. L'application doit être lancée avec `mvn spring-boot:run`
3. Les IDs des utilisateurs et animaux peuvent varier selon l'ordre de création
4. Ajustez les IDs dans les requêtes selon les réponses obtenues
5. La tâche automatique ne s'exécute que les mardis toutes les 20 secondes
