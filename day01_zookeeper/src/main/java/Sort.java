import java.util.Arrays;


public class Sort {
    public static void main(String[] args) {
        int[] arr = {8,9,5,4,7,3,1,10,2,6,11,12};
        sort(arr);
        //quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 希尔排序,根据间隔数进行分组
     */
    public static int[] sort(int[] arr) {
        //求出最大间隔数x
        int x = 1;
        while (x <= arr.length / 3){
            x = x * 3 + 1;
        }

        for (int h = x; h > 0; h = (h -1)/3){

            for (int i = h; i < arr.length; i++) {
                for (int j = i; j > h - 1; j -= h) {
                    if (arr[j] < arr[j - h]) {
                        int temp = arr[j];
                        arr[j] = arr[j - h];
                        arr[j - h] = temp;
                    }
                }
            }
        }
        return arr;
    }



    /**
     * 快速排序
     */

    public static int[] quickSort(int[] arr,int L,int R){
        int i = L;
        int j = R;
        int pivot = arr[(L + R )/2];

        while (i <= j) {


            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }

            if (i <=j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;

            }
        }

        if (L<j) {
            quickSort(arr, L, j);
        }
        if (i<R) {
            quickSort(arr, i, R);
        }

        return arr;
    }
}