/*
 * Chrom is m & primality each generation
 */
package integerfactorization;

/**
 * @date September 30, 2018
 * @author Emilia
 */

import java.math.BigInteger;
import java.util.Random;

public class Chromosome {

    private int[] chrom;  //the array that holds the order of the cities in question
    private int length;
    private Random rand;
    private long eval;
    private long prime;

  public Chromosome(int l, Random r) {

      length = l;
      chrom = new int[length];
      eval = 0;
      rand = r;
//      prime = 0;

  } //constructor
  
  public Chromosome(Chromosome c) {
      length = c.length;
      chrom = new int[length];
      for(int i= 0; i < length; i ++) {
          chrom[i] = c.chrom[i];
      }
      eval = c.getEval();
      prime = c.getPrime();
      rand = c.rand;
  } //copy

  public int[] getChromosome() {
      return chrom;
  }//getChromosome

  /*This method sets the ith element in the Chromosome to c
   * @param i  the position in the chrom array
   * @param c  the value the element will be set to
   * */
  public void set(int i, int c) {
    chrom[i] = c;
  } //set()

//  public void copy(Chromosome c) {
//      for(int i= 0; i < length; i ++) {
//          chrom[i] = c.get(i);
//      }
//      eval = c.getEval();
//      prime = c.getPrime();
//  }//copy

  public int getSize() {
      return chrom.length;
  }//getSize

  /*This function returns the value of the ith element in the chrom array
   * @param i  the element of the chrom array
   * @return int  the value of the ith element
   * */
  public int get(int i) {
    return chrom[i];
  } //get()

  public long getEval() {
      return eval;
  }//getEval

  public long getPrime() {
      return prime;
  }//getPrime

  public void setPrime(long p) {
      prime = p;
  }//setPrime

  public void randTour() {
    do {
        chrom[0] = 1;
        for (int i=1; i < length; i++) {
            chrom[i] = rand.nextInt(2);
        }
    } while (!isPrime());
  }//randTour

  public void mutation() {
      int time = 0;
      while(time < length) {
          time++;
          int randAllele = rand.nextInt(length-1) + 1;

          if (chrom[randAllele] == 0) {
              chrom[randAllele] = 1;
          } else {
              chrom[randAllele] = 0;
          }
          if (!isPrime()) { //revert the change if it does not make the number probably prime
              if (chrom[randAllele] == 0) {
                  chrom[randAllele] = 1;
              } else {
                  chrom[randAllele] = 0;
              } 
          } else break;
      };
  }//mutation

  public void evaluationFunction(long n) {
      String strChrom = "";
      for(int i = 0; i < chrom.length; i++) {
          strChrom = strChrom + chrom[i];
      }
      long decChrom = Long.parseLong(strChrom, 2);

      long mPlus = convertToPrimePlus(decChrom);
      long mMinus = convertToPrimeMinus(decChrom);

      long mPlusEval = n % mPlus;
      long mMinusEval = n % mMinus;

      if (mPlusEval < mMinusEval) {
          prime = mPlus;
          eval = mPlusEval;
      } else {
          prime = mMinus;
          eval = mMinusEval;
      }

  }//evaluationFunction

  private long convertToPrimePlus(long m) {
      return (6*m) + 1;
  } //convertToPrimePlus

  private long convertToPrimeMinus(long m) {
      return (6*m) - 1;
  } //convertToPrimeMinus

  public boolean isPrime() {
      String strChrom = "";
      for(int i = 0; i < chrom.length; i++) {
          strChrom = strChrom + chrom[i];
      }
      long decChrom = Long.parseLong(strChrom, 2);

      long mPlus = convertToPrimePlus(decChrom);
      long mMinus = convertToPrimeMinus(decChrom);

      BigInteger bigMPlus = BigInteger.valueOf(mPlus);
      BigInteger bigMMinus = BigInteger.valueOf(mMinus);

      return bigMPlus.isProbablePrime(1000000) || bigMMinus.isProbablePrime(1000000);
  } //isPrime

} //Chromosome