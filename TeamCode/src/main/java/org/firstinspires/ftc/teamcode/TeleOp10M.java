package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="SOPH_mecanumDrive_NFS", group="Sophomore")
final class TeleOp10M extends LinearOpMode {

    private static TeleOp10 teleOp;

    private void initialize(){
        teleOp = new TeleOp10(hardwareMap,gamepad1,this,telemetry,true);
        teleOp.setDriveMode(TeleOp10.DriveMode.NFSControl);
        teleOp.setVisualizing(true);
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
