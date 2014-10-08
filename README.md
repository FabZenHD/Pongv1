Pongv1
======

Alex B's pong program

Program Dependencies:
  Pong is just a program to create and begin the function of PongFrame.
  PongFrame creates the frame in which the game is played, 
    takes the keyboard input, and updates the game through DrawPong's paint method.
  DrawPong is what draws the game on PongFrame. It is also where the score is kept, 
    positions of paddles and the ball is stored, and where collisions are recognized.
    
  The version with a basic AI has the same dependencies, except "AI" is appended to 
  the end of each program name.
  
Controls:
  For the 2-player version, the left-hand paddle is controlled with the W and A keys,
    and the right-hand paddle is controlled with the up and down arrow keys.
  For the AI version, the left-hand panel is controlled with the up and down arrow keys,
    and the right-hand panel is controlled by an AI which tries at all times to keep
    the ball's center within the middle third of its paddle's center.
    
Gameplay and Altering Values:
  Every time the ball hits a paddle its speed increases by a set amount, found on line
    39 of both DrawPong and DrawPongAI.
  For the AI version, the AI has a higher speed than the player to compensate for the 
    player's ability to predict trajectories. The AI's speed is found on line 41 of DrawPongAI.
  The variables for the ball's speed and the players' speeds are on, respectively, lines 35 and 40 of
    both DrawPong and DrawPongAI.
  The height and width of the paddles are found on lines 50 and 51 of DrawPong, 
    and lines 51 and 52 of DrawPongAI.
  The diameter of the ball is set on line 63 of DrawPong and line 64 of DrawPongAI.
  The height and width of the frame are set on lines 10 and 11 of both PongFrame and PongFrameAI.
  
  The changing of any of the values above should not break the code, although if they are set
    to unreasonable values(e.g. height of paddle is larger than height of frame) it may interfere
    with the actual playing of the game.
