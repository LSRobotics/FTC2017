package org.firstinspires.ftc.teamcode.databases;

/**
 * Created by LBYPatrick on 2017/10/18.
 */

final public class Statics {

    final public static class Freshman{

        public static boolean visualizing = false;
        public static String LWheel = "left_tank_motor";
        public static String RWheel = "right_tank_motor";
        public static String Intake = "intake_motor";
        final public static class Auton {
            final public static double stageOneTime = 0.32;
        }


    }

    final public static class Sophomore {

        public static boolean visualizing = true;
        public static String glyphLift = "glyph_lift_motor";

        final public static class MecanumWheel {

            public static String frontLeft = "front_left_drive",
                                frontRight = "front_right_drive",
                                  rearLeft = "rear_left_drive",
                                 rearRight = "rear_right_drive";
        }

        final public static class Servos {
            public static String jewel = "jewel_arm",
                     left_glyphGrabber = "glyph_left_grabber",
                    right_glyphGrabber = "glyph_right_grabber";
        }

        final public static class Vuforia {
            public static String licenceKey = "AaqInFv/////AAAAGWgXZPyJL0f1o4/jFY9175+ChDm/B5AHc9pxmiOo8JTr1gMh3DluNlhLlVr+LzyCyUOjQdefC/Wms7sJj2mgzM0ydQ8VVgYuCmSqdx997Hdf0Xie43eqklVg+9GKxIxPtuKoOWWsooHDxs4m2G60qySXftQoTkuE94lF9r6AnEys0FFMcNC6eyO7Qo4DvOJJ6Qm6SBKAkFqhyO1kyKepCdclLrxi9NvaG3Tnpl/zN3H+fEg4fPZyWiREccROjyUzhcnQJ9uH/5cNqMns7mW2codvxT2xq9hFdDo0TxRZU3yppM35VUchlN2QITm/6hTMRQ61UVRGcQHQUm/8LKtLBAQn1iNYXFpgPuXD8rbTYJWw";
        }

        final public static class Auton {
            final public static double stageOneTime = 0.2688,
                                       stageTwoTime = 1.0,
                                       stageThreeTime = 0.3;
        }
    }
}
