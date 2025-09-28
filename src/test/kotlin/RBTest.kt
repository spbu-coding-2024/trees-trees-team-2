import dto.AVLNode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import trees.RedBlackTree
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RedBlackTreeTest {
    private fun generateRandomTree(randomValue: Long = System.currentTimeMillis(), size:Long = 50): RedBlackTree<Int, String> {
        val tree = RedBlackTree<Int, String>()
        val random = Random(randomValue)
        var nodesAdded = 0
        while (nodesAdded < size) {
            val key = random.nextInt(1, 50000)
            if (tree.search(key) == null) {
                tree.insert(key, key.toString())
                nodesAdded++
            }
        }
        return tree
    }

    private fun checkBlackHeightProperty(RBNode: RBNode<Int, String>?): Pair<Boolean, Int> {
        if (RBNode == null) {
            return Pair(true, 0)
        }
        val left = checkBlackHeightProperty(RBNode.left)
        val right = checkBlackHeightProperty(RBNode.right)
        if (!left.first || !right.first)
            return Pair(false, 0)
        if (left.second != right.second)
            return Pair(false, 0)
        val currentHeight = if (RBNode.color == Color.BLACK) {
            left.second + 1
        } else {
            left.second
        }
        return Pair(true, currentHeight)
    }

    private fun HeightValid(root: RBNode<Int, String>?): Boolean {
        return checkBlackHeightProperty(root).first
    }

    private fun checkProperty(RBNode: RBNode<Int, String>?, minimum: Int, maximum: Int, root: RBNode<Int, String>?,): Boolean {
        if (RBNode == null) return true
        if (root?.color == Color.RED) return false
        if (RBNode.color == Color.RED)
            if (!(RBNode.key > minimum && RBNode.key < maximum)) {
                return false
            }
        if (RBNode.color == Color.RED) {
            if ((RBNode.left?.color != Color.BLACK && RBNode.left?.color != null) || (RBNode.right?.color != Color.BLACK && RBNode.right?.color != null)) {
                return false
            }
        }
        if (!HeightValid(RBNode)) {
            return false
        }

        return checkProperty(RBNode.left, minimum, RBNode.key, root) && checkProperty(
            RBNode.right,
            RBNode.key,
            maximum,
            root
        )
    }
    private lateinit var rbTree: RedBlackTree<Int,String>
    @BeforeEach
    fun setUp(){
        rbTree = RedBlackTree()
    }

    @Test
    fun checkProperty1() {
        rbTree = generateRandomTree()
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
    }



    @Test
    fun checkInsertTree() {
        rbTree.insert(3, "three")
        rbTree.insert(10, "ten")
        rbTree.insert(18, "eighteen ")
        rbTree.insert(25, "twenty-five")
        rbTree.insert(19, "nineteen")
        rbTree.insert(5, "five")
        rbTree.insert(100, "one hundred")
        rbTree.insert(70, "seventy")
        val result = checkProperty(rbTree.getRootForTesting(), 2, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
    }

    @Test
    fun checkDeleteTree() {
        rbTree.insert(50, "fifty")
        rbTree.insert(30, "thirty")
        rbTree.insert(70, "seventy")
        rbTree.insert(60, "sixty")
        rbTree.insert(80, "eighty")
        rbTree.delete(30)
        val result = checkProperty(rbTree.getRootForTesting(), 2, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)

    }

    @Test
    fun checkInsertTree1() {
        rbTree = generateRandomTree()
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
    }

    @Test
    fun checkInsertTree2() {
        rbTree = generateRandomTree()
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
    }

    @Test
    fun checkInsertTree3() {
        rbTree = generateRandomTree()
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
    }

    @Test
    fun checkDeleteAndInsertTree1() {
        rbTree = generateRandomTree()
        rbTree.insert(3434, "3434")
        rbTree.delete(3434)
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)

    }

    @Test
    fun testBlackHeight() {
        rbTree.insert(4, "four")
        rbTree.insert(2, "two")
        rbTree.insert(6, "six")
        rbTree.insert(1, "one")
        rbTree.insert(3, "three")
        val result = checkBlackHeightProperty(rbTree.getRootForTesting())
        assertEquals(true, (result.first))
        assertEquals(2, result.second)
    }

    @Test
    fun `test left rotation`() {
        rbTree.insert(3, "three")
        rbTree.insert(5, "five")
        rbTree.insert(7, "seven")
        assertEquals("five", rbTree.search(5))
        assertEquals("three", rbTree.search(3))
        assertEquals("seven", rbTree.search(7))
    }

    @Test
    fun `test right rotation`() {
        rbTree.insert(7, "seven")
        rbTree.insert(5, "five")
        rbTree.insert(3, "three")
        assertEquals("five", rbTree.search(5))
        assertEquals("three", rbTree.search(3))
        assertEquals("seven", rbTree.search(7))
    }

    @Test
    fun deleteLeafNode() {
        rbTree.insert(5, "five")
        rbTree.insert(3, "three")
        rbTree.insert(7, "seven")
        rbTree.delete(3)
        assertNull(rbTree.search(3))
        assertEquals("five", rbTree.search(5))
        assertEquals("seven", rbTree.search(7))
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)

    }

    @Test
    fun `delete case 1-4`() {
        rbTree = generateRandomTree(8510092)
        rbTree.insert(2323,"2323" )
        rbTree.delete(2323)
        rbTree.insert(3333, "3333")
        rbTree.delete(3333)
        rbTree.insert(10000, "10000")
        rbTree.delete(10000)
        val result = checkProperty(rbTree.getRootForTesting(), -1, 500001, rbTree.getRootForTesting())
        assertEquals(true, result)
        val tree2 = generateRandomTree(4222228)
        tree2.insert(20000, "20000")
        tree2.delete(20000)
        tree2.insert(15000, "15000")
        tree2.delete(15000)
        val result2 = checkProperty(tree2.getRootForTesting(), -1, 50001, tree2.getRootForTesting())
        assertEquals(true, result2)
    }

    @Test
    fun `delete case 5-6`() {
        rbTree = generateRandomTree(21733)
        rbTree.insert(2323,"2323")
        rbTree.delete(2323)
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)
        val tree2 = generateRandomTree(19591)
        tree2.insert(2323,"2323")
        tree2.delete(2323)
        val result2  = checkProperty(rbTree.getRootForTesting(), -1, 50001, tree2.getRootForTesting())
        assertEquals(true, result2)
    }
    @Test
    fun `delete root`(){
        val rbTree = RedBlackTree<Int, Int>()
        rbTree.insert(2,2)
        rbTree.insert(4,4)
        rbTree.insert(3,3)
        rbTree.delete(3)
        assertEquals(2, rbTree.search(2))
        assertEquals(4, rbTree.search(4))
        assertNull(rbTree.search(3))

    }
    @Test
    fun `small rbTree`(){
        rbTree.insert(2,"two")
        rbTree.insert(3,"three")
        rbTree.delete(3)
        assertEquals("two", rbTree.search(2))
        val rbTree2 = RedBlackTree<Int, String>()
        rbTree2.insert(2,"two")
        rbTree2.insert(3,"three")
        rbTree2.delete(2)
        assertEquals("three", rbTree2.search(3))
        assertNull(rbTree2.search(2))

    }
    @Test
    fun `insert root node`() {
        rbTree.insert(10, "ten")
        assertEquals("ten", rbTree.search(10))
        assertNull(rbTree.search(5))
        assertNull(rbTree.search(15))
    }

    @Test
    fun `insert case 1`() {
        rbTree.insert(10, "ten")
        rbTree.insert(5, "five")
        assertEquals("ten", rbTree.search(10))
        assertEquals("five", rbTree.search(5))
    }

    @Test
    fun `insert case 2`() {
        rbTree.insert(10, "ten")
        rbTree.insert(5, "five")
        assertEquals("ten", rbTree.search(10))
        assertEquals("five", rbTree.search(5))
    }

    @Test
    fun `insert case 3`() {
        rbTree.insert(10, "ten")
        rbTree.insert(5, "five")
        rbTree.insert(15, "fifteen")
        rbTree.insert(3, "three")
        assertEquals("ten", rbTree.search(10))
        assertEquals("five", rbTree.search(5))
        assertEquals("fifteen", rbTree.search(15))
        assertEquals("three", rbTree.search(3))
    }

    @Test
    fun `insert case 4 left right rotation`() {
        rbTree.insert(10, "ten")
        rbTree.insert(5, "five")
        rbTree.insert(7, "seven")
        assertEquals("ten", rbTree.search(10))
        assertEquals("five", rbTree.search(5))
        assertEquals("seven", rbTree.search(7))
    }

    @Test
    fun `insert case 4 right left rotation`() {
        rbTree.insert(10, "ten")
        rbTree.insert(15, "fifteen")
        rbTree.insert(13, "thirteen")
        assertEquals("ten", rbTree.search(10))
        assertEquals("fifteen", rbTree.search(15))
        assertEquals("thirteen", rbTree.search(13))
    }

    @Test
    fun `complex insertion sequence`() {
        val rbTree = RedBlackTree<Int, Int>()
        rbTree.insert(10, 10)
        rbTree.insert(5, 5)
        rbTree.insert(15, 15)
        rbTree.insert(3, 3)
        rbTree.insert(7, 7)
        rbTree.insert(12, 12)
        rbTree.insert(17, 17)
        assertNotNull(rbTree.search(10))
        assertNotNull(rbTree.search(5))
        assertNotNull(rbTree.search(15))
        assertNotNull(rbTree.search(3))
        assertNotNull(rbTree.search(7))
        assertNotNull(rbTree.search(12))
        assertNotNull(rbTree.search(17))
    }
    @Test
    fun `insert and delete in big tree`(){
        rbTree = generateRandomTree(45,5000)
        rbTree.insert (5000,"5000")
        rbTree.delete(5000)
        rbTree.insert(10000,"10000")
        rbTree.delete(10000)
        rbTree.insert(6750,"6750")
        rbTree.delete(6750)
        rbTree.insert(8000,"8000")
        rbTree.delete(8000)
        val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
        assertEquals(true, result)


    }
    @Test
    fun `tree integrity across multiple random trees`(){
        for (i in 0L..30000L){
            val rbTree = generateRandomTree(i, 300)
            rbTree.insert (5000,"5000")
            rbTree.delete(5000)
            rbTree.insert(10000,"10000")
            rbTree.delete(10000)
            rbTree.insert(6750,"6750")
            rbTree.delete(6750)
            val result = checkProperty(rbTree.getRootForTesting(), -1, 50001, rbTree.getRootForTesting())
            assertEquals(true, result)


        }
    }
    @Test
    fun `delete null node`(){
        rbTree.delete(5)
        assertEquals(null, rbTree.getRootForTesting())
    }
    @Test
    fun `test BFS iterator`(){
        val rbTree = RedBlackTree<Int, String>()
        rbTree.insert(3,"three")
        rbTree.insert(10,"ten")
        rbTree.insert(6, "six")
        rbTree.insert(20,"twenty")
        val iterator = rbTree.treeBFSIterator()
        val nodes = mutableListOf<RBNode<Int, String>>()
        while (iterator.hasNext()){
            nodes.add(iterator.next())
        }
        assertEquals(6,nodes[0].key)
        assertEquals(3,nodes[1].key)
        assertEquals(10,nodes[2].key)
        assertEquals(20,nodes[3].key)
    }
    @Test
    fun `test DFS iterator`(){
        rbTree.insert(20,"twenty")
        rbTree.insert(40, "forty")
        rbTree.insert(10,"ten")
        rbTree.insert(15,"fifteen")
        rbTree.insert(25, "twenty five")
        val iterator = rbTree.treeDFSIterator()
        val nodes = mutableListOf<RBNode<Int, String>>()
        while (iterator.hasNext()){
            nodes.add(iterator.next())
        }
        assertEquals(10,nodes[0].key)
        assertEquals(15, nodes[1].key)
        assertEquals(20, nodes[2].key)
        assertEquals(25,nodes[3].key)
        assertEquals(40, nodes[4].key)

    }








}
