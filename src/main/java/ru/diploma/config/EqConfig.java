package ru.diploma.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:equation.properties")
@Getter
public class EqConfig {
    @Value("${wave.number}")
    private float wave_number;
    @Value("${angle.phi}")
    private float anglePhi;
    @Value("${Ex.component.external.electromag.field}")
    private float Ex;
    @Value("${Ey.component.external.electromag.field}")
    private float Ey;
    @Value("${Ez.component.external.electromag.field}")
    private float Ez;
}
