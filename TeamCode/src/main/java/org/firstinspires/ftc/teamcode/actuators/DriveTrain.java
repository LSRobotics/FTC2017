package org.firstinspires.ftc.teamcode.actuators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by LBYPatrick on 2017/11/3.
 */

final public class DriveTrain {


    public enum Wheels{
        FRONT_LEFT,
        FRONT_RIGHT,
        REAR_LEFT,
        REAR_RIGHT,
    }

    private              MotorControl FL                 = null;
    private              MotorControl FR                 = null;
    private              MotorControl RL                 = null;
    private              MotorControl RR                 = null;
    private              double  maxSpeed           = 1.0;
    private              double  speedLevel         = 1.0;
    private              double  frontLeftPower     = 0;
    private              double  frontRightPower    = 0;
    private              double  rearLeftPower      = 0;
    private              double  rearRightPower     = 0;
    private              boolean is4WD              = false;
    private              boolean isMecanum          = false;

    //Constructor for 4WD
    public DriveTrain(MotorControl frontLeftMotor, MotorControl frontRightMotor, MotorControl rearLeftMotor, MotorControl rearRightMotor) {

        FL = frontLeftMotor;
        FR = frontRightMotor;
        RL = rearLeftMotor;
        RR = rearRightMotor;

        FL.setReverse(true);
        FR.setReverse(true);

        is4WD = true;
    }

    public DriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor rearLeftMotor, DcMotor rearRightMotor) {
        this(new MotorControl(frontLeftMotor), new MotorControl(frontRightMotor), new MotorControl(rearLeftMotor),new MotorControl(rearRightMotor));
    }

    public DriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor) {
        this(new MotorControl(frontLeftMotor), new MotorControl(frontRightMotor));
    }


    //Constructor for 2WD
    public DriveTrain(MotorControl leftMotor, MotorControl rightMotor) {

        RL = leftMotor;
        RR = rightMotor;

        is4WD = false;
    }

    public void setWheelMode(boolean isMecanum) {
        this.isMecanum = isMecanum;
    }

    public void drive(double sideMove, double forwardBack, double rotation) {
        if(isMecanum) mecanumDrive(sideMove, forwardBack,rotation);
        else tankDrive(forwardBack,rotation);
    }

    public void tankDrive(double forwardBack, double rotation) {

        rotation = -rotation; // FTC 2018 tuning

        //Calculate Adequate Power Level for motors
        rearLeftPower = Range.clip(forwardBack + rotation, -1.0, 1.0);
        rearRightPower = Range.clip(forwardBack - rotation, -1.0, 1.0);

        if (is4WD) {
            frontLeftPower = rearLeftPower;
            frontRightPower = rearRightPower;
        }
        //Pass calculated power level to motors
        RL.move(rearLeftPower);
        RR.move(-rearRightPower);
        if(is4WD) {
            FL.move(frontLeftPower);
            FR.move(-frontRightPower);
        }

    }

    public void mecanumDrive(double sideMove, double forwardBack, double rotation) {

        //A little Math from https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
        final double r = Math.hypot(sideMove, forwardBack);
        final double robotAngle = Math.atan2(forwardBack, sideMove) - Math.PI / 4;

        frontLeftPower = r * Math.cos(robotAngle) + rotation;
        frontRightPower = r * Math.sin(robotAngle) - rotation;
        rearLeftPower = r * Math.sin(robotAngle) + rotation;
        rearRightPower = r * Math.cos(robotAngle) - rotation;

        // Send calculated power to motors
        FL.move(frontLeftPower);
        FR.move(frontRightPower);
        RL.move(rearLeftPower);
        RR.move(rearRightPower);
    }

    public void updateSpeedLimit(double speed) {

        if(is4WD) {
            FL.updateSpeedLimit(speed);
            FR.updateSpeedLimit(speed);
        }
        RL.updateSpeedLimit(speed);
        RR.updateSpeedLimit(speed);
    }

    public double getEncoderInfo(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return FR.getCurrentPosition();
            case FRONT_RIGHT : return FL.getCurrentPosition();
            case REAR_LEFT   : return RL.getCurrentPosition();
            case REAR_RIGHT  : return RR.getCurrentPosition();
            default          : return 666; // Actually won't happen because the enum has already limited the actual parameter
        }
    }

    public double getSpeed(Wheels position) {
        switch (position) {
            case FRONT_LEFT  : return FR.getPower();
            case FRONT_RIGHT : return FL.getPower();
            case REAR_LEFT   : return RL.getPower();
            case REAR_RIGHT  : return RR.getPower();
            default          : return 666; // Won't happen because of the enum in parameter
        }
    }

    public boolean get4WDStat() {return is4WD;}
}