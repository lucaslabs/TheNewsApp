package com.thenewsapp.playground

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.abs

class MiscPlayground {

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

    // Brute force: O(n^2) time and O(1) space complexities
    private fun twoSum(nums: IntArray, target: Int): IntArray {
        // Edge cases: What if nums is null, or has only 1 num in it?
        for (i in nums.indices) {
            for (j in nums.indices) {
                if (i != j && nums[i] + nums[j] == target) {
                    return intArrayOf(i, j)
                }
            }
        }
        return intArrayOf()
    }

    // Using a hashmap: O(n) time and O(n) space complexities
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
        for (i in str.indices) {
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
        val s = "IX"

        // When
        val output = romanToInt2(s)

        // Then
        assertThat(output, equalTo(9))
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

            when {
                value >= m1Value -> {
                    result += value
                }

                m1Value >= m2Value -> {
                    result -= value
                }

                else -> {
                    throw Exception("Wrong number")
                }
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
            val c = s[i] // current char
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
            prev = c // previous char
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
        var nextStr: String?
        for (i in strs.indices) {
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
                stack.add(')')
            else if (c == '{')
                stack.add('}')
            else if (c == '[')
                stack.add(']')
            else if (stack.isEmpty() || stack.removeLast() != c)
                return false
        }
        return stack.isEmpty()
    }

    /**
     * Remove Duplicates from Sorted Array:
     * Given an integer array nums sorted in non-decreasing order,
     * remove the duplicates in-place such that each unique element appears only once.
     * The relative order of the elements should be kept the same.
     */
    @Test
    fun removeDuplicates() {
        // Given
        val nums = intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4)
        val expectedNums = intArrayOf(0, 1, 2, 3, 4)

        // When
        val k = removeDuplicatesBest(nums)

        // Then
        assertThat(k, equalTo(expectedNums.size))
        for (i in 0 until k) {
            assertThat(nums[i], equalTo(expectedNums[i]))
        }
    }

    private fun removeDuplicates(nums: IntArray): Int {
        var k = 0

        for (i in 0 until nums.size) {
            for (j in (i + 1) until nums.size) {
                if (nums[i] == -101) {
                    nums[i] = nums[j]
                    nums[j] = -101
                } else if (nums[i] == nums[j]) {
                    nums[j] = -101
                }
            }
            if (nums[i] != -101) {
                k++
            }
        }

        return k
    }

    private fun removeDuplicatesBest(nums: IntArray): Int {
        var i = 0
        for (j in 1 until nums.size) {
            if (nums[j] != nums[i])
                nums[++i] = nums[j]
        }
        return i + 1
    }

    /**
     * Remove Element:
     * Given an integer array nums and an integer val,
     * remove all occurrences of val in nums in-place.
     * The relative order of the elements may be changed.
     */
    @Test
    fun removeElement() {
        // Given
        val nums = intArrayOf(0, 1, 2, 2, 3, 0, 4, 2)
        val expectedNums = intArrayOf(0, 1, 3, 0, 4)
        val target = 2

        // When
        val k = removeElementBest(nums, target)

        // Then
        assertThat(k, equalTo(expectedNums.size))
        for (i in 0 until k) {
            assertThat(nums[i], equalTo(expectedNums[i]))
        }
    }

    private fun removeElement(nums: IntArray, target: Int): Int {
        var k = 0
        var shouldSwap = false

        for (i in 0 until nums.size) {

            if (nums[i] == target || nums[i] == -101) {
                shouldSwap = true
            } else k++

            for (j in (i + 1) until nums.size) {
                if (shouldSwap) {
                    if (nums[j] != target && nums[j] != -101) {
                        // Swap
                        nums[i] = nums[j]
                        nums[j] = -101
                        shouldSwap = false
                        k++
                        break
                    }
                }
            }

            if (shouldSwap) {
                nums[i] = -101
                shouldSwap = false
            }
        }

        return k
    }

    private fun removeElementBest(nums: IntArray, target: Int): Int {
        var k = 0
        nums.forEach {
            if (it != target) {
                nums[k++] = it
            }
        }
        return k
    }

    /**
     * Implement strStr():
     * Return the index of the first occurrence of needle in haystack,
     * or -1 if needle is not part of haystack.
     */
    @Test
    fun strStr() {
        // Given
        val haystack = "hello"
        val needle = "ll"
        val expectedOutput = 2

        // When
        val output = strStrBest(haystack, needle)

        // Then
        assertThat(output, equalTo(expectedOutput))
    }

