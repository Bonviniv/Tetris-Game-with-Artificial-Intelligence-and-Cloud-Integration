# Tetris Angular Project

This project is an Angular application that displays a functional Tetris game originally developed in Java. The goal of this project is to provide a web-based version of the classic Tetris game, allowing users to play directly in their browsers.

## Project Structure

- **src/app/components/tetris/**: Contains the Tetris component files.
  - `tetris.component.ts`: Defines the TetrisComponent class responsible for rendering the game.
  - `tetris.component.html`: HTML template for the Tetris component.
  - `tetris.component.scss`: Styles specific to the Tetris component.
  - `tetris.component.spec.ts`: Unit tests for the TetrisComponent.

- **src/app/services/**: Contains the Tetris service files.
  - `tetris.service.ts`: Handles game logic and state management.

- **src/app/models/**: Contains data models used in the Tetris game.
  - `tetris.model.ts`: Defines shapes of Tetris blocks and game board structure.

- **src/app/**: Main application component files.
  - `app.component.ts`: Entry point for the Angular application.
  - `app.component.html`: HTML template for the main application component.
  - `app.component.scss`: Styles for the main application component.
  - `app.module.ts`: Main application module.

- **src/assets/tetris-java/**: Contains assets related to the Tetris game.

- **src/environments/**: Contains environment-specific settings.
  - `environment.ts`: Development settings.
  - `environment.prod.ts`: Production settings.

- **src/index.html**: Main HTML file for the Angular application.

- **src/styles.scss**: Global styles for the Angular application.

- **angular.json**: Configuration file for the Angular CLI.

- **package.json**: Configuration file for npm, listing dependencies and scripts.

- **tsconfig.json**: Configuration file for TypeScript.

## Getting Started

To run the project locally, follow these steps:

1. Clone the repository.
2. Navigate to the project directory.
3. Install the dependencies using `npm install`.
4. Start the development server with `ng serve`.
5. Open your browser and navigate to `http://localhost:4200` to play the Tetris game.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License. See the LICENSE file for details.