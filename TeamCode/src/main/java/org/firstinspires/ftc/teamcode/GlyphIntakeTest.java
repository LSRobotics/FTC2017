package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DcMotorControl;
import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/13/2017.
 */

@TeleOp(name = "Glyph Intake Test", group = "Freshman")

public class GlyphIntakeTest extends LinearOpMode{
    private     DcMotor                leftIntakeObj;
    private     DcMotorControl         leftIntake;
    private     DcMotor                rightIntakeObj;
    private     DcMotorControl         rightIntake;

    final private GamepadSpace previous = new GamepadSpace();
    final private ElapsedTime runtime = new ElapsedTime();



    private void collectGPStat() {
        previous.stat.LT         = gamepad1.left_trigger != 0;
        previous.stat.RT         = gamepad1.right_trigger!= 0;
    }

    private void saveGPData() {
        previous.LT        = gamepad1.left_trigger;
        previous.RT        = gamepad1.right_trigger;
    }

    private void initialize(){

        leftIntakeObj = hardwareMap.get(DcMotor.class, Statics.Freshman.leftIntake);
        rightIntakeObj = hardwareMap.get(DcMotor.class, Statics.Freshman.rightIntake);

        leftIntake = new DcMotorControl(leftIntakeObj, true);
        rightIntake = new DcMotorControl(rightIntakeObj,false);
        leftIntake.sensitivity = 1.0;
        rightIntake.sensitivity = 1.0;

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

            if(previous.stat.LT) { //LT for moving the grabbers inward
                leftIntake.moveLift(leftIntakeObj,true,false);
                rightIntake.moveLift(rightIntakeObj,true,false);
            }
            else if(previous.stat.RT) { //RT for moving the grabbers outward
                leftIntake.moveLift(leftIntakeObj,false,true);
                rightIntake.moveLift(rightIntakeObj,false,true);
            }
            //Save Data for next loop
            //saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Left Intake:", leftIntakeObj.getPower());
            telemetry.addData("Right Intake:", rightIntakeObj.getPower());
            telemetry.update();
        }
    }
}
