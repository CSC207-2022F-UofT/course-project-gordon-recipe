package usecase;

import database.Database;
import entity.Ingredient;
import entity.Recipe;
import entity.RecipeIngredient;

public class ChefMode {
    private final Database database;


    public ChefMode(Database database) {
        this.database = database;
    }

    public String showIngredient(){
        return "The ingredient you need: " + RecipeIngredient.getIngredient() +
                " with quantity: " + RecipeIngredient.getQuantity();
    }

}
