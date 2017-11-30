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

@TeleOp(name = "Glyph Intake Test FRESH", group = "Freshman")

public class GlyphIntakeTest extends LinearOpMode{
    private     DcMotor                intakeObj;
    private     DcMotorControl         intake;

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

        intakeObj = hardwareMap.get(DcMotor.class, Statics.Freshman.Intake);

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

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            collectGPStat();

            intake.moveLift(intakeObj,previous.stat.LT,previous.stat.RT);
            //Save Data for next loop
            //saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Intake:", intakeObj.getPower());
            telemetry.update();
        }
    }
}
