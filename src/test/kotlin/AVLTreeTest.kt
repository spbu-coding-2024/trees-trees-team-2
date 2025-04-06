import dto.AVLNode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import trees.AVLTree
import kotlin.math.max
import java.util.Random

class AVLTreeTest {

    private lateinit var bst: AVLTree<Int, String>

    fun Random.nextInt(range: IntRange): Int {
        return range.start + nextInt(range.last - range.start + 1)
    }

    private fun confirmHeight(node: AVLNode<Int, String>?): Int {
        if (node == null) {
            return 0
        }
        return node.height
    }

    private fun checkHeight(node: AVLNode<Int, String>?): Int{
        if (node != null) {
            return 1 + max(confirmHeight(node.left), confirmHeight(node.right))
        }
        return 0
    }

    private fun getBalanceValue(node: AVLNode<Int, String>?): Int {
        if (node == null) {
            return 0
        }
        return confirmHeight(node.right) - confirmHeight(node.left)
    }

    /* Функция проверки дерева на соответствие бинарному дереву */
    private fun checkBST(bst: AVLTree<Int, String>): Boolean {
        val iterator = bst.treeDFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        var prev = nodes.removeFirst()
        while (nodes.isNotEmpty()){
            val tmp = nodes.removeFirst()
            if (prev.key >= tmp.key) {
                return false
            }
            prev = tmp
        }
        return true
    }

    /* Функция проверяет свойства дерева на соответствие АВЛ-дереву */
    private fun checkAVL(bst: AVLTree<Int, String>, len: Int) {
        val iterator = bst.treeBFSIterator()
        /* Проходим поиском в ширину дерево*/
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        /* Проверяем размер дерева */
        assertEquals(len, nodes.size)

        /* Проверяем сбалансирован ли граф сверху вниз */
        while (nodes.isNotEmpty()){
            val tmp = nodes.removeLast()
            assertEquals(checkHeight(tmp), tmp.height)
            val balanceF = getBalanceValue(tmp)
            if ((balanceF < -1) || (balanceF > 1)){
                assertEquals(0, balanceF)
            }
        }
    }

    @BeforeEach
    fun setUp() {
        bst = AVLTree()
    }


