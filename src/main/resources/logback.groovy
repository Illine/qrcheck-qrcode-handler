import org.springframework.boot.logging.logback.ColorConverter
import java.nio.charset.Charset

statusListener(NopStatusListener)

conversionRule("clr", ColorConverter)
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d{ISO8601} %clr([${System.getProperty("PID") ?: ''}] [%X{traceId:-No traceId}] [%X{spanId:-No spanId}]){magenta} %clr([%level{5}]) %clr(---){faint} %clr([%logger{36}]){cyan} %clr(:){faint} %m%n%ex"
        charset = Charset.forName("UTF-8")
    }
}

root(ch.qos.logback.classic.Level.INFO, ["CONSOLE"])
logger("org.springframework.cloud.sleuth.instrument.jdbc.TraceListenerStrategy", WARN, ["CONSOLE"])