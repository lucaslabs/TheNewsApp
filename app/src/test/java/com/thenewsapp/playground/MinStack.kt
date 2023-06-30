package com.thenewsapp.playground

class MinStack<T: Comparable<T>> {
    private val stack = mutableListOf<T>()
    private val minStack = mutableListOf<T>()

    fun push(x: T) {
        stack.add(x)
        if(minStack.isEmpty() || x <= min()) {
            minStack.add(x)
        }
    }

    fun pop() {
        if(stack.last() == min()){
            minStack.removeAt(minStack.lastIndex)
        }
        stack.removeAt(stack.lastIndex)
    }

    fun top(): T? = stack.lastOrNull()

    fun min(): T = minStack.last()
}