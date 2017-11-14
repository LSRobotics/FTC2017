package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.teamcode.actuators.*;
import org.firstinspires.ftc.teamcode.databases.*;
/**
 * Created by LBYPatrick on 11/10/2017.
 */

class Utils {
    final public void overclock(DcMotorControl object, double speed) {object.maxSpeed = speed;}
    final public void overclock(ServoControl object, double speed) { object.maxSpeed = speed;}
    final public void overclock(DriveTrain object, double speed) {object.maxSpeed = speed;}
}
