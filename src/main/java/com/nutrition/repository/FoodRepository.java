package com.nutrition.repository;

import com.nutrition.model.Food;

import java.util.List;

public interface FoodRepository {
    List<Food> findAll();
}
