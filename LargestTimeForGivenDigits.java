import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * LeetCode 949. Largest Time for Given Digits
 * https://leetcode.com/problems/largest-time-for-given-digits/
 */
public class LargestTimeForGivenDigits {


    /**
     * Given an array arr of 4 digits, 
     * find the latest 24-hour time that can be made using each digit exactly once.
     * 
     * Runtime: 16 ms, faster than 40.20% of Java online submissions.
     * Memory Usage: 39.1 MB, less than 47.51% of Java online submissions.
     * 
     * 172 / 172 test cases passed.
     * Status: Accepted
     * Runtime: 16 ms
     * Memory Usage: 39.1 MB
     */
    static public String largestTimeFromDigits0(int[] arr) {
        
        // **** initialization ****
        int latest      = 0;
        Integer[] array = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++)
            array[i] = arr[i];

        // ???? ????
        // System.out.println("<<< array: " + Arrays.toString(array));

        // **** declare list of permutations ****
        ArrayList<Integer[]> perms = new ArrayList<>();

        // **** generate permutations ****
        permute(Arrays.asList(array), 0, perms);

        // ???? ????
        // System.out.println("<<< perms:");
        // for (Integer[] a : perms)
        //     System.out.println(Arrays.toString(a));

        // **** check if we cannot gereate a valid time ****
        if (perms.size() == 0) return "";

        // **** iterate though the list of permutations ****
        for (int i = 0; i < perms.size(); i++) {

            // **** for ease of use ****
            Integer[] a = perms.get(i);

            // ???? ????
            // System.out.println("<<< a: " + Arrays.toString(a));

            // **** convert array to integer ****
            int arrVal = 0;
            for (int j = 0; j < 4; j++) {
                arrVal *= 10;
                arrVal += a[j];
            }

            // **** skip values > 2359 ****
            if (arrVal > 2359) continue;

            // **** skip minutes > 59 ****
            if (arrVal % 100 > 59) continue;

            // **** return 00:00 ****
            if (arrVal == 0) return "00:00";

            // ???? ????
            // System.out.println("<<< arrVal: " + arrVal);

            // **** update latest time ****
            latest = latest >= arrVal ? latest : arrVal;
        }
        
        // ???? ????
        // System.out.println("<<< latest: " + latest);

        // **** return "" or HH:MM string ****
        if (latest == 0) return "";
        return String.format("%02d:%02d", latest / 100, latest % 100);
    }


    /**
     * Generate permutations using a list.
     * Recursive function.
     */
    static void permute(List<Integer> lst, int k, List<Integer[]> perms) {

        // **** generate permutations of k numbers ****
        for (int i = k; i < lst.size(); i++) {

            // **** swap elements ****
            Collections.swap(lst, i, k);

            // **** recurse ****
            permute(lst, k + 1, perms);

            // **** restore swap ****
            Collections.swap(lst, k, i);
        }

        // **** add array to perms list ****
        // if (k == lst.size() - 1 && lst.get(0) <= 2) {
        if (k == lst.size() - 1) {

            // **** ****
            Integer[] arr = lst.toArray(new Integer[lst.size()]);

            // **** add array to perms list ****
            perms.add(arr);
        }
    }


    /**
     * Given an array arr of 4 digits, 
     * find the latest 24-hour time that can be made using each digit exactly once.
     * 
     * Runtime: 9 ms, faster than 86.38% of Java online submissions for Largest Time for Given Digits.
     * Memory Usage: 37.1 MB, less than 84.39% of Java online submissions for Largest Time for Given Digits.
     * 
     * 172 / 172 test cases passed.
     * Status: Accepted
     * Runtime: 9 ms
     * Memory Usage: 37.1 MB
     */
    static public String largestTimeFromDigits(int[] arr) {
        
        // **** initialization ****
        int latest = 0;

        // **** declare array of permutations ****
        int[][] perms = new int[24][4];

        // **** generate permutations ****
        // ndx = 0;
        // permute(arr, 0, perms);
        permute(arr, 0, perms, new int[1]);

        // **** iterate though the list of permutations ****
        for (int i = 0; i < 24; i++) {

            // **** for ease of use ****
            int[] a = perms[i];

            // **** convert array to integer ****
            int arrVal = 0;
            for (int j = 0; j < 4; j++) {
                arrVal *= 10;
                arrVal += a[j];
            }

            // **** skip these values ****
            if (arrVal > 2359 || arrVal % 100 > 59) continue;

            // **** return 00:00 ****
            if (arrVal == 0) return "00:00";

            // **** update latest time ****
            latest = latest >= arrVal ? latest : arrVal;
        }

        // **** return "" or HH:MM string ****
        if (latest == 0) return "";
        return String.format("%02d:%02d", latest / 100, latest % 100);
    }


    // **** index into perms int[][] array...
    //      ...could / should made an argument (DONE) ****
    // static int ndx = 0;


    /**
     * Generate permutations using an array.
     * 
     * Runtime: 9 ms, faster than 86.38% of Java online submissions.
     * Memory Usage: 37.2 MB, less than 81.73% of Java online submissions.
     * 
     * 172 / 172 test cases passed.
     * Status: Accepted
     * Runtime: 9 ms
     * Memory Usage: 37.2 MB
     */
    static void permute(int[] arr, int k, int[][] perms, int[] ndex) {

        // **** generate permutations of k numbers ****
        for (int i = k; i < 4; i++) {

            // **** swap elements ****
            swap(arr, i, k);
            // int tmp = arr[i];
            // arr[i]  = arr[k];
            // arr[k]  = tmp;

            // **** recurse ****
            permute(arr, k + 1, perms, ndex);

            // **** restore swap ****
            swap(arr, k, i);
            // tmp = arr[i];
            // arr[i]  = arr[k];
            // arr[k]  = tmp;
        }

        // **** add array to perms list ****
        if (k == 3) {
            // perms[ndx++] = Arrays.copyOf(arr, 4);
            perms[ndex[0]++] = Arrays.copyOf(arr, 4);
        }
    }


    /**
     * Swap elements in array.
     */
    static private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i]  = arr[j];
        arr[j]  = tmp;
    }


    /**
     * Test scaffold.
     * !!! NOT PART OF SOLUTION !!
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read int[] arr ****
        int[] arr = Arrays.stream(br.readLine().trim().split(","))
                        .map(s -> s.trim())
                        .mapToInt(Integer::parseInt)
                        .toArray();

        // **** close buffered reader ****
        br.close();
        

        // // ???? ????
        // System.out.println("main <<< arr: " + Arrays.toString(arr));

        // // ???? ????
        // int[][] perms   = new int[24][4];

        // // ???? generate permutations ????
        // ndx = 0;
        // permute(arr, 0, perms);

        // // ???? ????
        // System.out.println("main <<< perms:");
        // for (int i = 0; i < 24; i++)
        //     System.out.println(Arrays.toString(perms[i]));


        // **** call function of interest and display output ****
        System.out.println("main <<< largestTimeFromDigits0: " + largestTimeFromDigits0(arr));

        // **** call function of interest and display output ****
        System.out.println("main <<<  largestTimeFromDigits: " + largestTimeFromDigits(arr));
    }

}