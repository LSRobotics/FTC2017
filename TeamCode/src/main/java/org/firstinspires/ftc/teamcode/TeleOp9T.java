package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.Controller;
import org.firstinspires.ftc.teamcode.actuators.MotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 2017/11/5.
 */
@TeleOp(name="FRESH_tankDrive", group="Freshman")
final public class TeleOp9T extends LinearOpMode {

        //Initialize objects
        private     DriveTrain      tankWheel;
        private Controller gamepad;
        private MotorControl GLift;
        private MotorControl intake;
        private boolean isSNP = false;

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
            gamepad = new Controller(this.gamepad1);

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

                if (gamepad.isKeyToggled(Controller.LB)) { //Sniping Mode Switch

                    isSNP = !isSNP;

                    final double speedLimit = isSNP ? 0.6 : 1.0;

                    GLift.updateSpeedLimit(speedLimit);
                    tankWheel.updateSpeedLimit(speedLimit);
                    intake.updateSpeedLimit(speedLimit);
                }


                if (gamepad.isKeyChanged(Controller.jLeftY) || gamepad.isKeyChanged(Controller.jRightX)) {
                    tankWheel.tankDrive(-gamepad.getValue(Controller.jLeftY), gamepad.getValue(Controller.jRightX)); //Drive the bot if any joystick moved
                }

                if(gamepad.isKeyChanged(Controller.dPadUp) || gamepad.isKeyChanged(Controller.dPadDown)) {
                    GLift.moveWithButton(gamepad.isKeyHeld(Controller.dPadUp), gamepad.isKeyHeld(Controller.dPadDown));
                }

                //Intake
                if(gamepad.isKeyChanged(Controller.LT) || gamepad.isKeyChanged(Controller.LT))
                    intake.moveWithButton(gamepad.isKeyHeld(Controller.LT),gamepad.isKeyHeld(Controller.RT));
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
