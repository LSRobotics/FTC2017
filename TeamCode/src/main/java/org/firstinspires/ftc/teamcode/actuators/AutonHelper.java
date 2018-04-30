package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by LBYPatrick on 12/4/2017.
 */

@SuppressWarnings("StatementWithEmptyBody")
final public class AutonHelper {

    private final ElapsedTime stageTime = new ElapsedTime();
    private final LinearOpMode opModeObj;
    private final DriveTrain driveObj;
    private boolean isMecanum;

    /**
     * Constructor
     * @param opMode the LinearOpMode object from your class extended from LinearOpMode
     * @param driveTrainObject The DriveTrain object you initialized to drive the robot
     */
    public AutonHelper(LinearOpMode opMode, DriveTrain driveTrainObject) {
        opModeObj = opMode;
        driveObj = driveTrainObject;
    }

    /**
     * Drives the robot.
     * @param forwardBack the speed for going forward and back.
     * @param leftRight the speed for robot rotation (if it is a positive value, then it's going to be clockwise, otherwise it's going to be reverse-clockwise)
     * @param time the time for the move (Will stop immediately afterwards)
     * @return
     */
    public boolean drive(double forwardBack, double leftRight, double time) {
        stageTime.reset();

        driveObj.drive(0,forwardBack,leftRight);

        while(opModeObj.opModeIsActive() && stageTime.milliseconds() <= time);
        driveObj.drive(0,0,0);

        return opModeObj.opModeIsActive();

    }

    /**
     * Moves a motor.
     * @param motor the MotorControl object you want to control
     * @param speed the speed for the motor
     * @param time the time for moving the motor
     * @return
     */
    public boolean runMotor(MotorControl motor,double speed,double time) {

        stageTime.reset();

        motor.move(speed);
        while(opModeObj.opModeIsActive() && stageTime.seconds() <= time);
        motor.move(0);

        return opModeObj.opModeIsActive();

    }

}
