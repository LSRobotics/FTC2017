package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DcMotorControl;
import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@Autonomous(name = "SOPH_POS_B",group = "Sophomore")
final public class AutonSophB extends LinearOpMode {

    //Initialize objects
    private     DriveTrain      mWheel;
    private Servo GGrabberLObj;
    private Servo GGrabberRObj;
    private ServoControl GGrabberL;
    private ServoControl GGrabberR;
    private DcMotorControl GLift;
    private DcMotor        GLiftObj;

    // Declare OpMode members.
    final private ElapsedTime globalTime = new ElapsedTime();
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {

        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.rearLeft);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.rearRight);

        mWheel = new DriveTrain(BL,BR);

        Servo jArmObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.jewel);
        ServoControl jArm = new ServoControl(jArmObj, true, 0.13, 0.7);
        //Glyph Grabbers
        GGrabberLObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.left_glyphGrabber);
        GGrabberRObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.right_glyphGrabber);

        GGrabberL = new ServoControl(GGrabberLObj, false, -1, 1);
        GGrabberR = new ServoControl(GGrabberRObj, true, -1, 1);

        GLiftObj = hardwareMap.dcMotor.get(Statics.Sophomore.glyphLift);
        GLift = new DcMotorControl(GLiftObj,false);
    }

    private boolean wait(double seconds) {
        stageTime.reset();
        while(opModeIsActive() && stageTime.seconds() <= seconds) {
            telemetry.addData("Current Stage: ", "Idle");
            telemetry.addData( "Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        if(opModeIsActive()) return true;
        else return false;
    }

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();
        //Move Jewels

        //Move Forward
        stageTime.reset();
        GGrabberRObj.setPosition(0.35);
        GGrabberLObj.setPosition(0.35);
        GLift.moveLift(GLiftObj, true, false);

        if(!wait(1.0)) return;

        //Stage 1
        stageTime.reset();
        mWheel.tankDrive(-1, 0);
        while (opModeIsActive() && stageTime.seconds() <= Statics.Sophomore.Auton.stageOneTime) {
            telemetry.addData("Current Stage: ", "Moving forward");
            telemetry.addData("Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        mWheel.tankDrive(0, 0);
        GLift.moveLift(GLiftObj, false, false);

        if(!wait(3.0)) return;

        //Turn right
        stageTime.reset();
        mWheel.tankDrive(0, 1);
        while (opModeIsActive() && stageTime.seconds() <= Statics.Sophomore.Auton.stageTwoTime) {
            telemetry.addData("Current Stage", "Turning Left");
            telemetry.addData("Global Time", globalTime.seconds());
            telemetry.addData("Stage Time", stageTime.seconds());
        }
        mWheel.tankDrive(0, 0);

        if(!wait(3.0)) return;

        //Move forward a little bit
        stageTime.reset();
        mWheel.tankDrive(-1, 0);
        while (opModeIsActive() && stageTime.seconds() <= Statics.Sophomore.Auton.stageThreeTime) {
            telemetry.addData("Current Stage: ", "Moving forward");
            telemetry.addData("Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        mWheel.tankDrive(0, 0);
        //Release Glyphs
        GGrabberRObj.setPosition(GGrabberR.maxPos);
        GGrabberLObj.setPosition(GGrabberL.maxPos);

        if(!wait(1.0)) return;

        mWheel.tankDrive(1.0,0);
        if(!wait(0.2)) return;
        mWheel.tankDrive(0,0);

        mWheel.tankDrive(-1.0,0);
        if(!wait(0.2)) return;
        mWheel.tankDrive(0,0);

        mWheel.tankDrive(1.0,0);
        if(!wait(0.05)) return;
        mWheel.tankDrive(0,0);
    }
}
