# Capstone-Wizards-Of-The-Code
## Group 3
* Nikolas Woods
* Taylor Springob
* Cesar Oriol Cortez
* Roger Henry Jr.

## Wizards of the Code Task List

* [X] Setup the project repo (Nikolas)
* [X] Configure the .gitignore (Nikolas)
* [X] Research Scryfall API (Everybody | 2 hours)
* [ ] Research Elastic Search (Everybody | 2 hours)

### Infrastructure
* [X] Create docker container (Nikolas/Taylor | 1 hour)
    * [X] Test container startup (Nikolas/Taylor | 0.5 hours)
* [x] Build the database
    * [x] Write the DDL (Cesar | 1 hour)
    * [x] Write the DML for testing until API is implemented (Taylor | 1 hour)
    * [x] Write a test database (Cesar/Taylor | 1 hour)
* [x] Configure environment variables (Roger | 0.5 hour)

### Server
* [X] Add Spring DI and Mocking to the project (Roger | 0.5 hours)
* [x] Create models (Taylor | 0.5 hours)
* [X] Implement repositories
    * [x] Repository for fetching users (Nikolas | 1 hour)
    * [X] Repository for fetching cards (Roger | 1 hour)
    * [x] Repository for fetching collections/collected cards (Taylor | 2 hour)
    * [X] Unit Tests (Nikolas/Taylor/Roger | 2 hours)
* [X] Implement services
    * [X] Service for users (Nikolas | 1 hour)
    * [X] Service for cards (Taylor | 1 hour)
    * [X] Service for collections (Roger | 2 hours)
    * [X] Unit Tests (Nikolas/Taylor/Roger | 2 hours)
* [X] Implement controllers
    * [X] Controller for users (Nikolas | 1 hour)
    * [X] Controller for cards (Taylor | 1 hour)
    * [X] Controller for collections (Roger | 1 hour)
    * [X] Global Exception Handler (Taylor | 0.5 hour)
    * [X] Auth Controller (Cesar/Nikolas | 2 hours)
    * [X] Write mappers (Nikolas/Taylor/Roger | 1 hour)
    * [X] Unit Tests (Nikolas/Taylor/Roger | 1 hour)
* [X] Implement updating the database via Scryfall API
  * [X] Access today's bulk download (Taylor/Roger | 3 hours)
  * [X] Parse the returned JSON to populate the local database (Taylor/Roger | 4 hours)
* [ ] Implement Elastic Search

### Client
* [X] Create react app and trim cruft (Cesar | 0.5 hours)
* [X] Create react components (Navbar/Footer) (Cesar | 0.5 hours)
* [X] Set up React routing – (Cesar | 0.5 hours)
* [X] Implement user login/registration page (Cesar | 2 hours)
    * [X] Implement user authentication (Cesar/Nikolas | 5 hours)
    * [X] Run tests (Roger | 0.5 hours)
* [ ] Implement Home page (Cesar | 1.5 hours)
    * [ ] Run tests (Taylor | 0.5 hour)
* [ ] Implement My Collection page (Cesar | 1.5 hours)
    * [ ] Run tests (Taylor | 0.5 hour)
* [ ] Implement pages for manipulating a specific card
    * [ ] Add a new card to the collection (Cesar | 2 hours)
    * [ ] Edit an existing collected card (Cesar | 1 hour)
    * [ ] Delete a card from the collection (Cesar | 1 hour)
    * [ ] Run tests (Nikolas | 2 hours)
* [ ] Implement Admin pages
    * [ ] Implement admin view: all users (Cesar | 2 hours)
    * [ ] Implement admin actions: ban/update users (Cesar | 1 hour)
    * [ ] Implement admin: update local database (Cesar | 2 hours)
    * [ ] Run tests (Roger | 2 hours)
* [ ] Connect and integrate API with frontend (Cesar | 2.5 hours)
* [ ] Run final tests (Roger | 2 hours)
* [ ] Style everything to look pretty-ish (Nikolas | 4 hours)
