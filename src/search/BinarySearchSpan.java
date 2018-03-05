package search;

public class BinarySearchSpan {
    /* Pre:
        forall i = 1 .. array.length() - 1: array[i - 1] >= array[i]*/
    /* Post: print result &&
        forall i = 0 .. result - 1: array[i] > x &&
        forall i = result .. array.length() - 1: array[i] >= x
        forall i = 0 .. array.length: array[i]` == array[i]
     */
    public static void main(String[] arrayString) {
        int x;

        int arraySize = arrayString.length - 1;
        if (arraySize < 0) {
            System.err.println("Error: too few arguments");
            return;
        }

        int[] array = new int[arraySize];

        try {
            x = Integer.parseInt(arrayString[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: x is'n int");
            return;
        }

        for (int i = 0; i < arraySize; i++) {
            try {
                array[i] = Integer.parseInt(arrayString[i + 1]);
            } catch (NumberFormatException e) {
                System.err.println("Error: argument number " + i + " is'n int");
                return;
            }
        }

        int leftEdge = findLeftAns(x, array, -1, array.length);
        System.out.println(leftEdge + " " + (findRightAns(x, array) - leftEdge));
    }


    /* Pre:  forall i = 0 .. array.length - 1: array[i - 1] >= array[i] */

    /* Post: (0 <= result <= array.length) &&
             (forall i = 0 .. result - 1: array[i] > x) &&
             (forall i = result .. array.length - 1: x >= array[i]) */
    private static int findRightAns(int x, int[] array) {
        int left = -1;
        int right = array.length;
        // Pre
        /* Inv: (((0 <= left < array.length) && (forall i = 0 .. left: array[i] >= x)) || left == -1) &&
                (((0 <= right < array.length) && (forall i = right .. array.length - 1 : x > array[i])) || right == array.length)
                right` - left` < right - left*/
        while (right - left > 1) {
            int middle = (right + left) / 2;
            // Inv
            // Pre

            /* middle >= 0 &&
               middle < array.length &&
               middle < right &&
               middle > left*/
            if (array[middle] >= x) {
                left = middle;
            } else {
                right = middle;
            }
            /* right` - left` < right - left*/

            //Inv
            //Pre
        }
        /* right - left <= 1 &&
            forall i = right .. array.length() - 1: array[i] <= x &&
            forall i = 0 .. left: array[i] > x ->
            Post*/

        return right;
    }




    /* Pre:  (forall i = 1 .. array.length - 1: a[i - 1] >= a[i]) &&
         (right == a.length || a[right] <= x) &&
         (-1 <= left < right <= a.length) */
    /* Post: (left < result <= right) &&
             (forall i = 0 .. result - 1: array[i] > x) &&
             (forall i = result .. array.length() - 1: x >= array[i])*/
    private static int findLeftAns(int x, int[] array, int left, int right) {
        if (right - left == 1){
            /* Pre && right == left + 1 -> Post*/
            return right;
        }
        //Pre
        int middle = (right + left) / 2;
        /*  middle >= 0 &&
            middle < array.length &&
            middle < right &&
            middle > left*/
        if (array[middle] > x) {
            //Pre`
            return findLeftAns(x, array, middle, right);
        } else{
            //Pre`
            return findLeftAns(x, array, left, middle);
        }
        /* left < middle < right && result == result` -> Post */
    }
}
