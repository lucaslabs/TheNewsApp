package com.thenewsapp.presentation.shownews

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ListsPlayground {

    @Test
    fun createTestList() {
        // Given
        val nums = intArrayOf(1, 2, 3)

        // When
        val output = createList(nums)

        // Then
        assertThat(output, notNullValue())
        assertThat(output?.num, equalTo(1))
        assertThat(output?.next, notNullValue())
        assertThat(output?.next?.num, equalTo(2))
        assertThat(output?.next?.next, notNullValue())
        assertThat(output?.next?.next?.num, equalTo(3))
    }

    @Test
    fun sortList() {
        // Given
        val list = createList(intArrayOf(3, 2, 1))

        // When
        val output = sortListByValue(list)

        // Then
        assertThat(output, notNullValue())
        assertThat(output?.num, equalTo(1))
        assertThat(output?.next, notNullValue())
        assertThat(output?.next?.num, equalTo(2))
        assertThat(output?.next?.next, notNullValue())
        assertThat(output?.next?.next?.num, equalTo(3))
    }

    // Bubble sort & swapping values
    private fun sortListByValue(list: ListNode?): ListNode? {
        var current = list

        while (current != null) {
            var aux = current.next

            while (aux != null) {
                if (current.num > aux.num) {
                    // Swap num
                    val temp = current.num
                    current.num = aux.num
                    aux.num = temp
                }
                aux = aux.next
            }
            current = current.next
        }
        return list
    }

    private fun createList(nums: IntArray): ListNode? {
        var aux: ListNode? = ListNode(-1)
        val root = aux

        for (i in 0 until nums.size) {
            val node = ListNode(nums[i])
            aux?.next = node // linking to next node
            aux = aux?.next // moving to next node
        }

        printList(root?.next)
        return root?.next
    }

    private fun printList(root: ListNode?) {
        var aux: ListNode? = root
        val builder = StringBuilder()
        while (aux != null) {
            builder.append(aux.num)
            aux = aux.next
        }
        println("List: $builder")
    }
}