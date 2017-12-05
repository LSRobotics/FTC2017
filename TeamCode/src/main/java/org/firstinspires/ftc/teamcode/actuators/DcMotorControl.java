package org.firstinspires.ftc.teamcode.actuators;


import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by LBYPatrick on 11/9/2017.
 */

final public class DcMotorControl {

            public      double  sensitivity = 1.0;
            public      double  maxSpeed = 1.0;
            private     double  speedLevel = 1.0;
            private     double  motorSpeed = 0;

    public DcMotorControl(DcMotor dcMotorObj, boolean forward) {
        dcMotorObj.setDirection((forward?DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE));
    }

    public void moveLift(DcMotor dcMotorObj, boolean up, boolean down) {

        motorSpeed = 0;
        if ((up && down) || (!up && !down)) motorSpeed = 0;
        else if (up)
            motorSpeed = sensitivity;
        else if (down)
            motorSpeed = -sensitivity;

        dcMotorObj.setPower(motorSpeed*speedLevel);
    }

    public void updateSpeedLimit(double speed){
        speedLevel = speed * maxSpeed;
    }
}
