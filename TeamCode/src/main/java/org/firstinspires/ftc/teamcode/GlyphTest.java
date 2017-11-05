package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/2/2017.
 */
@TeleOp(name = "Glyph Test", group = "Linear Opmode")
public class GlyphTest extends LinearOpMode {

    //final private Servo S1 = hardwareMap.get(Servo.class, Statics.Servos.left_glyphGrabber);

    //Initialize objects
    private ServoControl glyphGrabber_left;
    private ServoControl glyphGrabber_right;
    final private GamepadSpace previous = new GamepadSpace();

    // Declare OpMode members.
    final private ElapsedTime runtime = new ElapsedTime();
    private boolean isLTPressed = false,
                    isRTPressed = false;


    private void detectGPChange() {
        isLTPressed         = gamepad1.left_trigger != 0;
        isRTPressed         = gamepad1.right_trigger!= 0;
    }

    private void saveGPData() {
        previous.LT        = gamepad1.left_trigger;
        previous.RT        = gamepad1.right_trigger;
    }

    private void initialize(){

        Servo s1 = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.left_glyphGrabber);
        Servo s2 = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.right_glyphGrabber);
        glyphGrabber_left = new ServoControl(s1, true, -1, 1);
        glyphGrabber_right = new ServoControl(s2,false,-1,1);

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

            detectGPChange();

            if(isLTPressed) { //LT for moving the grabbers inward
                glyphGrabber_left.moveGlyphGrabber(true);
                glyphGrabber_right.moveGlyphGrabber(true);
             }
            else if(isRTPressed) { //RT for moving the grabbers outward
                glyphGrabber_left.moveGlyphGrabber(false);
                glyphGrabber_right.moveGlyphGrabber(false);
            }
            //Save Data for next loop
            //saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.addData("Left Grabber:", glyphGrabber_left.servoPos);
            telemetry.addData("Right Grabber:", glyphGrabber_right.servoPos);
            telemetry.update();
        }
    }
}

