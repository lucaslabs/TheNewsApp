package com.thenewsapp.playground

class SortingAlgo {

    /**
     * Bubble sort is a simple sorting algorithm that repeatedly steps through the list to be sorted,
     * compares each pair of adjacent items and swaps them if they are in the wrong order.
     * The pass through the list is repeated until no swaps are needed, which indicates that the list is sorted.
     *
     * Properties
     * Worst case performance O(n^2)
     * Best case performance O(n)
     * Average case performance O(n^2)
     */
    object BubbleSort {

        fun sort(arr: ArrayList<Int>) {
            val size = arr.size
            for (i in 0 until size) {
                for (j in 1 until size - i) {
                    if (arr[j - 1] > arr[j]) {
                        // Swap elements
                        val idx = j
                        val idy = j - 1
                        val temp = arr[idx]
                        arr[idx] = arr[idy]
                        arr[idy] = temp
                    }
                }
            }
        }
    }

    /**
     * Insertion sort is a simple sorting algorithm that builds the final sorted array (or list) one item at a time by comparisons.
     * It is much less efficient on large lists than more advanced algorithms such as quicksort, heapsort, or merge sort.
     *
     * Properties
     * Worst case performance O(n^2)
     * Best case performance O(n)
     * Average case performance O(n^2)
     */
    object InsertionSort {
         fun sort(arr: ArrayList<Int>) {
             var pos: Int

             // Loop from second element on
             for (i in 1 until arr.size) {
                 // Save current value at i and set position to i
                 val temp = arr[i]
                 pos = i

                 /* Move elements of arr[0..i-1], that are
                    greater than key, to one position ahead
                    of their current position */
                 while (0 < pos && temp < arr[pos - 1]) {
                     val idx = pos
                     val idy = pos - 1
                     val temp2 = arr[idx]
                     arr[idx] = arr[idy]
                     arr[idy] = temp2
                     pos--
                 }
             }
         }
    }

    /**
     * Selection sort is an in-place comparison sorting algorithm.
     *
     * Properties
     * Worst case performance O(n^2)
     * Best case performance O(n)
     * Average case performance O(n^2)
     */
    object SelectionSort {

        fun sort(arr: ArrayList<Int>) {

            // loop from 0 to one before last
            for (i in 0 until arr.size - 1) {
                // set the position of the smallest value to i
                var smallestPos = i

                // loop from past current (i + 1) to end
                for (j in i + 1 until arr.size) {
                    // if the current is smaller then save pos
                    if (arr[j] < arr[smallestPos]) {
                        smallestPos = j
                    }
                }
                // Swap smallest with current value at i
                val idx = i
                val idy = smallestPos
                val temp2 = arr[idx]
                arr[idx] = arr[idy]
                arr[idy] = temp2
            }
        }
    }
}