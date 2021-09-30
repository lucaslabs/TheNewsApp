package com.thenewsapp.playground

class MinStack {
    private val stack = mutableListOf<Int>()
    private val minStack = mutableListOf<Int>()

    fun push(x: Int) {
        stack.add(x)
        if (minStack.isEmpty() || x <= getMin())
            minStack.add(x)
    }

    fun pop() {
        if (stack.last() == getMin())
            minStack.removeAt(minStack.lastIndex)
        stack.removeAt(stack.lastIndex)
    }

    fun top() = stack.last()

    fun getMin() = minStack.last()
}