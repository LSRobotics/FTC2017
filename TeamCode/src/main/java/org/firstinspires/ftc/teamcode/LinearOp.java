/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

//import com.qualcomm.ftccommon.configuration.ScannedDevices;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.*;




/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class LinearOp extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private boolean isDPadUphanged = false,
                    isDPadDownChanged = false,
                    isJleftXChanged = false,
                    isJleftYChanged = false,
                    isJrightXChanged = false,
                    isJrightYChanged = false,
                    isLBChanged = false;


    //Create objects for access
    private MecanumDrive mWheel = new MecanumDrive();
    private ServoControl jArm = new ServoControl();
    GamepadSpace previous;
    private VuMarkSys vumark = new VuMarkSys(hardwareMap);



    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        initModules();//Modules init

        waitForStart(); // Wait for the game to start (driver presses PLAY)
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            detectGPChange();

            if (isLBChanged) { //Sniping Mode Switch
                if (gamepad1.left_bumper) {mWheel.speed = 0.3; jArm.speed = 0.3;} // Change the speed of Mecanum Wheels if Y key got pressed
                else {mWheel.speed = 1.0;jArm.speed = 1.0;}
            }


            if (isJleftXChanged || isJleftYChanged || isJrightXChanged || isJrightYChanged) {
                mWheel.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);
            } //Drive the bot if any joystick moved



            if (isDPadUphanged || isDPadDownChanged)
                jArm.move(gamepad1.dpad_up ? 1 : 0, gamepad1.dpad_down ? 1 : 0); //Move the arm if triggered DPAD Up or Down

            //Save Data for next loop
            saveGPData();

            //Start putting information on the Driver Station
            telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Motors",
                    "Left FrontWheel (%.2f) "
                            + "Right FrontWheel (%.2f) "
                            + "Left BackWheel (%.2f) "
                            + "Right BackWheel (%.2f)",
                    mWheel.leftFrontPower,
                    mWheel.rightFrontPower,
                    mWheel.leftBackPower,
                    mWheel.rightBackPower
            );

            telemetry.update();
        }
    }

    private void initModules() {

        //Jewel Arm
        jArm.deviceName = Statics.Servos.jewel;
        jArm.toFORWARD = true;
        jArm.initMotors();

        //Mecanum Wheel
        mWheel.FLDeviceName = Statics.MecanumWheel.Front.left;
        mWheel.FRDeviceName = Statics.MecanumWheel.Front.right;
        mWheel.BLDeviceName = Statics.MecanumWheel.Back.left;
        mWheel.BRDeviceName = Statics.MecanumWheel.Front.right;
        mWheel.initMotors();

    }

    private void detectGPChange() {
        isDPadUphanged    = gamepad1.dpad_up != previous.DPadUp? true : false;
        isDPadDownChanged = gamepad1.dpad_down != previous.DPadDown? true : false;
        isLBChanged       = gamepad1.left_bumper != previous.LB? true : false;
        isJleftXChanged   = gamepad1.left_stick_x != previous.JleftX? true : false;
        isJleftYChanged   = gamepad1.left_stick_y != previous.JleftY? true : false;
        isJrightXChanged  = gamepad1.right_stick_x != previous.JrightX? true : false;
        isJrightYChanged  = gamepad1.right_stick_y != previous.JrightY? true : false;
    }

    private void saveGPData() {
        previous.DPadDown = gamepad1.dpad_down;
        previous.DPadUp = gamepad1.dpad_up;
        previous.LB = gamepad1.left_bumper;
        previous.JleftX = gamepad1.left_stick_x;
        previous.JleftY = gamepad1.left_stick_y;
        previous.JrightX = gamepad1.right_stick_x;
        previous.JrightY = gamepad1.right_stick_y;
    }
}