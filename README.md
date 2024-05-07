
# OneRepMax Calculator

## Project Overview

The OneRepMax Calculator is an Android application developed in Kotlin that calculates the theoretical one-repetition maximum (one-rep max) for weightlifting exercises based on historical workout data. It displays the overall personal record (PR) for each exercise and provides a plot of the historical pattern of the user’s one-rep max over time.

## Design and Architecture Overview

### Modular Design
The project structure is designed to maintain a clean separation of concerns and to promote modular architecture, making the application easy to maintain and scale. Key components include data management, domain logic, and presentation, each isolated within their respective packages.

### Repository Pattern
This pattern allows the application to switch data sources without impacting other parts of the code. This could be useful, for example, if you decide to migrate from local storage to a remote database in the future.

`ExerciseLocalTxtRepositoryImpl.kt` is a concrete implementation that provides methods to fetch data from local storage, parsing text files that contain workout data. This setup ensures that the business logic remains completely decoupled from the data source.

### Use Case Abstraction

Each business operation is encapsulated within its own use case, found in the usecase package. This design aligns with principles of Clean Architecture, ensuring that each use case is responsible for a single aspect of the business logic. This also makes every bussiness operation very testeable and independent.

### Dependency Injection (DI)
Dependency injection is implemented to further decouple the application components, making them more modular and easier to manage. Dependency injection allows for swapping implementations and managing dependencies across the application without hard coding them into the codebase.

## Project Structure
The application adopts a clean architecture with a focus on separation of concerns and scalability:

- `data`:
    - `local`: Contains file reading utilities to process input data.
    - `model`: Houses the data transfer objects.
    - `parser`: Responsible for parsing workout data from files.
    - `repository`: Implements the logic to access data sources.
- `di`: Contains dependency injection modules for providing instances of classes needed     throughout the app.
- `domain`:
    - `entity`: Includes domain models.
    - `usecase`: Encapsulates business logic for calculating the one-rep max and fetching     historical details.
- `presentation`:
    - `navigation`: Manages screen navigation.
    - `theme`: Defines styling and theming of the app.
    - `view`: UI components and screens.
    - `viewmodels`: ViewModels that support UI with necessary data.
- `test`: Contains unit tests to ensure the functionality and reliability of use cases and data management.

## Setup Instructions
 - Clone the repository to your local machine.
 - Open the project in Android Studio Iguana or later.
 - Ensure you have the Kotlin plugin installed and updated in Android Studio.
- Start the application in an emulator or on a physical device.

## Modify Input File

To change the input file you should go to `app/src/main/assets` and modify the file `workout_data.txt` and include the data in the following format :

`Date of workout, Exercise Name, Reps, Weight`

Example: 

`Oct 11 2020,Back Squat,6,245`
