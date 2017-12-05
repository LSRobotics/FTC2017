package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by LBYPatrick on 12/4/2017.
 */

final public class AutonHelper {

    private ElapsedTime stageTime = new ElapsedTime();
    private LinearOpMode opModeObj;
    private DriveTrain driveObj;
    private boolean isMecanum = false;


    public AutonHelper(LinearOpMode opMode, DriveTrain driveTrainObject, boolean isMecanum) {
        this.opModeObj = opMode;
        this.driveObj = driveTrainObject;
        this.isMecanum = isMecanum;
    }

    private boolean detectOpStat() {
        if(opModeObj.opModeIsActive()) return true;
        else return false;
    }

    public boolean moveForward(double time) {

        stageTime.reset();
        if(!isMecanum) driveObj.tankDrive(1,0);
        else driveObj.mecanumDrive(0,1,0);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        driveObj.tankDrive(0,0); //Stop the robot
        return detectOpStat();

    }

    public boolean moveBack(double time) {

        stageTime.reset();
        if(!isMecanum) driveObj.tankDrive(-1,0);
        else driveObj.mecanumDrive(0,-1,0);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean turnLeft(double time) {

        stageTime.reset();
        if(!isMecanum)driveObj.tankDrive(0,-1);
        else driveObj.mecanumDrive(0,0,-1);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean turnRight(double time) {

        stageTime.reset();
        if(!isMecanum) driveObj.tankDrive(0,1);
        else driveObj.mecanumDrive(0,0,1);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean moveLiftUp(DcMotor motorObject, DcMotorControl controlObject, double time) {

        stageTime.reset();
        controlObject.moveLift(motorObject,true, false);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        controlObject.moveLift(motorObject,false, false);
        return detectOpStat();

    }

    public boolean moveLiftDown(DcMotor motorObject, DcMotorControl controlObject, double time) {

        stageTime.reset();
        controlObject.moveLift(motorObject,false, true);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        controlObject.moveLift(motorObject,false, false);
        return detectOpStat();

    }

}
