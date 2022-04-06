// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



//commands 

//subsystems
import frc.robot.subsystems.DriveSubSystem;
import frc.robot.subsystems.Lemonlight;
import frc.robot.subsystems.ShooterSubSystem;
// important shit to get working
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import org.photonvision.PhotonCamera;
import edu.wpi.first.math.controller.PIDController;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  //robots subsystems and commands get defined here
  private final DriveSubSystem m_Drive = new DriveSubSystem();
  private final ShooterSubSystem m_ShooterSubSystem = new ShooterSubSystem();
  private final Lemonlight m_Lemonlight = new Lemonlight("photonvison");

  // init controllers here
  private final Joystick m_leftJoystick = new Joystick(1);
  private final Joystick m_rightJoystick = new Joystick(2);
  private final XboxController m_XboxController = new XboxController(3); 
    // PID constants should be tuned per robot
    final double LINEAR_P = 0.1;
    final double LINEAR_D = 0.0;
    PIDController forwardController = new PIDController(LINEAR_P, 0, LINEAR_D);

    final double ANGULAR_P = 0.1;
    final double ANGULAR_D = 0.0;
    PIDController turnController = new PIDController(ANGULAR_P, 0, ANGULAR_D);

  private final Command m_autonomousCommand =
      new RunCommand(() -> m_Drive.drive(0.5, 0.5)).withTimeout(1);
 

    public RobotContainer(){
    
    
    
    
      m_Drive.setDefaultCommand(
        new RunCommand(
          () -> m_Drive.drive(m_leftJoystick.getY(), m_rightJoystick.getY()),
          m_Drive
        ));
        
      m_ShooterSubSystem.setDefaultCommand(
        new RunCommand(m_ShooterSubSystem::stop, m_ShooterSubSystem)
        );
      


      //shows what command is being run on shuffle baord
      SmartDashboard.putData(m_Drive);

         // Configure the button bindings
    configureButtonBindings();
    }


private void configureButtonBindings(){

new JoystickButton(m_XboxController, Button.kA.value)
.whenHeld(new RunCommand(m_ShooterSubSystem::shoot, m_ShooterSubSystem));


new JoystickButton(m_XboxController, Button.kB.value)
.whenHeld(new RunCommand(m_ShooterSubSystem::intake, m_ShooterSubSystem));

new JoystickButton(m_XboxController, Button.kX.value)
.whenHeld(new RunCommand(m_ShooterSubSystem::IntakeReversed, m_ShooterSubSystem));

new JoystickButton(m_XboxController, Button.kStart.value)
.whenHeld(new RunCommand(m_ShooterSubSystem::Unjam, m_ShooterSubSystem));


double forwards = m_leftJoystick.getY();

double rotationSpeed = m_Lemonlight.getHorizontalOffset();
new JoystickButton(m_XboxController, Button.kY.value)
.whenHeld(new RunCommand(() ->m_Drive.arcadeDrive(forwards, rotationSpeed), m_Drive));








}
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_autonomousCommand;
 }


}