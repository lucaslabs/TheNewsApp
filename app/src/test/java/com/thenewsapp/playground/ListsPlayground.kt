package com.thenewsapp.playground

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

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

    /**
     * Merge two sorted linked lists and return it as a sorted list.
     * The list should be made by splicing together the nodes of the first two lists.
     */
    @Test
    fun mergeTwoLists() {
        // Given
        val list1 = createList(intArrayOf(1, 2, 4))
        val list2 = createList(intArrayOf(1, 3, 4))
        val expectedNums = intArrayOf(1, 1, 2, 3, 4, 4)

        // When
        var output = mergeTwoListsBest(list1, list2)

        // Then
        assertThat(output, notNullValue())
        expectedNums.forEachIndexed { index, num ->
            assertThat(num, equalTo(output?.num))
            output = output?.next
        }
    }

    private fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        var list1 = l1
        var list2 = l2
        var aux = ListNode(-1)
        val root = aux

        while (list1 != null && list2 != null) {
            if (list1.num < list2.num) {
                aux.next = list1
                list1 = list1.next
            } else {
                aux.next = list2
                list2 = list2.next
            }
            aux.next?.let { aux = it }
        }

        if (list1 != null) {
            aux.next = list1
        } else {
            aux.next = list2
        }

        return root.next
    }

    // Recursive
    fun mergeTwoListsBest(l1: ListNode?, l2: ListNode?): ListNode? {
        if (l1 == null && l2 == null) {
            return null
        }
        if (l1 == null) {
            return l2
        }
        if (l2 == null) {
            return l1
        }
        if (l1.num < l2.num) {
            l1.next = mergeTwoLists(l1.next, l2)
            return l1
        }
        l2.next = mergeTwoLists(l2.next, l1)
        return l2
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