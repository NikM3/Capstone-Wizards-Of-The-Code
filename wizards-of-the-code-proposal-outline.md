
## Problem Statement

**Context:** Magic the Gathering is a huge game with a vast amount of cards, well over 20,000 of them. As a person gets more and more deep into the hobby, their collection will grow and grow, and they will soon find themselves will binders or even shoeboxes full of them.

**Core Problem:** This can very quickly become overwhelming and when it comes time to building decks, they may spend hours of their time searching through their collection to try and see if they have certain cards. And the time spent flipping through these pages, digging through bulk takes away from their time to actually enjoy what they like about the game: either filling in cards they may be missing, or finding the one specific one they need for the deck they want to play.

**Impact on Users:** Players miss out on being able to actually play the game. Time spent searching for cards is time that could have been spent enjoying. For Collectors, they don't want to be going through their sets slowly card-by-card, having to put it together piecemeal and trying to figure out if they have this one specific one or not.

**Opportunity for solution:** By creating an application to view, store, and manage collections, no matter what type of hobbyist they are, they will benefit. A player can quickly search through their collection to see if they have the card to fit into their next deck, while collectors can see if they need to go hunt down that one rare that they are missing.

## Technical Solution

**Overview of the Solution:** We are going to design a web application that allows for users to create a digital card collection. This collection will allow them to add cards to it by searching for them through a database of cards in MTG, edit details about the card such as its printing, how many they have, the condition it's in, as well as if it is currently in use in a deck of theirs. Additionally, they will be able to update these details about the cards in their collection, and lastly remove cards from their collection as well.

Additionally, admins of the website will be able to perform these same functionalities to keep track of a collection of their own, but also to update the local database of cards and managing users.

#### **Key Features and Functionalities:**

- **Card Creation:** Admins will be able to update the local database with all the cards for all users to be able to add individually to their collection.
- **User Management:** An admin will be able to manage the users of the application. Either soft-banning them (perhaps if they have bad etiquette, or make too many API calls) to restrict their access, or by removing them all together if they are a repeat/serious offender.
- **Search for a card:** Users will be able to find a card via a search bar with filters in order to get the card they are looking for. They can filter by things such as a card's colors, its rarity, and card type.
- **Add a Card:** Users will be able to then add cards they search for to their collection. They will be able to choose options such as the printing of the card (the set it is from, a card can be in many sets), how many of that particular card they own, the condition it's in (Near Mint, Lightly Played, Moderately Played, Heavily Played, Damaged), as well as if they are currently using that card in a deck at the moment, or if it is just free-floating in their collection.
- **Updating a Card:** A user will have the ability to update the individual cards in their collection. This includes the ability to update the printing, how many of the card they own, its condition, as well as if it is in use or not.
- **Deleting a Card:** If a user perhaps traded, or sold a card, then they will not own it anymore. Thus, they will have the ability to delete it entirely from their collection.
- **Viewing their Collection:** The main list view of the application. Here a user will see a list of their cards that they can filter by colors, rarity, and types, as well as search for an individual one. It will display basic information such as the card's name, its mana cost, its colors, rarity, set, and if it is in use. Additionally, they can click on a card in this list to see a more detailed view of it.
- **User Registration:** Users (guests) can create an account, log in, and track their collection.

#### **User Scenarios:**

- **Scenario 1:** Bob is an avid collector of trading cards. He is just getting into MTG and has bought dozens of packs of the latest sets to begin his collection. As he opens packs and pulls cards, he can add them to his collection as he goes, as well as update them as he pulls more copies of them. Easily tracking growing collection without any fuss.
- **Scenario 2:** Sally has a tournament coming up in a few weeks, and she wants to be ready for it. She doesn't know though if she has a certain card. She has been playing for years so her collection is massive. By quickly searching through her tracked collection with the app she sees that she does have the card, and it's in use in a deck of hers. Perfect! She can pull that out and add it to her tournament deck without having to dig through her massive amount of binders and shoeboxes of bulk.

#### **Technology Stack**

**Frontend:** React for building the interactive UI with dynamic features that responds to the user.
**Backend:** Spring Boot for creating a secure REST API to handle user authentication, card management, and data storage.
**Database:** MySQL to store user card data, and collections including relationships between users and their collections
**Scryfall Integration:** External Scryfall API for fetching the extensive list of cards in Magic the Gathering.
**Authentication:** JWT (JSON Web Token) for secure user login and role management.

## Glossary

**Magic The Gathering (MTG):** A trading card game created in 1993, owned by Wizards of the Coast. Games can have anywhere from 2-4 players across a variety of different formats with over 20,000 cards to pick from.

**Card:** The individual components in A game of Magic. They can have many different types such as Artifact, Creature, Sorcery, or Land as well as its name, its cost to play, rarity, and the set it is found in.

**Guest:** A person whose hobby is MTG. They might be casual, playing at home with their friends, competitive, attending tournaments, or even just a collector. All Guests of the application are users, but not all users will be guests.

