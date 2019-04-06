## FRC-2019
This is the repository for Team 5026, The Iron Panthers' robot code for our 2019 robot, the Mothership.

## Codebase structure
We use a WPILib command/subsystem-based approach for our robot code. Most FRC teams are familiar with (or use) some variation of this structure. Here is the way that we lay out our robot code:

* A <i>subsystem</i> refers to a major component of the robot. A concrete example is our robot's [drivebase subsystem](src/main/java/org/usfirst/frc/team5026/robot/subsystems/drive/Drive.java). Our subsystem classes tend to contain generic methods which act on or receive data from particular motors or sensors.
    * In our projects, subsystems are organized into their own directories.
 * A <i>command</i> is the core of the command-based framework. It is an abstraction of a specific action. This is more often an action on a subsystem (like driving based on joystick input), although we also use commands that do not require control of their respective subsystem.
    * A command that would act on `<subsystem>/SomeSubsystem.java` in our project would have a relative path of `<subsystem>/commands/SomeCommand.java`.
  * All library-like abstractions (for example, [motor controller groups](src/main/java/org/usfirst/frc/team5026/robot/util/SparkMaxMotorGroup.java)) can be found in [robot/util](src/main/java/org/usfirst/frc/team5026/robot/util).

If you would like more details about how WPILib commands and subsystems work, you can refer to [this article](https://wpilib.screenstepslive.com/s/currentCS/m/java/l/599732-what-is-command-based-programming)

## Getting an env running
In order to work on this project, no additional tools are required beyond what is [recommended here](https://wpilib.screenstepslive.com/s/4485/m/13503). Running a 'Build' command using the Visual Studio code plugin should install all the necessary vendor APIs.
