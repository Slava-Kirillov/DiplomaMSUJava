package ru.diploma;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;
import ru.diploma.service.ProcessingService;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        ProcessingService processingService = context.getBean(ProcessingService.class);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        processingService.runProcessing();

        stopWatch.stop();
        System.out.println(
                String.format("Время формирование данных для вычислений: %s ms", stopWatch.getLastTaskTimeMillis()));
    }
}
