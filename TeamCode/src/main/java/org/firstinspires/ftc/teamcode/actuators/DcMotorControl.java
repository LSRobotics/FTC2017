package org.firstinspires.ftc.teamcode.actuators;


import com.qualcomm.robotcore.hardware.DcMotor;
/**
 * Created by LBYPatrick on 11/9/2017.
 */

final public class DcMotorControl {

            private     double  sensitivity = 1.0;
            private     double  maxSpeed = 1.0;
            private     double  speedLevel = 1.0;
            private     double  motorSpeed = 0;
            private final DcMotor motorObj;

    public DcMotorControl(DcMotor dcMotorObj, boolean forward) {

        this.motorObj = dcMotorObj;

        this.motorObj.setDirection((forward?DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE));
    }

    public void setClockSpeed(double speed) { this.maxSpeed = speed;}
    public void setSensitivity(double sensitivity) {this.sensitivity = sensitivity;}

    public void moveLift(boolean up, boolean down) {

        if ((up && down) || (!up && !down)) this.motorSpeed = 0;
        else if (up)
            this.motorSpeed = this.sensitivity;
        else if (down)
            this.motorSpeed = -this.sensitivity;

        this.motorObj.setPower(this.motorSpeed*this.speedLevel);
    }

    public void updateSpeedLimit(double speed){
        this.speedLevel = speed * this.maxSpeed;
    }
}
