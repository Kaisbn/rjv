Introduction
============
In creeps, each player - you - has one and only one objective :
destroy your opponents and be the king of the hill.
Or in case you can't erase other players, simply dominate them by owning more
of the land than they do.

At the beginning of a game, you will be provided with two starting agents,
randomly placed in a tri-dimensional world full of blocks.

By using web services, you will be able to make these agents execute various
commands, ultimately providing you more resource, which in turn will allow
you to spawn more agents.

Different kind of agents are available:

* Peaceful ones

  * Probes - capable of exploration, resource gathering and terrain conversion
  * Scouts - capable of exploration and terrain conversion

* Harmful ones

  * Templars - can shape the landscape
  * Beacons - can produce some cataclysmic effects best triggered in your
    opponent's courtyard.

You can also spawn buildings:

* nexus - allows the creation of agents.
* pylons -  allows teleportation of units.

Every block of the game can be converted to your color, adding 1 to your score.
At any point of the game, if a player has no more blocks he owns, he has lost
and retire from the game.

Block can also be mined, gathering resources *and* converting them at the same
time.

Converted blocks that are destroyed later does not count in their players score
anymore.

The end of the game is reached when either one player remains or a timeout has
been reached.

Server Interface
================

In order to interact with the server, it exposes 4 services over HTTP:

* GET /status
* GET /init/``login``
* GET /report/``reportId``
* POST /command/``login``/``agentId``/``opcode``

You're allowed to use Unirest library.

    .. code:: java

        // GET method
        Unirest.get(uri).asJson()
        // POST method
        Unirest.post(uri).body("{}").asJson()

Documentation can be found at http://unirest.io/java.html.

GET /status
-----------
Return the current status of the server.
Use it to detected if the game has started or not.

.. code:: raw

    {
        "pluginRunning" : true,     // Ignore
        "gameRunning" : true,       // Game status
        "scores" : {                // List of player and associated scores
            "login_x" : 9
        }
    }


GET /init/login
---------------
Register you as a player using the provided ``login`` - valid logins: ^[a-z0-9.-_]+$.

.. code:: raw

    {
        "error" : null,             // Error description
        "login" : "login_x",        // Player login
        "color" : "WHITE",          // Player Color
        "startX" : 224,             // Initial X coordinate
        "startY" : 94,              // Initial Y coordinate
        "startZ" : 0,               // Initial Z coordinate
        "baseId" : "1a2b3c4d5",     // Initial nexus ID
        "probeId" : "a1b2c3d4e",    // Initial probe ID
    }

If error field is not null, an error occured:

* "login not available"

  ``login`` provided is not available. Retry with another one.

* "max players reached on this server."

  This server is full. Try another server.

POST /command/login/agentId/opcode
----------------------------------
Order the agent with the given ``agentId`` to perform the command with the
given ``opcode``.

Even if the command does not take any argument, you **MUST** provide a
Json body in your request.

.. code:: raw

    {
        "opcode" : "action",        // Information about command transmission success
        "reportId" : "1a2b3c4d5",   // Report ID
        "error" : "",               // Error description
        "login" : "",               // Player login - might be empty
        "id" : "",                  // Agent ID - might be empty
        "misses" : 0                // Number of misses
    }

If opcode field is different from "action", an error occured:

* "noplayer"

  ``login`` is not matching any player on the server.
  You have been kick for inactivy.

* "unavailable"

  Your agent is already doing something. Wait until he finished before sending
  it another job. Note that your missed calls counter has increased. If it goes
  over a certain value, next missed calls will leads to the death of the agent.

* "nomoney"

  Your ressources are not sufficiant for the moment. Retry later when they do.

* "dead"

  Your agent dies due to too much missed calls. Note that report is send only
  once, after that you will get a "noagent" response.

* "noagent"

  ``agentId`` is not matching any of your units. Either you previously released
  it or it died.

* "initerror"

  Body of the request caused an error.

GET /report/reportId
--------------------
Retrieve the report with the given reportId.

You will find response structure for each opcode in command section.

If ``reportId`` does not exist you will get:

.. code:: raw

    {
        "opcode" : "noreport",
        "error" : "No such report",
        "reportId" : "173040eba"
    }

Units
=====

Agents
------

Probe
~~~~~
Part of your first units, probes are versatile ; capable of converting
mining, scanning, they can also build nexus.

Opcodes available:

* ``status``
* ``release``
* ``convert``
* ``mine``
* ``spawn:nexus``
* ``scan``, ``scan5``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Scout
~~~~~
Scouts are useful to have a quick and wide overview of surrounding world with
``scan9``. Note that they cannot mine nor build.

Opcodes available:

* ``status``
* ``release``
* ``convert``
* ``scan``, ``scan5``, ``scan9``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Templar
~~~~~~~
Your wizardry thing. It can invoke giant blob of matter pretty much anywhere.

