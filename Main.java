/**
 * Names: Ishan Garg and Simon Bakan
 * Date: April 28, 2022
 * Teacher: Mr. Netchev
 * Prupose: This program takes input for the number of two alleles in a population
 *          and bottlenecks the population, resulting in the inputted size. The
 *          new population is then inflated to the original population size with the
 *          same ratio of each allele, and the number of bottlenecks needed to 
 *          eliminate one of the alleles is determined.
 */

import java.util.*;
import java.io.*;

public class Main
{
    private int lowP;
    private int[] pop;
    private int[] newPop;
    private int numBottlenecks;
    private ArrayList<Integer> values;
    
    public Main() {
        lowP = 0;
        pop = new int[2];
        newPop = new int[2];
        numBottlenecks = 0;
        values = new ArrayList<Integer>(300);
    }
    
    public void run(int l, int p1, int p2) {
        newPop[0] = 0;
        newPop[1] = 0;
        lowP = l;
        pop[0] = p1;
        pop[1] = p2;

        for (int i = 0; i < lowP; i++) {
            if ((int)(Math.random() * (pop[0] + pop[1]) + 1) <= pop[0]) { //Randomly selects an allele based on its frequency.
                newPop[0]++; //If it is the first allele, add 1 to the new, post-bottleneck population and subtract 1 from the original.
                pop[0]--;
            }
            else {
                newPop[1]++;
                pop[1]--;
            }
        }

        newPop[0] *= (p1+p2)/lowP; //Magnify the population back to the original total based on its frequency.
        newPop[1] *= (p1+p2)/lowP;
        numBottlenecks++;

        values.add(numBottlenecks); //Add the elements to an ArrayList for storage.
        values.add(newPop[0]);
        values.add(newPop[1]);

    }
    
    //Prints the data to a .txt file for observation and analysis
    public void writeFile(int l, int p1, int p2) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(p1 + "allele1_" + p2 + "allele2_" + l + "sample.txt"));
        out.println("\t  Bottleneck\t|\t   Allele 1  \t|\t   Allele 2");
        out.println("——————————————————————————————————————————————————————————————————————————");
        for(int i = 0; i<values.size()-2; i+=3) {
            out.println("\t\t" + values.get(i) + "\t\t|\t\t" + values.get(i+1) + "\t\t|\t\t" + values.get(i+2));
        }
        out.println("\nThere were " + numBottlenecks + " bottlenecks.");
        out.close();
    }

    //Prints the data to the Java console for analysis without needing an external file
    public void printData() {
        System.out.println("\t    Bottleneck  \t|\t     Allele 1  \t\t|\t     Allele 2");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for(int i = 0; i<values.size()-2; i+=3) {
            System.out.println("\t\t" + values.get(i) + "\t\t|\t\t" + values.get(i+1) + "\t\t|\t\t" + values.get(i+2));
        }
        System.out.println("\nThere were " + numBottlenecks + " bottlenecks.");
    }
    
    public int getP1() {
        return newPop[0];
    }
    
    public int getP2() {
        return newPop[1];
    }
    
    public int getLowP() {
        return lowP;
    }
    
    public static void main(String[] args) throws IOException {
        Main m = new Main();
        Scanner in = new Scanner(System.in);
        // System.out.println("Enter the number of allele 1: "); //Takes input for initial allele population sizes
        // int p1 = in.nextInt();
        // System.out.println("Enter the number of allele 2: ");
        // int p2 = in.nextInt();
        System.out.println("Enter the number of indiviuals being chosen: "); //Takes input for initial bottleneck population size
        int l = in.nextInt();

        m.run(l, 100, 100); //Runs the bottleneck cycle
        while (m.getP1() != 0 && m.getP2() != 0) { //Continues running the bottleneck cycle until either allele reaches zero
            m.run(m.getLowP(), m.getP1(), m.getP2());
        }

        m.printData(); //Writes data to an external file and to the console
        m.writeFile(l, 100, 100);
    }
}