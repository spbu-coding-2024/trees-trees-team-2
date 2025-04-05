import org.junit.jupiter.api.Test
import trees.RedBlackTree
import kotlin.random.Random
import kotlin.test.assertEquals

class RedBlackTreeTest{
    private fun generateRandomTree(): RedBlackTree<Int, Int> {
        val tree = RedBlackTree<Int, Int>()
        val random = Random(System.currentTimeMillis())
        var nodesAdded = 0
        while (nodesAdded < 50) {
            val key = random.nextInt(50, 5000)
            if (tree.search(key) == null) {
                tree.insert(key, key)
                nodesAdded++
            }
        }
        return tree
    }
    private fun checkBlackHeightProperty(RBNode: RBNode<Int, Int>?): Pair<Boolean, Int> {
        if (RBNode == null) {
            return Pair(true, 1)
        }
        val left = checkBlackHeightProperty(RBNode.left)
        val right = checkBlackHeightProperty(RBNode.right)
        if (!left.first || !right.first) return Pair(false, 0)
        if (left.second != right.second) return Pair(false, 0)
        val currentHeight = if (!RBNode.color) left.second + 1 else left.second
        return Pair(true, currentHeight)
    }
    private fun HeightValid(root: RBNode<Int, Int>?): Boolean {
        return checkBlackHeightProperty(root).first
    }
    private fun checkProperty(RBNode: RBNode<Int,Int>?, minimum:Int, maximum:Int):Boolean{

        if (RBNode == null) return true
        if (!(RBNode.key > minimum && RBNode.key < maximum)){
            return false
        }
        if (RBNode.color){
            if ((RBNode.left?.color != false && RBNode.left?.color != null) || (RBNode.right?.color != false && RBNode.right?.color != null)){
                return false
            }
        }
        if (!HeightValid(RBNode)){
            return false
        }
        return checkProperty(RBNode.left, minimum, RBNode.key) && checkProperty(RBNode.right, RBNode.key, maximum)
    }
    @Test
    fun checkProperty1(){
        val tree = generateRandomTree()
        val result  = checkProperty(tree.root, -1, 10001)
        assertEquals(true, result)
    }

    val rbTest = RedBlackTree<Int, Int>()
    @Test
    fun checkInsertTree () {
        rbTest.insert(3,3)
        rbTest.insert(10,10)
        rbTest.insert(18,18)
        rbTest.insert(25,25)
        rbTest.insert(19,19)
        rbTest.insert(5,5)
        rbTest.insert(100,100)
        rbTest.insert(70,70)
        val result  = checkProperty(rbTest.root, 2, 101)
        assertEquals(true, result)
    }
    @Test
    fun checkDeleteTree(){
        rbTest.insert(50,50)
        rbTest.insert(30,30)
        rbTest.insert(70,70)
        rbTest.insert(60,60)
        rbTest.insert(80,80)
        rbTest.delete(30)
        val result  = checkProperty(rbTest.root, 2, 101)
        assertEquals(true,result)

    }
    @Test
    fun checkInsertTree1(){
        val tree = generateRandomTree()
        val result  = checkProperty(tree.root, -1, 10001)
        assertEquals(true, result)
    }
    @Test
    fun checkInsertTree2(){
        val tree = generateRandomTree()
        val result  = checkProperty(tree.root, -1, 10001)
        assertEquals(true, result)
    }
    @Test
    fun checkInsertTree3(){
        val tree = generateRandomTree()
        val result  = checkProperty(tree.root, -1, 10001)
        assertEquals(true, result)
    }
    @Test
    fun checkDeleteAndInsertTree1(){
        val tree = generateRandomTree()
        tree.insert(3434, 3434)
        tree.insert(2323,2323)
        tree.delete(2323)
        tree.delete(3434)
        val result  = checkProperty(tree.root, -1, 10001)
        assertEquals(true, result)
    }


}