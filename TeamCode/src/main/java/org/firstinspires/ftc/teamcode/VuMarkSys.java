package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Gordon's on 2017/9/16.
 */

public class VuMarkSys {
    //Initialization
    static OpenGLMatrix lastLocation = null;
    static VuforiaLocalizer vuforia;

    static VuforiaTrackables relicTrackables = null;
    static VuforiaTrackable relicTemplate = null;

    static int cameraMinitorViewId = 0;

    VuforiaLocalizer.Parameters parameters = null;

    VuMarkSys(HardwareMap hardwareMap) {
    /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);



        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code onthe next line, between the double quotes.
         */

        parameters.vuforiaLicenseKey = "AaqInFv/////AAAAGWgXZPyJL0f1o4/jFY9175+ChDm/B5AHc9pxmiOo8JTr1gMh3DluNlhLlVr+LzyCyUOjQdefC/Wms7sJj2mgzM0ydQ8VVgYuCmSqdx997Hdf0Xie43eqklVg+9GKxIxPtuKoOWWsooHDxs4m2G60qySXftQoTkuE94lF9r6AnEys0FFMcNC6eyO7Qo4DvOJJ6Qm6SBKAkFqhyO1kyKepCdclLrxi9NvaG3Tnpl/zN3H+fEg4fPZyWiREccROjyUzhcnQJ9uH/5cNqMns7mW2codvxT2xq9hFdDo0TxRZU3yppM35VUchlN2QITm/6hTMRQ61UVRGcQHQUm/8LKtLBAQn1iNYXFpgPuXD8rbTYJWw";

                /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();
    }

    double[] getPos(){
        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

            int markVisible = vuMark==RelicRecoveryVuMark.LEFT ? 0 : (vuMark == RelicRecoveryVuMark.CENTER ? 1 : 2);

            double[] pos = new double[6];

            /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
             * it is perhaps unlikely that you will actually need to act on this pose information, but
             * we illustrate it nevertheless, for completeness. */
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();

            /* We further illustrate how to decompose the pose into useful rotational and
             * translational components */
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                // Extract the X, Y, and Z components of the offset of the target relative to the robot
                pos[0] = trans.get(0);
                pos[1] = trans.get(1);
                pos[2] = trans.get(2);

                // Extract the rotational components of the target relative to the robot
                pos[3] = rot.firstAngle;
                pos[4] = rot.secondAngle;
                pos[5] = rot.thirdAngle;
            }
            return pos;
        }
        else {
            return new double[] {1};
        }
    }
}

