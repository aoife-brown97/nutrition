package com.nutrition.config;

import com.nutrition.exception.CsvFileLoadingException;
import com.nutrition.repository.CsvFoodRepository;
import com.nutrition.repository.FoodRepository;
import com.nutrition.repository.JsonFoodRepository;
import com.nutrition.repository.XmlFoodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

@Configuration
public class FoodRepositoryConfig {

    @Value("${nutrition-search.data.file.name}")
    private String nutritionDataFile;

    @Value("${nutrition-search.data.file.type}")
    private String fileType;

    @Bean
    public File csvFile() {
        try {
            var nutritionData = new ClassPathResource(nutritionDataFile);
            return nutritionData.getFile();
        } catch (IOException ex) {
            throw new CsvFileLoadingException(ex);
        }
    }

    @Bean
    public FoodRepository foodRepository(File csvFile) {
        return switch (fileType.toLowerCase()) {
            case "csv" -> new CsvFoodRepository(csvFile);
            case "xml" -> new XmlFoodRepository();
            case "json" -> new JsonFoodRepository();
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileType);
        };
    }
}
