package interface_adapters.tui.controllers;

import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeRecommender;

import java.util.List;


/**
 * An operation for running the recipe recommender use-case.
 */
public class RecipeRecommenderOperation implements TextualOperation {

    private final TextualReader reader;
    private final RecipeRecommender recipeRecommender;

    private final List<TextualOperation> operations = List.of(
            new RecipeRecommendationProvider()
    );

    public RecipeRecommenderOperation(TextualReader reader, RecipeRecommender recipeRecommender) {
        this.reader = reader;
        this.recipeRecommender = recipeRecommender;
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Recommender");
    }

    @Override
    public String getDescription() {
        return "Get a recipe recommendation";
    }

    @Override
    public String getCode() {
        return "recipe recommender";
    }

    private class RecipeRecommendationProvider implements TextualOperation {
        @Override
        public String getCode() {
            return "recommendation";
        }

        @Override
        public String getDescription() {
            return "Get a recipe recommendation";
        }

        @Override
        public void run() {
            Recipe suggestion = recipeRecommender.getRecommendation();
            Colour.info("You should try %s", suggestion.getName());
        }
    }



}