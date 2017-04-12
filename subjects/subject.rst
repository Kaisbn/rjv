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

You can also spawn building:

* nexus - allows the creation of agents.

Every block of the game can be converted to your color, adding 1 to your score.
At any point of the game, if a player does not own at least one block any more,
he has lost and retire from the game.

Block can also be mined, gathering resources *and* converting them at the same
time.

Converted blocks that are destroyed later does not count in their players score
anymore.

The end of the game is reached when either one player remains or a timeout has
been reached.

Server Interface
================

You can monitor all the servers running via ``https://creeps.acu.epita.fr/``

Your AI should connect to one of the available IP using the port 1664.

In order to interact with the server, it exposes 4 services over HTTP:

* GET /status
* GET /init/``login``
* GET /report/``reportId``
* POST /command/``login``/``agentId``/``opcode``

You're allowed to use Unirest with Gson library.

    .. code:: java

        // GET method
        Unirest.get(uri).asJson()
        // POST method
        Unirest.post(uri).body("{}").asJson()

Documentation can be found at http://unirest.io/java.html.

GET /status
-----------

Returns the current status of the server.
Use it to detected if the game has started or not.

.. code:: raw

    {
        "pluginRunning" : true,     // Ignore
        "gameRunning" : true,       // Game status
        "scores" : {                // List of players and associated scores
            "login_x" : 9,
            "login_y" : 4
        }
    }


GET /init/login
---------------

Register you as a player using the provided ``login`` - valid logins:
``^\[\d+\] [a-z0-9.-_]+$``.

The first part of your login ``[\d+\]`` must be your UID (from the intranet)
enclosed by brackets. This will allow us to identify yours AIs but this part
will not be display.

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

If error field is not null, an error occurred:

* "login not available"

  ``login`` provided is not available. Retry with another one.

* "max players reached on this server."

  This server is full. Try another server.

POST /command/login/agentId/opcode
----------------------------------

Orders the agent with the given ``agentId`` to perform the command with the
given ``opcode``.

Even if the command does not take any argument, you **MUST** provide a
Json body in your request, i.e. ``{}``.

.. code:: raw

    {
        "opcode" : "action",        // Information about command transmission success
        "reportId" : "1a2b3c4d5",   // Report ID
        "error" : null,             // Error description
        "login" : "",               // Player login - might be empty
        "id" : "",                  // Agent ID - might be empty
        "misses" : 0                // Number of misses
    }

If opcode field is different from "action", an error occurred:

* "notrunning"

  The game isn't running. It hasn't started yet or it already ended.

* "noplayer"

  ``login`` is not matching any player on the server.
  You have been kick for inactivity.

* "agent not available"

  Your agent is already doing something. Wait until he finished before sending
  it another job. Note that your missed calls counter has increased. If it goes
  over a certain value, next missed calls will leads to the death of the agent.

* "nomoney"

  Your resources are not sufficient for the moment. Retry later when they do.

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

Retrieves the report with the given reportId.

You will find response structure for each opcode in command section.

The report is available after the duration specified for each task. If
``reportId`` does not exist or is not ready yet, you will get:

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

Part of your first units, probes are versatile; capable of converting
mining, scanning, they can also build nexus.

Opcodes available:

* ``noop``
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

* ``noop``
* ``status``
* ``release``
* ``convert``
* ``scan``, ``scan5``, ``scan9``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Templar
~~~~~~~

Your wizardry thing. It can invoke giant blob of matter pretty much anywhere.

Opcodes available:

* ``noop``
* ``status``
* ``release``
* ``sphere``
* ``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``

Beacon
~~~~~~

This breaks things. Once spawned, move it to the location of something you want
blown, executes one of its destructive commands and profit.

Opcodes available:

* ``noop``
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

* ``noop``
* ``status``
* ``release``
* ``playerstatus``
* ``spawn:probe``, ``spawn:scout``, ``spawn:beacon``, ``spawn:templar``


Commands
========

Each command has an execution duration and might have a cost or a reward in
biomass/minerals.
Those information are available in Creepstants.java.

Each kind of block has a different yield in biomass and minerals, they are
described in BlockValues.java
If you cannot find the reference of a block type, it simply gives 0
of each resource.

Finally, severals commands return one or more location objects.
A location object looks like this:

.. code:: raw

    {
        "x" : 32,                 // X coordinate
        "y" : 32,                 // Y coordinate
        "z" : 32,                 // Z coordinate
        "type" : "AIR",             // Material
        "player" : "login_x"        // Owner if any
    }

``status``
----------

Provides agent status.
Location is relative to the block the agent is currently on.

Report structure:

.. code:: raw

    {
        "opcode" : "status",        // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "status" : "alive"          // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object
    }


``moveup``, ``movedown``, ``movenorth``, ``movesouth``, ``movewest``, ``moveeast``
----------------------------------------------------------------------------------

Moves the agent according to the direction suffix.
Agents can move through any kind of terrain but are limited on Y axis : 1 < y < 256.

