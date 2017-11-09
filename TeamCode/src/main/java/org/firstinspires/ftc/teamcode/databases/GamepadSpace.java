package org.firstinspires.ftc.teamcode.databases;

/**
 * Created by LBYPatrick on 10/20/2017.
 */

final public class GamepadSpace {
    public float LT = 0,
                 RT = 0,
                 JLeftX = 0,
                 JLeftY = 0,
                 JRightX= 0,
                 JRightY= 0;

    public boolean DPadUp = false,
                   DPadDown = false,
                   DPadLeft = false,
                   DPadRight = false,
                   Triangle = false,
                   Cross = false,
                   Square = false,
                   Circle = false,
                   LB = false,
                   RB = false;


    final public StatusContainer stat = new StatusContainer();

    final public class StatusContainer {

        public boolean DPadUp       = false,
                       DPadDown     = false,
                       DPadLeft     = false,
                       DPadRight    = false,
                       Triangle     = false,
                       Cross        = false,
                       Square       = false,
                       Circle       = false,
                       LB           = false,
                       RB           = false,
                       LT           = false,
                       RT           = false,
                       JLeftX       = false,
                       JLeftY       = false,
                       JRightX      = false,
                       JRightY      = false;
    }

}
