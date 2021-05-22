import arraylist.MyArrayList
import org.junit.Test
import kotlin.test.assertEquals

class ArrayListTest {
    private val smallList = listOf(4, -45, -90, 180, 7998, 8, 988)
    private val largeList = listOf(4, 5, 10, 15, -99, 0, -190, -1949, 41414, 444, 5155, 1515, 15)

    @Test
    fun emptyListCheckSize() {
        val list = MyArrayList<Int>()
        assertEquals(0, list.size)
    }

    @Test
    fun emptyListCheckCapacity() {
        val list = MyArrayList<Int>()
        assertEquals(10, list.capacity())
    }

    @Test
    fun emptyListInitializeCapacityCheckCapacity() {
        val list = MyArrayList<Int>(200)
        assertEquals(200, list.capacity())
    }

    @Test
    fun copyConstructorCheckSize() {
        val list1 = MyArrayList<Int>()
        largeList.forEach { list1.add(it) }
        val list2 = MyArrayList<Int>(list1)
        assertEquals(largeList.size, list2.size)
    }

    @Test
    fun copyConstructorCheckContents() {
        val list1 = MyArrayList<Int>()
        largeList.forEach { list1.add(it) }
        val list2 = MyArrayList<Int>(list1)
        assertEquals(list1.get(0), list2.get(0))
    }

    @Test
    fun varArgConstructorCheckSize() {
        val list1 = MyArrayList(1, 2, 3, 4, 5)
        assertEquals(5, list1.size)
    }

    @Test
    fun addElementsCheckSize() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        assertEquals(smallList.size, list.size)
    }

    @Test
    fun addElementAtIndexCheckValue() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        list.addAt(0, 4444)
        assertEquals(list.get(0), 4444)
    }

    @Test
    fun removeElementCheckSize() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        list.remove(4)
        assertEquals(smallList.size - 1, list.size)
    }

    @Test
    fun removeElementSuccessfully() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        assertEquals(true, list.remove(smallList[0]))
    }

    @Test
    fun removeElementUnsuccessfully() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        assertEquals(false, list.remove(9999))
    }

    @Test
    fun removeElementAtIndexSuccessfully() {
        val list = MyArrayList<Int>()
        smallList.forEach { list.add(it) }
        list.removeAt(0)
        assertEquals(list.get(0), smallList[1])
    }

    @Test
    fun resizeListCheckCapacity() {
        val list = MyArrayList<Int>()
        largeList.forEach { list.add(it) }
        assert(list.capacity() >= largeList.size)
    }
}