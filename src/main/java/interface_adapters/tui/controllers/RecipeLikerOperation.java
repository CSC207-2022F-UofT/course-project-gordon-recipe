package interface_adapters.tui.controllers;

import entity.Recipe;
import interface_adapters.tui.Colour;
import interface_adapters.tui.TextualOperation;
import interface_adapters.tui.TextualReader;
import usecase.RecipeLiker;

import java.util.List;

public class RecipeLikerOperation implements TextualOperation {
    private final RecipeLiker recipeLiker;
    private final TextualReader reader;

    private final List<TextualOperation> operations = List.of(
            new LikeAdder(),
            new UnlikeAdder(),
            new ShowLiked(),
            new ShowUnliked()
    );

    public RecipeLikerOperation(TextualReader reader, RecipeLiker recipeLiker) {
        this.recipeLiker = recipeLiker;
        this.reader = reader;
    }

    @Override
    public String getCode() {
        return "recipe liker";
    }

    @Override
    public String getDescription() {
        return "Manage liked recipes";
    }

    @Override
    public void run() {
        reader.chooseOperation(operations, "Recipe Liker");
    }

    private class LikeAdder implements TextualOperation {

        @Override
        public String getCode() {
            return "like";
        }

        @Override
        public String getDescription() {
            return "Like recipes";
        }

        @Override
        public void run() {
            while (true) {
                List<Recipe> recipes = recipeLiker.getUnlikedRecipes();
                Recipe recipe = reader.chooseFromList(recipes, "Recipe to Like");

                if (recipe == null) {
                    break;
                }

                recipeLiker.likeRecipe(recipe);
                Colour.info("Liked %s", recipe.getName());
            }
        }
    }

    private class UnlikeAdder implements TextualOperation {

        @Override
        public String getCode() {
            return "unlike";
        }

        @Override
        public String getDescription() {
            return "Unlike recipes";
        }

        @Override
        public void run() {
            while (true) {
                List<Recipe> recipes = recipeLiker.getLikedRecipes();
                Recipe recipe = reader.chooseFromList(recipes, "Recipe to Unlike");

                if (recipe == null) {
                    break;
                }

                recipeLiker.unlikeRecipe(recipe);
                Colour.info("Unliked %s", recipe.getName());
            }
        }
    }

    private class ShowLiked implements TextualOperation {

        @Override
        public String getCode() {
            return "show liked";
        }

        @Override
        public String getDescription() {
            return "Show liked recipes";
        }

        @Override
        public void run() {
            Colour.section("Liked Recipes");

            List<Recipe> likes = recipeLiker.getLikedRecipes();

            for (Recipe recipe : likes) {
                System.out.println(recipe.getName());
            }
        }
    }

    private class ShowUnliked implements TextualOperation {

        @Override
        public String getCode() {
            return "show unliked";
        }

        @Override
        public String getDescription() {
            return "Show unliked Recipe";
        }

        @Override
        public void run() {
            Colour.section("Unliked Recipes");

            List<Recipe> likes = recipeLiker.getUnlikedRecipes();

            for (Recipe recipe : likes) {
                System.out.println(recipe.getName());
            }
        }
    }
}


