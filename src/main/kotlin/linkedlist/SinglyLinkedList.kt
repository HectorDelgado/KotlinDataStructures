package linkedlist

import java.lang.IndexOutOfBoundsException

/**
 * A simple implementation of a singly linked list data structure.
 * This list implements common actions such as adding, removing,
 * searching and sorting.
 */
class SinglyLinkedList<T: Comparable<T>> {
    private var head: SLLNode<T>?
    var size: Int = 0
        private set

    /**
     * Default constructor to initialize head to null.
     */
    constructor() {
        head = null
    }

    /**
     * Constructor to initialize head node with data.
     * @param data The data to initialize the head node with.
     */
    constructor(data: T) {
        head = SLLNode(data)
    }

    /**
     * Adds a new node to the end of the list.
     * @param data The data to insert into the list.
     */
    fun append(data: T) {
        if (head == null) {
            head = SLLNode(data)
        } else {
            lastNode()?.let {
                it.next = SLLNode(data)
            }
        }
        size++
    }

    /**
     * Adds a new node before a given index.
     * @param index The index to add data before.
     * @param data The data to insert into the list.
     */
    fun addBefore(index: Int, data: T) {
        checkElementIndex(index)
        if (index == 0) {
            val newNode = SLLNode(data, head)
            head = newNode
        } else {
            nthNode(index - 1)?.let {
                val newNode = SLLNode(data, it.next)
                it.next = newNode
            }
        }
        size++
    }

    /**
     * Adds a new node after a given index.
     * @param index The index to add data after.
     * @param data The data to insert into the list.
     */
    fun addAfter(index: Int, data: T) {
        checkElementIndex(index)
        nthNode(index)?.let {
            val newNode = SLLNode(data, it.next)
            it.next = newNode
            size++
        }
    }

    /**
     * Adds a new node the the beginning of the list.
     * @param data The data to insert into the list.
     */
    fun push(data: T) {
        head = if (head == null) {
            SLLNode(data)
        } else {
            val newNode = SLLNode(data, head)
            newNode
        }
        size++
    }

    /**
     * Removes the first occurrence of a node
     * with the specified data from the list.
     * @param data The data to remove from the list.
     * @return True if the data was found and removed from the list.
     */
    fun remove(data: T): Boolean {
        if (head == null)
            return false
        head?.data.let {
            if (it == data) {
                head = head?.next
                size--
                return true
            }
        }

        var previousNode = head!!
        var currentNode = previousNode.next

        while (currentNode != null) {
            if (currentNode.data == data) {
                unlink(previousNode)
                size--
                return true
            }
            previousNode = currentNode
            currentNode = currentNode.next
        }

        return false
    }

    /**
     * Removes the node at the given index.
     * @param index The index of the node to delete.
     */
    fun removeAt(index: Int) {
        checkElementIndex(index)
        if (index == 0) {
            head = head?.next
            size--
        } else {
            nthNode(index - 1)?.let { previous ->
                unlink(previous)
                size--
            }
        }
    }

    /**
     * Removes all elements from the list.
     */
    fun clear() {
        head = null
        size = 0
    }

    /**
     * Retrieves the data of the node at the given index.
     * @return The data of the node at the given index.
     */
    fun get(index: Int): T? {
        checkElementIndex(index)
        val node = nthNode(index) ?: return null
        return node.data
    }

    /**
     * Updates the data of the node at the given index.
     * @param index The index of the node to update.
     * @param data The new data for the node.
     */
    fun set(index: Int, data: T) {
        checkElementIndex(index)
        nthNode(index)?.let {
            it.data = data
        }
    }

    /**
     * Returns the first element in the list.
     */
    fun first(): T? {
        return head?.data
    }

    /**
     * Returns the last element in the list.
     */
    fun last(): T? {
        return lastNode()?.data
    }

    /**
     * Checks if the data exists in the list.
     * @return true if the data exists in the list.
     */
    fun contains(data: T): Boolean {
        return indexOf(data) >= 0
    }

    /**
     * Returns the index of the first occurrence
     * of the specified data.
     * @param data The data to search for.
     * @return The index the data was found at.
     */
    fun indexOf(data: T): Int {
        var index = 0
        var current = head

        while (current != null) {
            current.data?.let {
                if (it == data)
                    return index
            }
            current = current.next
            index++
        }

        return -1
    }

    fun toList(): List<Any?> {
        val result = ArrayList<Any?>()
        var current = head
        while (current != null) {
            result.add(current.data)
            current = current.next
        }
        return result
    }

    /**
     * Convenience method to print the contents of the list.
     */
    fun print() {
        if (head == null) {
            println("LinkedList is empty.")
        } else {
            var current = head
            println("Size -> $size")
            print("Head -> ")
            while (current != null) {
                if (current.next != null)
                    print("${current.data}, ")
                else
                    print("${current.data}")
                current = current.next
            }
            println()
        }
    }

    /**
     * Gets the first node with the specified data.
     * @param data The data to search for.
     * @return The node containing the specified data.
     */
    private fun getNode(data: T): SLLNode<T>? {
        var current = head
        while (current != null) {
            if (current.data == data)
                return current
            current = current.next
        }
        return null
    }

    /**
     * Gets the node at the given index.
     * @param index The index of the node to retrieve.
     * @return The node at the given index.
     */
    private fun nthNode(index: Int): SLLNode<T>? {
        var current = head
        var currentIndex = 0

        while (current != null) {
            if (index == currentIndex)
                return current
            current = current.next
            currentIndex++
        }

        return null
    }

    /**
     * Gets the last node in the list.
     * @return The last node in the list.
     */
    private fun lastNode(): SLLNode<T>? {
        var current = head

        while (current != null) {
            if (current.next == null)
                return current
            current = current.next
        }
        return null
    }

    /**
     * Unlinks the node after this node from the list.
     */
    private fun unlink(previousNode: SLLNode<T>) {
        previousNode.next?.let { nextNode ->
            previousNode.next = nextNode.next
        }
    }

    /**
     * Checks if the specified index is valid at
     * retrieving elements from the list.
     */
    private fun checkElementIndex(index: Int) {
        if (index < 0 || index >= size)
            throw IndexOutOfBoundsException("Index $index Size $size")
    }
}