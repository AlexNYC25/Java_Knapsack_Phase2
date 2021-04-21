import java.io.File;
import java.util.Scanner;
import java.util.Vector;
import java.io.FileWriter;

public class knapsack{

    public static int doesItInclude(String[] arr, String word){

        for(int x = 0; x < arr.length; x++){
            if(arr[x].matches(word)){
                return x;
            }
        }

        return -1;
    }
    public static void main(String args[]){

        RandomKnapsack randomKnapsack = new RandomKnapsack(67,79,127);
        BruteKnapsack bruteKnapsack = new BruteKnapsack();

        String[] initialStrings = null;

        Scanner sc = null;


        // argument flags
        int inputPosition = doesItInclude(args, "-i") + 1;
        int outputPosition = doesItInclude(args, "-o") + 1;

        if(inputPosition >= args.length || outputPosition >= args.length || inputPosition == -1 || outputPosition == -1){
            System.out.println("There is a missing parameter");
            return;
        }
        
        // initial data file
        try{
            File file = new File(args[inputPosition]); 
            sc = new Scanner(file); 
        } catch( Exception e){
            System.out.println("Error found");
        }
        

        // 2d arrays to store values 
        int[][] easyMatrix = new int[10][2];
        int[][] mediumMatrix = new int[15][2];
        int[][] largeMatrix = new int[20][2];

         // split initial file into array of strings
         for(int x = 0; x < 1; x++){
            initialStrings = sc.nextLine().split(" ");
        }
        // split strings into the proper array
        for(int x = 0; x < 10; x++){
            String[] tempWeight = initialStrings[0].split(",");
            String[] tempValue = initialStrings[1].split(",");
            easyMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            easyMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }

        for(int x = 0; x < 15; x++){
            String[] tempWeight = initialStrings[2].split(",");
            String[] tempValue = initialStrings[3].split(",");
            mediumMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            mediumMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }

        for(int x = 0; x < 20; x++){
            String[] tempWeight = initialStrings[4].split(",");
            String[] tempValue = initialStrings[5].split(",");
            largeMatrix[x][0] = Integer.parseInt(tempWeight[x]);
            largeMatrix[x][1] = Integer.parseInt(tempValue[x]);
        }

        // execution and output
        try{

            File outPutFile = null;
            FileWriter myFileWriter = null;

            // if input file is passed but no output file
            if(inputPosition >= 0 && inputPosition < args.length && outputPosition == -1 ){
                // create file 
                
                System.out.println("Missing ouutput paramaeter");
                outPutFile = new File("output.txt");
                if(outPutFile.createNewFile()){
                    System.out.println("Output file created");

                } else {
                    System.out.println("Output file has been overwritten");
                }

                // create fileWriter object to write output
                myFileWriter = new FileWriter("output.txt");
                
            }
            else{
                outPutFile = new File(args[outputPosition]);
                if(outPutFile.createNewFile()){
                    System.out.println("Output file created");

                } else {
                    System.out.println("Output file has been overwritten");
                }
                myFileWriter = new FileWriter(args[outputPosition]);
                
            }
            
            // 10 items

            myFileWriter.write("***** Start of small knapsack problem ***** \n\n");

            int[] tempWeight = new int[10];
            int[] tempValue = new int[10];

            for(int i = 0; i < 10; i++){
                tempWeight[i] = easyMatrix[i][0];
                tempValue[i] = easyMatrix[i][1];
            }
            for(int x = 0; x <= 10; x++){
               bruteKnapsack.createCombinations(bruteKnapsack.smallNums, 10, x); 
            }
            
            myFileWriter.write("The Brute force solution: \n");
            bruteKnapsack.bestCombination(easyMatrix, 30, myFileWriter);
            myFileWriter.write("\n");
            bruteKnapsack.listOfCombinations = new Vector<int[]>();

            //randomKnapsack.evaluateRandomPopulations(tempWeight, tempValue, 30);
            randomKnapsack.evaluateRandomPopulations_output(tempWeight, tempValue, 30, myFileWriter);
            myFileWriter.write("\n");

            // 15 items
            myFileWriter.write("***** Start of medium knapsack problem ***** \n\n");

            tempWeight = new int[15];
            tempValue = new int[15];

            for(int x = 0; x <= 15; x++){
                bruteKnapsack.createCombinations(bruteKnapsack.mediumNums, 15, x); 
             }
            
             myFileWriter.write("The Brute force solution: \n");
             bruteKnapsack.bestCombination(mediumMatrix, 30, myFileWriter);
             myFileWriter.write("\n");
             bruteKnapsack.listOfCombinations = new Vector<int[]>();

            for(int i = 0; i < 15; i++){
                tempWeight[i] = mediumMatrix[i][0];
                tempValue[i] = mediumMatrix[i][1];
            }

            //randomKnapsack.evaluateRandomPopulations(tempWeight, tempValue, 30);
            randomKnapsack.evaluateRandomPopulations_output(tempWeight, tempValue, 30, myFileWriter);
            myFileWriter.write("\n");

            // 20 items 
            myFileWriter.write("***** Start of large knapsack problem ***** \n\n");

            tempWeight = new int[20];
            tempValue = new int[20];

            for(int x = 0; x <= 20; x++){
                bruteKnapsack.createCombinations(bruteKnapsack.largeNums, 20, x); 
             }
             
             myFileWriter.write("The Brute force solution: \n");
             bruteKnapsack.bestCombination(largeMatrix, 30, myFileWriter);
             myFileWriter.write("\n");
             bruteKnapsack.listOfCombinations = new Vector<int[]>();

            for(int i = 0; i < 20; i++){
                tempWeight[i] = largeMatrix[i][0];
                tempValue[i] = largeMatrix[i][1];
            }

            //randomKnapsack.evaluateRandomPopulations(tempWeight, tempValue, 30);
            randomKnapsack.evaluateRandomPopulations_output(tempWeight, tempValue, 30,myFileWriter);
            myFileWriter.write("\n");

            myFileWriter.close();

        } catch (Exception e){
            System.out.println("An error occured when creating output file");
            e.printStackTrace();
        }

        sc.close();
        System.out.println("Program has finished executing");

    }
}