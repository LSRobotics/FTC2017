package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.MotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.actuators.GamepadControl;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 2017/11/5.
 */
@TeleOp(name="FRESH_tankDrive", group="Freshman")
final public class TeleOp9T extends LinearOpMode {

        //Initialize objects
        private     DriveTrain      tankWheel;
        private     GamepadControl   gamepad;
        private MotorControl GLift;
        private MotorControl intake;

        // Declare OpMode members.
        final private ElapsedTime runtime = new ElapsedTime();

        private void initialize(){

            //Tank Wheels
            MotorControl leftMotor = new MotorControl(hardwareMap.get(DcMotor.class, Statics.FRESH_L_WHEEL));
            MotorControl rightMotor = new MotorControl(hardwareMap.get(DcMotor.class, Statics.FRESH_R_WHEEL));
            tankWheel = new DriveTrain(leftMotor,rightMotor);

            //Glyph Lift
            DcMotor GLiftObj = hardwareMap.get(DcMotor.class, Statics.GLYPH_LIFT);
            GLift = new MotorControl(GLiftObj,false);

            //Glyph Intake
            DcMotor intakeObj = hardwareMap.get(DcMotor.class, Statics.FRESH_INTAKE);
            intake = new MotorControl(intakeObj, true);

            //gamepad
            gamepad = new GamepadControl(this.gamepad1);

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

                gamepad.updateStatus();

                if (gamepad.L1) { //Sniping Mode Switch
                    // Change the speed of Mecanum Wheels if Y key got pressed
                    if(gamepad.current.L1) {
                        tankWheel.updateSpeedLimit(0.6);
                        GLift.updateSpeedLimit(0.6);
                    }
                    else {
                        tankWheel.updateSpeedLimit(1.0);
                        GLift.updateSpeedLimit(1.0);
                    }
                }


                if (gamepad.JLeftY || gamepad.JRightX)
                    tankWheel.tankDrive(gamepad.current.JLeftY, gamepad.current.JRightX); //Drive the bot if any joystick moved

                if(gamepad.DPadUp || gamepad.DPadDown)
                    GLift.moveWithButton(gamepad.current.DPadUp, gamepad.current.DPadDown);


                //Intake
                if(gamepad.L2 || gamepad.R2)
                    intake.moveWithButton(gamepad.current.L2 != 0,gamepad.current.R2 != 0);

                //Start putting information on the Driver Stations
                if(Statics.FRESH_VISUALIZING) {
                    telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
                    telemetry.addData("Tank Wheels   ", " ");
                    telemetry.addData("Left Wheel ", tankWheel.getSpeed(DriveTrain.Wheels.REAR_LEFT) + "\n\tencoder: ");
                    telemetry.addData("Right Wheel", tankWheel.getSpeed(DriveTrain.Wheels.REAR_RIGHT) + "\n\tencoder: ");
                }
                telemetry.update();
            }
            telemetry.addData("Status","Stopped");
            telemetry.update();
        }
    }
