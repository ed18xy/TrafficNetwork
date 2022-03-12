# javaclasses
Compile: javac -d classes GameEngine/*.java Vehicles/*.java DecisionMaking/*.java Map/*.java Graphics/*.java Player/*.java
Run:
1.copy map.txt into generated compiled folder classes in package Map if doesn't exist. 
2.navigate: cd classes
3.run: java GameEngine.Main


COSC 3P91 – Assignment 2 
ELYA DENYSOVA, Brock University, Canada
ACM Reference Format:
Elya Denysova. 2022. COSC 3P91 – Assignment 2 
1 GENERAL
My game has 6 packages : GameEngine, Map, Vehicles, Player, DecisionMaking and Graphics.
A Map illustration1for provided .txt map is included in a Map package. A .txt file describes
the adjacency matrix for the directed graph. GameEngine has a Class Main that is running
the Game. I have Graphics package that doesn’t do anything from previous design and I
decided to leave for later (in case we will be implementing something like that). Traffic
Network is represented by Map as a collection of Road segments with lanes that contain cars.
Mobile entities are Cars, Trucks, and Buses in package Vehicles. Time-stepped simulation is
a reference to a Runnable lambda expression. Scanner and Array were used from java.util.
The main Objective is to navigate vehicle properly to avoid collisions. After each collision
car would take damage. If car’s condition reaches Broken, the game is over. The will also be
over if a user gets off the Map2
1.1 GameEngine
This is the main package that runs the game. The Main class generates all the necessary
objects before the game starts. Then it runs the simulation. In the method addPlayer
anonymous class was used to link a car to Players name. For later usage when implementing
multiplayer. A package has two public enum classes: Direction (East,West,North,South;)
and Sides(Right, Left). The former is used to simulate turns in intersections and the latter
is for lane changing options. It also has a class WrongInputException, which is thrown when
player enters input that does not correspond to any options.
1.2 Map
Representation of a map is graph with nodes as Intersections and edges as Road Segments. It
is implemented in a form of adjacency matrix with addition of directions. 3 A class generates
new Map consisting of Intersections and RoadSegments arrays. RoadSegment connects two
Intersections and consists of 1 or more Lanes. The RoadSegment represents only one side
(one direction of movement). A Lane has a direction and length. The Lane has a defined
length so depending on the length of vehicle it might or might not have the space for it. To
check this the getAvailableSpace method can be used.Additionally to the length requirement,
it can only have 10 vehicles at a time to enable safe distance. The Intersection class has a
1map.pdf drawn by me
2gets to an intersection that doesn’t lead to anywhere
3N, S, E, W
Author’s address: Elya Denysova, Brock University, 1812 Sir Isaac Brock Way, St. Catharines, ON, L2S
3A1, Canada.
ACM Comput. Surv., Vol. 0, No. 0, Article 1. Publication date: March 2022.
1:2 Elya Denysova
name and list of available turns with all the options in which driver can turn. The Turn
class has a direction which this turn represents in Intersection.
1.3 DecisionMaking
This package has everything that Player or NPC might need to take an action. A Driver
Interface specifies the required functionality to navigate on the map and drive the car. It
includes following methods declarations:
∙ Look() - to get surroundings info and see available option
∙ Verify() - verify that the move can be made (has a default implementation)
∙ Gamble() - to take a chance if the move is possible but there is not enough space now
∙ MakeMove() - to move if decision was verified or player decided to gamble. May result
in a collision that causes damage to both vehicles.
There are few more default helping methods (has all needed explanation in comments).
public default <T> void wrongInput(T uInput) throws Exception uses Type Parameter
to display the input back to user in case it didn’t correspond to expected value/letter.
Damage to the vehicles is given according to cars weight and reputation. There are two
Driver implementations for Player (PlayerDriverImpl) and for the NPC (PCDriverImpl).
Player is a driver so a Player class extends PlayerDriverImpl. PCDriverImpl is linked to a
vehicle and allows NPC to drive it.
1.4 Player
A Player has a Vehicle. Player can look at the list of available actions at the current location
and chooseNextMove by typing the corresponding action code into the terminal. A player
method updater updates the location of the vehicle. turns.stream().sorted().forEach(s ->
System.out.println(s)); Stream used to sort options by the action code that is printed at the
start.
1.5 Vehicles
A super class Vehicle has the following attributes:
∙ speed : int
∙ damageStatus : int - a value that corresponds to car’s condition. Increases after an
unsuccessful gamble and collision. Heavier vehicles take less damage than lighter ones.
Starts with 0.
∙ condition : Condition - represents car’s condition based on the damageStatus. Ex. 0 -
New, 100 - Broken. It starts with New and updated in takeDamage method.
∙ reputationValue : int - all vehicles start the game with the same reputation value of
the average of maxReputationValue and minReputationValue.
∙ weight : double
∙ length : double
∙ Lane : currentLane location on a Map
∙ RoadSegment : currentRoad location on a Map
There are 3 subclasses: Car, Truck and Bus with some specific features.
1.6 Graphics
A GUI class can generateVisual() and updateVisual().