Opcodes available:

* ``status``
* ``release``
* ``sphere``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Beacon
~~~~~~
This breaks things. Once spawned, move it to the location of something you want
blown, executes one of its destructive commands and profit.

Opcodes available:

* ``status``
* ``release``
* ``ion``
* ``laser``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Buildings
---------

Nexus
~~~~~
Part of your first unit, nexus allow you to spawn units and get a detailed
report over you current situation.

Opcodes available:

* ``status``
* ``release``
* ``playerstatus``
* ``spawn:probe``, ``spawn:scout``, ``spawn:beacon``, ``spawn:templar``

Pylon
~~~~~
This building allows you to transfer units on the same block to any other pylon
you own.

Opcodes available:

* ``status``
* ``release``
* ``transfer``

Commands
========
Each commands has an execution time and might have a cost or a rewards in
biomass/minerals.
Those informations are available in Creepstants.java.

Each kind of block has a different yield in biomass and minerals, they are
described in BlockValues.java
If you cannot find the reference of a block type, it simply gives 0
of each resource.

Finally, severals commands return one or more location objects.
A location object looks like this:

.. code:: raw

    {
        "x" : "32",                 // X coordinate
        "y" : "32",                 // Y coordinate
        "z" : "32",                 // Z coordinate
        "type" : "AIR",             // Material
        "player" : "login_x"        // Owner if any
    }

``status``
----------
Provides agent status.
Location is relative to the block the agent is currently on.

Report structure

.. code:: raw

    {
        "opcode" : "status",        // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "status" : "alive"          // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object.
    }


``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``
----------------------------------------------------------------------------------
Moves the agent according to the direction suffix.
Agents can move through any kind of terrain but are limited on Y axis : 1 < y < 256.

Report structure

.. code:: raw

    {
        "opcode" : "move",          // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "location" : {}             // A Location object.
    }

``convert``
-----------
Converts the block to your color, giving you one point.
Beware though, converting lava or some other nasty block will have very bad
side-effects.

Report structure

.. code:: raw

    {
        "opcode" : "convert",       // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "status" : "alive"          // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object.
    }

``mine``
--------
Mines the block for resource and converts it.
As with converting, make sure you are not mining anything exploding or hot...

Report structure

.. code:: raw

    {
        "opcode" : "mine",          // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "mineralsEarned" : 42,      // Minerals earned by the action
        "biomassEarned" : 42,       // Biomass earned by the action
        "status" : "alive",         // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object.
    }

``playerstatus``
----------------
Provides player status.

Report structure

.. code:: raw

    {
        "opcode" : "playerstatus",  // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "minerals" : 42,            // Minerals of the player
        "biomass" : 42              // Biomass of the player
    }

``scan``, ``scan5``, ``scan9``
------------------------------
``scan``: Gives information on the 9 blocks forming the cube centered on the agent.

``scan5``: Gives information on the 125 blocks forming the cube centered on the agent.

``scan9``: Gives information on the 729 blocks forming the cube centered on the agent.

Report structure

.. code:: raw

    {
        "opcode" : "scan",          // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "scan" : {                  // List of Location
            "32,40,23" : {},        // Location object
            "32,41,23" : {},        // Location object
            ...
        }
    }

``spawn:beacon``, ``spawn:nexus``, ``spawn:probe``, ``spawn:pylon``, ``spawn:scout``, ``spawn:templar``
-------------------------------------------------------------------------------------------------------
Spawns the given unit at the place it has been invoked.

Report structure

.. code:: raw

    {
        "opcode" : "spawn",         // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "type" : "probe",           // Unit type
        "location" : {},            // Location object
        "error" : ""                // Error description
    }

``transfer``
------------
Transfer unit from one pylon to another.
Unit to transfer must be at the same coordinate as the pylon.

You must provide the agentId of the unit and the agentId of the destination
pylon.

.. code:: raw

    {
        "targetId" : "1a2b3c4d5",   // Destination pylon ID
        "agentId" : "a1b2c3d4e"     // Agent ID
    }

Report structure

