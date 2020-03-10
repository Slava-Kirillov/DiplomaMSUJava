package ru.diploma;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.diploma.config.CellConfig;
import ru.diploma.error.DataReadException;
import ru.diploma.util.IOUtils;

import java.io.IOException;

@ComponentScan
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        CellConfig cellConfig = context.getBean(CellConfig.class);

        try {
            float[][][] object = IOUtils.getArrayOfCells(cellConfig.getNumberOfPoints(), cellConfig.getNumberOfCoordinatesPerPoint());
        } catch (DataReadException | IOException e) {
            e.printStackTrace();
        }
    }

}
