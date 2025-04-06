import dto.BNode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import trees.BinarySearchTree
import kotlin.random.Random

class BinarySearchTreeTest {

    private lateinit var bst: BinarySearchTree<Int, String>

    @BeforeEach
    fun setUp() {
        bst = BinarySearchTree()
    }

    @Test
    fun `test insert when tree is empty`() {
        bst.insert(10, "Ten")
        assertEquals("Ten", bst.search(10))
    }

    @Test
    fun `test insert and search when tree has elements`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        assertEquals("Ten", bst.search(10))
        assertEquals("Five", bst.search(5))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test insert with existing key updates value`() {
        bst.insert(10, "Ten")
        bst.insert(10, "Updated Ten")
        assertEquals("Updated Ten", bst.search(10))
    }

    @Test
    fun `test delete when deleting leaf node`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.delete(5)

        assertNull(bst.search(5))
        assertEquals("Ten", bst.search(10))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test delete when deleting node with one child`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")
        bst.delete(5)

        assertNull(bst.search(5))
        assertEquals("Three", bst.search(3))
        assertEquals("Ten", bst.search(10))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test delete when deleting node with two children`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")
        bst.insert(7, "Seven")
        bst.delete(5)

        assertNull(bst.search(5))
        assertEquals("Three", bst.search(3))
        assertEquals("Seven", bst.search(7))
        assertEquals("Ten", bst.search(10))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test delete root node`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.delete(10)

        assertNull(bst.search(10))
        assertEquals("Five", bst.search(5))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test delete when node doesn't exist`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.delete(20)