.. code:: raw

    {
        "opcode" : "transfer",      // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``sphere``
----------
Invokes a sphere of matter around the templar.

You must provide the ``material`` argument in the json body of your POST request.

.. code:: raw

    {
        "material" : "lava"         // Material can be water, sand, lava or tnt
    }

Report structure

.. code:: raw

    {
        "opcode" : "sphere",        // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``ion``
-------
Triggers an Ion Cannon discharge for orbital barge "Litany of Fury." Ouch.

Report structure

.. code:: raw

    {
        "opcode" : "ion",           // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``laser``
---------
Fires orbital laser, nothing should left before the bedrock is reached. Ouch-much.

Report structure

.. code:: raw

    {
        "opcode" : "laser",         // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``release``
-----------
Releases the agent, giving you some ressources back depending on the unit type.

Report structure

.. code:: raw

    {
        "opcode" : "release",       // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "minerals" : 42,            // Minerals of the player
        "biomass" : 42              // Biomass of the player
    }

``noop``
--------
Does nothing, for testing purpose.

Report structure

.. code:: raw

    {
        "opcode" : "scan9",         // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

Behaviour and Design Tips
=========================
Agents and threading model
--------------------------
Even though it would be possible to implement an IA over a single execution thread, said IA would be very limited in
terms of capabilities. We **strongly** encourage you to adopt a more advanced design, where each agent will be executed
as a separate execution thread (not necessarily as a system thread though, as we have seen they can be
quite limited). This would allow you to scale up to dozen or even thousands of agents on general-availability
computer depending on your implementation.

As such things as coroutines, fibers, green threads or agent systems are not available to you in this project, we
suggest you take interest in the reactor pattern, especially implementations like the one found in the Rx project
(note that you are not allowed to use the library, only try to understand and emulate it). Using CompletableFuture
and its sibling classes presented in this projects own presentation should allow you to do so in no time.

Time-sensitive API
------------------
As you will soon experience yourself, the API exposed by the server will take some time to executes the commands you
request. Each and every separate command has a specific execution time during which you are forbidden to call the
agent again. Doing so will result in various kind of penalties being applied, like the extension of unavailability
time, a decrease in resource or even the death of the agent. More over, some operations might slow down the server
to a point where the expected time of completion of an action will be exceeded. In such case, you will be notified
of the problem and will suffer no penalty.

Obviously, we will provide a complete description of each action, which will include the execution time of each
command. Beware though as this file might change during the duration of the project, keep its loading mechanism as
dynamic as possible so you would not loose too much time if such case was to occur.

Here and there...
-----------------
As a conclusion to this chapter, let me sum it up for you.
You should develop a mechanism that will:

* Take a command, some code to execute after completion and some code to execute should any error occur.
* Ideally, the ``after completion`` code and the error code should be implemented using the same mechanism, thus
  creating a chaining feature.
* Have this mechanism class execute the code on a separate thread of execution, by any means you see fit.
* Have it wait for the execution of the command (plus some added safety time buffer).
* Have it retrieve the execution report and interpret it.
* Based on the report interpretation, choose to trigger either the next action or the error code.

So, in pseudo-code your IA might look like that:

.. code:: java

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


Technicalities
==============
The project structure is provided to you in the form of the project-login_l.tar.gz file. All your source code needs
to be placed under the ${root}/src/main/java/ folder (or subfolder for packages, obviously).

The build-system used by this project is maven. Even though you have not yet learned the use of this tool, things
should be straightforward has the only difficult par - configuration - has already been done for you. Unless
explicitly told by an assistant, do not modifiy the pom.xml file at the root of the project as it holds said
configuration.

Intellij IDEA is perfectly suited to workd with maven projects. As such, you should not experience any problem
importing and running your project. Simply do as follow:

1. File > Open
2. Browse until you find the pom.xml file at the root of the project. Select and load it.
3. After a short import time, the project should be properlty set. If asked whether you want to enable auto-import,reply that yes indeed, you wish so.
4. Once imported, you should have a "Maven Projects" panels available on the right side of your IDE. Open it.
5. This panel contains all the commands you can run on you project. Only a couple of them will be of interest to us
   for this project, but feel free to search further if your are interested:

   * The ``clean`` command, located under the ``Lyfecicle`` category will clean your project and remove all the files
     unnecessary for distribution.
   * The ``install`` command will compile and build the Jar file of your project.
   * The mvn ``exec:java`` will launch you project.
6. The main of you application is already defined for you (for maven configuration purposes). Please place you entry
   point code in the ``com.epita.Creeps::main`` method.
7. The project is already configured with two additional libraries to help you go faster with some aspects of the
   project which were not the primary notions we wanted you to work on.

   * Unirest: this library will allow you to write REST calls very easily. You can find the documentation ot the library (http://unirest.io/java.html). Skip the installation part, it has been done for you.
   * Gson: google's take on JSon parsing in java. We also provided a helper class to make it even easier (``com.epita.utils.Json``).
8. Unless explicitly authorized by an assistant, no other library is allowed for this project.
9. The class Creepstants.properties defines all the values you need to develop your client. It may change during the
   course of the project, make sure it is loaded dynamically to save you some time.



A word on AI development
========================
This project is **NOT** about AI development.
If you are specifically interested in the subject and want to spend some time
on the development of a neat and elegant AI algorithm, please be our guest.
But you should not be expecting any support in terms of theory or implementation
from your assistants on this specific matter.
Furthermore, this should not take precedence over the core features and the
overall quality of your project.

A very basic IA will get your all the points there is to get on this subject.

A word of advice
================
Fail fast, fail often.
