package arraylist

/**
 * A simple implementation of an ArrayList data structure.
 * This list implements common actions such as adding, removing,
 * resizing, searching, and sorting a dynamic list.
 */
class MyArrayList<T: Comparable<T>> {
    private companion object {
        private const val DEFAULT_CAPACITY = 10
    }

    var size = 0
        private set
    private var elements: Array<Any?>

    /**
     * Default constructor to instantiate an array of [DEFAULT_CAPACITY] elements.
     */
    constructor() {
        elements = Array(DEFAULT_CAPACITY) { null }
    }

    /**
     * Constructor to instantiate an array of a specific capacity.
     * @param initialCapacity The initial capacity of the list.
     */
    constructor(initialCapacity: Int) {
        if (initialCapacity < 0)
            throw IllegalArgumentException("Capacity must be non-negative")

        elements = Array(initialCapacity) { null }
    }

    /**
     * Copy constructor to instantiate an main.arraylist from another [MyArrayList].
     */
    constructor(list: MyArrayList<T>) {
        elements = list.elements.copyOf()
        size = list.size
    }

    constructor(vararg items: T) {
        elements = Array(DEFAULT_CAPACITY) { null }
        for (item in items) {
            add(item)
        }
    }

    /**
     * Adds the element to the end of the list.
     * The array may grow in size if the internal array is at capacity.
     * @param data The element to add.
     */
    fun add(data: T) {
        ensureCapacity()
        elements[size++] = data
    }

    /**
     * Adds the element to the end of the list at the specified position.
     * The array may grow in size if the internal array is at capacity.
     * @param index The index to insert data at.
     * @param data The element to add.
     */
    fun addAt(index: Int, data: T) {
        if (index < 0 || index >= size)
            throw ArrayIndexOutOfBoundsException("Cannot add outside of array range.")
        ensureCapacity()
        shuffleRight(index, size)
        elements[index] = data
        size++
    }

    /**
     * Removes the specified element from the list if it is found.
     * @param data The data to remove.
     * @return True if the list contained the specified element.
     */
    fun remove(data: T): Boolean {
        val indexFound = indexOf(data)
        if (indexFound == -1)
            return false
        removeAt(indexFound)
        return true
    }

    /**
     * Removes the element at the specified index from the list.
     * @param index The index to remove data at.
     */
    fun removeAt(index: Int) {
        if (index < 0 || index >= size)
            throw ArrayIndexOutOfBoundsException("Cannot add outside of array range.")
        for (i in 0 until size) {
            if (i == index) {
                shuffleLeft(i, size--)
                break
            }
        }
    }

    /**
     * Removes all elements from the list and resets the size.
     */
    fun removeAll() {
        for (i in 0 until size) {
            elements[i] = null
        }
        size = 0
    }

    /**
     * Retrieves the element found at the specified index.
     * @param index The index to retrieve data from.
     * @return The element found at the specified index.
     */
    fun get(index: Int): T {
        if (index < 0 || index >= size)
            throw ArrayIndexOutOfBoundsException("Cannot get element outside of array range.")
        return elementData(index)
    }

    /**
     * Updates the element at teh specified index.
     * @param index The index to update data at.
     * @param data The new data that will replace the previous data.
     */
    fun set(index: Int, data: T) {
        if (index < 0 || index >= size)
            throw ArrayIndexOutOfBoundsException("Out of range access prohibited")
        elements[index] = data
    }

    /**
     * Checks if the specified element exists in the list.
     * @param o The element to search for.
     * @return True if the element exists in the list.
     */
    fun contains(o: Any): Boolean {
        return indexOf(o) > -1
    }

    /**
     * Returns the index of the first occurrence of the
     * specified element or -1 if it is not found.
     * @param o The element to search for.
     * @return The index that the element was found at.
     */
    fun indexOf(o: Any): Int {
        for (i in 0 until size) {
            if (elements[i] == o)
                return i
        }
        return -1
    }

    /**
     * The number of elements the list can currently store without growing.
     * @return The number of elements the list can currently store without growing.
     */
    fun capacity() = elements.size

    /**
     * Whether the list is empty or not.
     * @return True if the list is empty.
     */
    fun isEmpty() = size == 0

    /**
     * Checks to see if the array must grow in size.
     * If so, a new duplicated larger array is assigned.
     */
    fun ensureCapacity() {
        if (size == elements.size) {
            elements = grow()
        }
    }

    /**
     * Trims the capacity of the array to the current number of elements.
     */
    fun trimToSize() {
        if (size < elements.size && size != 0) {
            elements = elements.copyOf(size)
        }
    }

    /**
     * Reverses the contents of the list in place.
     */
    fun reverseInPlace() {
        var start = 0
        var end = size - 1
        var temp: Any?

        while (start < end) {
            temp = elements[start]
            elements[start] = elements[end]
            elements[end] = temp
            start++
            end--
        }
    }

    fun toList(): List<Any?> {
        val list = ArrayList<Any?>()
        elements.forEach { list.add(it) }
        return list
    }

    /**
     * Helper method to print the contents of the list.
     */
    fun prettyPrint() {
        println("Size: $size")
        println("Capacity: ${elements.size}")

        if (size == 0) {
            println("List is empty.")
        } else {
            print("[")
            for (i in 0 until size) {
                print("${elements[i]}${if (i < size - 1) ", " else ""}")
            }
            println("]")
        }
    }

    /**
     * Creates a new larger array containing the same
     * elements currently in the list.
     * @return A new array double the size of the current array.
     */
    private fun grow(): Array<Any?> {
        val newCapacity = elements.size * 2
        return elements.copyOf(newCapacity)
    }

    /**
     * Returns the element at the specified index.
     * @param index The index to return an element at.
     * @return The element at the specified index.
     */
    @Suppress("UNCHECKED_CAST")
    private fun elementData(index: Int): T {
        return elements[index] as T
    }

    /**
     * Convenience method to shift all elements
     * to the left from start to end.
     */
    private fun shuffleLeft(start: Int, end: Int) {
        for (i in start until end) {
            elements[i] = elements[i + 1]
        }
    }

    /**
     * Convenience method to shift all elements
     * to the right from start to end.
     */
    private fun shuffleRight(start: Int, end: Int) {
        for (i in start until end) {
            elements[i + 1] = elements[i]
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MyArrayList<*>) return false

        if (size != other.size) return false
        if (!elements.contentEquals(other.elements)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + elements.contentHashCode()
        return result
    }
}