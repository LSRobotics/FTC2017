package org.firstinspires.ftc.teamcode.actuators;


import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;
/**
 * Created by LBYPatrick on 12/6/2017.
 */

final public class RGBSensorControl {

    private final ColorSensor device;

    public RGBSensorControl (ColorSensor colorSensorObject) {this.device = colorSensorObject;}
    public int getBallColor() {

        if (device.blue() > device.red()) return 0;
        else if (device.red() > device.blue()) return 1;
        else return 2;
    }
}
