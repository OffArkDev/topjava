package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 400),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 29,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(12, 0), LocalTime.of(21,0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for (UserMealWithExceed l : list) {
            System.out.println(String.format("time %s description %s calories %d exceed %b",
                    l.getDateTime(), l.getDescription(), l.getCalories(), l.isExceed()));
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result;

        Map<LocalDate, Integer> caloriesSum = mealList.stream()
                .collect(Collectors.toMap(m -> m.getDateTime().toLocalDate(), UserMeal::getCalories, (oldValue, newValue) -> newValue += oldValue));

        result = mealList
                .stream()
                .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .map(temp -> {
                    boolean exceed = caloriesSum.get(temp.getDateTime().toLocalDate()) > caloriesPerDay;
                    return new UserMealWithExceed(temp.getDateTime(), temp.getDescription(), temp.getCalories(), exceed);
                }).collect(Collectors.toList());


        return result;
    }
}