    /* Немного тестов для итератора и вставки */

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
        assertEquals("Ten", nodes[0].value)
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
        assertEquals("Ten", nodes[0].value)
    }

    @Test
    fun `test small AVLTree with BFS and DFS iterators`() {
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
        assertEquals("Ten", bfsNodes[0].value)
        assertEquals("Five", bfsNodes[1].value)
        assertEquals("Fifteen", bfsNodes[2].value)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<AVLNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(3, dfsNodes.size)
        assertEquals(5, dfsNodes[0].key)
        assertEquals(10, dfsNodes[1].key)
        assertEquals(15, dfsNodes[2].key)
        assertEquals("Five", dfsNodes[0].value)
        assertEquals("Ten", dfsNodes[1].value)
        assertEquals("Fifteen", dfsNodes[2].value)
    }

    @Test
    fun `test BFS iterator with 7 nodes`() {
        val bst = AVLTree<Int, String>()

        bst.insert(4, "Four")
        bst.insert(6, "Six")
        bst.insert(2, "Two")
        bst.insert(7, "Seven")
        bst.insert(3, "Three")
        bst.insert(1, "One")
        bst.insert(5, "Five")

        val bfsIterator = bst.treeBFSIterator()
        val bfsNodes = mutableListOf<AVLNode<Int, String>>()
        while (bfsIterator.hasNext()) {
            bfsNodes.add(bfsIterator.next())
        }

        assertEquals(7, bfsNodes.size)
        assertEquals(4, bfsNodes[0].key)
        assertEquals(2, bfsNodes[1].key)
        assertEquals(6, bfsNodes[2].key)
        assertEquals(1, bfsNodes[3].key)
        assertEquals(3, bfsNodes[4].key)
        assertEquals(5, bfsNodes[5].key)
        assertEquals(7, bfsNodes[6].key)

        val dfsIterator = bst.treeDFSIterator()
        val dfsNodes = mutableListOf<AVLNode<Int, String>>()
        while (dfsIterator.hasNext()) {
            dfsNodes.add(dfsIterator.next())
        }

        assertEquals(7, dfsNodes.size)
        assertEquals(1, dfsNodes[0].key)
        assertEquals(2, dfsNodes[1].key)
        assertEquals(3, dfsNodes[2].key)
        assertEquals(4, dfsNodes[3].key)
        assertEquals(5, dfsNodes[4].key)
        assertEquals(6, dfsNodes[5].key)
        assertEquals(7, dfsNodes[6].key)
    }

    /* Проверка базовой функциональности дерева с нулевым влиянием балансировки*/

    @Test
    fun `test insert and search with one node tree`() {
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
    fun `test insert and search with existing key updates value`() {
        bst.insert(10, "Ten")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(1, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals("Ten", nodes[0].value)


        bst.insert(10, "Updated Ten")
        assertEquals("Updated Ten", bst.search(10))

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()) {
            nodes.removeLast()
        }
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

    /* Проверка балансировки */

    @Test
    fun `test insert with left rotation`() {

        bst.insert(1, "One")
        bst.insert(2, "Two")
        bst.insert(3, "Three")

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(2, nodes[0].key)
        assertEquals(1, nodes[1].key)
        assertEquals(3, nodes[2].key)
        assertEquals("Two", nodes[0].value)
        assertEquals("One", nodes[1].value)
        assertEquals("Three", nodes[2].value)
    }

    @Test
    fun `test insert with right rotation`() {

        bst.insert(3, "Three")
        bst.insert(2, "Two")
        bst.insert(1, "One")

        //test a number of elements in tree
        val iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(2, nodes[0].key)
        assertEquals(1, nodes[1].key)
        assertEquals(3, nodes[2].key)
        assertEquals("Two", nodes[0].value)
        assertEquals("One", nodes[1].value)
        assertEquals("Three", nodes[2].value)
    }

    @Test
    fun `test insert with left-right rotation`() {

        bst.insert(10, "Ten")
        bst.insert(6, "Six")
        bst.insert(15, "Fifteen")
        bst.insert(5, "Five")
        bst.insert(7, "Seven")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(5, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(6, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals(5, nodes[3].key)
        assertEquals(7, nodes[4].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Six", nodes[1].value)
        assertEquals("Fifteen", nodes[2].value)
        assertEquals("Five", nodes[3].value)
        assertEquals("Seven", nodes[4].value)

        bst.insert(4, "Four")

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(6, nodes.size)
        assertEquals(6, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(4, nodes[3].key)
        assertEquals(7, nodes[4].key)
        assertEquals(15, nodes[5].key)
        assertEquals("Six", nodes[0].value)
        assertEquals("Five", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("Four", nodes[3].value)
        assertEquals("Seven", nodes[4].value)
        assertEquals("Fifteen", nodes[5].value)
    }

    @Test
    fun `test insert with right-left rotation`() {

        bst.insert(5, "Five")
        bst.insert(1, "One")
        bst.insert(10, "Ten")
        bst.insert(7, "Seven")
        bst.insert(11, "Eleven")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(5, nodes.size)
        assertEquals(5, nodes[0].key)
        assertEquals(1, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(7, nodes[3].key)
        assertEquals(11, nodes[4].key)
        assertEquals("Five", nodes[0].value)
        assertEquals("One", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("Seven", nodes[3].value)
        assertEquals("Eleven", nodes[4].value)

        bst.insert(6, "Six")

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(6, nodes.size)
        assertEquals(7, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(1, nodes[3].key)
        assertEquals(6, nodes[4].key)
        assertEquals(11, nodes[5].key)
        assertEquals("Seven", nodes[0].value)
        assertEquals("Five", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("One", nodes[3].value)
        assertEquals("Six", nodes[4].value)
        assertEquals("Eleven", nodes[5].value)
    }

    @Test
    fun `test delete with left rotation`() {

        bst.insert(3, "Three")
        bst.insert(2, "Two")
        bst.insert(4, "Four")
        bst.insert(1, "One")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(4, nodes.size)
        assertEquals(3, nodes[0].key)
        assertEquals(2, nodes[1].key)
        assertEquals(4, nodes[2].key)
        assertEquals(1, nodes[3].key)
        assertEquals("Three", nodes[0].value)
        assertEquals("Two", nodes[1].value)
        assertEquals("Four", nodes[2].value)
        assertEquals("One", nodes[3].value)

        bst.delete(4)

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(2, nodes[0].key)
        assertEquals(1, nodes[1].key)
        assertEquals(3, nodes[2].key)
        assertEquals("Two", nodes[0].value)
        assertEquals("One", nodes[1].value)
        assertEquals("Three", nodes[2].value)
    }

    @Test
    fun `test delete with right rotation`() {

        bst.insert(3, "Three")
        bst.insert(2, "Two")
        bst.insert(4, "Four")
        bst.insert(5, "Five")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(4, nodes.size)
        assertEquals(3, nodes[0].key)
        assertEquals(2, nodes[1].key)
        assertEquals(4, nodes[2].key)
        assertEquals(5, nodes[3].key)
        assertEquals("Three", nodes[0].value)
        assertEquals("Two", nodes[1].value)
        assertEquals("Four", nodes[2].value)
        assertEquals("Five", nodes[3].value)

        bst.delete(2)

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(3, nodes.size)
        assertEquals(4, nodes[0].key)
        assertEquals(3, nodes[1].key)
        assertEquals(5, nodes[2].key)
        assertEquals("Four", nodes[0].value)
        assertEquals("Three", nodes[1].value)
        assertEquals("Five", nodes[2].value)

    }

    @Test
    fun `test delete with left-right rotation`() {

        bst.insert(10, "Ten")
        bst.insert(6, "Six")
        bst.insert(15, "Fifteen")
        bst.insert(5, "Five")
        bst.insert(7, "Seven")
        bst.insert(16, "Sixteen")
        bst.insert(4, "Four")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(7, nodes.size)
        assertEquals(10, nodes[0].key)
        assertEquals(6, nodes[1].key)
        assertEquals(15, nodes[2].key)
        assertEquals(5, nodes[3].key)
        assertEquals(7, nodes[4].key)
        assertEquals(16, nodes[5].key)
        assertEquals(4, nodes[6].key)
        assertEquals("Ten", nodes[0].value)
        assertEquals("Six", nodes[1].value)
        assertEquals("Fifteen", nodes[2].value)
        assertEquals("Five", nodes[3].value)
        assertEquals("Seven", nodes[4].value)
        assertEquals("Sixteen", nodes[5].value)
        assertEquals("Four", nodes[6].value)

        bst.delete(16)

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(6, nodes.size)
        assertEquals(6, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(4, nodes[3].key)
        assertEquals(7, nodes[4].key)
        assertEquals(15, nodes[5].key)
        assertEquals("Six", nodes[0].value)
        assertEquals("Five", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("Four", nodes[3].value)
        assertEquals("Seven", nodes[4].value)
        assertEquals("Fifteen", nodes[5].value)

    }

    @Test
    fun `test delete with right-left rotation`() {

        bst.insert(5, "Five")
        bst.insert(2, "Two")
        bst.insert(10, "Ten")
        bst.insert(7, "Seven")
        bst.insert(11, "Eleven")
        bst.insert(1, "One")
        bst.insert(6, "Six")

        //test a number of elements in tree
        var iterator = bst.treeBFSIterator()
        val nodes = mutableListOf<AVLNode<Int, String>>()
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(7, nodes.size)
        assertEquals(5, nodes[0].key)
        assertEquals(2, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(1, nodes[3].key)
        assertEquals(7, nodes[4].key)
        assertEquals(11, nodes[5].key)
        assertEquals(6, nodes[6].key)
        assertEquals("Five", nodes[0].value)
        assertEquals("Two", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("One", nodes[3].value)
        assertEquals("Seven", nodes[4].value)
        assertEquals("Eleven", nodes[5].value)
        assertEquals("Six", nodes[6].value)

        bst.delete(1)

        //test a number of elements in tree
        iterator = bst.treeBFSIterator()
        while (nodes.isNotEmpty()){
            nodes.removeLast()
        }
        while (iterator.hasNext()) {
            nodes.add(iterator.next())
        }
        assertEquals(6, nodes.size)
        assertEquals(7, nodes[0].key)
        assertEquals(5, nodes[1].key)
        assertEquals(10, nodes[2].key)
        assertEquals(2, nodes[3].key)
        assertEquals(6, nodes[4].key)
        assertEquals(11, nodes[5].key)
        assertEquals("Seven", nodes[0].value)
        assertEquals("Five", nodes[1].value)
        assertEquals("Ten", nodes[2].value)
        assertEquals("Two", nodes[3].value)
        assertEquals("Six", nodes[4].value)
        assertEquals("Eleven", nodes[5].value)
    }


    /* Проверка больших деревьев и property-based тесты */

    @Test
    fun `test insert from 1 to 10000`() {
        for (i in 1..10000) {
            bst.insert(i, "Value$i")
        }

        for (i in 1..10000) {
            assertEquals("Value$i", bst.search(i))
        }

        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 10000)
    }

    @Test
    fun `test insert from 1 to 10000 in another order`() {
        for (i in 1..2500) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 2500)

        for (i in 7501..10000) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 5000)

        for (i in 2501..5000) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 7500)

        for (i in 5001..7500) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 10000)

        for (i in 1..10000) {
            assertEquals("Value$i", bst.search(i))
        }
    }

    @Test
    fun `test insert and delete for big tree`() {
        for (i in 1..1000) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 1000)

        for (i in 1..1000) {
            assertEquals("Value$i", bst.search(i))
        }

        for (i in 1..500) {
            bst.delete(i)
            /* Проверка выполнения свойств авл-дерева */
            checkAVL(bst, 1000-i)
        }

        for (i in 1..500) {
            assertNull(bst.search(i))
        }

        for (i in 501..1000) {
            assertEquals("Value$i", bst.search(i))
        }
    }

    @Test
    fun `test insert and delete for large tree`() {
        for (i in 1..1250) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 1250)


        for (i in 7501..10000) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 3750)


        for (i in 1251..2500) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 5000)


        for (i in 5001..7500) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 7500)


        for (i in 2501..5000) {
            bst.insert(i, "Value$i")
        }
        /* Проверка выполнения свойств авл-дерева */
        assertTrue(checkBST(bst))
        checkAVL(bst, 10000)


        for (i in 1..10000) {
            assertEquals("Value$i", bst.search(i))
        }

        for (i in 100..500) {
            bst.delete(i)
            /* Проверка выполнения свойств авл-дерева */
            checkAVL(bst, 10000 + 99 - i)
        }
        assertTrue(checkBST(bst))

        for (i in 100..500) {
            assertNull(bst.search(i))
        }

        for (i in 1..99) {
            assertEquals("Value$i", bst.search(i))
        }
        for (i in 501..10000) {
            assertEquals("Value$i", bst.search(i))
        }

    }

    @Test
    fun `test with random tree1`() {
        val random = Random()
        val addedNodes = mutableListOf<Int>()
        for (i in 1..250) {
            val tmp = random.nextInt(1..10000)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size)
        }

        for (i in 0..<addedNodes.size) {
            val tmp = addedNodes[i]
            assertEquals("Value$tmp", bst.search(tmp))
        }

        var len = 0
        for (i in 1..100) {
            val tmp = random.nextInt(1..10000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }
    }

    @Test
    fun `test with random tree2`() {
        val random = Random()
        val addedNodes = mutableListOf<Int>()
        for (i in 1..500) {
            val tmp = random.nextInt(2000..10000)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size)
        }

        for (i in 0..<addedNodes.size) {
            val tmp = addedNodes[i]
            assertEquals("Value$tmp", bst.search(tmp))
        }
        var len = 0
        for (i in 1..200) {
            val tmp = random.nextInt(1..8000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }

    }

    @Test
    fun `test with random tree3`() {
        val random = Random()
        val addedNodes = mutableListOf<Int>()
        for (i in 1..500) {
            val tmp = random.nextInt(1000..10000)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size)
        }


        for (i in 0..<addedNodes.size) {
            val tmp = addedNodes[i]
            assertEquals("Value$tmp", bst.search(tmp))
        }

        var len = 0
        for (i in 1..200) {
            val tmp = random.nextInt(1..5000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }


        for (i in 1..200) {
            val tmp = random.nextInt(10001..13000)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }

    }

    @Test
    fun `test with random tree4`() {
        val random = Random()
        val addedNodes = mutableListOf<Int>()
        for (i in 1..500) {
            val tmp = random.nextInt(5000..10000)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size)
        }


        for (i in 0..<addedNodes.size) {
            val tmp = addedNodes[i]
            assertEquals("Value$tmp", bst.search(tmp))
        }

        var len = 0
        for (i in 1..200) {
            val tmp = random.nextInt(1..15000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }


        for (i in 1..200) {
            val tmp = random.nextInt(10001..12500)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }

        for (i in 1..100) {
            val tmp = random.nextInt(8000..15000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }

    }

    @Test
    fun `test with random tree5`() {
        val random = Random()
        val addedNodes = mutableListOf<Int>()
        for (i in 1..500) {
            val tmp = random.nextInt(1000..7500)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size)
        }


        for (i in 0..<addedNodes.size) {
            val tmp = addedNodes[i]
            assertEquals("Value$tmp", bst.search(tmp))
        }

        var len = 0
        for (i in 1..400) {
            val tmp = random.nextInt(1..10000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }


        for (i in 1..200) {
            val tmp = random.nextInt(8000..12500)
            bst.insert(tmp, "Value$tmp")
            addedNodes.add(tmp)
            var cnt = 0
            while (cnt < addedNodes.size) {
                if (cnt == addedNodes.size - 1) {
                    break
                }
                if (addedNodes[cnt] == tmp) {
                    addedNodes.removeLast()
                    break
                }
                cnt++
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }

        for (i in 1..300) {
            val tmp = random.nextInt(5000..10000)
            if (bst.delete(tmp)) {
                len++
                assertNull(bst.search(tmp))
            }
            /* Проверка выполнения свойств авл-дерева */
            assertTrue(checkBST(bst))
            checkAVL(bst, addedNodes.size - len)
        }
    }
}
