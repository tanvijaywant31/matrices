
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Complexity:
 * -----------
 * n = row count.
 * m = column count.
 * 
 * O (logn * logm) ( complexity is correct be happy )
 */
public class FindElementInSortedMatrix {

    
   public static int findNearestValueLesserThanOrEqualToX (int[] a, int x,  int low, int high) {
        
        while (low <= high) {
            int mid = (low + high) / 2;

            if (a[mid] == x) return mid;
            
            // test perfect range.
            if (a[mid] < x && ((mid == a.length - 1) || x < a[mid + 1])) {
                return mid;
            }

            // keep searching.
            if (x < a[mid]) { 
                high = mid - 1;
            } else { 
                low = mid + 1;
            }
        }
        
        return -1;
    }

    /**
     * Following the pattern of Arrays and binary search on docjar line 988
     * http://www.docjar.com/html/api/java/util/Arrays.java.html
     * 
     * @param m
     * @param x
     * @return
     */
    public static boolean matrixSearch(int[][] m, int x) {
       return matrixSearch (m, x, 0, m.length - 1, 0, m[0].length -1);
    }

    
    /*
     * cannot do a while loop here since we call recursion twice, 
     * matrixSearch (m, x, currentRow + 1, downRow, leftColumn, currentColumn) || 
     * matrixSearch (m, x, upRow, currentRow - 1, currentColumn + 1, rightColumn);
     * 
     */
    private static boolean matrixSearch (int[][] m, int x, int upRow, int downRow, int leftColumn, int rightColumn) {
        
        if (upRow > downRow) {
            return false;
        }

        final int midRow = (downRow + upRow) / 2;

        // reach middle column.
        final int currentColumn = findNearestValueLesserThanOrEqualToX (m[midRow], x, leftColumn, rightColumn); // find the row.

        /*
         * x is smallest element in the current row, Go up AND ONLY UP. DONT GO DOWN, AS IT MAKES NO SENSE.
         */
        if (currentColumn < 0) {
            return matrixSearch (m, x, upRow, midRow - 1, leftColumn, rightColumn);
        }
         
        if (x == m[midRow][currentColumn]) {
            return true;
        }

        // https://bitbucket.org/ameyapatil/all-images/commits/0848982c8a57575364d04fc83969ca468b4c3da4
        // if (x > m[currentRow][currentColumn]) {
        return matrixSearch (m, x, upRow, midRow - 1, currentColumn + 1, rightColumn) || 
                matrixSearch (m, x, midRow + 1, downRow, leftColumn, currentColumn);
    }

    public static void main(String args[]) {
        int[][] m = { { 10, 20,   30,   40,   95 }, 
                      { 60, 70,   80,   90,  100 }, 
                      { 75, 120, 130,  140,  150 }, 
                     };
        
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                assertTrue(matrixSearch(m, m[i][j]));
            }
        }

        // testing out of matrix
        assertFalse(matrixSearch(m, -40));
        assertFalse(matrixSearch(m, 42));
    	}
}
