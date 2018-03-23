## Welcome
This is the code for FTC 2017 season. 

## Travis-CI build status

LSRobotics(Main) Branch [![Build Status](https://travis-ci.org/LSRobotics/FTC-11319-S18.svg?branch=master)](https://travis-ci.org/LSRobotics/FTC-11319-S18)

## About Pushing Code
You Must open a branch and pull request before merging into the main branch!

<<<<<<< HEAD
## Committing
Minimum request:
    - Briefly specify the changes you've made
    - If available, put down known bugs, or some precaution, so that other collaborators can understand better
=======
## Downloading the Project
It is important to note that this repository is large and can take a long time and use a lot of space to download. If you would like to save time and space, there are some options that you can choose to download only the most current version of the Android project folder:

* If you are a git user, *FIRST* recommends that you use the --depth command line argument to only clone the most current version of the repository:

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;git clone --depth=1 https://github.com/ftctechnh/ftc_app.git</p>

* Or, if you prefer, you can use the "Download Zip" button available through the main repository page.  Downloading the project as a .ZIP file will keep the size of the download manageable.

* You can also download the project folder (as a .zip or .tar.gz archive file) from the Downloads subsection of the Releases page for this repository.

Once you have downloaded and uncompressed (if needed) your folder, you can use Android Studio to import the folder  ("Import project (Eclipse ADT, Gradle, etc.)").

## Getting Help
### User Documentation and Tutorials
*FIRST* maintains an online wiki with information and tutorials on how to use the *FIRST* Tech Challenge software and robot control system.  You can access the wiki at the following address:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/ftctechnh/ftc_app/wiki

### Javadoc Reference Material
The Javadoc reference documentation for the FTC SDK is now available online.  Visit the following URL to view the FTC SDK documentation as a live website:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftctechnh.github.io/ftc_app/doc/javadoc/index.html    

Documentation for the FTC SDK is also included with this repository.  There is a subfolder called "doc" which contains several subfolders:

 * The folder "apk" contains the .apk files for the FTC Driver Station and FTC Robot Controller apps.
 * The folder "javadoc" contains the JavaDoc user documentation for the FTC SDK.

### Online User Forum
For technical questions regarding the SDK, please visit the FTC Technology forum:

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://ftcforum.usfirst.org/forumdisplay.php?156-FTC-Technology


**************************************************************************************
# Release Information
**************************************************************************************

Version 3.7 (built on 18.03.12)

Changes include:
 * Interim fix to allow FTC apps to run on Android Nougat (7.x) devices.

**************************************************************************************
# Release Information
**************************************************************************************

Version 3.6 (built on 17.12.18)

Changes include:
 * Blocks Changes
     - Uses updated Google Blockly software to allow users to edit their op modes on Apple iOS devices (including iPad and iPhone).
     - Improvement in Blocks tool to handle corrupt op mode files.
     - Autonomous op modes should no longer get switched back to tele-op after re-opening them to be edited.
     - The system can now detect type mismatches during runtime and alert the user with a message on the Driver Station.
 * Updated javadoc documentation for setPower() method to reflect correct range of values (-1 to +1).
 * Modified VuforiaLocalizerImpl to allow for user rendering of frames
     - Added a user-overrideable onRenderFrame() method which gets called by the class's renderFrame() method.

**************************************************************************************
# Release Information
**************************************************************************************

Version 3.5 (built on 17.10.30)

Changes with version 3.5 include:
 * Introduced a fix to prevent random op mode stops, which can occur after the Robot Controller app has been paused and then resumed (for example, when a user temporarily turns off the display of the Robot Controller phone, and then turns the screen back on).
 * Introduced a fix to prevent random op mode stops, which were previously caused by random peer disconnect events on the Driver Station.
 * Fixes issue where log files would be closed on pause of the RC or DS, but not re-opened upon resume.
 * Fixes issue with battery handler (voltage) start/stop race.
 * Fixes issue where Android Studio generated op modes would disappear from available list in certain situations.
 * Fixes problem where OnBot Java would not build on REV Robotics Control Hub.
 * Fixes problem where OnBot Java would not build if the date and time on the Robot Controller device was "rewound" (set to an earlier date/time).
 * Improved error message on OnBot Java that occurs when renaming a file fails.
 * Removed unneeded resources from android.jar binaries used by OnBot Java to reduce final size of Robot Controller app.
 * Added MR_ANALOG_TOUCH_SENSOR block to Blocks Programming Tool.

**************************************************************************************
# Release Information
**************************************************************************************

Version 3.4 (built on 17.09.06)

Changes with version 3.4 include:
 * Added telemetry.update() statement for BlankLinearOpMode template.
 * Renamed sample Block op modes to be more consistent with Java samples.
 * Added some additional sample Block op modes.
 * Reworded OnBot Java readme slightly.

**************************************************************************************

Version 3.3 (built on 17.09.04)

This version of the software includes improves for the FTC Blocks Programming Tool and the OnBot Java Programming Tool.

Changes with verion 3.3 include:
 * Android Studio ftc_app project has been updated to use Gradle Plugin 2.3.3.
 * Android Studio ftc_app project is already using gradle 3.5 distribution.
 * Robot Controller log has been renamed to /sdcard/RobotControllerLog.txt (note that this change was actually introduced w/ v3.2).
 * Improvements in I2C reliability.
 * Optimized I2C read for REV Expansion Hub, with v1.7 firmware or greater.
 * Updated all external/samples (available through OnBot and in Android project folder).
 * Vuforia
    - Added support for VuMarks that will be used for the 2017-2018 season game.
 * Blocks
    - Update to latest Google Blockly release.
    - Sample op modes can be selected as a template when creating new op mode.
    - Fixed bug where the blocks would disappear temporarily when mouse button is held down.
    - Added blocks for Range.clip and Range.scale.
    - User can now disable/enable Block op modes.
    - Fix to prevent occasional Blocks deadlock.
 * OnBot Java
    - Significant improvements with autocomplete function for OnBot Java editor.
    - Sample op modes can be selected as a template when creating new op mode.
    - Fixes and changes to complete hardware setup feature.
    - Updated (and more useful) onBot welcome message.
    
Known issues:
 * Android Studio
    - After updating to the new v3.3 Android Studio project folder, if you get error messages indicating "InvalidVirtualFileAccessException" then you might need to do a File->Invalidate Caches / Restart to clear the error.
 * OnBot Java
    - Sometimes when you push the build button to build all op modes, the RC returns an error message that the build failed.  If you press the build button a second time, the build typically suceeds.
>>>>>>> 4396aa89761b5a959ecce0dd811c54efeb0d019b
    
## Credits
Copyright (C) 2017 La Salle Robotics
