package com.example.sortingandsearching

// --- Ham moi de doc dau vao cua ban ---
/**
 * Chuyen mot chuoi nhu "8, 3, 5, 1" thanh mot mang IntArray
 */
fun parseInputArray(input: String): IntArray {
    if (input.isBlank()) return intArrayOf()
    try {
        return input.split(',')
            .map { it.trim() } // xoa khoang trang
            .mapNotNull { it.toIntOrNull() } // chuyen sang so, bo qua loi
            .toIntArray()
    } catch (e: Exception) {
        throw IllegalArgumentException("Dinh dang mang khong hop le.")
    }
}

// --- Cac ham sap xep ---

//  Insertion Sort
fun insertionSort(arr: IntArray) {
    for (i in 1 until arr.size) {
        val key = arr[i]
        var j = i - 1
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = key
    }
}

//  Merge Sort
fun mergeSort(arr: IntArray) {
    val n = arr.size
    if (n < 2) return

    val mid = n / 2
    val leftArray = arr.copyOfRange(0, mid)
    val rightArray = arr.copyOfRange(mid, n)

    mergeSort(leftArray)
    mergeSort(rightArray)
    merge(leftArray, rightArray, arr)
}

fun merge(leftArray: IntArray, rightArray: IntArray, arr: IntArray) {
    var i = 0
    var j = 0
    var k = 0

    while (i < leftArray.size && j < rightArray.size) {
        if (leftArray[i] <= rightArray[j]) {
            arr[k] = leftArray[i]
            i++
        } else {
            arr[k] = rightArray[j]
            j++
        }
        k++
    }
    while (i < leftArray.size) {
        arr[k] = leftArray[i]
        i++
        k++
    }
    while (j < rightArray.size) {
        arr[k] = rightArray[j]
        j++
        k++
    }
}

// Project 29: Quick Sort
fun quickSort(arr: IntArray, start: Int, end: Int) {
    if (start < end) {
        val pivotIndex = partition(arr, start, end)
        quickSort(arr, start, pivotIndex - 1)
        quickSort(arr, pivotIndex + 1, end)
    }
}

fun partition(arr: IntArray, start: Int, end: Int): Int {
    val pivot = arr[end]
    var i = start - 1
    for (j in start until end) {
        if (arr[j] < pivot) {
            i++
            swap(arr, i, j)
        }
    }
    swap(arr, i + 1, end)
    return i + 1
}

fun swap(arr: IntArray, i: Int, j: Int) {
    val temp = arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}