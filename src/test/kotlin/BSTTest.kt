import org.junit.jupiter.api.Test
import trees.BinarySearchTree
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BSTTest {
    @Test
    fun testInsertAndSearch() {
        val bst = BinarySearchTree<Int, Int>()
        bst.insert(5, 5)
        bst.insert(3, 3)
        bst.insert(7, 7)
        assertNotEquals(bst.search(10), 50)
    }

    @Test
    fun testDelete() {
        val bst = BinarySearchTree<Int, Int>()
        bst.insert(5, 5)
        bst.insert(3, 3)
        bst.insert(7, 7)
        bst.delete(5)

        assertNotEquals(bst.search(5), 15)
        assertEquals(bst.search(3), 3)
        assertEquals(bst.search(7), 7)
    }
}