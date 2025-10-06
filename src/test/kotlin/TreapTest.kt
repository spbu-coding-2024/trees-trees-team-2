import dto.TreapNode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import trees.RedBlackTree
import trees.TreapTree
import javax.swing.plaf.basic.BasicTreeUI
import kotlin.test.assertEquals

class TreapTest{

    private lateinit var TreapTree: TreapTree<Int, String>
    @BeforeEach
    fun setUp(){
        TreapTree = TreapTree()
    }
    private fun checkPriority(root: TreapNode<Int,String>?):Boolean{
        root?.left?.let {
            if (root.priority < it.priority) return false
        }
        root?.right?.let { if (root.priority < it.priority) return false }

        if (root?.left!=null){
            return checkPriority(root.left)
        }
        else if(root?.right != null){
            return checkPriority(root.right)

        }
        return true
    }
    @Test
    fun checkInsertTree1(){
        TreapTree.insert(20,"20")
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        assertEquals(true, checkPriority(TreapTree.getRootForTesting()))
        assertEquals(TreapTree.search(40) , "40")
        assertEquals(TreapTree.search(60) , "60")
        assertEquals(TreapTree.search(20) , "20")
    }
    @Test
    fun checkInsertTree2(){
        TreapTree.insert(50,"50")
        TreapTree.insert(70,"70")
        TreapTree.insert(20,"20")
        TreapTree.insert(10,"10")
        assertEquals(true, checkPriority(TreapTree.getRootForTesting()))
        assertEquals(TreapTree.search(70), "70")
        assertEquals(TreapTree.search(20), "20")
    }
    @Test
    fun checkDeleteNode(){
        TreapTree.insert(21,"21")
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        TreapTree.insert(20,"20")
        TreapTree.delete(40)
        assertEquals(true, checkPriority(TreapTree.getRootForTesting()))
        assertEquals(TreapTree.search(21) , "21")
        assertEquals(TreapTree.search(20) , "20")
        assertEquals(TreapTree.search(60) , "60")
    }
    @Test
    fun checkDeleteTree(){
        TreapTree.insert(50,"50")
        TreapTree.insert(100,"100")
        TreapTree.insert(150,"150")
        TreapTree.insert(155,"155")
        TreapTree.insert(70,"70")
        TreapTree.delete(70)
        TreapTree.delete(155)
        TreapTree.delete(100)
        TreapTree.delete(50)
        assertEquals(TreapTree.search(150), "150")
    }
    @Test
    fun checkDeleteRoot(){
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        TreapTree.insert(50,"50")
        TreapTree.insert(55,"55")
        TreapTree.delete(60)
        assertEquals(checkPriority(TreapTree.getRootForTesting()),true )
        assertEquals(TreapTree.search(55), "55")
    }
    @Test fun `search for a non-existent key`(){
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        TreapTree.insert(50,"50")
        assertEquals(TreapTree.search(100), null)

    }
    @Test fun `delete non-existent node`(){
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        TreapTree.insert(50,"50")
        TreapTree.delete(100)
        assertEquals(checkPriority(TreapTree.getRootForTesting()), true)
        assertEquals(TreapTree.search(60), "60")
    }
    @RepeatedTest(20) fun `insert and delete`(){ // тест повторяется несколько раз, чтобы проверить дерево для разных приортетов
        TreapTree.insert(40,"40")
        TreapTree.insert(60,"60")
        TreapTree.insert(50,"50")
        TreapTree.delete(60)
        TreapTree.insert(45, "45")
        TreapTree.insert(90, "90")
        TreapTree.insert(80, "80")
        TreapTree.delete(80)
        TreapTree.insert(55, "55")
        TreapTree.insert(46, "46")
        TreapTree.delete(45)
        TreapTree.delete(90)
        assertEquals(checkPriority(TreapTree.getRootForTesting()), true)
    }
}