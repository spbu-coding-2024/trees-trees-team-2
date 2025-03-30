import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Assertions.*
import dto.Node
import iterators.TreeBFSIterator
import iterators.TreeDFSIterator
import trees.BinarySearchTree

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

        val iterator = bst.treeBFSIterator()

        val nodes = mutableListOf<Node<Int, String>>()
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

        val nodes = mutableListOf<Node<Int, String>>()
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

        val nodes = mutableListOf<Node<Int, String>>()
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
    fun `test mocking treeBFSIterator with Mockito`() {
        val nodeMock1: Node<Int, String> = mock()
        val nodeMock2: Node<Int, String> = mock()
        val nodeMock3: Node<Int, String> = mock()

        whenever(nodeMock1.key).thenReturn(10)
        whenever(nodeMock1.value).thenReturn("Ten")

        whenever(nodeMock2.key).thenReturn(5)
        whenever(nodeMock2.value).thenReturn("Five")

        whenever(nodeMock3.key).thenReturn(15)
        whenever(nodeMock3.value).thenReturn("Fifteen")

        val bfsIteratorMock = mock<TreeBFSIterator<Int, String>>()
        whenever(bfsIteratorMock.hasNext()).thenReturn(true, true, false)
        whenever(bfsIteratorMock.next()).thenReturn(nodeMock1, nodeMock2, nodeMock3)

        val nodes = mutableListOf<Node<Int, String>>()
        while (bfsIteratorMock.hasNext()) {
            nodes.add(bfsIteratorMock.next())
        }

        assertEquals(3, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(15, nodes[2].key)
    }

    @Test
    fun `test mocking treeDFSIterator with Mockito`() {
        val nodeMock1: Node<Int, String> = mock()
        val nodeMock2: Node<Int, String> = mock()
        val nodeMock3: Node<Int, String> = mock()

        whenever(nodeMock1.key).thenReturn(10)
        whenever(nodeMock1.value).thenReturn("Ten")

        whenever(nodeMock2.key).thenReturn(5)
        whenever(nodeMock2.value).thenReturn("Five")

        whenever(nodeMock3.key).thenReturn(15)
        whenever(nodeMock3.value).thenReturn("Fifteen")

        val dfsIteratorMock = mock<TreeDFSIterator<Int, String>>()
        whenever(dfsIteratorMock.hasNext()).thenReturn(true, true, true, false)
        whenever(dfsIteratorMock.next()).thenReturn(nodeMock1, nodeMock2, nodeMock3)

        val nodes = mutableListOf<Node<Int, String>>()
        while (dfsIteratorMock.hasNext()) {
            nodes.add(dfsIteratorMock.next())
        }

        assertEquals(3, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(15, nodes[2].key)
    }

    @Test
    fun `test actual BinarySearchTree with BFS and DFS iterators`() {
        val bst = BinarySearchTree<Int, String>()

        bst.insert(10, "Ten")
        bst.insert(5, "Five")
        bst.insert(15, "Fifteen")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<Node<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(3, bfsNodes.size)
        assertEquals(10, bfsNodes[0].key)
        assertEquals(5, bfsNodes[1].key)
        assertEquals(15, bfsNodes[2].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<Node<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(3, dfsNodes.size)
        assertEquals(10, dfsNodes[0].key)
        assertEquals(5, dfsNodes[1].key)
        assertEquals(15, dfsNodes[2].key)
    }
}