import dto.AVLNode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import trees.AVLTree

class AVLTreeTest {

    private lateinit var bst: AVLTree<Int, String>

    private fun confirmHeight(node: AVLNode<Int, String>?): Int {
        if (node == null) {
            return 0
        }
        return node.height
    }

    private fun getBalanceValue(node: AVLNode<Int, String>?): Int {
        if (node == null) {
            return 0
        }
        return confirmHeight(node.right) - confirmHeight(node.left)
    }

    @BeforeEach
    fun setUp() {
        bst = AVLTree()
    }

    @Test
    fun `test insert when tree is empty`() {
        bst.insert(10, "Ten")
        assertEquals("Ten", bst.search(10))

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(1, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals("Ten", nodes[0].value)

    }

    @Test
    fun `test insert and search when tree has elements`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        assertEquals("Ten", bst.search(10))
        assertEquals("Five", bst.search(5))
        assertEquals("Fifteen", bst.search(15))

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Five", nodes[1].value)
        assertEquals("Fifteen", nodes[2].value)
    }

    @Test
    fun `test insert with existing key updates value`() {
        bst.insert(10, "Ten")
        bst.insert(10, "Updated Ten")
        assertEquals("Updated Ten", bst.search(10))

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(1, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals("Updated Ten", nodes[0].value)
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

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(2, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(15, nodes[1].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Fifteen", nodes[1].value)
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

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(3, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Three", nodes[1].value)
        assertEquals("Fifteen", nodes[2].value)
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

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(4, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(7, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals(3, nodes[3].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Seven", nodes[1].value)
        assertEquals("Fifteen", nodes[2].value)
        assertEquals("Three", nodes[3].value)
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

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(2, nodes.size)
        assertEquals(15, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals("Fifteen", nodes[0].value)
        assertEquals("Five", nodes[1].value)
    }

    @Test
    fun `test delete when node doesn't exist`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.delete(20)

        assertNotNull(bst.search(10))
        assertNotNull(bst.search(5))

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(2, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Five", nodes[1].value)

    }

    @Test
    fun `test BFS iterator with multiple nodes`() {
        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        val iterator = bst.treeBFSIterator()

        val nodes = mutableListOf<AVLNode<Int, String>>()
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

        val nodes = mutableListOf<AVLNode<Int, String>>()
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

        val nodes = mutableListOf<AVLNode<Int, String>>()
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

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }

        assertEquals(500, nodes.size)
        while (nodes.isNotEmpty()){
            val tmp = nodes.removeLast()
            if ((getBalanceValue(tmp) < -1) || (getBalanceValue(tmp) > 1)){
                assertEquals(0, getBalanceValue(tmp))
            }
            assertEquals(confirmHeight(tmp), tmp.height)
        }
    }

    @Test
    fun `test insert for tree with 10000 elements`() {
        for (i in 1..10000) {
            bst.insert(i, "Value$i")
        }

        for (i in 1..10000) {
            assertEquals("Value$i", bst.search(i))
        }
        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(10000, nodes.size)
        while (nodes.isNotEmpty()){
            val tmp = nodes.removeLast()
            if ((getBalanceValue(tmp) < -1) || (getBalanceValue(tmp) > 1)){
                assertEquals(0, getBalanceValue(tmp))
            }
            assertEquals(confirmHeight(tmp), tmp.height)
        }
    }


    @Test
    fun `test insert balance`() {

        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")
        bst.insert(4, "Four")
        bst.insert(3, "Three")

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(5, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(4, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals(3, nodes[3].key)
        assertEquals(5, nodes[4].key)

        while (nodes.isNotEmpty()){
            val tmp = nodes.removeLast()
            assertEquals(confirmHeight(tmp), tmp.height)
            if ((getBalanceValue(tmp) < -1) || (getBalanceValue(tmp) > 1)){
                assertEquals(0, getBalanceValue(tmp))
            }
            assertEquals(confirmHeight(tmp), tmp.height)
        }
    }

    @Test
    fun `test actual AVLTree with BFS and DFS iterators`() {
        val bst = AVLTree<Int, String>()

        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<AVLNode<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(3, bfsNodes.size)
        assertEquals(10, bfsNodes[0].key)
        assertEquals(5, bfsNodes[1].key)
        assertEquals(15, bfsNodes[2].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<AVLNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(3, dfsNodes.size)
        assertEquals(5, dfsNodes[0].key)
        assertEquals(10, dfsNodes[1].key)
        assertEquals(15, dfsNodes[2].key)
    }
}