        assertNotNull(bst.search(10))
        assertNotNull(bst.search(5))
    }

    @Test
    fun `test BFS iterator with multiple nodes`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        bst.treeBFSIterator()
        val iterator = bst.treeBFSIterator()

        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(3, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(15, nodes[2].key)
    }

    @Test
    fun `test BFS iterator with empty tree`() {
        val iterator = bst.treeBFSIterator()
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `test DFS iterator with empty tree`() {
        val iterator = bst.treeDFSIterator()
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `test BFS iterator with one node`() {
        bst.insert(10, "Ten")
        val iterator = bst.treeBFSIterator()

        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(1, nodes.size)
        assertEquals(10, nodes[0].key)
    }

    @Test
    fun `test DFS iterator with one node`() {
        bst.insert(10, "Ten")
        val iterator = bst.treeDFSIterator()

        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(1, nodes.size)
        assertEquals(10, nodes[0].key)
    }

    @Test
    fun `test insert and delete for large tree`() {
        for (i in 1..1000) {
            bst.insert(i, "Value$i")
        }

        for (i in 1..1000) {
            assertEquals("Value$i", bst.search(i))
        }

        for (i in 1..500) {
            bst.delete(i)
        }

        for (i in 1..500) {
            assertNull(bst.search(i))
        }

        for (i in 501..1000) {
            assertEquals("Value$i", bst.search(i))
        }
    }


    @Test
    fun `test actual BinarySearchTree with BFS and DFS iterators`() {
        val bst = BinarySearchTree<Int, String>()

        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<BNode<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(3, bfsNodes.size)
        assertEquals(10, bfsNodes[0].key)
        assertEquals(5, bfsNodes[1].key)
        assertEquals(15, bfsNodes[2].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<BNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(3, dfsNodes.size)
        assertEquals(5, dfsNodes[0].key)
        assertEquals(10, dfsNodes[1].key)
        assertEquals(15, dfsNodes[2].key)
    }

    @Test
    fun `test insert and search with negative values`() {
        bst.insert(-10, "Negative Ten")
        bst.insert(-5, "Negative Five")
        bst.insert(-15, "Negative Fifteen")

        assertEquals("Negative Ten", bst.search(-10))
        assertEquals("Negative Five", bst.search(-5))
        assertEquals("Negative Fifteen", bst.search(-15))
    }

    @Test
    fun `test insert and search with duplicate negative values`() {
        bst.insert(-10, "Negative Ten")
        bst.insert(-10, "Updated Negative Ten")
        assertEquals("Updated Negative Ten", bst.search(-10))
    }

    @Test
    fun `test delete when deleting non-root node with two children`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")
        bst.insert(7, "Seven")
        bst.insert(6, "Six")
        bst.delete(5)

        assertNull(bst.search(5))
        assertEquals("Three", bst.search(3))
        assertEquals("Seven", bst.search(7))
        assertEquals("Six", bst.search(6))
        assertEquals("Ten", bst.search(10))
        assertEquals("Fifteen", bst.search(15))
    }

    @Test
    fun `test delete when deleting non-root node with one child`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(3, "Three")
        bst.insert(7, "Seven")
        bst.delete(5)

        assertNull(bst.search(5))
        assertEquals("Three", bst.search(3))
        assertEquals("Seven", bst.search(7))
        assertEquals("Ten", bst.search(10))
    }

    @Test
    fun `test delete node that has no children (leaf)`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(3, "Three")
        bst.delete(3)

        assertNull(bst.search(3))
        assertEquals("Ten", bst.search(10))
        assertEquals("Five", bst.search(5))
    }

    @Test
    fun `test BFS iterator on larger tree`() {
        for (i in 1..10) {
            bst.insert(i, "Value$i")
        }

        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(10, nodes.size)
        assertEquals(1, nodes[0].key)
        assertEquals(10, nodes[9].key)
    }

    @Test
    fun `test DFS iterator on larger tree`() {
        for (i in 1..10) {
            bst.insert(i, "Value$i")
        }

        val iterator = bst.treeDFSIterator()
        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(10, nodes.size)
        assertEquals(1, nodes[0].key)
        assertEquals(10, nodes[9].key)
    }

    @Test
    fun `test insert and delete with string keys`() {
        val stringBst = BinarySearchTree<String, Int>()

        stringBst.insert("apple", 1)
        stringBst.insert("banana", 2)
        stringBst.insert("cherry", 3)

        assertEquals(1, stringBst.search("apple"))
        assertEquals(2, stringBst.search("banana"))
        assertEquals(3, stringBst.search("cherry"))

        stringBst.delete("banana")

        assertNull(stringBst.search("banana"))
        assertEquals(1, stringBst.search("apple"))
        assertEquals(3, stringBst.search("cherry"))
    }

    @Test
    fun `test insert and delete with large negative keys`() {
        bst.insert(-1000, "Minus One Thousand")
        bst.insert(-500, "Minus Five Hundred")
        bst.insert(-100, "Minus One Hundred")

        assertEquals("Minus One Thousand", bst.search(-1000))
        assertEquals("Minus Five Hundred", bst.search(-500))
        assertEquals("Minus One Hundred", bst.search(-100))

        bst.delete(-500)

        assertNull(bst.search(-500))
        assertEquals("Minus One Thousand", bst.search(-1000))
        assertEquals("Minus One Hundred", bst.search(-100))
    }

    @Test
    fun `test delete all nodes one by one`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")

        bst.delete(3)
        bst.delete(5)
        bst.delete(10)
        bst.delete(15)

        assertNull(bst.search(3))
        assertNull(bst.search(5))
        assertNull(bst.search(10))
        assertNull(bst.search(15))
    }

    @Test
    fun `test search on an empty tree`() {
        assertNull(bst.search(10))
        assertNull(bst.search(5))
    }

    @Test
    fun `test insert and search when tree is initially empty and key is negative`() {
        bst.insert(-10, "Negative Ten")
        assertEquals("Negative Ten", bst.search(-10))
    }

    @Test
    fun `test delete root node when it has no children`() {
        bst.insert(10, "Ten")
        bst.delete(10)

        assertNull(bst.search(10))
    }

    @Test
    fun `test delete root node when it has one child`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.delete(10)

        assertNull(bst.search(10))
        assertEquals("Five", bst.search(5))
    }

    @Test
    fun `test delete root node when it has two children`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")
        bst.insert(7, "Seven")
        bst.delete(10)

        assertNull(bst.search(10))
        assertEquals("Five", bst.search(5))
        assertEquals("Fifteen", bst.search(15))
        assertEquals("Three", bst.search(3))
        assertEquals("Seven", bst.search(7))
    }

    @Test
    fun `test search for a non-existent node in an empty tree`() {
        assertNull(bst.search(10))
    }

    @Test
    fun `test insert multiple elements with varying key values`() {
        bst.insert(50, "Fifty")
        bst.insert(30, "Thirty")
        bst.insert(70, "Seventy")
        bst.insert(20, "Twenty")
        bst.insert(40, "Forty")
        bst.insert(60, "Sixty")
        bst.insert(80, "Eighty")

        assertEquals("Fifty", bst.search(50))
        assertEquals("Thirty", bst.search(30))
        assertEquals("Seventy", bst.search(70))
        assertEquals("Twenty", bst.search(20))
        assertEquals("Forty", bst.search(40))
        assertEquals("Sixty", bst.search(60))
        assertEquals("Eighty", bst.search(80))
    }

    @Test
    fun `test insert and delete when inserting duplicate keys with different values`() {
        bst.insert(10, "Ten")
        bst.insert(10, "Updated Ten")
        bst.insert(10, "Final Ten")

        assertEquals("Final Ten", bst.search(10))

        bst.delete(10)

        assertNull(bst.search(10))
    }

    @Test
    fun `test delete when deleting node with multiple children and subtree exists`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(3, "Three")
        bst.insert(7, "Seven")
        bst.insert(13, "Thirteen")
        bst.insert(17, "Seventeen")

        bst.delete(15)

        assertNull(bst.search(15))
        assertEquals("Ten", bst.search(10))
        assertEquals("Five", bst.search(5))
        assertEquals("Seventeen", bst.search(17))
    }

    @Test
    fun `test insert and delete when tree is initially empty`() {
        bst.insert(10, "Ten")
        assertEquals("Ten", bst.search(10))

        bst.delete(10)
        assertNull(bst.search(10))
    }

    @Test
    fun `test insert, search and delete on a large tree with mixed positive and negative keys`() {
        bst.insert(100, "Hundred")
        bst.insert(-50, "Negative Fifty")
        bst.insert(200, "Two Hundred")
        bst.insert(-200, "Negative Two Hundred")
        bst.insert(0, "Zero")
        bst.insert(150, "One Hundred Fifty")

        assertEquals("Hundred", bst.search(100))
        assertEquals("Negative Fifty", bst.search(-50))
        assertEquals("Two Hundred", bst.search(200))
        assertEquals("Negative Two Hundred", bst.search(-200))
        assertEquals("Zero", bst.search(0))
        assertEquals("One Hundred Fifty", bst.search(150))

        bst.delete(100)
        bst.delete(-50)
        bst.delete(200)
        bst.delete(-200)

        assertNull(bst.search(100))
        assertNull(bst.search(-50))
        assertNull(bst.search(200))
        assertNull(bst.search(-200))
    }

    @Test
    fun `test insert and search with large key values`() {
        bst.insert(Int.MAX_VALUE, "Max Value")
        bst.insert(Int.MIN_VALUE, "Min Value")

        assertEquals("Max Value", bst.search(Int.MAX_VALUE))
        assertEquals("Min Value", bst.search(Int.MIN_VALUE))
    }

    @Test
    fun `test tree traversal order for DFS and BFS after insertion`() {
        bst.insert(30, "Thirty")
        bst.insert(20, "Twenty")
        bst.insert(40, "Forty")
        bst.insert(10, "Ten")
        bst.insert(25, "Twenty-Five")
        bst.insert(35, "Thirty-Five")
        bst.insert(50, "Fifty")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<BNode<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(7, bfsNodes.size)
        assertEquals(30, bfsNodes[0].key)
        assertEquals(20, bfsNodes[1].key)
        assertEquals(40, bfsNodes[2].key)
        assertEquals(10, bfsNodes[3].key)
        assertEquals(25, bfsNodes[4].key)
        assertEquals(35, bfsNodes[5].key)
        assertEquals(50, bfsNodes[6].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<BNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(7, dfsNodes.size)
        assertEquals(10, dfsNodes[0].key)
        assertEquals(20, dfsNodes[1].key)
        assertEquals(25, dfsNodes[2].key)
        assertEquals(30, dfsNodes[3].key)
        assertEquals(35, dfsNodes[4].key)
        assertEquals(40, dfsNodes[5].key)
        assertEquals(50, dfsNodes[6].key)
    }

    @Test
    fun `test DFS iterator for unbalanced tree`() {
        bst.insert(50, "Fifty")
        bst.insert(60, "Sixty")
        bst.insert(70, "Seventy")
        bst.insert(80, "Eighty")
        bst.insert(90, "Ninety")

        val iterator = bst.treeDFSIterator()
        val nodes = mutableListOf<BNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(5, nodes.size)
        assertEquals(50, nodes[0].key)
        assertEquals(60, nodes[1].key)
        assertEquals(70, nodes[2].key)
        assertEquals(80, nodes[3].key)
        assertEquals(90, nodes[4].key)
    }

    @Test
    fun `test BFS and DFS iterators for a balanced tree`() {
        bst.insert(50, "Fifty")
        bst.insert(30, "Thirty")
        bst.insert(70, "Seventy")
        bst.insert(20, "Twenty")
        bst.insert(40, "Forty")
        bst.insert(60, "Sixty")
        bst.insert(80, "Eighty")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<BNode<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(7, bfsNodes.size)
        assertEquals(50, bfsNodes[0].key)
        assertEquals(30, bfsNodes[1].key)
        assertEquals(70, bfsNodes[2].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<BNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(7, dfsNodes.size)
        assertEquals(20, dfsNodes[0].key)
        assertEquals(30, dfsNodes[1].key)
        assertEquals(40, dfsNodes[2].key)
    }

    @Test
    fun `test insert random values`() {
        val randomValues = List(100) { Random.nextInt(-1000, 1000) }
        val expectedMap = mutableMapOf<Int, String>()

        randomValues.forEach {
            val value = "Value$it"
            bst.insert(it, value)
            expectedMap[it] = value
        }

        randomValues.forEach {
            assertEquals("Value$it", bst.search(it))
        }
    }

    @Test
    fun `test delete random values`() {
        val randomValues = List(100) { Random.nextInt(-1000, 1000) }

        randomValues.forEach {
            bst.insert(it, "Value$it")
        }

        randomValues.shuffled().forEach {
            bst.delete(it)
            assertNull(bst.search(it))
        }

        randomValues.forEach {
            assertNull(bst.search(it))
        }
    }

    @Test
    fun `test insert random values and update value with same key`() {
        val randomValues = List(100) { Random.nextInt(-1000, 1000) }

        randomValues.forEach {
            bst.insert(it, "Value$it")
        }

        randomValues.forEach {
            assertEquals("Value$it", bst.search(it))
        }

        randomValues.shuffled().take(50).forEach {
            bst.insert(it, "Value$it") // Обновляем значение для половины значений
            assertEquals("Value$it", bst.search(it))
        }

        randomValues.shuffled().take(50).forEach {
            assertEquals("Value$it", bst.search(it)) // Проверяем обновленные значения
        }

        randomValues.filterNot { it in randomValues.shuffled().take(50) }.forEach {
            assertEquals("Value$it", bst.search(it)) // Проверяем не обновленные значения
        }
    }

    @Test
    fun `test random insert search and delete operations`() {
        val randomValues = List(100) { Random.nextInt(-1000, 1000) }
        val expectedMap = mutableMapOf<Int, String>()

        randomValues.forEach {
            val value = "Value$it"
            bst.insert(it, value)
            expectedMap[it] = value
        }

        randomValues.forEach {
            assertEquals("Value$it", bst.search(it))
        }

        val valuesToDelete = randomValues.shuffled().take(50)
        valuesToDelete.forEach {
            bst.delete(it)
            assertNull(bst.search(it))
        }

        randomValues.forEach {
            if (it in valuesToDelete) {
                assertNull(bst.search(it))
            } else {
                assertEquals("Value$it", bst.search(it))
            }
        }
    }

    @Test
    fun `test insert values in descending order`() {
        val descendingValues = (100 downTo 1).toList()
        descendingValues.forEach {
            bst.insert(it, "Value$it")
        }

        descendingValues.forEach {
            assertEquals("Value$it", bst.search(it))
        }
    }

    @Test
    fun `test insert with same key updates value`() {
        val key = 100
        bst.insert(key, "Value1")
        bst.insert(key, "Value2")
        bst.insert(key, "Value3")

        assertEquals("Value3", bst.search(key))
    }
}