package myfirstproject;
import java.util.Scanner;
public class sumofdigits {

	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		int n,sum,r;
        System.out.println("enter n value");
        n=s.nextInt();
        sum=0;
        while(n!=0)
        {
          r=n%10;
          sum=sum+r;
          n=n/10;
          
	}
       System.out.println("sum is "+sum);
	}
}