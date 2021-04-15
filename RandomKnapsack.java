import java.util.Vector;
import java.lang.Math;

public class RandomKnapsack {
    private int[] scores;

    private int[] weights;
    private int[] values;

    private int maxWeightPossible;
    private boolean[] bestRandomScore;

    private Vector<boolean[]> randomCombinations = new Vector<boolean[]>();
    private Vector<boolean[]> bbsCombinations = new Vector<boolean[]>();




    // generates 1000 random sequences of passed size variable using Math.random()

    public RandomKnapsack() {

    }


    private void generateRandomSequences(int size){

        if(this.randomCombinations.size() > 0){
            // empty the vector
            this.randomCombinations.clear();
        }
        
        for(int i = 0; i < 1000; i++){
            boolean[] sequence = new boolean[size];

            for(int j = 0; j < size; j++){
                // converts math random into 0 and 1 
                sequence[j] = (Math.round(Math.random()) == 1);
            }

            this.randomCombinations.add(sequence);
        }
        
    }

    private void generateBBS_Sequences(){

    }

    private int evaluateFitness(boolean[] mem_pop){
        int tempValue = 0;
        int tempWeight = 0;

        for(int i = 0; i < mem_pop.length; i++){
            if(mem_pop[i] == true){
                tempWeight += this.weights[i];
                tempValue += this.values[i];
            }
        }

        if(tempWeight > this.maxWeightPossible){
            return 0;
        }
        
        return tempValue;
    }

    private void evaluatePopulationFitness(Vector<boolean[]> pop){
        // array of fitness scores of respective combination in vector object
        this.scores = new int[pop.size()];

        // for each soution use function to calulate fitness score;
        for(int i = 0; i < pop.size(); i++){
            this.scores[i] = this.evaluateFitness(pop.get(i));
        }

        // TODO find min and average fitness


    }

    private void saveBestRandomScore(){
        int score = 0;
        int numOfCombo = -1;

        for(int i = 0; i < this.scores.length; i++){
            if(this.scores[i] > score){
                score = this.scores[i];
                numOfCombo = i;
                
            }
        }

        this.bestRandomScore = this.randomCombinations.get(numOfCombo);
    }

    public void evaluateRandomPopulations(int[] weight, int[] value, int maxKnapsackWeight){

        this.maxWeightPossible = maxKnapsackWeight;

        this.weights = weight;
        this.values = value;
        
        if(weight.length != value.length){
            System.out.println("The data sets are not the same length, and are not compatible with each other");
        }

        int dataSize = weight.length;

        for(int i = 0; i < 10; i++){
            // generates 1000 random solutions
            this.generateRandomSequences(dataSize);
            // calculates the fitness scores of the solution
            this.evaluatePopulationFitness(this.randomCombinations);
            // saves the best score of the population
            this.saveBestRandomScore();
            
        }

        
        System.out.println("The overall best solution");
        int bestValue = 0;
        int bestWeight = 0;
        for(int j = 0; j < this.bestRandomScore.length; j++){
            if(this.bestRandomScore[j] == true){
                System.out.print("1 ");
                bestValue += this.values[j];
                bestWeight += this.weights[j];
            } else {
                System.out.print("0 ");
            }

        }

        System.out.println("The best weight is " + bestWeight + " , the best value is " + bestValue);
        


    }

}