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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DcMotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.actuators.GamepadControl;
import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.Statics;

//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.Range;

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

@TeleOp(name="SOPH_tankDrive_NFS", group="Sophomore")
final public class TeleOp10T extends LinearOpMode {


    //Initialize objects
    private     DriveTrain      mWheel;

    private     ServoControl    GGrabberL;
    private     ServoControl    GGrabberR;
    private     Servo           GGrabberLObj;
    private     Servo           GGrabberRObj;
    private DcMotorControl      GLift;

    private GamepadControl g1;



    // Declare OpMode members.
    final private ElapsedTime runtime = new ElapsedTime();

    private void initialize(){
        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.SOPH_RL_WHEEL);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.SOPH_RR_WHEEL);
        mWheel = new DriveTrain(BL,BR);

        //jArmObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.jewel);
        //jArm = new ServoControl(jArmObj, true, 0.13, 0.7);

        g1 = new GamepadControl(this.gamepad1);

        //Glyph Grabbers
        GGrabberLObj = hardwareMap.get(Servo.class, Statics.SOPH_LEFT_GLYPH_GRABBER);
        GGrabberRObj = hardwareMap.get(Servo.class, Statics.SOPH_RIGHT_GLYPH_GRABBER);

        GGrabberL = new ServoControl(GGrabberLObj, false, -1, 1);
        GGrabberR = new ServoControl(GGrabberRObj,true,-1,1);
        DcMotor GLiftObj = hardwareMap.get(DcMotor.class, Statics.GLYPH_LIFT);
        GLift = new DcMotorControl(GLiftObj,false);

    }

    @Override
    public void runOpMode() {

        boolean toCloseGrabbers = false;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialize();
        waitForStart(); // Wait for the game to start (driver presses PLAY)
        runtime.reset();

        telemetry.addData("Status", "Running");
        telemetry.update();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            g1.updateStatus();

            if (g1.Square) { //Sniping Mode Switch
                if(g1.current.Square) {
                    mWheel.updateSpeedLimit(0.6);
                    GLift.updateSpeedLimit(0.6);
                    GGrabberL.updateSpeedLimit(0.6);
                    GGrabberR.updateSpeedLimit(0.6);
                }
                else {
                    mWheel.updateSpeedLimit(1.0);
                    GLift.updateSpeedLimit(1.0);
                    GGrabberL.updateSpeedLimit(1.0);
                    GGrabberR.updateSpeedLimit(1.0);
                }
            }


            if (g1.L2 || g1.R2 || g1.JLeftX) {
                mWheel.tankDrive(-(g1.current.R2-g1.current.L2),g1.current.JLeftX);
            }
            //Jewel Arm (Currently disabled)
            //if (previous.stat.Triangle) jArm.moveJewelArm(jArmObj);

            if(g1.R1 || g1.L1)
            GLift.moveLift(g1.current.R1, g1.current.L1);

            //Glyph Grabber
            if(g1.Circle && g1.current.Circle) {
                    toCloseGrabbers = !toCloseGrabbers;
                    if(!toCloseGrabbers) {GGrabberL.setPosition(0.6);GGrabberR.setPosition(0.6);}
                    else {GGrabberL.setPosition(0.35);GGrabberR.setPosition(0.35);}
            }
            //Start putting information on the Driver Station


            if(Statics.SOPH_VISUALIZING) {
                    telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
                    telemetry.addData("RL encoder: ", mWheel.getEncoderInfo(DriveTrain.Wheels.REAR_LEFT));
                    telemetry.addData("RR encoder: ", mWheel.getEncoderInfo(DriveTrain.Wheels.REAR_RIGHT));
                    //telemetry.addData("Jewel Arm:  ", jArm.servoPos);
                    telemetry.addData("RL Wheel:        ", mWheel.getSpeed(DriveTrain.Wheels.REAR_LEFT));
                    telemetry.addData("RR Wheel:        ", mWheel.getSpeed(DriveTrain.Wheels.REAR_RIGHT));
                    telemetry.addData("GGrabbers:       ", GGrabberL.getPosition());

            }
            telemetry.update();

        }
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
