# Introduction

In creeps, each player - you - has one and only one objective. Destroy your opponents and be king of the hill.
Or in case you can't erase other players, simply dominate them by owning more land than they do.

In more technical terms, you will be provided with two starting agents, randomly placed in a tri-dimensional world
full of blocks.
By using web services, you will be able to make these agents execute various commands, ultimately providing you more
 resource, which in turn will allow you to spawn more agents. Different kind of agents are available, for example,
 probes are peaceful units capable of exploration, resource gathering and terrain conversion, templars are mobile
 utility units that can shape the landscape and beacons are self-destructing units producing some cataclysmic effects,
 best triggered in your opponent's courtyard. You can also spawn buildings: nexus will allow the creation of agents,
 pylons allow teleportation of units and the photon cannon will defend your land against enemy intrusions.

 Every block of the game can be converted to your color, adding 1 to your score. At any point of the game, if a
 player has no more blocks of his own, he has **lost** and is removed of the game.

 Block can also be mined, gathering resources *and* converting them at the same time, for thrice the time of a simple
  conversion.

  Converted blocks that are destroyed later do not count in their players score anymore.

  The end of the game is reached when either one player remains or a timeout has been reached.


# Rules, Units List and Powers
## Connectivity

Creeps servers are hosted by your beloved assistants. They will provide you with a number of URLs in order for you to
 connect and test your AIs.

 Every server exposes four services in over HTTP:

* `GET:/status` will return the current status of the server. Use it to detect whether the game has started or not.
 Here is
  an example output:
```sh
Creeps plugin running ok.
No game started.
psx:32
```

* `GET:/init/:login` will register you as a player using the provided login. You should always start by this command.
  As a return of this command, you will get the X, Y and Z coordinates of your starting point and the IDs of both
  your first free probe and of your nexus. Keep good track of them as you won't be able to issue commands otherwise.

* `POST:/command/:login/:agentId/:opcode` will order the agent with the given id to perform the command with the given
 opcode. Even if the command does not take any argument, you should pass a Json body (simply use an empty object for
no-args commands). This will return a report id.

* `GET:/report/:reportId` will retrieve the report with the given report id.

 That's it for services. Only four.

## Agent types:
 We might or might not add more agents as the rush goes on. Just for the fun of it.
 For each agent type, the cost in biomass and minerals and the spawn time will be given in the constants file.

### Probe:
Your bread and butter unit. It can move, convert blocks to your color, mine blocks (both to gather resources and
convert blocks), it can build buildings and scout around itself in either a short range / quick execution or
medium range / medium execution.

#### Opcodes available:
`moveup`, `movedown`, `movenorth`, `movesouth`, `movewest`, `moveeast`, `scan`, `scan5`, `convert`, `mine`, `status`

### Scout:
The scout can move and perform the three kind of scan: small, medium and big.

#### Opcodes available:
`moveup`, `movedown`, `movenorth`, `movesouth`, `movewest`, `moveeast`, `scan`, `scan5`, `scan9`, `status`

### Templar:
 Your wizardry thing. It can invoke giant blob of matter pretty much anywhere.

#### Opcodes available:
  `moveup`, `movedown`, `movenorth`, `movesouth`, `movewest`, `moveeast`, `sphere`, `status`

### Beacon:
 This breaks things. Once spawned, move it to the location of something you want blown, executes one of its
 destructive commands... profit.

#### Opcodes available:
  `moveup`, `movedown`, `movenorth`, `movesouth`, `movewest`, `moveeast`, `ion`, `laser`, `status`

## Building types:
As with the agents, we will probably add some building during the project.

### Nexus:
This building allows you to spawn units and get a detailed report over your current situation.

#### Opcodes available:
`spawn:probe`, `spawn:scout`, `spawn:beacon`, `spawn:templar`, `status`, `playerstatus`

## Commands:

A lot of commands send block status information. A BlockStatus object is composed of :

* x coordinate.

* y coordinate.

* z coordinate.

* The material of the block.

* The player owning the block, if any.

All the technical details of the commands are given in the Creepstants file. The provided information is:

* Execution time.

* Cost in minerals.

* Cost in biomass.

* Expected arguments (most take none).

### `status`
Provides the player with information on the agent it's cast on. Information is : status (dead or alive), causeOfDeath, and
the block status of the block its currently on.

### `moveXXX`
Move the target agent in the given direction if possible (ie, y > 1 and < 256). Agents can move through any terrain
without restriction.

### `convert`
Convert the block to the color of the player, thus granting him one point. Beware though, converting lava or some
other nasty block will have very bad side-effects.

### `mine`
Mine the block for resource and convert it to the players color. As with converting, make sure you are not mining
anything exploding or hot... Rewards in biomass and minerals for different block types will be provided in the
Creepstants file. If you cannot find the reference of a block type, it does simply gives 0 for each resource.

### `playerstatus`
Gives the player information about his current money levels !

### `scan`
Gives information on the 9 blocks forming the cube centered on the agent.

### `scan5`
Gives information on the 125 blocks forming the cube centered on the agent.

### `scan9`
Gives information on the 729 blocks forming the cube centered on the agent.

### `noop`
Does nothing, for testing.

### `sphere`
Invokes a sphere of matter around the templar. You must provide the `material` argument with one of the following value:

* water

* sand

* lava

### `ion`
<!--- FIXME: maybe give some technical details here? -->
Triggers an Ion Cannon discharge for orbital barge "Litany of Fury." Ouch.

