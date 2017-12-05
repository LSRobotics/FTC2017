package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DcMotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 2017/11/5.
 */
@TeleOp(name="FRESH_tankDrive", group="Freshman")
final public class TeleOp9T extends LinearOpMode {

        //Initialize objects
        private     DriveTrain      tankWheel;
        private     GamepadSpace    previous;
        private     DcMotorControl  GLift;
        private     DcMotor         GLiftObj;
    private     DcMotor                intakeObj;
    private     DcMotorControl         intake;

        // Declare OpMode members.
        final private ElapsedTime runtime = new ElapsedTime();

        private void collectGPStat() {
            previous.stat.LB         = gamepad1.left_bumper;
            previous.stat.JLeftY     = gamepad1.left_stick_y != previous.JLeftY;
            previous.stat.JRightX    = gamepad1.right_stick_x != previous.JRightX;
            previous.stat.DPadUp         = gamepad1.dpad_up;
            previous.stat.DPadDown       = gamepad1.dpad_down;
            previous.stat.LT         = gamepad1.left_trigger != 0;
            previous.stat.RT         = gamepad1.right_trigger!= 0;
        }

        private void saveGPData() {

            previous.Triangle  = gamepad1.y;
            previous.JLeftX    = gamepad1.left_stick_x;
            previous.JLeftY    = gamepad1.left_stick_y;
            previous.JRightX   = gamepad1.right_stick_x;
            previous.LT        = gamepad1.left_trigger;
            previous.RT        = gamepad1.right_trigger;
        }

        private void initialize(){
            DcMotor leftMotor = hardwareMap.get(DcMotor.class, Statics.FRESH_L_WHEEL);
            DcMotor rightMotor = hardwareMap.get(DcMotor.class, Statics.FRESH_R_WHEEL);
            tankWheel = new DriveTrain(leftMotor,rightMotor);
            previous = new GamepadSpace();
            GLiftObj = hardwareMap.get(DcMotor.class, Statics.GLYPH_LIFT);
            GLift = new DcMotorControl(GLiftObj,false);

            intakeObj = hardwareMap.get(DcMotor.class, Statics.FRESH_INTAKE);
            intake = new DcMotorControl(intakeObj, true);
            intake.sensitivity = 1.0;

        }

        @Override
        public void runOpMode() {

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            initialize();
            waitForStart(); // Wait for the game to start (driver presses PLAY)
            runtime.reset();

            telemetry.addData("Status", "Running");
            telemetry.update();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {

                collectGPStat();

                if (previous.stat.LB) { //Sniping Mode Switch
                    // Change the speed of Mecanum Wheels if Y key got pressed
                    tankWheel.updateSpeedLimit(0.6);
                    GLift.updateSpeedLimit(0.6);
                }
                else {
                    tankWheel.updateSpeedLimit(1.0);
                    GLift.updateSpeedLimit(1.0);
                }

                if (previous.stat.JLeftY || previous.stat.JRightX) {
                    tankWheel.tankDrive(-gamepad1.left_stick_y, gamepad1.right_stick_x);
                } //Drive the bot if any joystick moved

                GLift.moveLift(previous.stat.DPadUp,previous.stat.DPadDown);

                //Intake
                intake.moveLift(previous.stat.LT,previous.stat.RT);
                //Save Data for next loop
                saveGPData();

                //Start putting information on the Driver Stations


                if(Statics.FRESH_VISUALIZING) {
                    telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
                    telemetry.addData("Tank Wheels   ", " ");
                    telemetry.addData("Left Front Wheel ", tankWheel.getSpeed(1) + "\n\tencoder: ");
                    telemetry.addData("Right Front Wheel", tankWheel.getSpeed(0) + "\n\tencoder: ");
                }
                telemetry.update();
            }
            telemetry.addData("Status","Stopped");
            telemetry.update();
        }
    }
