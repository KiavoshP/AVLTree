import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * These tests are not exhaustive.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class AVLStudentTest {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotationRoot() {
        /*
                3               4
                 \             / \
                  5     ->    3   5
                 /
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       526   856
                 / \                     /
               386 526                 386
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        Integer maximum = new Integer(646);
        avlTree.add(477);
        avlTree.add(526);
        avlTree.add(386);
        avlTree.add(maximum);

        assertSame(maximum, avlTree.get(new Integer(646)));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        avlTree.add(new Integer(477));
        avlTree.add(new Integer(526));
        avlTree.add(new Integer(386));
        avlTree.add(new Integer(646));

        assertEquals(true, avlTree.contains(new Integer(477)));
        assertEquals(true, avlTree.contains(new Integer(386)));
        assertEquals(true, avlTree.contains(new Integer(646)));
        assertEquals(false, avlTree.contains(new Integer(387)));
        assertEquals(false, avlTree.contains(new Integer(700)));
        assertEquals(false, avlTree.contains(new Integer(500)));
    }


    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                     646
                    /   \
                  477   856
                  / \
                386 526
         */
        avlTree.add(646);
        avlTree.add(386);
        avlTree.add(856);
        avlTree.add(526);
        avlTree.add(477);

        assertEquals(2, avlTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessor() {
        /*
                76
              /    \
            34      90
             \      /
              40  81
         */

        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);
        avlTree.add(40);
        avlTree.add(81);

        assertEquals((Integer) 40, avlTree.predecessor(76));
        assertEquals((Integer) 34, avlTree.predecessor(40));
        assertEquals((Integer) 76, avlTree.predecessor(81));
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallest() {
        /*
                    76
                 /      \
               34        90
              /  \      /
            20    40  81
         */

        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);
        avlTree.add(20);
        avlTree.add(40);
        avlTree.add(81);

        List<Integer> smallest = new ArrayList<>();
        smallest.add(20);
        smallest.add(34);
        smallest.add(40);

        // Should be [20, 34, 40]
        assertEquals(smallest, avlTree.kSmallest(3));

        smallest.add(76);

        // Should be [20, 34, 40, 76]
        assertEquals(smallest, avlTree.kSmallest(4));

        smallest.add(81);
        smallest.add(90);

        // Should be [20, 34, 40, 81, 90]
        assertEquals(smallest, avlTree.kSmallest(6));
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorAndClear() {
        /*
                     7
                    / \
                   1   24
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(7);
        toAdd.add(24);
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    // tests by suzan manasreh

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        avlTree.add(1);
        avlTree.add(0);
        avlTree.add(null);
    }

    // test to verify that adding an element to AVL that already exists does nothing
    @Test(timeout = TIMEOUT)
    public void addExistingElement() {
        /*
              5
             / \
            1   9
        */
        avlTree.add(5);
        avlTree.add(9);
        avlTree.add(1);

        assertEquals(3, avlTree.size());

        assertEquals((Integer) 5, avlTree.getRoot().getData());
        assertEquals((Integer) 1, avlTree.getRoot().getLeft().getData());
        assertEquals((Integer) 9, avlTree.getRoot().getRight().getData());

        avlTree.add(1);
        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 5, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotation() {
        /*
                  3                 4
                   \               / \
                    4       ->    3   5
                     \
                      5
         */
        avlTree.add(3);
        avlTree.add(4);
        avlTree.add(5);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotationRoot() {
        /*
                5               4
               /               / \
              3       ->      3   5
               \
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotationWithSubtrees() {
        /*
                     6                     6
                   /   \                 /   \
                  4     9      ->       4     9
                 / \   /               / \   /
                1   5 8               2   5 8
                 \                   / \
                  2                 1   3
                   \
                    3
         */
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(9);
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(8);
        avlTree.add(2);
        avlTree.add(3);

        assertEquals(8, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 6, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 4, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 8, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotationWithSubtrees() {
        /*
                     6                     4
                   /   \                 /   \
                  4     9      ->       1     6
                 / \                   /     / \
                1   5                 0     5   9
               /
              0
         */
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(9);
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(0);

        assertEquals(6, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 6, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 0, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        avlTree.remove(null);
    }

    // test removing data that is not in the tree
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveElementNotInBST() {
        avlTree.add(6);
        avlTree.add(9);
        avlTree.add(3);
        avlTree.remove(2);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithRotation() {
        // there's a right and right-left rotation
        /*
                     6                     10
                   /   \                 /    \
                  4     12      ->      6     12
                 / \   /  \            / \    / \
                1   5 10  13          1   9  11 13
               /     /  \    \       / \  /      \
              0     9   11    14    0   4 8       14
                   /
                  8
         */
        Integer toBeRemoved = 5;
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(12);
        avlTree.add(1);
        avlTree.add(toBeRemoved);
        avlTree.add(10);
        avlTree.add(13);
        avlTree.add(0);
        avlTree.add(9);
        avlTree.add(11);
        avlTree.add(14);
        avlTree.add(8);

        assertSame(toBeRemoved, avlTree.remove(5));

        assertEquals(11, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 10, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 6, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 12, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 11, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 13, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 0, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 4, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 8, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 14, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullData() {
        avlTree.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetDataNotInAVL() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        avlTree.get(18);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullData() {
        avlTree.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testContainsDataNotInTree() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        assertFalse(avlTree.contains(18));
        assertFalse(avlTree.contains(14));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPredecessorNullData() {
        avlTree.predecessor(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testPredecessorDataNotInTree() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        avlTree.predecessor(18);
        avlTree.predecessor(14);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKSmallestException() {
        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);

        avlTree.kSmallest(-1);
        avlTree.kSmallest(10);
    }

    @Test(timeout = TIMEOUT)
    public void testHeightEmptyTree() {
        assertEquals(-1, avlTree.height());
    }


    //tests by Michael John Sweeney

    @Test(timeout = TIMEOUT)
    public void testKSmallestEmptyTree() {
        assertEquals(0, avlTree.kSmallest(0).size());
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestA() {
        /*
         30
        /  \
      20    40
     /        \
   10          50
         */
        avlTree.add(30);
        avlTree.add(20);
        avlTree.add(40);
        avlTree.add(10);
        avlTree.add(50);
        assertEquals((Integer) 30, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(10);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(20);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(30);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(40);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(50);
        assertEquals(smallest, avlTree.kSmallest(5));
        avlTree.clear();
        smallest.clear();
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestB() {
        /*
        30
      /    \
    10      50
      \    /
      20  40
         */
        avlTree.add(30);
        avlTree.add(10);
        avlTree.add(50);
        avlTree.add(20);
        avlTree.add(40);
        assertEquals((Integer) 30, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(10);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(20);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(30);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(40);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(50);
        assertEquals(smallest, avlTree.kSmallest(5));
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestC() {
        /*
          3
        /   \
       2     6
      /    /   \
     1    4     8
          \     /
           5   7
         */
        avlTree.add(3);
        avlTree.add(2);
        avlTree.add(6);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(8);
        avlTree.add(5);
        avlTree.add(7);
        assertEquals((Integer) 3, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(1);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(2);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(3);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(4);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(5);
        assertEquals(smallest, avlTree.kSmallest(5));
        smallest.add(6);
        assertEquals(smallest, avlTree.kSmallest(6));
        smallest.add(7);
        assertEquals(smallest, avlTree.kSmallest(7));
        smallest.add(8);
        assertEquals(smallest, avlTree.kSmallest(8));
    }

    // Tests by Rian Rahman

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChildWithRotation() {
        /*
                        70                                   70
                    /         \                         /          \
                  55          140             ->      55           150
                 /  \      /       \                 /  \         /   \
               38    65   110      160             38    65     140   160
              /     /  \    \      /  \           /     /  \    /    /  \
            19    62   68   114  150  180        19   62   68 114  155   180
                                   \
                                   155
         */
        Integer toBeRemoved = 110;
        avlTree.add(70);
        avlTree.add(55);
        avlTree.add(140);
        avlTree.add(38);
        avlTree.add(65);
        avlTree.add(toBeRemoved);
        avlTree.add(160);
        avlTree.add(19);
        avlTree.add(62);
        avlTree.add(68);
        avlTree.add(114);
        avlTree.add(150);
        avlTree.add(180);
        avlTree.add(155);

        assertSame(toBeRemoved, avlTree.remove(110));

        assertEquals(13, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 70, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 55, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 150, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 38, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(1, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 65, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 140, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(1, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 160, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 19, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 62, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 68, root.getLeft().getRight().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 114, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 155, root.getRight().getRight().getLeft().getData());
        assertEquals(0, root.getRight().getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 180, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveInternalNodeWithRotation() {
        /*
                     70                                70
                  /      \                          /      \
                55        140        ->           55       140
               /  \       /  \                   /  \     /   \
              38   67   110   160              38    65  110  160
             /    /  \    \                   /     /  \    \
            19  62    68   114              19    62   68   114
                 \
                 65
         */
        Integer toBeRemoved = 67;
        avlTree.add(70);
        avlTree.add(55);
        avlTree.add(140);
        avlTree.add(38);
        avlTree.add(toBeRemoved);
        avlTree.add(110);
        avlTree.add(160);
        avlTree.add(19);
        avlTree.add(62);
        avlTree.add(68);
        avlTree.add(114);
        avlTree.add(65);

        assertSame(toBeRemoved, avlTree.remove(67));

        assertEquals(11, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 70, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 55, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 140, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 38, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(1, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 65, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 110, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(-1, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 160, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 19, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 62, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 68, root.getLeft().getRight().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 114, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRootWithRotation() {
        /*
                        70                                   84
                     /      \                             /      \
                   55        140                        55        140
                  /  \     /     \                     /  \      /   \
                 38  67  96      160       ->        38    67  110   160
                /       /  \     /  \               /         /  \   /  \
              19       84   110 145  190          19        96  114 145 190
                           /  \                              \
                          98   114                           98
         */
        Integer toBeRemoved = 70;
        avlTree.add(toBeRemoved);
        avlTree.add(55);
        avlTree.add(140);
        avlTree.add(38);
        avlTree.add(67);
        avlTree.add(96);
        avlTree.add(160);
        avlTree.add(19);
        avlTree.add(84);
        avlTree.add(110);
        avlTree.add(145);
        avlTree.add(190);
        avlTree.add(98);
        avlTree.add(114);

        assertSame(toBeRemoved, avlTree.remove(70));

        assertEquals(13, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 84, root.getData());
        assertEquals(4, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals((Integer) 55, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 140, root.getRight().getData());
        assertEquals(3, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 38, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(1, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 67, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 110, root.getRight().getLeft().getData());
        assertEquals(2, root.getRight().getLeft().getHeight());
        assertEquals(1, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 160, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 19, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 96, root.getRight().getLeft().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(-1, root.getRight().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 114, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 145, root.getRight().getRight().getLeft().getData());
        assertEquals(0, root.getRight().getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 190, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 98, root.getRight().getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorCase1() {
        /*
                          50
                     /           \
                   25            80
                  /  \          /  \
                 18   45     78    101
                     /  \   / \
                   43   48 76  79
         */
        avlTree.add(50);
        avlTree.add(25);
        avlTree.add(80);
        avlTree.add(18);
        avlTree.add(45);
        avlTree.add(78);
        avlTree.add(101);
        avlTree.add(43);
        avlTree.add(48);
        avlTree.add(76);
        avlTree.add(79);
        assertEquals((Integer) 48, avlTree.predecessor(50));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorCase2() {
        /*
                          50
                     /           \
                   25            80
                  /  \          /  \
                 18   45     78    101
                     /  \   / \
                   43   48 76  79
         */
        avlTree.add(50);
        avlTree.add(25);
        avlTree.add(80);
        avlTree.add(18);
        avlTree.add(45);
        avlTree.add(78);
        avlTree.add(101);
        avlTree.add(43);
        avlTree.add(48);
        avlTree.add(76);
        avlTree.add(79);
        assertEquals((Integer) 25, avlTree.predecessor(43));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithNoPredecessorA() {
        /*
                        55
                      /    \
                    20      78
                      \    /
                      31  66
         */
        avlTree.add(55);
        avlTree.add(20);
        avlTree.add(78);
        avlTree.add(31);
        avlTree.add(66);
        assertEquals(null, avlTree.predecessor(20));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithNoPredecessorB() {
        /*
                       90
                    /      \
                  72       105
                 /  \      /  \
                65   76  103   120
               /      \
              54      88
         */
        avlTree.add(90);
        avlTree.add(72);
        avlTree.add(105);
        avlTree.add(65);
        avlTree.add(76);
        avlTree.add(103);
        avlTree.add(120);
        avlTree.add(54);
        avlTree.add(88);
        assertEquals(null, avlTree.predecessor(54));
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessorWithNoPredecessorC() {
        /*
                    40
         */
        avlTree.add(40);
        assertEquals(null, avlTree.predecessor(40));
    }


    // Tests by Adil Farooq

    /* Improvements on tests testPredecessorDataNotInTree(methods 1-2) and
     * testKSmallestException(methods 3-4) by suzan manasreh
     */

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testPredecessorDataNotInTree1() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        assertEquals((Integer) 17, avlTree.predecessor(22));
        assertEquals(null, avlTree.predecessor(15));
        assertEquals((Integer) 15, avlTree.predecessor(17));
        assertEquals((Integer) 22, avlTree.predecessor(34));
        avlTree.predecessor(18);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testPredecessorDataNotInTree2() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        avlTree.predecessor(14);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKSmallestException1() {
        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);

        avlTree.kSmallest(-1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKSmallestException2() {
        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);

        avlTree.kSmallest(10);
    }

    //Initialize with a collection correctly
    @Test(timeout = TIMEOUT)
    public void testAddCollection() {
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        Collection<Integer> coll = new ArrayList<>();
        coll.add(5);
        coll.add(4);
        coll.add(3);

        avlTree = new AVL<>(coll);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    //Initialize with a null collection correctly
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullCollection() {
        avlTree = new AVL<>(null);
    }

    //Initialize with a collection with null data correctly
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPartlyNullCollection() {
        Collection<Integer> coll = new ArrayList<>();
        coll.add(5);
        coll.add(null);
        coll.add(3);

        avlTree = new AVL<>(coll);
    }

    //Remove leaf
    @Test(timeout = TIMEOUT)
    public void testRemoveLeaf() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       477   856
                 / \                     /
               386 526                 386
         */
        Integer toBeRemoved = new Integer(526);
        avlTree.add(646);
        avlTree.add(477);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(toBeRemoved);

        assertSame(toBeRemoved, avlTree.remove(new Integer(526)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 477, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
    }

    //Remove one-child
    @Test(timeout = TIMEOUT)
    public void testRemoveOneChild() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       386   856
                 /
               386
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
    }

    //Shravan Cheekati
    @Test(timeout = TIMEOUT)
    public void addSortedDataplusConstructor() {
        avlTree.add(10);
        avlTree.add(11);
        avlTree.add(12);
        avlTree.add(13);
        avlTree.add(14);
        avlTree.add(15);
        avlTree.add(16);
        avlTree.add(17);
        avlTree.add(18);
        avlTree.add(19);
        avlTree.add(20);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 13, root.getData());

        avlTree.clear();
        assertEquals(0, avlTree.size());

        List<Integer> toAdd = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        avlTree = new AVL<>(toAdd);
        root = avlTree.getRoot();
        assertEquals((Integer) 13, root.getData());

    }

    @Test(timeout = TIMEOUT)
    public void removeMultiRotationsAndSomePredChecks() {
        List<Integer> toAdd = Arrays.asList(5, 2, 8, 1, 3, 7, 10, 4, 6, 9, 11, 12);
        avlTree = new AVL<>(toAdd);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 5, root.getData());

        avlTree.remove(1);
        root = avlTree.getRoot();

        assertEquals((Integer) 8, root.getData());
        assertEquals((Integer) 7, avlTree.predecessor(8));
        assertEquals((Integer) 8, avlTree.predecessor(9));
        assertEquals((Integer) 3, avlTree.predecessor(4));
        assertEquals((Integer) 5, avlTree.predecessor(6));
        assertEquals((Integer) 11, avlTree.predecessor(12));
        assertNull(avlTree.predecessor(2));
    }

    @Test(timeout = TIMEOUT)
    public void addMultiRotation1() {
        List<Integer> toAdd = Arrays.asList(20, 4, 26, 3, 9, 21, 30, 2, 7, 11);
        avlTree = new AVL<>(toAdd);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 20, root.getData());

        avlTree.add(8);
        root = avlTree.getRoot();

        assertEquals((Integer) 9, root.getData());
        assertEquals((Integer) 7, avlTree.predecessor(8));
        assertEquals((Integer) 8, avlTree.predecessor(9));
        assertEquals((Integer) 3, avlTree.predecessor(4));
        assertEquals((Integer) 26, avlTree.predecessor(30));
        assertEquals((Integer) 11, avlTree.predecessor(20));
        assertNull(avlTree.predecessor(2));
    }

    @Test(timeout = TIMEOUT)
    public void addMultiRotation2() {
        List<Integer> toAdd = Arrays.asList(20, 4, 26, 3, 9, 21, 30, 2, 7, 11);
        avlTree = new AVL<>(toAdd);

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 20, root.getData());

        avlTree.add(15);
        root = avlTree.getRoot();

        assertEquals((Integer) 9, root.getData());
        assertEquals((Integer) 9, avlTree.predecessor(11));
        assertEquals((Integer) 7, avlTree.predecessor(9));
        assertEquals((Integer) 3, avlTree.predecessor(4));
        assertEquals((Integer) 26, avlTree.predecessor(30));
        assertEquals((Integer) 15, avlTree.predecessor(20));
        assertNull(avlTree.predecessor(2));
    }

}