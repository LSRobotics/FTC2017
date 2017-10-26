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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//import com.qualcomm.robotcore.util.Range;
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

@TeleOp(name="Test Drive", group="Linear Opmode")
public class TestDrive extends LinearOpMode {

    //Tank Drive Class
    final private class TankDrive {
        private DcMotor leftMotor = null;
        private DcMotor rightMotor = null;
        public double leftPower;
        public double rightPower;

        public double speed = 0.1;
        public String LDeviceName = null;
        public String RDeviceName = null;

        public void initMotors(String LName, String RName) {
            // Initialize the hardware variables. Note that the strings used here as parameters
            // to 'get' must correspond to the names assigned during the robot configuration
            // step (using the FTC Robot Controller app on the phone).

            leftMotor = hardwareMap.get(DcMotor.class, LDeviceName);
            rightMotor = hardwareMap.get(DcMotor.class, RDeviceName);

            leftMotor.setDirection(DcMotor.Direction.FORWARD);
            rightMotor.setDirection(DcMotor.Direction.REVERSE);
        }

        public void initMotors() {
            initMotors(LDeviceName,RDeviceName);
        }
        public void move(float JleftY, float JrightX) {

            leftPower    = Range.clip((-JleftY) + JrightX, -1.0, 1.0) ;
            rightPower   = Range.clip((-JleftY) - JrightX, -1.0, 1.0) ;

            // Send calculated power to wheels
            leftPower *= speed;
            rightPower *= speed;
            leftMotor.setPower(leftPower);
            rightMotor.setPower(rightPower);
        }
    }

    //Mecanum Wheel Drive Class
    final private class MecanumDrive {

        private DcMotor leftFront = null;
        private DcMotor rightFront = null;
        private DcMotor leftBack = null;
        private DcMotor rightBack = null;
        public double leftFrontPower;
        public double rightFrontPower;
        public double leftBackPower;
        public double rightBackPower;

        public double speed = 0.1;
        public String FLDeviceName;
        public String FRDeviceName;
        public String BLDeviceName;
        public String BRDeviceName;

        MecanumDrive(String FL,String FR,String BL,String BR) {FLDeviceName = FL;FRDeviceName = FR;BLDeviceName = BL;BRDeviceName = BR;}

        MecanumDrive(){}

        final public void init(String FL, String FR, String BL, String BR) {
            // Initialize the hardware variables. Note that the strings used here as parameters
            // to 'get' must correspond to the names assigned during the robot configuration
            // step (using the FTC Robot Controller app on the phone).

            leftFront = hardwareMap.get(DcMotor.class, FLDeviceName);
            rightFront = hardwareMap.get(DcMotor.class, FRDeviceName);
            leftBack = hardwareMap.get(DcMotor.class, BLDeviceName);
            rightBack = hardwareMap.get(DcMotor.class, BRDeviceName);

            leftFront.setDirection(DcMotor.Direction.FORWARD);
            leftBack.setDirection(DcMotor.Direction.FORWARD);
            rightFront.setDirection(DcMotor.Direction.REVERSE);
            rightBack.setDirection(DcMotor.Direction.REVERSE);
        }

        final public void init(){
            init(FLDeviceName,FRDeviceName,BLDeviceName,BRDeviceName);
        }

        final public void move(float leftX, float leftY, float rightX) {

            leftX = -leftX; //Essential for FTC 2018

            //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
            double r = Math.hypot(leftX, leftY);
            double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;


            // Setup a variable for each drive wheel to save power level for telemetry

            leftFrontPower = r * Math.cos(robotAngle) + rightX;
            rightFrontPower = r * Math.sin(robotAngle) - rightX;
            leftBackPower = r * Math.sin(robotAngle) + rightX;
            rightBackPower = r * Math.cos(robotAngle) - rightX;

            leftFrontPower *= speed;
            rightFrontPower *= speed;
            leftBackPower *= speed;
            rightBackPower *= speed;
            // Send calculated power to wheels
            leftFront.setPower(leftFrontPower);
            rightFront.setPower(rightFrontPower);
            leftBack.setPower(leftBackPower);
            rightBack.setPower(rightBackPower);
        }
    }

    //Servo Control Class
    final private class ServoControl {

