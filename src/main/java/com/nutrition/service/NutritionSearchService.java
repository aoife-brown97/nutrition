package com.nutrition.service;

import com.nutrition.dto.NutritionSearchRequest;
import com.nutrition.dto.Sort;
import com.nutrition.dto.SortField;
import com.nutrition.dto.SortOrder;
import com.nutrition.model.Food;
import com.nutrition.repository.FoodRepository;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public final class NutritionSearchService {

    private static final EnumMap<SortField, Comparator<Food>> FIELD_COMPARATORS = new EnumMap<>(SortField.class);

    static {
        FIELD_COMPARATORS.put(SortField.CALORIES, Comparator.comparing(
            Food::calories, Comparator.naturalOrder()));
        FIELD_COMPARATORS.put(SortField.NAME, Comparator.comparing(
            Food::name, String.CASE_INSENSITIVE_ORDER));
    }

    private final FoodRepository foodRepository;

    public NutritionSearchService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public List<Food> searchNutrition(NutritionSearchRequest request) {
        return foodRepository.findAll().stream()
                .filter(f -> matchesFilters(request, f))
            .sorted(buildComparator(request))
            .limit(request.limit())
            .toList();
    }

    private boolean matchesFilters(NutritionSearchRequest request, Food food) {
        return hasMatchingFatRating(request, food.fatRating())
                && isGreaterThanMinCalories(request, food.calories())
                && isLessThanMaxCalories(request, food.calories());
    }

    private boolean hasMatchingFatRating(NutritionSearchRequest request, Food.FatRating fatRating) {
        return request.fatRating() == null || request.fatRating().equals(fatRating);
    }

    private boolean isGreaterThanMinCalories(NutritionSearchRequest request, Integer calories) {
        return request.minCalories() == null || calories >= request.minCalories();
    }

    private boolean isLessThanMaxCalories(NutritionSearchRequest request, Integer calories) {
        return request.maxCalories() == null || calories <= request.maxCalories();
    }

    private Comparator<Food> buildComparator(NutritionSearchRequest request) {
        return request.sorts().stream()
            .map(this::getComparator)
            .reduce(Comparator::thenComparing)
            .orElse((h1, h2) -> 0);
    }

    private Comparator<Food> getComparator(Sort sort) {
        var comparator = FIELD_COMPARATORS.get(sort.field());
        return sort.order() == SortOrder.ASC ? comparator : comparator.reversed();
    }

}
