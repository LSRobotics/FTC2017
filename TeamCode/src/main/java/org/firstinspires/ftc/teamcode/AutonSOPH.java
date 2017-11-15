package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.actuators.DriveTrain;
import org.firstinspires.ftc.teamcode.actuators.ServoControl;
import org.firstinspires.ftc.teamcode.databases.GamepadSpace;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 11/14/2017.
 */
@TeleOp(name = "Autonomous SOPH",group = "Sophomore")
public class AutonSOPH extends LinearOpMode {

    //Initialize objects
    private     DriveTrain      mWheel;

    private     ServoControl    jArm;
    private     Servo           jArmObj;
    private     ServoControl    GGrabberL;
    private     ServoControl    GGrabberR;
    private     Servo           GGrabberLObj;
    private     Servo           GGrabberRObj;
    // Declare OpMode members.
    final private ElapsedTime globalTime = new ElapsedTime();
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {
        DcMotor FL = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.frontLeft);
        DcMotor FR = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.frontRight);
        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.rearLeft);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.Sophomore.MecanumWheel.rearRight);
        mWheel = new DriveTrain(FL, FR, BL, BR);

        jArmObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.jewel);
        jArm = new ServoControl(jArmObj, true, 0.13, 0.7);

        //Glyph Grabbers
        GGrabberLObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.left_glyphGrabber);
        GGrabberRObj = hardwareMap.get(Servo.class, Statics.Sophomore.Servos.right_glyphGrabber);

        GGrabberL = new ServoControl(GGrabberLObj, false, -1, 1);
        GGrabberR = new ServoControl(GGrabberRObj,true,-1,1);


    }


    @Override
    public void runOpMode() {

    //Move Jewels


    //Move Forward
    stageTime.reset();
    mWheel.mecanumDrive(0,1,0);
    while(stageTime.seconds() <= 1.0) {
        telemetry.addData("Current Stage: ", "Moving forward");
        telemetry.addData( "Global Time: ", globalTime.seconds());
        telemetry.addData("Stage Time: ", stageTime.seconds());
        telemetry.update();
    }
    mWheel.mecanumDrive(0,0,0);


    //Turn left
    }
}
