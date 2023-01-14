# Sudoku

Sudoku is a web-application built with Next.js and Material UI that creates sudoku puzzles and allows users to solve them. Users may receive assistance from the "check" and "solve" buttons, which checks their progress and solves the puzzle, respectively. The goal of the project was to solidify understandings of API fetches and gain more practice with frontend engineering. In the future, I hope to implement the Algorithm X with Dancing Links to solve sudoku puzzles, as it is faster than the already implemented backtracing algorithm.

<p align="center">
  <img
    src="project_screenshots/empty-sudoku.png"
    alt="Empty Sudoku"
    title="Empty Sudoku"
    style="display: inline-block; margin: 0 auto; width: 350px; height: 350px">
  <img
    src="project_screenshots/sample-sudoku.png"
    alt="Sample Sudoku"
    title="Sample Sudoku"
    style="display: inline-block; margin: 0 auto; width: 350px; height: 350px">
</p>

## Getting Started

First, clone the repository:

    git clone https://github.com/Calvineng72/Sudoku.git
      
Then, install the required dependencies: 
 
    cd frontend
 
    npm install
      
Next, run the development server:

    npm run dev
    
Finally, execute the backend server and go to the following link:

    http://localhost:3000

## Running the tests

In order to run the backend tests for this project, you can navigate to the backend directory and run the classes containing tests. As for the frontend tests, simply type npm test within the frontend directory. 

## Deployment

I am working on deploying this application with Heroku once I complete Algorithm X.

## To-Do

I want to deploy this application with Heroku and implement the Algorithm X. In addition, there is work to be done in generating sudoku puzzles with unique solutions in order to guarantee a user's solution is the same as that there puzzle is compared with. Another possibility is ensuring that a user's solution is a valid sudoku. 

## Acknowledgments

I used the following resources to develop this project:

- https://javascript.plainenglish.io/build-a-sudoku-solver-app-with-javascript-32afbe384d1e
- https://medium.com/javarevisited/building-a-sudoku-solver-in-java-with-dancing-links-180274b0b6c1#:~:text=Dancing%20Links%20is%20a%20technique,exercise%20for%20beginners%20in%20programming.
- https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/sudoku.paper.html
- https://www.kth.se/social/files/58861771f276547fe1dbf8d1/HLaestanderMHarrysson_dkand14.pdf
- https://www.geeksforgeeks.org/implementation-of-exact-cover-problem-and-algorithm-x-using-dlx/
- https://en.wikipedia.org/wiki/Sudoku_solving_algorithms
- http://julian.togelius.com/Moraglio2006Product.pdf
- https://osuva.uwasa.fi/bitstream/handle/10024/10326/Osuva_Amil_Mantere_2019a.pdf?sequence=2&isAllowed=y
