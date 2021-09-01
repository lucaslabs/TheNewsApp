package com.thenewsapp.presentation.shownews

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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
}