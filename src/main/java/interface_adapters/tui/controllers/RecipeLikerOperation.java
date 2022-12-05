package interface_adapters.tui.controllers;

import entity.Recipe;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeLiker;
import usecase.RecipeManager;

import java.util.List;

public class RecipeLikerOperation implements TextualOperation {
    private Recipe recipe;
    private final RecipeLiker recipeLiker;
    private final TextualReader reader;
    private final RecipeManager recipeManager;

    private final List<TextualOperation> operations = List.of(
            new Liker(),
            new Unlike(),
            new GetLiked(),
            new GetUnliked()
    );


    public RecipeLikerOperation(TextualReader reader, RecipeLiker recipeLiker, RecipeManager recipeManager) {
        this.recipeLiker = recipeLiker;
        this.reader = reader;
        this.recipeManager = recipeManager;
    }


    @Override
    public String getCode() {
        return "recipe liker";
    }

    @Override
    public String getDescription() {
        return "Like or Unlike the recipe";
    }

    @Override
    public void run() {
        while (true) {
            List<Recipe> recipes = recipeManager.getAllRecipes();
            this.recipe = reader.chooseFromList(recipes, "Like or Unlike Recipe");

            if (this.recipe == null) {
                break;
            }
        }
        reader.chooseOperation(operations, "Recipe Liker");
    }

    private class Liker implements TextualOperation{

        @Override
        public String getCode() {
            return "like recipe";
        }

        @Override
        public String getDescription() {
            return "Like This Recipe";
        }

        @Override
        public void run() {
            recipeLiker.likeRecipe(recipe);
        }
    }

    private class Unlike implements  TextualOperation{

        @Override
        public String getCode() {
            return "unlike recipe";
        }

        @Override
        public String getDescription() {
            return "Unlike This Recipe";
        }

        @Override
        public void run() {
            recipeLiker.unlikeRecipe(recipe);

        }
    }

    private class GetLiked implements TextualOperation{

        @Override
        public String getCode() {
            return "get liked recipe";
        }

        @Override
        public String getDescription() {
            return "Get Liked Recipe";
        }
        @Override
        public void run() {
            recipeLiker.getLikedRecipes();
        }
    }
    private class GetUnliked implements TextualOperation{

        @Override
        public String getCode() {
            return "get unliked recipe";
        }

        @Override
        public String getDescription() {
            return "Get Unliked Recipe";
        }

        @Override
        public void run() {
            recipeLiker.getUnlikedRecipes();

        }
    }
}


