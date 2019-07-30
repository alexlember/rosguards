package ru.lember.rosguards.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lember.rosguards.battlefield.Battlefield;
import ru.lember.rosguards.battlefield.BattlefieldImpl;
import ru.lember.rosguards.battlefield.Street;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Configuration
@Slf4j
public class BattlefieldConfiguration {

	@PostConstruct
	private void postConstruct() {
		log.info("initialized");
	}

	@Bean
	public Battlefield battlefield() {

		Street tverskaya = new Street("Тверская");
		Street sakharova = new Street("Сахарова");
		Street bolotnaya = new Street("Болотная");
		Map<String, Street> streets = new TreeMap<>();
		streets.put(tverskaya.getName(), tverskaya);
		streets.put(sakharova.getName(), sakharova);
		streets.put(bolotnaya.getName(), bolotnaya);
		return new BattlefieldImpl(streets);
	}

}