### `laser`
They really pissed the guys on the Litany of Fury up there. Fire orbital laser, nothing should be left before the
bedrock is reached. Ouch-much.

## Errors
If you send a request with bad values into it, you will also have a response with information on your mistake.

### `Init`
If you try to register with a login already present in the server's list of player, all the fields will be null and
the error field will provide you with description of your error.

### `Command and Reports`
If you send bad information, response will be composed of:

* repcode with the error code

* error description

* Some other specific fields varying in function of the error

If you try to send multiple commands without waiting for the completion of the first command, you will also have an
error in addition of a penalty.


# Behavior and Design Tips
## Agents and threading model

Event though it would be possible to implement an AI over a single execution thread, said AI would be very limited in
 terms of capabilities. We **strongly** encourage you to adopt a more advanced design, where each agent will be executed
  as a separate execution thread (not necessarily as a system thread though, as we have seen they can be
  quite limited). This would allow you to scale up to dozen or even thousands of agents on general-availability
  computer depending on your implementation.

  <!--- FIXME: Maybe give a link to the Rx project? -->
  As such things as coroutines, fibers, green threads or agent systems are not available to you in this project, we
  suggest you take interest in the reactor pattern, especially implementations like the one found in the Rx project
  (note that you are not allowed to use the library, only try to understand and emulate it). Using CompletableFuture
  and its sibling classes presented in this project's own presentation should allow you to do so in no time.

## Time-sensitive API

As you will soon experience yourself, the API exposed by the server will take some time to execute the commands you
 request. Each and every separate command has a specific execution time during which you are forbidden to call the
 agent again. Doing so will result in various kind of penalties being applied, like the extension of unavailability
 time, a decrease in resource or even the death of the agent. More over, some operations might slow down the server
 to a point where the expected time of completion of an action will be exceeded. In such case, you will be notified
 of the problem and will suffer no penalty.

 Obviously, we will provide a complete description of each action, which will include the execution time of each
 command. Beware though as this file might change during the duration of the project, keep its loading mechanism as
 dynamic as possible so you would not loose too much time if such case was to occur.

## Here and there...

As a conclusion to this chapter, let me sum it up for you.
You should develop a mechanism that will:

* Take a command, some code to execute after completion and some code to execute should any error occur.

* Ideally, the `after completion` code and the error code should be implemented using the same mechanism, thus
 creating a chaining feature.

* Have this mechanism class execute the code on a separate thread of execution, by any means you see fit.

* Have it wait for the execution of the command (plus some added safety time buffer).

* Have it retrieve the execution report and interpret it.

* Based on the report interpretation, choose to trigger either the next action or the error code.

 So, in pseudo-code your AI might look like that:

     public void advanceAndMine(Command andThen) {
         command("movenorth",
             command("movenorth",
                command("mine", () -> andThen.invoke, () -> this.handleError()),
                () -> this.handleError(),
             )
             () -> this.handleError()
         )
     }



Add in some clever use of SAMs, lambdas, a scheduler, a strategy and maybe even some observers and it should be quite
 easy to start playing with probes and templars.


# Technicalities
The project structure is provided to you in the form of the project-login_l.tar.gz file. All your source code needs
to be placed under the ${root}/src/main/java/ folder (or subfolders for packages, obviously).

The build-system used by this project is maven. Even though you have not yet learned the use of this tool, things
should be straightforward has the only difficult part - configuration - has already been done for you. Unless
explicitly told by an assistant, do not modify the pom.xml file at the root of the project as it holds said
configuration.

Intellij IDEA is perfectly suited to work with maven projects. As such, you should not experience any problem
importing and running your project. Simply do as follow:

1. File > Open

2. Browse until you find the pom.xml file at the root of the project. Select and load it.

3. After a short import time, the project should be properly set. If asked whether you want to enable auto-import,
reply that yes indeed, you wish so.

4. Once imported, you should have a "Maven Projects" panels available on the right side of your IDE. Open it.

5. This panel contains all the commands you can run on you project. Only a couple of them will be of interest to us
for this project, but feel free to search further if your are interested:

    * The `clean` command, located under the `Lyfecicle` category will clean your project and remove all the files
    unnecessary for distribution.

    * The `install` command will compile and build the Jar file of your project.

    * The mvn `exec:java` will launch you project.

6. The main of your application is already defined for you (for maven configuration purposes). Please place you entry
point code in the `com.epita.Creeps::main` method.

7. The project is already configured with two additional libraries to help you go faster with some aspects of the
project which were not the primary notions we wanted you to work on.

    * Unirest: this library will allow you to write REST calls very easily. You can find the documentation of the
    library [here](http://unirest.io/java.html). Skip the installation part, it has been done for you.

    * Gson: google's take on JSon parsing in java. We also provided a helper class to make it even easier (`com.epita
    .utils.Json`).

8. Unless explicitly authorized by an assistant, no other library is allowed for this project.

9. The class Creepstants.properties defines all the values you need to develop your client. It may change during the
course of the project, make sure it is loaded dynamically to save you some time.



# A word on AI development

This project is **_not_** about AI development. If you are specifically interested in the subject and want to spend
 some time on the development of a neat and elegant AI algorithm, please be our guest. But you should not be
 expecting any support in terms of theory or implementation from your assistants on this specific matter.
 Furthermore, this should not take precedence over the core features and the overall quality of your project.

A very basic IA will get your all the points there is to get on this subject.

# A word of advice
Fail fast, fail often.
