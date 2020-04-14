package ru.diploma;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;
import ru.diploma.service.MainProcessingService;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        MainProcessingService mainProcessingService = context.getBean(MainProcessingService.class);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        mainProcessingService.runProcessing();

        stopWatch.stop();
        System.out.println(String.format("Общее время вычислений: %s ms", stopWatch.getLastTaskTimeMillis()));
    }
}