        private Servo servoOBJ = null;
        private double servoPos = 0;
        public double speed = 0.1;
        public String deviceName;
        public boolean toFORWARD = true;

        ServoControl(String DName, boolean forward){deviceName = DName; toFORWARD = forward;}
        ServoControl(String DName){deviceName = DName;}
        ServoControl(){}

        public void init(String DName) {
            servoOBJ = hardwareMap.get(Servo.class, DName);
            if(toFORWARD) servoOBJ.setDirection(Servo.Direction.FORWARD);
            else servoOBJ.setDirection(Servo.Direction.REVERSE);
        }

        public void init(){
            init(deviceName);
        }

        public int move(double plus, double minus) {
            //Right trigger ==> Plus; Left trigger ==> Minus

            if ((plus - minus) == 0) return 1;
            else {
                servoPos += ((plus - minus)* speed); // Determines whether going forward

                //Out-of-Range Detection
                if (servoPos < -1) servoPos = -1;
                else if (servoPos > 1) servoPos = 1;

                servoOBJ.setPosition(servoPos);
                return 0;
            }
        }
    }



    //Initialize objects
    final private MecanumDrive mWheel =
            new MecanumDrive(
                    Statics.MecanumWheel.Front.left,
                    Statics.MecanumWheel.Front.right,
                    Statics.MecanumWheel.Back.left,
                    Statics.MecanumWheel.Back.right
            );

    final private ServoControl jArm = new ServoControl(Statics.Servos.jewel,true);
    final private GamepadSpace previous = new GamepadSpace();

    // Declare OpMode members.
    final private ElapsedTime runtime = new ElapsedTime();
    private boolean isDPadUphanged = false,
            isDPadDownChanged = false,
            isJleftXChanged = false,
            isJleftYChanged = false,
            isJrightXChanged = false,
            isLBChanged = false;



    @Override
    public void runOpMode(){
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        jArm.init();
        mWheel.init();
        runtime.reset();

        while (opModeIsActive()) {

            detectGPChange();

            if (isLBChanged) { //Sniping Mode Switch
                if (gamepad1.left_bumper) {mWheel.speed = 0.3; jArm.speed = 0.3;} // Change the speed of Mecanum Wheels if Y key got pressed
                else {mWheel.speed = 1.0;jArm.speed = 1.0;}
            }


            if (isJleftXChanged || isJleftYChanged || isJrightXChanged) {
                mWheel.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            } //Drive the bot if any joystick moved



            if (isDPadUphanged || isDPadDownChanged)
                jArm.move(gamepad1.dpad_up ? 1 : 0, gamepad1.dpad_down ? 1 : 0); //Move the arm if triggered DPAD Up or Down

            //Save Data for next loop
            saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Mecanum Wheels", " " );
            telemetry.addData("Left Front Wheel", mWheel.leftFrontPower);
            telemetry.addData("Right Front Wheel", mWheel.rightFrontPower);
            telemetry.addData("Left Back Wheel", mWheel.leftBackPower);
            telemetry.addData("Right Back Wheel", mWheel.leftBackPower);
            telemetry.addData("Gamepad", " ");
            telemetry.addData("Left Joystick", "(" + previous.JleftX + ", " + previous.JleftY + ")");

            telemetry.update();
        }
    }


    private void detectGPChange() {
        isDPadUphanged    = gamepad1.dpad_up != previous.DPadUp;
        isDPadDownChanged = gamepad1.dpad_down != previous.DPadDown;
        isLBChanged       = gamepad1.left_bumper != previous.LB;
        isJleftXChanged   = gamepad1.left_stick_x != previous.JleftX;
        isJleftYChanged   = gamepad1.left_stick_y != previous.JleftY;
        isJrightXChanged  = gamepad1.right_stick_x != previous.JrightX;
    }

    private void saveGPData() {
        previous.DPadDown = gamepad1.dpad_down;
        previous.DPadUp = gamepad1.dpad_up;
        previous.LB = gamepad1.left_bumper;
        previous.JleftX = gamepad1.left_stick_x;
        previous.JleftY = gamepad1.left_stick_y;
        previous.JrightX = gamepad1.right_stick_x;
    }
}
