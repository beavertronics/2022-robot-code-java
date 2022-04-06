package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;

import java.util.ArrayList;
import java.util.Arrays;



/**
 * Device driver for the limelight.
 */
public class Lemonlight implements Sendable {
    private final NetworkTableEntry tv, tx, ty, ta, ledMode, camMode, pipeline;
    

    // VALUES SHOULD BE IN CM and DEGREES
    // TODO - Set these
    public static final double
            MAIN_MOUNT_HEIGHT = 79.5,
            MAIN_MOUNT_ANGLE = 36.8,
            MAIN_TARGET_HEIGHT = 257,
            BALL_MOUNT_HEIGHT = 22.0,
            BALL_MOUNT_ANGLE = -20.0,
            BALL_MOUNT_ANGLE_X = 0.0,
            BALL_TARGET_HEIGHT = 12.065;

    /**
     * Creates a new limelight object.
     *
     * @param tableName The table of the limelight. Default is "limelight"
     * @param forBall If the limelight is for a ball
     * @param photonVision If the vision is running photonVision.
     */
    public Lemonlight(String tableName) {
        NetworkTable PhotonVision;
        PhotonVision = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable(tableName);
        


            tv = PhotonVision.getEntry("hasTarget");
            tx = PhotonVision.getEntry("targetYaw");
            ty = PhotonVision.getEntry("targetPitch");
            ta = PhotonVision.getEntry("targetArea");
        

       
        ledMode = PhotonVision.getEntry("ledMode");
        camMode = PhotonVision.getEntry("camMode");

        pipeline = PhotonVision.getEntry("pipeline");

    }

    /**
     * Enum to describe the state of the LED.
     */
    public enum LEDModes {
        PIPELINE(0),
        FORCE_OFF(1),
        FORCE_BLINK(2),
        FORCE_ON(3);

        public final int value;

        LEDModes(int value) {
            this.value = value;
        }
    }

    /**
     * Enum to describe the state of the limelight camera.
     */
    public enum CamModes {
        VISION_PROCESSOR(0),
        DRIVER_CAMERA(1);

        public final int value;

        CamModes(int value) {
            this.value = value;
        }
    }

    /**
     * Sets the LED mode.
     *
     * @param mode the LED mode to switch to
     */
    public void setLEDMode(LEDModes mode) {
        ledMode.setDouble(mode.value);
    }

    /**
     * Sets the camera mode.
     *
     * @param mode the new mode
     */
    public void setCamMode(CamModes mode) {
        camMode.setDouble(mode.value);
    }

    /**
     * Sets the pipeline.
     *
     * @param pipe sets the pipeline to an int between 0 and 9
     */
    public void setPipeline(int pipe) {
        pipeline.setDouble(pipe);
    }

    /**
     * Checks to see if the limelight has a target.
     *
     * @return if limelight has a target
     */
    public boolean hasTarget() {
        return tv.getDouble(0) == 1;
    }

    /**
     * Gets the horizontal offset from the limelight.
     *
     * @return the horizontal offset
     */
    public double getHorizontalOffset() {
        return tx.getDouble(0);
    }

    /**
     * Gets the vertical offset from the limelight.
     *
     * @return the vertical offset
     */
    public double getVerticalOffset() {
        return ty.getDouble(0);
    }

    /**
     * Gets the percentage of coverage area from the limelight.
     *
     * @return the percentage of area
     */
    public double getAreaPercentage() {
        return ta.getDouble(0);
    }

    /**
     * gets a distance estimate IN INCHES of the target using the limelight and trig.
     * You need to check if the limelight has a target before running this
     *
     * @param mountHeight Mounting height of the limelight
     * @param mountAngle Mounting angle of the limelight
     * @param targetHeight Target Height.
     * @param targetYOffset The yOffset
     * @return the distance estimate or -1 if no target found
     */
    public static double getLimelightDistanceEstimateIN(
            double mountHeight, double mountAngle, double targetHeight, double targetYOffset
    ) {
        return ((targetHeight - mountHeight)
            / Math.tan((targetYOffset + mountAngle) * (Math.PI / 180))) 
            * 0.393701;
    }

    public static double getXOOffsetDistanceEstimateIN(
            double targetAngle, double mountAngle, double mountOffset, double targetDistance
    ) {
        return targetDistance * Math.tan((targetAngle + mountAngle) * (Math.PI / 180)) * 0.393701;
    }



   

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Lemonlight");
        builder.addDoubleProperty("verticalOffset", this::getVerticalOffset, null);
        builder.addDoubleProperty("horizontalOffset", this::getHorizontalOffset, null);
        builder.addDoubleProperty("distance Estimate", () -> {
            return Lemonlight.getLimelightDistanceEstimateIN(MAIN_MOUNT_HEIGHT, MAIN_MOUNT_ANGLE, MAIN_TARGET_HEIGHT, this.getVerticalOffset());
        }, null);
        builder.addBooleanProperty("hasTarget", this::hasTarget, null);
      
    }
}