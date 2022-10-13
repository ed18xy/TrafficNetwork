# TrafficNetwork
## 1 GENERAL
My game has 7 packages : GameEngine, TCPServerClient, Map, Vehicles, Player, Decision- Making and Graphics. Game implements Client-Server paradigm or can be run as pure simulation. Traffic Network is represented by Map as a collection of Road segments with lanes that contain cars. Mobile entities are Cars, Trucks, and Buses in package Vehicles. Time-stepped simulation is a reference to a Runnable lambda expression. Scanner and Array were used from java.util. The main Objective is to navigate vehicle properly to avoid collisions. After each collision car would take damage. If car’s condition reaches Broken, the game is over.
## 1.1 GameEngine
This is the main package that runs the game. class Main is generating a single GameController and runs pure simulation without player. The GameController class generates all the necessary objects before the game starts. Then it runs the simulation. A package has two public enum classes: Direction (East,West,North,South;) and Sides(Right, Left). The former is used to simulate turns in intersections and the latter is for lane changing options. LoadVehicles method creates vehicles and assigns NPC drives to them. vehicleGenerator returns a vehicle of a random type (car, truck or bus) that is produced by a corresponding factory. Vehicles creation is implemented with the use of Factory Design Pattern. In class GameController in the method loadVehicles NPCs drivers are created and a thread for each one and assigned to update driver on each step of simulation: stepUpd.
## 1.2 Map
Map is defined in Map.xml file following the schema in Map.xsd file. 1 A class generates new Map consisting of Intersections and RoadSegments arrays. In classes Mapreader, RoadSeg- mentReader, XMLNodeConverter, XMLObject, XMLReader, XMLTools I used some of the code provided by instructor in XML example. RoadSegment connects two Intersections and consists of 1 or more Lanes. The RoadSegment represents only one side (one direction of movement). A Lane has a direction and length. The Lane has a defined length so depending on the length of vehicle it might or might not have the space for it. To check this the getAvailableSpace method can be used.Additionally to the length requirement, it can only have 10 vehicles at a time to enable safe distance. The Intersection class has a name and list of available turns with all the options in which driver can turn. The Turn class has a direction which this turn represents in Intersection.
## 1.3 DecisionMaking
This package has everything that Player or NPC might need to take an action. A Driver Interface specifies the required functionality to navigate on the map and drive the car. It includes following methods declarations:
∙ Look() - to get surroundings info and see available option
∙ Verify() - verify that the move can be made (has a default implementation)
∙ Gamble() - to take a chance if the move is possible but there is not enough space now
∙ MakeMove() - to move if decision was verified or player decided to gamble. May result
in a collision that causes damage to both vehicles.
There are few more default helping methods (has all needed explanation in comments). Damage to the vehicles is given according to cars weight and reputation. There are two Driver implementations for Player (PlayerDriverImpl) and for the NPC (PCDriverImpl). Player is a driver so a Player class extends PlayerDriverImpl. PCDriverImpl is linked to a vehicle and allows NPC to drive it.
## 1.4 Player
A Player has a Vehicle. Player can look at the list of available actions at the current location and chooseNextMove by typing the corresponding action code into the terminal. A player method updater updates the location of the vehicle. Architectural Design Pattern: Model View Controller is implemented. To enable that View and Controller classes are introduced. View class is responsible for interactions of the player with the model without an overhead to the model.
## 1.5 Vehicles
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
There are 3 subclasses: Car, Truck and Bus with some specific features. There is a Factory interface with a default method and 3 implementations that are responsible for creation of Car, Truck or Bus. Factory Design Pattern was used here.
## 1.6 Graphics
A different graphics can be chosen for pure simulation or game. Representation of vehicles can be plain text or emoji. Since emoji may not be supported, you are presented with a choice. This decision can only be made once and server decides on the representation so that players have a consistent experience.
## 1.7 TPCServerClient
Sever is initiating the connection with the client and communicate with player according to protocol. State enum class introduces 3 states: WAITING, ACTIONRESPONSE, and GAMBLERESPONSE. AuthProtocol allows server to authenticate user before letting them to join the game. Database class imitates database with username. Communication happens through sending text-based messages. Client knows that if the message has ’;’ at the ends, it needs to wait for more messages from Server. Protocol and Authprotocol has all the possible options that player might go with. First you need to start server specifieng the port connection. Then a player can join that server refering by that port number.
### 2 CODE
## 2.1 Compile Command
javac -d classes GameEngine/*.java Vehicles/*.java DecisionMaking/*.java Map/*.java Graphics/*.java Player/*.java TCPServerClient/*.java
## 2.2 Run Command
copy map.xml and map.xsd into generated compiled folder classes if doesn’t exist. navigate: cd classes Pure simulation: run: java GameEngine.Main
Connection: start server first: java TCPServerClient.Server 1 and select gui type before
trying to add client
add client: java TCPServerClient.Client 0 1