Report structure:

.. code:: raw

    {
        "opcode" : "move",          // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "location" : {}             // A Location object
    }

``convert``
-----------

Converts the block to your color, giving you one point.
Beware though, converting lava or some others nasty blocks will have very bad
side-effects.

Report structure:

.. code:: raw

    {
        "opcode" : "convert",       // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "status" : "alive"          // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object
    }

``mine``
--------

Mines the block for resource and converts it.
As with converting, make sure you are not mining anything exploding or hot...

Report structure:

.. code:: raw

    {
        "opcode" : "mine",          // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "mineralsEarned" : 42,      // Minerals earned by the action
        "biomassEarned" : 42,       // Biomass earned by the action
        "status" : "alive",         // Can be "alive" or "dead"
        "causeOfDeath" : "",        // Can be "release", "tnt" or "lava"
        "location" : {}             // A Location object
    }

``playerstatus``
----------------

Provides player status.

Report structure:

.. code:: raw

    {
        "opcode" : "playerstatus",  // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "minerals" : 42,            // Minerals of the player
        "biomass" : 42              // Biomass of the player
    }

``scan``, ``scan5``, ``scan9``
------------------------------

``scan``: Gives information on the 3x3x3 cube centered on the agent.

``scan5``: Gives information on the 5x5x5 cube centered on the agent.

``scan9``: Gives information on the 9x9x9 cube centered on the agent.

Report structure:

.. code:: raw

    {
        "opcode" : "scan",          // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "scan" : {                  // List of Location
            "32,40,23" : {},        // Location object
            "32,41,23" : {},        // Location object
            ...
        }
    }

``spawn:beacon``, ``spawn:nexus``, ``spawn:probe``, ``spawn:scout``, ``spawn:templar``
-------------------------------------------------------------------------------------------------------

Spawns the given unit at the place it has been invoked.

Report structure:

.. code:: raw

    {
        "opcode" : "spawn",         // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "type" : "probe",           // Unit type
        "location" : {},            // Location object
        "error" : null              // Error description
    }

``sphere``
----------

Invokes a sphere of matter around the templar.

You must provide the ``material`` argument in the Json body of your POST request.

.. code:: raw

    {
        "material" : "lava"         // Can be "water", "sand", "lava", or "tnt"
    }

Report structure:

.. code:: raw

    {
        "opcode" : "sphere",        // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``ion``
-------

Triggers an Ion Cannon discharge for orbital barge "Litany of Fury." Ouch!

Report structure:

.. code:: raw

    {
        "opcode" : "ion",           // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``laser``
---------

Fires orbital laser, nothing should left before the bedrock is reached.
Ouch-much!

Report structure:

.. code:: raw

    {
        "opcode" : "laser",         // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

``release``
-----------

Releases the agent, giving you some resources back depending on the unit type.

Report structure:

.. code:: raw

    {
        "opcode" : "release",       // Action opcode
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x",        // Player login
        "minerals" : 42,            // Minerals of the player
        "biomass" : 42              // Biomass of the player
    }

``noop``
--------

Does nothing, for testing purpose.

Report structure:

.. code:: raw

    {
        "opcode" : "noop",         // Action opcode.
        "reportId" : "1a2b3c4d5",   // Report ID
        "id" : "a1b2c3d4e",         // Agent ID
        "login" : "login_x"         // Player login
    }

Behaviour and Design Tips
=========================

Agents and threading model
--------------------------

Even though it would be possible to implement an IA over a single execution
thread, said IA would be very limited in terms of capabilities. You **Must**
adopt a more advanced design, where each agent will be executed as a separate
execution thread (not necessarily as a system thread though, as we have seen
they can be quite limited). This would allow you to scale up to dozen or even
thousands of agents on general-availability computer depending on your
implementation.

.. block:: warning

    Proper usage of Threads will be checked during your defense.
    If you don't use multithreading you will get a zero.

As such things as coroutines, fibers, green threads or agent systems are not
available to you in this project, we suggest you take interest in the reactor
pattern, especially implementations like the one found in the Rx project
(note that you are not allowed to use the library, only try to understand and
emulate it). Using CompletableFuture and its sibling classes presented in this
projects own presentation should allow you to do so in no time.

Technicalities
==============

The project structure is provided to you in the form of the
``creeps.tar.bz2`` file.

The build-system used by this project is gradle. Configuration files
- build.gradle and settings.gradle - are provided. Unless explicitly told by
an assistant, do not modify those files.

All your source code needs to be placed under the ``${root}/src/main/java/``
folder. Entry point is defined in ``com.epita.creeps.Program::main``.

You are allowed to use two libraries for this project:

* Unirest: for REST calls.
* Gson: for Json parsing. See ``com.epita.creeps.utils.Json``.

Import project:

1. File > Open
2. Browse and select the build.gradle file at the root of the project.


Usage
-----

During the defense, your program will be executed as follow :

.. code:: raw

    java -jar creeps.jar [HOSTNAME] [PORT] [USERNAME]