    private fun strStr(haystack: String, needle: String): Int {
        if (needle.isEmpty())
            return 0

        if (needle.length > haystack.length)
            return -1

        var occurrence = -1

        for (i in 0 until haystack.length) {
            for (j in 0 until needle.length) {
                val index = i + j
                if (index < haystack.length) {
                    if (haystack[index] == needle[j]) {
                        if (j == 0) {
                            occurrence = i
                        }
                        if (j == needle.length - 1 && occurrence != -1) {
                            return occurrence
                        }
                    } else {
                        occurrence = -1
                    }
                } else {
                    occurrence = -1
                }
            }
        }

        return occurrence
    }

    private fun strStrBest(haystack: String, needle: String): Int {
        if (needle.isEmpty()) return 0

        for (i in 0 until haystack.length - needle.length + 1) {
            var isMatching = false
            for (j in 0 until needle.length) {
                if (haystack[j + i] == needle[j]) {
                    isMatching = true
                } else {
                    isMatching = false
                    break
                }
            }
            if (isMatching) {
                return i
            }
        }

        return -1
    }

    /**
     * Valid Anagram:
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     */
    @Test
    fun isAnagram() {
        // Given
        val s = "anagram"
        val t = "gramana"

        // When
        val output = isAnagram(s, t)

        // Then
        assertThat(output, equalTo(true))
    }

    // O(n) time and O(26) space complexities
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val map = IntArray(26)

        for (i in 0 until s.length) {
            map[s[i] - 'a']++
            map[t[i] - 'a']--
        }

        for (m in map) {
            if (m != 0)
                return false
        }

