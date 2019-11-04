# SNAKE
Snake game for CS349 A3.

## Instructions to Run the Game
* ensure that the font 'verdana' is installed and available
* navigate to the a3/ folder and run the program by typing 'make run'
* select one of the 3 levels on the starting screen with your mouse to start

## Controls
* use arrow keys up, down, left and right to control the snake
* while running the game, use number keys 1, 2, 3 to jump between levels
	- this will play a "level up" sound effect
* press P to pause and resume the game
* press R reset the game and go back to the home screen
* press Q to end the game and see your score
* press ESC or the exit button at the end of the game to exit


## Notes
* if a fruit appears at a position where the snake is currently moving through, it will consider the fruit as "eaten"
		- this can happen when changing between levels and for randomly generated fruits
		- you might not see the fruit appear since the snake is already on it, but you will hear the sound effect


Bonus features:
* added background music, sound effect for when the snake eats a fruit, level up sounds and end game buzzer sound
* game board wraps so that when the snake hit the edges of the board, it appears on opposite side
