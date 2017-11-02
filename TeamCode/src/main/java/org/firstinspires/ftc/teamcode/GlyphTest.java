package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.MecanumDrive;
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
    final GamepadSpace previous = new GamepadSpace();

    // Declare OpMode members.
    final private ElapsedTime runtime = new ElapsedTime();
    private boolean isLTChanged = false,
                    isRTChanged = false;


    private void detectGPChange() {
        isLTChanged         = gamepad1.left_trigger != previous.LT;
        isRTChanged         = gamepad1.right_trigger != previous.RT;
    }

    private void saveGPData() {
        previous.LT        = gamepad1.left_trigger;
        previous.RT        = gamepad1.right_trigger;
    }

    private void initialize(){

        Servo s1 = hardwareMap.get(Servo.class, Statics.Servos.left_glyphGrabber);
        Servo s2 = hardwareMap.get(Servo.class, Statics.Servos.right_glyphGrabber);
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

            if(isLTChanged || isRTChanged) {
                glyphGrabber_left.move_glyphGrabber();
                glyphGrabber_right.move_glyphGrabber();
            }
            //Save Data for next loop
            saveGPData();

            //Start putting information on the Driver Station
            //telemetry.addData("VuMark", vumark.getPos()); // Get VuMark informations
            telemetry.addData("Status           ", "Run Time: " + runtime.toString());// Show the elapsed game time and wheel power.
            telemetry.update();
        }
    }
}

