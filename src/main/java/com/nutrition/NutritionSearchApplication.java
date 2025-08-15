package com.nutrition;

import com.nutrition.exception.CsvFileLoadingException;
import com.nutrition.repository.CsvFoodRepository;
import com.nutrition.repository.FoodRepository;
import com.nutrition.service.NutritionSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class NutritionSearchApplication {

    @Bean
    public NutritionSearchService nutritionSearchService(FoodRepository foodRepository) {
        return new NutritionSearchService(foodRepository);
    }

    public static void main(String[] args) {
        SpringApplication.run(NutritionSearchApplication.class, args);
    }
}
