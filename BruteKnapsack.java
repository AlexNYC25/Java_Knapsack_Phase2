import java.util.Vector;
import java.io.FileWriter;
import java.io.IOException;

public class BruteKnapsack {
    private int count = 0;
    public Vector<int[]> listOfCombinations = new Vector<int[]>();

    public int[] bestCombination = null;
    private int bestCombinationValue = 0;

    public int[] smallNums = {0,1,2,3,4,5,6,7,8,9};
    public int[] mediumNums = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    public int[] largeNums = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};

    public BruteKnapsack(){
        
    }

    private void combinationUtil(int arr[], int data[], int start, int end, int index, int size){
        // Current combination is ready to be printed, print it
        if (index == size)
        {
            // creats a temp array of the possible array 
            int[] temp = new int[size];

            // captures the current combination
            for (int j=0; j<size; j++){
                temp[j] = data[j];
            }
            
            count++;
            this.listOfCombinations.add(temp);
            return;
        }
  
        /*
            calls the next recursive call for the next combination
        */
        for (int i = start; i<=end && (end - i + 1 >= size-index); i++)
        {
            data[index] = arr[i];
            // calls the function recursivley with new parameters
            this.combinationUtil(arr, data, i+1, end, index+1, size);
        }
    }

    // The main utilty function that is called to properly use the combinationUtil function
    public void createCombinations(int arr[], int end, int size){
        // an initial empty array to hold the combination of elements of length size 
        int data[] = new int[size];
  
        // actual combination work done by other function, initial function call to start the recursive search
        this.combinationUtil(arr, data, 0, end-1, 0, size);
    }

    public void bestCombination(int[][] valueMatrix, int maxWeight, FileWriter output){
        // resets global values
        bestCombination = new int[0];
        bestCombinationValue = 0;

        for(int x = 0; x < listOfCombinations.size(); x++){
            int[] currentCombination = listOfCombinations.get(x);
            int totalWeight = 0;
            int totalValue = 0;
            for(int y = 0; y < currentCombination.length; y++){
                totalWeight += valueMatrix[currentCombination[y]][0];
                totalValue += valueMatrix[currentCombination[y]][1];

            }
            if(totalWeight < maxWeight){

                if(bestCombination == null){
                    bestCombination = currentCombination;
                    bestCombinationValue = totalValue;
                }
                
                if(totalValue > bestCombinationValue){
                    bestCombination = currentCombination;
                    bestCombinationValue = totalValue;
                }

            }
        }

        try{
            int comboWeight = 0;
            output.write("Running " + count + " combinations for the knapsack problem \n");
            output.write("The best combinaton of items are: ");

            for(int x = 0; x < valueMatrix.length; x++){
                boolean inArray = false;
                for(int y = 0; y < bestCombination.length; y++){
                    if(bestCombination[y] == x){
                        inArray = true;
                    }
                }

                if(inArray){
                    output.write("1 ");
                }
                else {
                    output.write("0 ");
                }
            }
            output.write("\n");

            output.write("The total weight of the items: " + comboWeight + "\n");
            output.write("The total value of the items: " + bestCombinationValue + "\n");
            
            
        } catch (IOException e){
            System.out.println("An error has occured when writing to output file");
        }

        count = 0;

    }

    /*
        helper array to check if a parameter flag is in an the args array
    */
    public static int doesItInclude(String[] arr, String word){

        for(int x = 0; x < arr.length; x++){
            if(arr[x].matches(word)){
                return x;
            }
        }

        return -1;
    }

    
}
