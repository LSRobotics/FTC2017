package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;
import org.firstinspires.ftc.teamcode.Utils;

/**
 * Created by LBYPatrick on 11/2/2017.
 */
@TeleOp(name = "Grabber Test SOPH", group = "Sophomore")
public class GrabberTest extends LinearOpMode {

    //final private Servo S1 = hardwareMap.get(Servo.class, Statics.Servos.left_glyphGrabber);

    //Initialize objects
    private     ServoControl    GGrabberL;
    private     ServoControl    GGrabberR;
    private     Servo           GGrabberLObj;
    private     Servo           GGrabberRObj;

    final private GamepadSpace previous = new GamepadSpace();
    final private ElapsedTime   runtime = new ElapsedTime();



    private void collectGPStat() {
        previous.stat.LT         = gamepad1.left_trigger != 0;
        previous.stat.RT         = gamepad1.right_trigger!= 0;
    }

    private void saveGPData() {
        previous.LT        = gamepad1.left_trigger;
        previous.RT        = gamepad1.right_trigger;
    }

    private void initialize(){

        GGrabberLObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.left_glyphGrabber);
        GGrabberRObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.right_glyphGrabber);

        GGrabberL = new ServoControl(GGrabberLObj, true, -1, 1);
        GGrabberR = new ServoControl(GGrabberRObj,false,-1,1);

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
                GGrabberL.moveGlyphGrabber(GGrabberLObj,true);
                GGrabberR.moveGlyphGrabber(GGrabberRObj,true);
             }
            else if(previous.stat.RT) { //RT for moving the grabbers outward
                GGrabberL.moveGlyphGrabber(GGrabberLObj,false);
                GGrabberR.moveGlyphGrabber(GGrabberRObj,false);
            }
            //Save Data for next loop
            //saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Left Grabber:", GGrabberL.servoPos);
            telemetry.addData("Right Grabber:", GGrabberR.servoPos);
            telemetry.update();
        }
    }
}

