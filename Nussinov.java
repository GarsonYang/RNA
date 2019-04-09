import java.util.*;
import java.lang.*;
import java.io.*;


public class Nussinov
{
   //I use the index in the slides
   public static void main (String[] args) throws FileNotFoundException
   {

      
      ArrayList<String> array = new ArrayList<String>();
      Scanner input=new Scanner(new File("testcases.txt"));
      String S;
      int n=0;
      
      while(input.hasNextLine()){
         array.add(input.nextLine());
         n++;
      }
      
      for(int i=0;i<n;i++)
         Nussinov(array.get(i));
      
      
      if(false)      //true to execute timing tests, false to skip them
         for(int k=4; k<=12;k++){
            Nussinov(randomS((int)Math.pow(2,k)));
            Nussinov(randomS((int)Math.pow(2,k)));
            Nussinov(randomS((int)Math.pow(2,k)));
         }
   }
   
   private static void Nussinov(String S){
      int n = S.length();
      int[][] OPT = new int[n][n];
      int temp = 0;  //to store the largest result in t loop
      
      System.out.println(S);
      
      double startTime = System.nanoTime();
      
      for(int j=5;j<n;j++)
         for(int i=j-5;i>=0;i--){
            for(int t=j-5;t>=i;t--)
               if(S.charAt(j)+S.charAt(t)==138||S.charAt(j)+S.charAt(t)==150)
                  if(i==t) //if j pairs with i
                     temp = Math.max(temp, 1+OPT[t+1][j-1]);
                  else
                     temp = Math.max(temp, OPT[i][t-1]+1+OPT[t+1][j-1]);
            OPT[i][j] = Math.max(OPT[i][j-1], temp);
            temp=0;
         }
      
      //Traceback
      char[] se = new char[n];
      for(int i=0;i<n;i++)
         se[i] = '.';
      
      System.out.println(Traceback(OPT,0,n-1,se));
      
      double endTime = System.nanoTime();
      
      //print[3]
      System.out.print("Length = "+n);
      System.out.print(", Pairs = "+OPT[0][n-1]);
      System.out.println(", Time = "+(endTime-startTime)/1000000000+" sec");
      
      //print matrix
      if(n<=25)
         for(int i=0;i<n;i++){
           for(int j=0;j<n;j++)
               System.out.print(OPT[i][j]+" ");
          System.out.println();
        }
        
      System.out.println();
   }


   private static char[] Traceback(int[][] OPT, int left, int right, char[] se){
      if(right-left<5) return se;
      
      //find the j that is part of a base pair
      for(int j=right;j>=left+5;j--)
         if(OPT[left][j]>OPT[left][j-1]){
            se[j]=')';
            
            //find the t that pairs j
            int t;
            for(t=j-5;t>left;t--)
               if(t==left&&OPT[t][j]==1+OPT[t+1][j-1]) 
                  break;
               else if(OPT[left][j]==1+OPT[left][t-1]+OPT[t+1][j-1])
                  break;
            
            se[t]='(';
            
            //call the subroutines
            Traceback(OPT, left, t-1, se);
            Traceback(OPT, t+1, j-1, se);
            break;
         }
      return se;
   }
   
  private static String randomS (int size){
      String input = "AUGC";
      char[] output = new char[size];
      for(int i=0;i<size;i++)
         output[i] = input.charAt((int)(Math.random()*4));
   return new String(output);
   }
}