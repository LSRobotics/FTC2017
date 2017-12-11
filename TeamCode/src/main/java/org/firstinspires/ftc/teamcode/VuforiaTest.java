package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.actuators.RGBSensorControl;
import org.firstinspires.ftc.teamcode.actuators.VuforiaControl;
import org.firstinspires.ftc.teamcode.databases.Statics;

/**
 * Created by LBYPatrick on 12/7/2017.
 */
@TeleOp (name="Vuforia Test",group="test")


public class VuforiaTest extends LinearOpMode {

    private VuforiaControl vuforia;

    private void initialize() {
        vuforia = new VuforiaControl(this.hardwareMap,true,false,Statics.SOPH_VUFORIA_LICENSE_KEY);
    }

    @Override
    public void runOpMode(){

        initialize();
        waitForStart();

        while(opModeIsActive()){

            telemetry.addData("Position",vuforia.getPosition());
            telemetry.update();
        }

    }
}
