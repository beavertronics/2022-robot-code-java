package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;




import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ShooterSubSystem extends SubsystemBase {

    private final WPI_TalonFX m_ShooterMotor = new WPI_TalonFX(46);
    private final WPI_VictorSPX m_IntakerMotor = new WPI_VictorSPX(47);
    private final WPI_VictorSPX m_IndexerMotor = new WPI_VictorSPX(45);


    public ShooterSubSystem(){
        addChild("ShooterMotor", m_ShooterMotor);
        addChild("IntakerMotor", m_IntakerMotor);
        addChild("IndexerMotor", m_IndexerMotor);
    }
    


    public void log(){
        SmartDashboard.putNumber("ShooterMotor", m_ShooterMotor.get());
        SmartDashboard.putNumber("IntakerMotor", m_IntakerMotor.get());
        SmartDashboard.putNumber("IndexerMotor", m_IndexerMotor.get());
    }

    public void shoot(){
        m_ShooterMotor.set(1);
        m_IndexerMotor.set(1);
    }

    public void intake(){
        m_IntakerMotor.set(-0.5);
        m_IndexerMotor.set(0.5);
        m_ShooterMotor.set(0.4);
    }

    public void Unjam(){
        m_IndexerMotor.set(-0.3);
        m_ShooterMotor.set(0.3);
    }

    public void IntakeReversed(){
        m_IntakerMotor.set(-0.5);
        m_ShooterMotor.set(0.5);
    }

    public void stop(){
        m_ShooterMotor.set(0);
        m_IntakerMotor.set(0);
        m_IndexerMotor.set(0);
    }

    @Override
    public void periodic() {
      log();
    }
}
