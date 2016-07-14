package de.codecentric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBinding(Sink.class)
public class EdmpSampleStreamSinkApplication {

	private static Logger logger = LoggerFactory.getLogger(EdmpSampleStreamSinkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EdmpSampleStreamSinkApplication.class, args);
	}

    @Autowired
    TimeService timeService;

    @Bean
    public TimeService timeService() {
        return new TimeService();
    }

	@StreamListener(Sink.INPUT)
	public void loggerSink(Integer counter) {

        if (counter == 0) {
            timeService.beginMeasuring();
        }
        if (counter == 999999) {
            long duration = timeService.endMeasuring();
            logger.info("Received 1Mio entries in " + duration / 1000 + " seconds.");
            logger.info("Received " + 1000000.0 / (duration / 1000) + " entries per second.");
        }

        if (counter != null && counter % 10000 == 0) {
            logger.info("Element received: " + counter);
        }
	}
	
	public static class SinkTimeInfo{

		private Integer counter;
		private String time;
		private String label;

		public Integer getCounter() {
			return counter;
		}
		public void setCounter(Integer counter) {
			this.counter = counter;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setSinkLabel(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		@Override
		public String toString() {
			return "SinkTimeInfo [counter=" + counter + ", time=" + time + ", label=" + label + "]";
		}
		
	}


}

