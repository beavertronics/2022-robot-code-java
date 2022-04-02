// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogGyro;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubSystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private final MotorController M_LeftMotor =
  new MotorControllerGroup(new WPI_VictorSPX(41), new WPI_VictorSPX(42));

  private final MotorController M_RightMotor =
  new MotorControllerGroup(new WPI_VictorSPX(43), new WPI_VictorSPX(44));

  private final DifferentialDrive M_Drive = new DifferentialDrive(M_LeftMotor,M_RightMotor);


  private final AnalogGyro m_gyro = new AnalogGyro(1);



  public DriveSubSystem() {
super();

M_RightMotor.setInverted(true);


addChild("Gyro", m_gyro);

  }


  public void log(){
    SmartDashboard.putNumber("Gyro", m_gyro.getAngle());

  }



public void drive(double left, double right){
  M_Drive.tankDrive(left, right);
}

public double getHeading() {
  return m_gyro.getAngle();
}

 public void reset() {
m_gyro.reset();

 }

  @Override
  public void periodic() {
    log();
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
