package ch.hslu.ad.a1;


import ch.hslu.ad.a1.animation.SortingAnimation;

public class ArraySorter {
    public static void insertionSort(int[] array){
        long comparisons = 0;
        for(int i = 0; i < array.length; i++){
            int element = array[i];
            int j = i;

            while(j > 0){
                comparisons++;
                if(array[j-1] > element){
                    array[j] = array[j-1];
                    j--;
                } else {
                    break;
                }
            }

            array[j] = element;
        }

        System.out.println(comparisons+" Vergleiche");
    }

    public static void selectionSort(int[] array){
        long comparisons = 0;
        for(int i = 0; i < array.length; i++){
            int element = array[i];
            int minV = i;

            for(int j = i+1; j < array.length; j++){
                comparisons++;
                if(array[j] < array[minV]) minV = j;
            }

            array[i] = array[minV];
            array[minV] = element;
        }

        System.out.println(comparisons+" Vergleiche");
    }


    public static void bubbleSort(int[] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 1; j < array.length-i; j++){
                SortingAnimation.instance().showArray(array, 1, j);
                if(array[j-1] > array[j]){
                    int el = array[j];
                    array[j] = array[j-1];
                    array[j-1] = el;
                }
            }
        }
    }


    public static void quickSort(int[] array, int start, int end){
        if(start<end){
            int pivot = partition(array, start, end);
            quickSort(array, start, pivot);
            quickSort(array, pivot+1, end);
        }
    }

    private static int partition(int[] array, int start, int end) {
        int pivot = array[start];
        int i = start-1;
        int j = end+1;


        while(true){
            do{
                i++;
            }while(array[i] < pivot);

            do{
                j--;
            }while(array[j] > pivot);

            if(i < j){
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }

            if(i >= j){
                return j;
            }
        }
    }
}
