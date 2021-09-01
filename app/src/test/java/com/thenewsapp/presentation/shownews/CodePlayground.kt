package com.thenewsapp.presentation.shownews

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.math.abs

@RunWith(JUnit4::class)
class CodePlayground {

    /**
     * Two sum: Given an array of integers nums and an integer target,
     * return indices of the two numbers such that they add up to target.
     * You may assume that each input would have exactly one solution,
     * and you may not use the same element twice.
     * You can return the answer in any order.
     */
    @Test
    fun twoSum() {
        // Given
        val nums = intArrayOf(2, 7, 11, 15)
        val target = 9

        // When
        val output = twoSumBest(nums, target)

        // Then
        assertThat(output, equalTo(intArrayOf(0, 1)))
    }

    // Brute force: O(n^2) runtime and O(1) space complexities
    private fun twoSum(nums: IntArray, target: Int): IntArray {
        // Edge cases:
        // What if nums is null, or has only 1 num in it?

        for (i in 0 until nums.size - 1) {
            for (j in i + 1 until nums.size - 1) {
                if (nums[i] + nums[j] == target) {
                    return intArrayOf(i, j)
                }
            }
        }
        return intArrayOf()
    }

    // Using a hashmap: O(n) runtime and O(n) space complexities
    private fun twoSumBest(nums: IntArray, target: Int): IntArray {
        val diffMap = mutableMapOf<Int, Int>()
        nums.forEachIndexed { index, num ->
            val complement = target - num
            diffMap[complement]?.let {
                return intArrayOf(it, index)
            } ?: run { diffMap[num] = index }
        }
        return intArrayOf()
    }

    /**
     * Reverse Integer: Given a signed 32-bit integer x, return x with its digits reversed.
     * If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1],
     * then return 0.
     * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
     */
    @Test
    fun reverseInteger() {
        // Given
        val x = -123

        // When
        val output = reverseIntegerBest(x)

        // Then
        assertThat(output, equalTo(-321))
    }

    private fun reverseInteger(x: Int): Int {
        return try {
            if (x >= 0) {
                x.toString().reversed().toInt()
            } else {
                val y = x.toString().substring(1).reversed().toInt()
                y * -1
            }
        } catch (e: Exception) {
            0
        }
    }

    private fun reverseIntegerBest(x: Int): Int {
        return try {
            var reversedNum = abs(x).toString().reversed().toInt()
            if (x < 0) reversedNum *= -1
            reversedNum
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Given an integer x, return true if x is palindrome integer.
     * An integer is a palindrome when it reads the same backward as forward.
     * For example, 121 is palindrome while 123 is not.
     */
    @Test
    fun isPalindrome() {
        // Given
        val x = 121

        // When
        val output = isPalindromeBest(x)

        // Then
        assertThat(output, equalTo(true))
    }

    private fun isPalindrome(x: Int): Boolean {
        val output = false
        val str = x.toString()
        for (i in 0..str.length - 1) {
            for (j in str.length - (1 + i) downTo 0) {
                if (i >= j) return true
                if (str[i] != str[j]) {
                    return false
                } else break
            }
        }
        return output
    }

    private fun isPalindromeBest(x: Int): Boolean {
        val str = "$x"
        for (i in 0..(str.length / 2)) {
            if (str[i] != str[str.length - (1 + i)]) {
                return false
            }
        }
        return true
    }

    private fun isPalindromeCheat(x: Int): Boolean {
        return "$x" == "$x".reversed()
    }
}