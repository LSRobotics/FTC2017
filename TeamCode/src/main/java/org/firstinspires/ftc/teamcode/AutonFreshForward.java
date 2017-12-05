package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@Autonomous(name = "FRESH_POS_FORWARD",group = "Freshman")
public class AutonFreshForward extends LinearOpMode {

    //Initialize objects
    private     DriveTrain      mWheel;

    private     Servo           GGrabberLObj;
    private     Servo           GGrabberRObj;

    // Declare OpMode members.
    final private ElapsedTime globalTime = new ElapsedTime();
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {

        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.Freshman.LWheel);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.Freshman.RWheel);

        mWheel = new DriveTrain(BL,BR);

        //Glyph Grabbers
        //GGrabberLObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.left_glyphGrabber);
        //GGrabberRObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.right_glyphGrabber);

    }

    private boolean wait(double seconds) {
        stageTime.reset();
        while(opModeIsActive()&&stageTime.seconds() <= seconds){continue;}
        if(!opModeIsActive()) return false;
        else return true;

    }

    @Override
    public void runOpMode() {

        initialize();
        waitForStart();
        //Move Jewels

        //Move Forward
        stageTime.reset();
        mWheel.tankDrive(-1,0);
        if(!wait(0.328)) return;
        mWheel.tankDrive(0,0);



    }
}
