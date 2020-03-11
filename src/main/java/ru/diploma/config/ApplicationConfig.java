package ru.diploma.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationConfig {

    @Value("${data.file}")
    private String dataFile;

    @Value("${cell.points}")
    private int numPoints;

    @Value("${cell.point.coordinates}")
    private int numCoordinatePoint;
}