        return true
    }

    /**
     * Min Stack:
     * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
     *
     * Implement the MinStack class:
     * 1. MinStack() initializes the stack object.
     * 2. void push(int val) pushes the element val onto the stack.
     * 3. void pop() removes the element on the top of the stack.
     * 4. int top() gets the top element of the stack.
     * 5. int getMin() retrieves the minimum element in the stack.
     */
    @Test
    fun minStack() {
        // Given
        val minStack = MinStack<Int>()
        minStack.push(-2)
        minStack.push(0)
        minStack.push(-3)

        // When
        val output1 = minStack.min() // return -3
        minStack.pop()
        val output2 = minStack.top()    // return 0
        val output3  = minStack.min() // return -2

        // Then
        assertEquals(output1, -3)
        assertEquals(output2, 0)
        assertEquals(output3, -2)
    }

    /**
     * Caesar Cipher:
     * Given a string and integer shift implement a function which shifts each character
     * in that string by shift amount ( cesar cipher) eg. if shift is 1 then a becomes b, c becomes d, z became a etc.
     * Allowed input is string containing only lowercase characters from alphabet abcdefghijklmnopqrstuvwxyz.
     *
     * encodeCaesarCipher("abc", 1) // bcd
     *
     * encodeCaesarCipher("abc", 3) // def
     *
     * encodeCaesarCipher("xyz", 1) // xya
     */
    @Test
    fun `"abc" with shift 1 return "bcd"`() {
        // Given
        val input = "abc"
        val shift = 1
        val expectedOutput = "bcd"

        // When
        val output = encodeCaesarCipher(input, shift)

        // Then
        Assert.assertEquals(output, expectedOutput)
    }

    @Test
    fun `"abcdefghijklmnopqrstuvwxyz" shift 1 returns "bcdefghijklmnopqrstuvwxyza"`() {
        // Given
        val input = "abcdefghijklmnopqrstuvwxyz"
        val shift = 1
        val expectedOutput = "bcdefghijklmnopqrstuvwxyza"

        // When
        val output = encodeCaesarCipher(input, shift)

        // Then
        Assert.assertEquals(output, expectedOutput)
    }

    @Test
    fun `"abcdefghijklmnopqrstuvwxyz" shift 50 returns "yzabcdefghijklmnopqrstuvwx"`() {
        // Given
        val input = "abcdefghijklmnopqrstuvwxyz"
        val shift = 50
        val expectedOutput = "yzabcdefghijklmnopqrstuvwx"

        // When
        val output = encodeCaesarCipher(input, shift)

        // Then
        Assert.assertEquals(output, expectedOutput)
    }

    private fun encodeCaesarCipher(str: String, shift: Int): String {
        val max = 122
        val size = 26

        val diff = shift % size
        if (diff == 0) return str

        return str.map { c ->
            var n = (c.code + diff).toChar()
            if (n.code > max) n -= size
            n
        }.joinToString("")
    }

    /**
     * Majority element:
     * Given an array nums of size n, return the majority element.
     * The majority element is the element that appears more than ⌊n / 2⌋ times.
     * You may assume that the majority element always exists in the array.
     */
    @Test
    fun majorityElement() {
        // Given
        val nums = intArrayOf(3, 2, 3)
        val expected = 3

        // When
        val output = majorityElement(nums)

        // Then
        assertThat(output, equalTo(expected))
    }

    private fun majorityElement(nums: IntArray): Int {
        // Boyer-Moore Algorithm
        var count = 0
        var candidate = 0
        nums.forEach { num ->
            if (count == 0) {
                candidate = num
            }
            count += if (num == candidate) 1 else -1
        }
        return candidate
    }

    /**
     * Given an array of integers [1 4 2 3 5 6 ], apply an alternating sort of the integers
     * and arrange the elements in it so that: o[0] ≤ o[1] ≥ o[2] ≤ o[3] ≥ o[4]...
     * So, rearrange the array such that every second element of the array is greater
     * than its left and right elements
     *
     * For example:
     * 1 4 2 3 5 6 ->  1 ≤ 4 ≥ 2 ≤ 5 ≥ 3 ≤ 6
     * https://www.techiedelight.com/rearrange-the-array-with-alternate-high-and-low-elements
     */

    @Test
    fun rearrangeArray() {
        val input = intArrayOf(1, 4, 2, 3, 5, 6)

        val output = rearrangeArray(input)

        assertThat(input, equalTo(output))
    }

    // Function to rearrange the array such that every second element
    // of the array is greater than its left and right elements
    private fun rearrangeArray(nums: IntArray): IntArray {

        // start from the second element and increment index by 2 for each iteration of the loop
        for (i in 1..nums.size step 2) {

            // if the previous element is greater than the current element, swap the elements
            if (nums[i - 1] > nums[i]) {
                swap(nums, i - 1, i)
            }

            // if the next element is greater than the current element, swap the elements
            if (i + 1 < nums.size && nums[i + 1] > nums[i]) {
                swap(nums, i + 1, i)
            }
        }

        return nums
    }

    private fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]
        nums[i] = nums[j]
        nums[j] = temp
    }

    /**
     * Write a program to help Jane and Alice exchange stamps: it receives two unordered arrays
     * of stamps that Jane and Alice has, and return two unordered arrays of stamps
     * that Jane and Alice will get from the other side.
     * Output order of stamps in each array is not relevant,
     * however the first array is always Jane’s stamps,
     * and the second array is always Alice’s stamps in both input and output.
     */
    @Test
    fun exchangeStamps() {
        // Given
        val janeStamps = arrayOf(1, 7, 3, 1, 7, 4, 5, 1, 7, 1)
        val aliceStamps = arrayOf(2, 3, 3, 2, 4, 3, 2)

        // When
        val output = exchangeStamps(janeStamps, aliceStamps, spare = 2)

        // Then
        val expected = Pair(arrayOf(2, 3), arrayOf(1, 1, 7))
        assertThat(output.first, equalTo(expected.first))
        assertThat(output.second, equalTo(expected.second))
    }

    private fun exchangeStamps(
        firstStamps: Array<Int>,
        secondStamps: Array<Int>,
        spare: Int
    ): Pair<Array<Int>, Array<Int>> {
        // count stamp occurrences
        val firstMap = firstStamps.groupingBy { it }.eachCount()
        val secondMap = secondStamps.groupingBy { it }.eachCount()

        // find stamps to exchange
        val firstExchange = firstMap.filter { (stamp, count) ->
            count > spare && secondMap.getOrDefault(stamp, 0) <= 1
        }.flatMap { (stamp, count) ->
            List(count - spare) { stamp }
        }.toTypedArray()

        val secondExchange = secondMap.filter { (stamp, count) ->
            count > spare && firstMap.getOrDefault(stamp, 0) <= 1
        }.flatMap { (stamp, count) ->
            List(count - spare) { stamp }
        }.toTypedArray()

        return Pair(secondExchange, firstExchange)
    }

    /**
     * The challenge is to find and print the number of occurrences of the letter “a”
     * in the first n letters of an infinite string.
     * s is the string to be repeated.
     * n is the number of characters to consider.
     */
    @Test
    fun countOccurrences() {
        // Given
        val s = "aba"
        val n = 6L

        // When
        val output = countOccurrences(s, n)

        // Then
        assertThat(output, equalTo(4))
    }

    private fun countOccurrences(s: String, n: Long): Long {
        val occurrencesPerRepeat = s.count { it == 'a' }
        val repeatCount = n / s.length
        val remainingChars = (n % s.length).toInt()

        return occurrencesPerRepeat * repeatCount +
                s.substring(0, remainingChars).count { it == 'a' }
    }
}