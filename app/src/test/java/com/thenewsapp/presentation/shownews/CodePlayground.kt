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
     * Two Sum:
     * Given an array of integers nums and an integer target,
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
     * Reverse Integer:
     * Given a signed 32-bit integer x, return x with its digits reversed.
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
     * Palindrome Number:
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

    /**
     * Roman to Integer:
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     */
    @Test
    fun romanToInt() {
        // Given
        val s = "MCMXCIV"

        // When
        val output = romanToInt2(s)

        // Then
        assertThat(output, equalTo(1994))
    }

    private fun romanToInt(s: String): Int {
        var output = 0
        s.forEachIndexed { i, char ->
            when (char) {
                'I' -> {
                    output += 1
                }
                'V' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'I') 3 else 5
                    } catch (e: Exception) {
                        5
                    }
                }
                'X' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'I') 8 else 10
                    } catch (e: Exception) {
                        10
                    }
                }
                'L' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'X') 30 else 50
                    } catch (e: Exception) {
                        50
                    }
                }
                'C' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'X') 80 else 100
                    } catch (e: Exception) {
                        100
                    }
                }
                'D' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'C') 300 else 500
                    } catch (e: Exception) {
                        500
                    }
                }
                'M' -> {
                    output += try {
                        val prev = s[i - 1]
                        if (prev == 'C') 800 else 1000
                    } catch (e: Exception) {
                        1000
                    }
                }
            }

        }
        return output
    }

    private fun romanToIntBest(s: String): Int {
        var m1Value = 0
        var m2Value = 0

        var result = 0

        for (i in s.length - 1 downTo 0) {
            val letter = s[i]

            val value = getValue(letter)

            if (value >= m1Value) {
                result += value
            } else if (m1Value >= m2Value) {
                result -= value
            } else {
                throw Exception("Wrong number")
            }

            m2Value = m1Value
            m1Value = value
        }

        return result
    }

    fun getValue(letter: Char) = when (letter) {
        'I' -> 1
        'V' -> 5
        'X' -> 10
        'L' -> 50
        'C' -> 100
        'D' -> 500
        'M' -> 1000
        else -> throw Exception("Unexpected letter $letter")
    }

    private fun romanToInt2(s: String): Int {
        var output = 0
        var prev: Char? = null
        for (i in s.length - 1 downTo 0) {
            val c = s[i]
            when (c) {
                'I' -> {
                    output += when (prev) {
                        'V', 'X' -> -1
                        else -> 1
                    }
                }
                'V' -> output += 5
                'X' -> {
                    output += when (prev) {
                        'L', 'C' -> -10
                        else -> 10
                    }
                }
                'L' -> output += 50
                'C' -> {
                    output += when (prev) {
                        'D', 'M' -> -100
                        else -> 100
                    }
                }
                'D' -> output += 500
                'M' -> output += 1000
            }
            prev = c
        }
        return output
    }

    /**
     * Longest Common Prefix:
     * Write a function to find the longest common prefix string amongst an array of strings.
     * If there is no common prefix, return an empty string "".
     */
    @Test
    fun longestCommonPrefix() {
        // Given
        val strs = arrayOf("flower", "flower", "flower", "flower")

        // When
        val output = longestCommonPrefixBest(strs)

        // Then
        assertThat(output, equalTo("flower"))
    }

    private fun longestCommonPrefix(strs: Array<String>): String {
        val builder = StringBuilder(strs[0])
        var currentStr: String? = null
        var nextStr: String? = null
        for (i in 0 until strs.size - 1) {
            currentStr = if (currentStr == null) {
                strs[i]
            } else builder.toString()

            nextStr = strs[i + 1]

            builder.clear()

            for (j in currentStr.indices) {
                try {
                    if (currentStr[j] == nextStr[j]) {
                        builder.append(currentStr[j])
                    } else break
                } catch (e: Exception) {
                }
            }
        }
        return builder.toString()
    }

    private fun longestCommonPrefixBest(strs: Array<String>): String {
        val longestPrefix = strs[0]
        var longestCommonPrefix = ""

        for (i in longestPrefix.indices) {
            val singlePrefix = longestPrefix[i]

            for (j in 1 until strs.size) {
                if (i == strs[j].length || strs[j][i] != singlePrefix) {
                    return longestCommonPrefix
                }
            }
            longestCommonPrefix += singlePrefix
        }

        return longestCommonPrefix
    }


    /**
     * Valid Parentheses:
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     */
    @ExperimentalStdlibApi
    @Test
    fun validParentheses() {
        // Given
        // "()[]{}"
        // "{[]}"
        // "(]"
        // "([)]"
        // "(["
        val s = "()[]{}"

        // When
        val output = isValidBest(s)

        //Then
        assertThat(output, equalTo(true))
    }

    @ExperimentalStdlibApi
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        s.forEach { c ->
            if (isOpeningChar(c)) {
                stack.add(c)
            } else {
                // Is closing char
                if (stack.isEmpty()) return false
                else {
                    val prev = stack.removeLast()
                    val openingChar = getOpeningCharByClosingChar(c)
                    if (prev != openingChar) return false
                }
            }
        }
        return stack.isEmpty()
    }

    private fun isOpeningChar(c: Char): Boolean {
        return when (c) {
            '(', '[', '{' -> true
            else -> false
        }
    }

    private fun getOpeningCharByClosingChar(closingChar: Char) = when (closingChar) {
        ')' -> '('
        ']' -> '['
        '}' -> '{'
        else -> throw Exception("Unexpected char $closingChar")
    }

    @ExperimentalStdlibApi
    private fun isValidBest(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        s.forEach { c ->
            if (c == '(')
                stack.add(')');
            else if (c == '{')
                stack.add('}');
            else if (c == '[')
                stack.add(']');
            else if (stack.isEmpty() || stack.removeLast() != c)
                return false
        }
        return stack.isEmpty()
    }
}