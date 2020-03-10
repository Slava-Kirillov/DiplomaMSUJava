package ru.diploma.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class CellConfig {
    @Value("${cell.points}")
    private int numberOfPoints;
    @Value("${cell.point.coordinates}")
    private int numberOfCoordinatesPerPoint;
}