**Admin:** A user with an administrator role. They have more priviliges in the Collection Application, such as being able to update the card database, or user management. All Admins are users, but not all users are admins.

## High Level Requirements

**Manage 4-7 Database Tables (Entities) that are Independent Concepts**
Plan to meet requirement:
We plan to implement 4-7 independent database entities that represent different core concepts within the application. They are not entities connected by simple bridge tables, but wille each have unique attributes and relationships to other examples. For example, there will be separate tables for users, cards, collections, and roles.

**MySQL for Data Management**
Plan to meet requirement:
We will use MySQL for our relational database management system for storing and retrieving our data. The schema will be designed to ensure data integrity and for optimized querying. Spring Data will be used in tandem with MySQL to manage entities and perform CRUD operations.

**Spring Boot, MVC, JDBC, Testing, React**
Plan to meet requirement:
The backend of the Application will be implemented in Java using Spring Boot, utilizing the MVC (Model-View-Controller) pattern for organization and program structure. The JDBC will be used for handling the database connection and transactions. It will have full testing for the Service as well as the Data. The Frontend will be created with React, enabling a responsive, functional, and clean looking UI, with its own testing plan to meet application requirements.

**HTML and CSS UI Built with React**
Plan to meet requirement:
The user interface will be built using React. This will ensure a clean, professional, and user-first design with HTML and CSS. It will follow modern web development practices such as: responsive design so it will work regardless of platform, and ease of use and access no matter the technological skill-level of the user. The layout will be designed in a way to be well organized and intuitive for users to easily browse their collections, as well as add cards to it.

**Sensible Layering and Pattern choices**
Plan to meet requirement:
We are going to follow best practices for application structure, utilizing layered design patterns. This includes separating source files and their logic across distinct layers such as controllers, security, domain, and data. Backend code will follow the Single Responsibility Principle to ensure ease of understanding, maintenance, and scalability.

**Full Test Suite for Data and Domain Layers**
Plan to meet requirement:
The project will have a full test suite. This includes unit testing for the data layer (the repositories and database communication), as well as the domain layer (services and models). Additionally, integration tests will be written to make sure that all of the application's components come together and function as expected.

**Must Have at Least 2 Roles**
Plan to meet requirement:
The application will implement role-based access control. The system will have two roles: Guests and Admins. Guests will be able to create and manage their own collection, while admins will have the ability to refresh the card database, as well as manage users. These roles will be securely handled through authentication and authorization.

## User Stories

#### **Add Cards to the Collection**
**Goal:** As a user, I want to be add cards to my collection.
Plan to meet requirement: Users will be able to search for a card with filters for finding what they are looking for, then fill a form out where they can select the printing of the card, the quantity of it, its condition, and if it is currently in use in a deck or not. Once submitted it will be added to their collection for viewing.
Precondition: The user must be logged in with the GUEST or ADMIN role. The card cannot already be in the collection.

#### **Update a Card in the Collection**
**Goal:** As a user, I want to be able to update an existing card in my collection.
Plan to meet requirement: An edit feature will be provided that will allow users to modify collection details about a card already present in their collection. This includes details such as the printing of it, the quantity they own, the condition it is in, and if it is in use or not. Only cards already in a user's collection can be edited.
Preconditions: The user must be logged in with the GUEST or ADMIN role. The card must already be in the collection.

#### **Remove a Card from a Collection**
**Goal:** As a user, I want to be able to remove a card from my collection.
Plan to meet requirement: Users will be able to delete cards that are in their collection. This will remove a card completely from their collection, meaning it will no longer appear in a user's front-facing UI for their collection.
Preconditions: The user must be logged in with the GUEST or ADMIN role. The card must already be in the collection.

#### **View Cards in the Collection**
**Goal:** As a user, I want to be able to view my collection in a clear and meaningful way.
Plan to meet requirement: A default list view of a user's collection will be shown to them. This will include basic details such as the card's name, its cost, quantity, printing, and if it is currently in use. A user will have the ability to click an individual card to get a more detailed view of it, as well as filter and search their collection to find certain card(s) that they are looking for.
Preconditions: The user must be logged in with the GUEST or ADMIN role.

#### *Manage the Card Database**
**Goal:** As an ADMIN, I want to be able to update the local card database to make sure it has all the latest cards in it for users.
Plan to meet requirement: In the ADMIN view, a feature will be provided to make a call with the Scryfall external API to fetch all card data in order to update our local repository. This ensures it is up to date, and we do not have to spend the ludicrous amount of time it would take to add new cards individually to the database.
Preconditions: The user must be logged in with the ADMIN role.

#### **Manage Users**
**Goal:** As an ADMIN, I want to be able to manage users of the application, including changing their permissions and even removing if necessary.
Plan to meet requirement: In ADMIN view, a feature will be provided to manage users. This means the ability to change a user's permissions to perhaps only search or manage their current collection, or even removing them outright, as well as updating usernames.
preconditions: The user must be logged in with the ADMIN role.



