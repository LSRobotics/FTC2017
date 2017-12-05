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
        if(this.opModeObj.opModeIsActive()) return true;
        else return false;
    }

    public boolean moveForward(double time) {

        this.stageTime.reset();
        if(!this.isMecanum) this.driveObj.tankDrive(1,0);
        else this.driveObj.mecanumDrive(0,1,0);
        while(this.opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        this.driveObj.tankDrive(0,0); //Stop the robot
        return detectOpStat();

    }

    public boolean moveBack(double time) {

        this.stageTime.reset();
        if(!this.isMecanum) this.driveObj.tankDrive(-1,0);
        else this.driveObj.mecanumDrive(0,-1,0);
        while(this.opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        this.driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean turnLeft(double time) {

        this.stageTime.reset();
        if(!this.isMecanum)this.driveObj.tankDrive(0,-1);
        else this.driveObj.mecanumDrive(0,0,-1);
        while(this.opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        this.driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean turnRight(double time) {

        this.stageTime.reset();
        if(!this.isMecanum) this.driveObj.tankDrive(0,1);
        else this.driveObj.mecanumDrive(0,0,1);
        while(this.opModeObj.opModeIsActive() && stageTime.seconds() <= time) {continue;}
        this.driveObj.tankDrive(0,0);
        return detectOpStat();

    }

    public boolean moveLiftUp(DcMotorControl controlObject, double time) {

        this.stageTime.reset();
        controlObject.moveLift(true, false);
        while(this.opModeObj.opModeIsActive() && this.stageTime.seconds() <= time) {continue;}
        controlObject.moveLift(false, false);
        return detectOpStat();

    }

    public boolean moveLiftDown(DcMotorControl controlObject, double time) {

        this.stageTime.reset();
        controlObject.moveLift(false, true);
        while(this.opModeObj.opModeIsActive() && this.stageTime.seconds() <= time) {continue;}
        controlObject.moveLift(false, false);
        return detectOpStat();

    }

}
