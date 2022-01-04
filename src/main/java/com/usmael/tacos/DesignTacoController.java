package com.usmael.tacos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import javax.validation.Valid;

import com.usmael.tacos.Ingredient.Type;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DesignTacoController {
    
    private List<Ingredient> ingredients = Arrays.asList (
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
            );
    @GetMapping("/design")
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());
        Type[] types = Type.values();
        for (Type t : types) {
            model.addAttribute(t.toString().toLowerCase(), filterByType(ingredients, t));
        }
        return "design";
    }
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping("/design")
    public String processTaco(@Valid Taco taco, Errors errors) {

        if (errors.hasErrors()) {
            return "design";
        }

        log.info("Processing taco: " + taco);

        return "redirect:/orders/current";
    }
}