package guru.drinkit.listview;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.emptySet;

@SuppressWarnings("unused")
public class Criteria {

    private Set<Integer> cocktailTypes = emptySet();
    private Set<Integer> ingredients =emptySet();
    private Set<Integer> options= emptySet();

    public Criteria() {
    }

    public Criteria(Set<Integer> cocktailTypes, Set<Integer> ingredients, Set<Integer> options) {
        this.cocktailTypes = cocktailTypes;
        this.ingredients = ingredients;
        this.options = options;
    }

    public Set<Integer> getCocktailTypes() {
        return cocktailTypes;
    }

    public void setCocktailTypes(Set<Integer> cocktailTypes) {
        this.cocktailTypes = cocktailTypes;
    }

    public Set<Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Integer> getOptions() {
        return options;
    }

    public void setOptions(Set<Integer> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "cocktailTypes=" + cocktailTypes +
                ", ingredients=" + ingredients +
                ", options=" + options +
                '}';
    }
}