#### **Create multiple Collections (Optional Stretch Goal)**
**Goal:** As a user, I want to be able to create multiple collections to add my cards to and better sort them.
Plan to meet requirement: users will be able to create multiple collections to spread their cards over. This will be provided with a form to give the collections names and descriptions, such as "For Trade", or "Commonly Used Cards."
Precondition: User must be logged in with the GUEST or ADMIN role.

## Learning Goals

#### **Learning Goal:** We want to learn how to integrate an external API for initial database population

- **Application:** We will use the Scryfall API to fetch the list of cards found within MTG and add them to our local database to be browsed by users to add to their collections.
- **Research and Resources:** Starting with the Scryfall API documentation and any tutorials on implementations.
- **Challenges:** There is a vast amount of data, with well over 20,000 cards that will need to be fetched, so learning best practices for retrieving and manipulating datasets that large.
- **Success Criteria:** If we are able to seamlessly integrate this external API for populating our database, ensuring users are able to find even the latest cards to add to their collection as well as displaying them properly, then this goal will be considered achieved.

#### **Learning Goal:** Implementing Elastic Search for a Positive User Experience when Looking for Cards

- **Application:** We will make use of the Elasticsearch Engine to provide more ease-of-access when it comes to searching. Magic cards can have long, or hard to remember names. By implementing Elastic Search, users will not have to be exactly precise with what they search for, and if they can remember some details about the card, they will be able to find it.
- **Research and Resources:** Start with the official Elasticsearch documentation, and follow it with any tutorials or courses on its implementation in a web application
- **Challenges:** Unfamilirity with the technology, and learning how to implement it into our program. It needs to be able to make the user's life easier. There is a lot of data that will need to be searched through with MTG cards so they must be able to find what they are searching for even without the exact name.
- **Success Criteria:** If users are able to search both their collection, as well as the database for cards that find what they are looking for dynamically with ease then this learning goal will be considered achieved.

## Class Diagram
.
└── src/
    ├── main/
    │   ├── java/
    │   │   └── wotc/
    │   │       ├── App.java
    │   │       ├── controllers/
    │   │       │   ├── AuthController.java
    │   │       │   ├── CardController.java
    │   │       │   ├── CollectionController.java
    │   │       │   └── GlobalExceptionHandler.java
    │   │       ├── data/
    │   │       │   ├── UserRoleJdbcTemplateRepository.java
    │   │       │   ├── UserRoleRepository.java
    │   │       │   ├── UserJdbcTemplateRepository.java
    │   │       │   ├── UserRepository.java
    │   │       │   ├── CardJdbcTemplateRepository.java
    │   │       │   └── CardRepository.java
    │   │       ├── domain/
    │   │       │   ├── ActionStatus.java
    │   │       │   ├── CardService.java
    │   │       │   └── CollectionService.java
    │   │       ├── models/
    │   │       │   ├── UserRole.java
    │   │       │   ├── User.java
    │   │       │   ├── Card.java
    │   │       │   ├── CollectedCard.java
    │   │       │   └── Collection.java
    │   │       └── security/
    │   │           ├── AppUserService.java
    │   │           └── SecurityConfig.java
    │   └── resources/
    │       └── application.properties
    └── test/
        ├── java/
        │   └── wotc/
        │       ├── controllers/
        │       │   ├── CardControllerTest.java
        │       │   └── CollectionControllerTest.java
        │       ├── data/
        │       │   ├── UserRoleJdbcTemplateRepositoryTest.java
        │       │   ├── UserJdbcTemplateRepositoryTest.java
        │       │   └── CardJdbcTemplateRepositoryTest.java
        │       └── domain/
        │           ├── CardServiceTest.java
        │           └── CollectionServiceTest.java
        └── resources/
            └── application.properties

## Class List

## Wizards of the Code Task List

* [ ] Setup the project repo
* [ ] Configure the .gitignore

## Server
* [ ] Add Spring DI to the project
* [ ] Create models
* [ ] Implement repositories
    * [ ] Repository for fetching users
    * [ ] Repository for fetching cards
    * [ ] Unit Tests
* [ ] Implement services
    * [ ] Service for collections
    * [ ] Service for cards
    * [ ] Unit Tests
* [ ] Implement controllers
    * [ ] Controller for collections
    * [ ] Controller for cards
    * [ ] Global Exception Handler
    * [ ] Auth Controller
    * [ ] Write mappers
    * [ ] Unit Tests
* [ ] Build the database
    * [ ] Write the DDL
    * [ ] Write the DML
    * [ ] Write a test database
* [ ] Implement updating the database via Scryfall API
    * [ ] Access today's bulk download
    * [ ] Parse the returned JSON to populate the local database
    
## Client
* [ ] Create react app and trim cruft
* [ ] Implement user login/registration page
    * [ ] Implement user authentication
    * [ ] Run tests
* [ ] Implement Home/My Collection pages
    * [ ] Run tests
* [ ] Implement pages for manipulating a specific card 
    * [ ] Add a new card to the collection
    * [ ] Edit an existing collect
    * [ ] Run tests
* [ ] Style everything to look pretty-ish