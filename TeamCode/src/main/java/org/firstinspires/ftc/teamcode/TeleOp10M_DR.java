
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.actuators.Controller;
import org.firstinspires.ftc.teamcode.databases.Statics;


@TeleOp(name="SOPH_mecanumDrive_dualDriver", group="Sophomore")
final public class TeleOp10M_DR extends LinearOpMode {


    //Initialize objects
    static TeleOp10 teleOp;

    private void initialize() {
        teleOp = new TeleOp10(hardwareMap,gamepad1);
        teleOp.setMecanum(true);
        teleOp.setDriveMode(TeleOp10.DriveMode.NFSControl);
        teleOp.useSecondGamepad(new Controller(gamepad2));
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialize();
        waitForStart(); // Wait for the game to start (driver presses PLAY)

        telemetry.addData("Status","Running");
        telemetry.update();

        //Block when OpMode is not active
        while (!opModeIsActive());

        teleOp.start();

        while(opModeIsActive()) {
            if (Statics.SOPH_VISUALIZING) {
                teleOp.showData(telemetry);
                telemetry.update();
            }
        }

        teleOp.stopWorking();

        telemetry.addData("Status","Stopped");
        telemetry.update();
    }
}
