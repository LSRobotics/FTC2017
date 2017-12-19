package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@Autonomous(name = "SOPH_POS_A",group = "Sophomore")
final public class AutonSophA extends LinearOpMode {

    private AutonHelper auton;
    private Servo GGrabberLObj;
    private Servo GGrabberRObj;
    private ServoControl GGrabberL;
    private ServoControl GGrabberR;
    private DcMotorControl GLift;

    // Declare OpMode members.
    final private ElapsedTime globalTime = new ElapsedTime();
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {

        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.SOPH_RL_WHEEL);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.SOPH_RR_WHEEL);

        DriveTrain mWheel = new DriveTrain(BL, BR);

        //Servo jArmObj = hardwareMap.get(Servo.class, Statics.SOPH_SERVO_JEWEL);
        //ServoControl jArm = new ServoControl(jArmObj, true, 0.13, 0.7);
        //Glyph Grabbers
        GGrabberLObj = hardwareMap.get(Servo.class, Statics.SOPH_LEFT_GLYPH_GRABBER);
        GGrabberRObj = hardwareMap.get(Servo.class, Statics.SOPH_RIGHT_GLYPH_GRABBER);
        GGrabberL = new ServoControl(GGrabberLObj, false, -1, 1);
        GGrabberR = new ServoControl(GGrabberRObj, true, -1, 1);

        DcMotor GLiftObj = hardwareMap.dcMotor.get(Statics.GLYPH_LIFT);
        GLift = new DcMotorControl(GLiftObj,false);

        auton = new AutonHelper(this, mWheel,false);
    }

    private boolean wait(double seconds) {
        stageTime.reset();
        while(opModeIsActive() && stageTime.seconds() <= seconds) {
            telemetry.addData("Current Stage: ", "Idle");
            telemetry.addData( "Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        return opModeIsActive();
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
        if(!wait(1.0)) return;


        if(!auton.moveLiftUp(GLift,1.0)) return;

        //Move forward
        if(!auton.moveForward(Statics.SOPH_AUTON_STAGE_ONE_TIME)) return;


        if(!wait(3.0)) return;

        //Turn left
        if(!auton.turnLeft(Statics.SOPH_AUTON_STAGE_TWO_TIME)) return;

        if(!wait(3.0)) return;

        //Move forward a little bit
        if(!auton.moveForward(Statics.SOPH_AUTON_STAGE_THREE_TIME)) return;

        //Release Glyphs
        GGrabberRObj.setPosition(GGrabberL.getMaxPos());
        GGrabberLObj.setPosition(GGrabberR.getMaxPos());
        if(!wait(1.0)) return;


        if(!auton.moveBack(0.2)) return;

        if(!wait(1.0)) return;

        if(!auton.moveForward(0.2)) return;

        if(!wait(1.0)) return;

        auton.moveBack(0.05);
    }
}
