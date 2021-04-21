Java Knapsack problem

Phase 2: Random Solutions

    compile instructions:
        javac knapsack.java RandomKnapsack.java BruteKnapsack.java
    
    execution instructions
        java knapsack -i data.txt -o output.txt

    input file structure, <> - denotes list of numbers seperated by commmas, x values represent weight values, y values represent "value" values
        <x1,x2,...,x10> <y1,y2,...,y10> <x1,x2,...,x15> <y1,y2,...,y15> <x1,x2,...,x20> <y1,y2,...,y20>
        * Same as phase 2

    summary:
        The knapsack class executes both the BruteKnapsack class with the same data as before, looking and comparing every possible combination of 
        items for knapsack class, then it also executes the RandomKnapsack class that looks over 10000 random solutions for each random number generator
        Math.random() and Blum Blum Shum. The program then outputs the best solution from the different approaches including the average fitness and minimum
        fitness from the genration generated. Note that for all knapsack classes the max weight the "knapsack" can hold is 30, and for the blum blum shum 
        generator the parameters are the same as those given in the example.
       