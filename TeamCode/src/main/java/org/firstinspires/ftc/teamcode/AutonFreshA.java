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
@Autonomous(name = "FRESH_POS_A",group = "Freshman")
final public class AutonFreshA extends LinearOpMode {

    //Initialize objects

    private     DriveTrain      mWheel;

    // Declare OpMode members.
    final private ElapsedTime globalTime = new ElapsedTime();
    final private ElapsedTime stageTime = new ElapsedTime();

    private void initialize() {


        DcMotor BL = hardwareMap.get(DcMotor.class, Statics.Freshman.LWheel);
        DcMotor BR = hardwareMap.get(DcMotor.class, Statics.Freshman.RWheel);

        mWheel = new DriveTrain(BL,BR);

    }


    private void wait(double seconds) {
        stageTime.reset();
        while(stageTime.seconds() <= seconds) {
            telemetry.addData("Current Stage: ", "Idle");
            telemetry.addData( "Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() {

        initialize();

        //Move Forward
        stageTime.reset();
        mWheel.tankDrive(-1,0);
        while(stageTime.seconds() <= 1.0) {
            telemetry.addData("Current Stage: ", "Moving forward");
            telemetry.addData( "Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        mWheel.tankDrive(0,0);

        wait(3.0);

        //Turn left
        stageTime.reset();
        mWheel.tankDrive(0,-1);
        while(stageTime.seconds() <= 0.75){
            telemetry.addData("Current Stage", "Turning Left");
            telemetry.addData("Global Time",globalTime.seconds());
            telemetry.addData("Stage Time",stageTime.seconds());
        }
        mWheel.tankDrive(0,0);

        wait(3.0);

        stageTime.reset();
        mWheel.tankDrive(-1,0);
        while(stageTime.seconds() <= 0.3) {
            telemetry.addData("Current Stage: ", "Moving forward");
            telemetry.addData( "Global Time: ", globalTime.seconds());
            telemetry.addData("Stage Time: ", stageTime.seconds());
            telemetry.update();
        }
        mWheel.tankDrive(0,0);

    }
}
