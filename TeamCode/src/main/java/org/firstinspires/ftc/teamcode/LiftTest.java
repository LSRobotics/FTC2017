package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Utils;


/**
 * Created by LBYPatrick on 11/10/2017.
 */

@TeleOp(name = "Glyph Lift Test", group = "Linear Opmode")

public class LiftTest extends LinearOpMode {
    private     DcMotorControl  GLift;

    private     DcMotor         GLiftObj;

    final private GamepadSpace previous = new GamepadSpace();
    final private ElapsedTime   runtime = new ElapsedTime();


    private void collectGPStat() {
        previous.stat.DPadUp         = gamepad1.dpad_up != previous.DPadUp;
        previous.stat.DPadDown       = gamepad1.dpad_down != previous.DPadDown;
        previous.stat.LB             = gamepad1.left_bumper != previous.LB;
    }

    private void saveGPData() {
        previous.DPadUp        = gamepad1.dpad_up;
        previous.DPadDown      = gamepad1.dpad_down;
        previous.LB            = gamepad1.left_bumper;
    }

    private void initialize(){

        GLiftObj = hardwareMap.get(DcMotor.class, Statics.Sophomore.glyphLift);
        GLift = new DcMotorControl(GLiftObj,true);
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

            if(previous.stat.LB){
                if(gamepad1.left_bumper) GLift.updateSpeedLimit(GLiftObj,0.6);
                else GLift.updateSpeedLimit(GLiftObj,1.0);
            }

            if(previous.stat.DPadUp || previous.stat.DPadDown) { //LT for moving the grabbers inward
                GLift.moveLift(GLiftObj,gamepad1.dpad_up,gamepad1.dpad_down);
            }

            //Save Data for next loop
            //saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Lift Motor: ", GLiftObj.getPower());
            telemetry.update();
        }
    }
}
