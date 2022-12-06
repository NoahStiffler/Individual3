import java.util.*;
import java.io.*;
import java.lang.*;

public class Sequence {
	
	// I needed a lot of help with this one, I watched several videos to understand the topic and algorithm,
	// but I could not come up with a solution on my own just based on those videos and the textbook.
	// I do understand what the program is doing, I have commented throughout to show my understanding.
	
	static void minPenalty(String a, String b, int noMatch, int gap) {
		
		
		// length of the two strings we are comparing
		int n = a.length();
		int k = b.length();
		
		// creating a table to store values of our penalties.
		int table[][] = new int[n+k+1][n+k+1];
		
		
		for (int[] x1 : table) {
			Arrays.fill(x1, 0);
		}
		
		
		// We fill the first row and column of the table with multiples of our gap penalty
		for (int i = 0; i <= n+k; i++) {
			table[i][0] = i*gap;
			table[0][i] = i*gap;
			
		}
		
		
		
	//	System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));
		
		// Here we are filling in our table with the values associated with each movement we can make. 
		// If we have a match in characters, we do nothing, but if we do not have a match, we want to store
		// the minimum value it would cost to have a gap or a mismatch character. 
		// This is storing the optimal "answer" between the comparisons.
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= k; j++) {
				if (a.charAt(i-1) == b.charAt(j-1))
					table[i][j] = table[i-1][j-1];
				else
					table[i][j] = Math.min(Math.min(table[i-1][j-1] + noMatch, table[i-1][j] + gap), table[i][j-1] + gap);
			}
		}
	//	System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));

		int length = n+k;
		int i = n;
		int j = k;
		
		int x = length;
		int y = length;
		
		int xAns[] = new int[length+1];
		int yAns[] = new int[length+1];
		
//		System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));
		
		
		// Here we are backtracking through our table of values and storing our answers into the xAns and yAns arrays
		// If we have a need for a gap, we insert an underscore into our answer.
		while ( !(i == 0 || j == 0)) {
			if (a.charAt(i-1) == b.charAt(j-1)) {
				
				xAns[x--] = (int)a.charAt(i-1);
				yAns[y--] = (int)b.charAt(j-1);
				i--;
				j--;
				
			}
			else if (table[i-1][j-1] + noMatch == table[i][j]) {
				
				xAns[x--] = (int)a.charAt(i-1);
				yAns[y--] = (int)b.charAt(j-1);
				i--;
				j--;
			}
			else if (table[i-1][j] + gap == table[i][j]) {
				
				xAns[x--] = (int)a.charAt(i-1);
				yAns[y--] = (int)'_';
				i--;
			}
			else if (table[i][j-1] + gap == table[i][j]) {
				
				xAns[x--] = (int)'_';
				yAns[y--] = (int)b.charAt(j-1);
				j--;
			}
		}
//		System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));
		
		while (x > 0) {
			if (i > 0 ) {
				xAns[x--] = (int)a.charAt(--i);
			}
			else {
				xAns[x--] = (int)'_';
			}
		}
		while (y > 0) {
			if (j > 0) {
				yAns[y--] = (int)b.charAt(--j);
			}
			else {
				yAns[y--] = (int)'_';
			}
		}
		
		int id = 1;
		for (i = length; i >= 1; i--) {
			if ((char)yAns[i] == '_' && (char)xAns[i] == '_') {
				id = i + 1;
				break;
			}
		}
		
//		System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));
		
		// Here we print our our two words so that they are aligned and we can see where we placed gaps, have  mismatches, and matches
		System.out.println("Minimum Penalty: " + table[n][k]);
		
	//	System.out.println("Aligned: ");
		
		for (i = id; i <= length; i++) {
			System.out.print((char)xAns[i]);
		}
		System.out.println();
		for (i = id; i <= length; i++) {
			System.out.print((char)yAns[i]);
		}
		
		System.out.println("\n");
	//	return;
		
		
		
	}

	public static void main(String[] args) throws Exception, NullPointerException {
		
		Scanner input = new Scanner (new File ("input"));
		String inputs[] = new String[5];
		int i = 0;
		while(input.hasNext()) {
			inputs[i] = input.next();
			i++;
		//	System.out.print(input.next() + "  ");
		}


		Scanner in = new Scanner(System.in);
		System.out.println("Enter a word to compare: ");
		String userWord = in.next();
		
		for (i = 0; i < inputs.length; i++) {
			minPenalty(userWord, inputs[i], 3, 2);
			
		}


	}

}
