package com.nutrition.repository;

import com.nutrition.model.Food;

import java.util.List;

public class XmlFoodRepository implements FoodRepository {
    @Override
    public List<Food> findAll() {
        return List.of();
    }
}
