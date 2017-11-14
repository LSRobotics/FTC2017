package org.firstinspires.ftc.teamcode.actuators;


import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by LBYPatrick on 11/9/2017.
 */

public class DcMotorControl {

            public      double  sensitivity = 0.5;
            public      double  maxSpeed = 1.0;
            private     double  speedLevel = 1.0;
            private     double  motorSpeed = 0;

    public DcMotorControl(DcMotor dcMotorObj, boolean forward) {
        dcMotorObj.setDirection((forward?DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE));
    }

    public void moveLift(DcMotor dcMotorObj, boolean up, boolean down) {

        if ((up && down) || (!up && !down)) motorSpeed = 0;
        if (up) motorSpeed = sensitivity;
        if (down) motorSpeed = -sensitivity;

        dcMotorObj.setPower(motorSpeed*speedLevel);
    }

    public void updateSpeedLimit(DcMotor dcMotorObj, double speed){
        speedLevel = speed * maxSpeed;
        dcMotorObj.setPower(motorSpeed*speedLevel);
    }
}
