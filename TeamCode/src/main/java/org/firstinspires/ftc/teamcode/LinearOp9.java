package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;
import org.firstinspires.ftc.teamcode.Utils;

/**
 * Created by LBYPatrick on 2017/11/5.
 */
@TeleOp(name="Test Drive FRESH", group="Freshman")
public class LinearOp9 extends LinearOpMode {

        //Initialize objects
        private     DriveTrain      tankWheel;
        private     GamepadSpace    previous;

        // Declare OpMode members.
        final private ElapsedTime runtime = new ElapsedTime();

        private void collectGPStat() {
            previous.stat.LB         = gamepad1.left_bumper != previous.LB;
            previous.stat.JLeftY     = gamepad1.left_stick_y != previous.JLeftY;
            previous.stat.JRightX    = gamepad1.right_stick_x != previous.JRightX;
        }

        private void saveGPData() {

            previous.Triangle  = gamepad1.y;
            previous.LB        = gamepad1.left_bumper;
            previous.JLeftX    = gamepad1.left_stick_x;
            previous.JLeftY    = gamepad1.left_stick_y;
            previous.JRightX   = gamepad1.right_stick_x;
            previous.LT        = gamepad1.left_trigger;
            previous.RT        = gamepad1.right_trigger;
        }

        private void initialize(){
            DcMotor leftMotor = hardwareMap.get(DcMotor.class, Statics.Freshman.TankWheel.left);
            DcMotor rightMotor = hardwareMap.get(DcMotor.class, Statics.Freshman.TankWheel.right);
            tankWheel = new DriveTrain(leftMotor,rightMotor);
            previous = new GamepadSpace();

        }

        @Override
        public void runOpMode() {

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            initialize();
            waitForStart(); // Wait for the game to start (driver presses PLAY)
            runtime.reset();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

                collectGPStat();

                if (previous.stat.LB) { //Sniping Mode Switch
                    // Change the speed of Mecanum Wheels if Y key got pressed
                    if (gamepad1.left_bumper) tankWheel.updateSpeedLimit(0.6);
                    else tankWheel.updateSpeedLimit(1.0);
                }

                if (previous.stat.JLeftY || previous.stat.JRightX) {
                    tankWheel.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_x);
                } //Drive the bot if any joystick moved

                //Save Data for next loop
                saveGPData();

                //Start putting information on the Driver Stations
                //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
                telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
                telemetry.addData("Tank Wheels   ", " ");
                telemetry.addData("Left Front Wheel ", tankWheel.FL.getPower() + "\n\tencoder: " + tankWheel.FL.getCurrentPosition());
                telemetry.addData("Right Front Wheel", tankWheel.FR.getPower() + "\n\tencoder: " + tankWheel.FR.getCurrentPosition());
                telemetry.update();
            }
        }
    }
