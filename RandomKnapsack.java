import java.util.Vector;


import java.io.FileWriter;
import java.lang.Math;
import java.math.BigInteger;

public class RandomKnapsack {
    // array for generations
    private int[] scores;
    private int[] weights;
    private int[] values;

    // varaibles for best possible score
    private int maxWeightPossible;
    private boolean[] bestRandomScore;
    private boolean[] bestBBS_RandomScore;

    // vectors that hold the generations combinations
    private Vector<boolean[]> randomCombinations = new Vector<boolean[]>();
    private Vector<boolean[]> bbsCombinations = new Vector<boolean[]>();

    // blum blum shum variables
    private int bbs_p;
    private int bbs_q;
    private int bbs_x;
    private int bbs_n;

    // average and minimum values
    private int average ;
    private int minimum ;


    public RandomKnapsack(int p, int q, int x) {
        this.bbs_p = p;
        this.bbs_q = q;
        this.bbs_x = x;
        this.bbs_n = this.bbs_p * this.bbs_q;

    }

    // generates 1000 random sequences of passed size variable using Math.random()
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

    // generates boolena value using bbs
    private boolean generateBBS_binary(){
        int temp = (this.bbs_x * this.bbs_x) % this.bbs_n;

        this.bbs_x = temp;

        return (Math.round((double)temp/(this.bbs_n - 1)) == 1);

    }

    // generates combination using the bbs number generator
    private void generateBBS_Sequences(int size){
        if(this.bbsCombinations.size() > 0){
            this.bbsCombinations.clear();
        }

        for(int i = 0; i < 1000; i++){
            boolean[] sequence = new boolean[size];

            for(int j = 0; j < size; j++){
                // converts math random into 0 and 1 
                sequence[j] = generateBBS_binary();
            }

            this.bbsCombinations.add(sequence);
        }

    }

    // function to evaluate the fitness of a combination
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

    // function that runs through every member of a generator and its statistics
    private void evaluatePopulationFitness(Vector<boolean[]> pop){
        // array of fitness scores of respective combination in vector object
        this.scores = new int[pop.size()];

        // for each soution use function to calulate fitness score;
        for(int i = 0; i < pop.size(); i++){
            this.scores[i] = this.evaluateFitness(pop.get(i));
        }

        // TODO find min and average fitness

        this.findMinAverage(this.scores);


    }

    // utility function to find the min and average 
    private void findMinAverage(int[] arr){
        int min = Integer.MAX_VALUE;
        int total = 0;

        for(int i = 0; i < arr.length; i++){
            if(arr[i] < min){
                min = arr[i];
            }
            total += arr[i];
        }

        this.minimum = min;
        this.average = (total / arr.length);

    }


    private void outputMinAverage(FileWriter output){
        try{
            output.write("Minimum fitness score: " + this.minimum + ", " + "The average fitness score: " + this.average);
        } catch (Exception e){
            System.out.println("There was an error in printing out the minimum and average ");
        }
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

    private void saveBest_BBS_Score(){
        int score = 0;
        int numOfCombo = -1;

        for(int i = 0; i < this.scores.length; i++){
            if(this.scores[i] > score){
                score = this.scores[i];
                numOfCombo = i;
                
            }
        }

        this.bestBBS_RandomScore = this.bbsCombinations.get(numOfCombo);
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

    public void evaluateRandomPopulations_output(int[] weight, int[] value, int maxKnapsackWeight, FileWriter output){

        this.maxWeightPossible = maxKnapsackWeight;

        this.weights = weight;
        this.values = value;
        
        if(weight.length != value.length){
            System.out.println("The data sets are not the same length, and are not compatible with each other");
            try{
                output.write("The data sets are not the same length, and are not compatible with each other\n");
            } catch (Exception e){
                System.out.println("An error occured printing out to the output");
            }
            
        }

        int dataSize = weight.length;

        /*
            Math.random() section
        */

        for(int i = 0; i < 100; i++){
            // generates 1000 random solutions
            this.generateRandomSequences(dataSize);
            // calculates the fitness scores of the solution
            this.evaluatePopulationFitness(this.randomCombinations);
            // output this generations min and average
            try{
               output.write("\nStatistics for Math.random() generation " + (i+1) + " :\n"); 
            } catch(Exception e){

            }
            
            this.outputMinAverage(output);
            
            // saves the best score of the population
            this.saveBestRandomScore();
            
        }

        try{
            output.write("\n\nThe overall best Math.random() solution\n");
            int bestValue = 0;
            int bestWeight = 0;
            for(int j = 0; j < this.bestRandomScore.length; j++){
                if(this.bestRandomScore[j] == true){
                    //System.out.print("1 ");
                    output.write("1 ");
                    bestValue += this.values[j];
                    bestWeight += this.weights[j];
                } else {
                    //System.out.print("0 ");
                    output.write("0 ");
                }

            }
            output.write("\n");
            output.write("The best weight is " + bestWeight + " , the best value is " + bestValue + "\n");
        } catch (Exception e){
            System.out.println("An error occured printing out to the output");
        }


        /*
            BBS section
        */

        for(int i = 0; i < 100; i++){
            // generates 1000 random solutions
            this.generateBBS_Sequences(dataSize);
            // calculates the fitness scores of the solution
            this.evaluatePopulationFitness(this.bbsCombinations);
            // output this generations min and average
            try{
                output.write("\nStatistics for blum blum shum generation " + (i+1) + " :\n");
            } catch(Exception e){

            }
            this.outputMinAverage(output);
            // saves the best score of the population
            this.saveBest_BBS_Score();
            
        }

        try{
            output.write("\n\nThe overall best Blum Blum Shum solution\n");
            int bestValue = 0;
            int bestWeight = 0;
            for(int j = 0; j < this.bestBBS_RandomScore.length; j++){
                if(this.bestBBS_RandomScore[j] == true){
                    //System.out.print("1 ");
                    output.write("1 ");
                    bestValue += this.values[j];
                    bestWeight += this.weights[j];
                } else {
                    //System.out.print("0 ");
                    output.write("0 ");
                }

            }
            output.write("\n");
            output.write("The best weight is " + bestWeight + " , the best value is " + bestValue + "\n");
        } catch (Exception e){
            System.out.println("An error occured printing out to the output");
        }
        


    }

}