package CarRentalSystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.CompositeHealth;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Component
public class HealthLogger {
    private static final Logger log = LoggerFactory.getLogger("HealthLogger");

    private final HealthEndpoint healthEndpoint;
    private static java.util.logging.Logger fileLogger;

    static {
        try {
            String logDir = System.getProperty("user.dir") + File.separator + "healthlogs";
            File directory = new File(logDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs(); // ensure folder creation
                if (!created) {
                    System.err.println("Failed to create log directory: " + logDir);
                }
            }

            String logFilePath = logDir + File.separator + "health-status.log";

            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            FileHandler fileHandler = new FileHandler(logFilePath, true); // append mode
            fileHandler.setFormatter(new SimpleFormatter());

            fileLogger = java.util.logging.Logger.getLogger("HealthFileLogger");
            fileLogger.addHandler(fileHandler);
            fileLogger.setUseParentHandlers(false);

            System.out.println("✅ Health log file will be written at: " + logFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HealthLogger(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void logHealthStatus() {
        HealthComponent healthComponent = healthEndpoint.health();
        StringBuilder message = new StringBuilder();

        if (healthComponent instanceof Health health) {
            message.append("Health Status: ").append(health);
        } else if (healthComponent instanceof CompositeHealth compositeHealth) {
            message.append("Composite Health Status: ").append(compositeHealth.getStatus()).append("\n");
            compositeHealth.getComponents().forEach((name, comp) ->
                    message.append("Component: ").append(name).append(", Status: ").append(comp.getStatus()).append("\n"));
        } else {
            message.append("Unknown HealthComponent: ").append(healthComponent);
        }
        log.info(message.toString());
        fileLogger.info(message.toString());
    }
}

