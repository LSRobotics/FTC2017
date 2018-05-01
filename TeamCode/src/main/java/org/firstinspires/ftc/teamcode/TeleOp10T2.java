package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.actuators.DriveTrain;


@TeleOp(name="SOPH_tankDrive_monoStick", group="Sophomore")
final public class TeleOp10T2 extends LinearOpMode {

    //Initialize objects
    private static TeleOp10 teleOp;

    private void initialize() {
        teleOp = new TeleOp10(hardwareMap,gamepad1,this,telemetry,false);
        teleOp.setDriveMode(TeleOp10.DriveMode.OneStick);
        teleOp.setWheelMode(DriveTrain.WheelMode.TANK_WHEEL);
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialize();
        waitForStart(); // Wait for the game to start (driver presses PLAY)

        telemetry.addData("Status","Running");
        telemetry.update();

        teleOp.run();

        telemetry.addData("Status","Stopped");
        telemetry.update();
    }
}
