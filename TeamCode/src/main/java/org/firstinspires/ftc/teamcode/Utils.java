package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.teamcode.actuators.*;

/**
 * Created by LBYPatrick on 11/10/2017.
 */

class Utils {
    final public void overclock(DcMotorControl object, double speed) {object.maxSpeed = speed;}
    final public void overclock(ServoControl object, double speed) { object.maxSpeed = speed;}
    final public void overclock(DriveTrain object, double speed) {object.maxSpeed = speed;}
}
