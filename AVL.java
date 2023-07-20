import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Kiavosh Peynabard
 * @userid kpeynabard3
 * @GTID 903353136
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection can not be null");
        }
        for (T obj: data
        ) {
            add(obj);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        root = addHelper(root, data);
    }

    /**
     * Recursive Add helper method. Takes in the data to be added and
     * initial root.
     * @param data The data to be added
     * @param currNode the node which we use to travel
     * @return a AVLNode contains elements to be chained
     */
    private AVLNode<T> addHelper(AVLNode<T> currNode, T data) {
        if (currNode == null) {
            size++;
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode = updateHBF(newNode);
            return newNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(addHelper(currNode.getRight(), data));
            currNode = balanceMaintainance(updateHBF(currNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(addHelper(currNode.getLeft(), data));
            currNode = balanceMaintainance(updateHBF(currNode));
        }
        return currNode;
    }

    /**
     * Maintain the balance of the subtree
     * @param currNode the node which we use to balance
     * @return a AVLNode which is balanced
     */
    private AVLNode<T> balanceMaintainance(AVLNode<T> currNode) {
        //Checking left heavy
        if (currNode.getBalanceFactor() == 2) {
            //Checking if the child is right heavy
            if (currNode.getLeft().getBalanceFactor() == -1) {
                currNode.setLeft(leftRotation(currNode.getLeft()));
                return rightRotation(currNode);
            } else {
                return rightRotation(currNode);
            }
        } else if (currNode.getBalanceFactor() == -2) {
            if (currNode.getRight().getBalanceFactor() == 1) {
                currNode.setRight(rightRotation(currNode.getRight()));
                return leftRotation(currNode);
            } else {
                return leftRotation(currNode);
            }
        }
        return currNode;
    }

    /**
     * Balance the node with proper rotation
     * @param currNode the node which we use to rotate
     * @return a AVLNode which is rotated
     */
    private AVLNode<T> leftRotation(AVLNode<T> currNode) {
        AVLNode<T> rC = currNode.getRight();
        currNode.setRight(rC.getLeft());
        currNode = updateHBF(currNode);
        rC.setLeft(currNode);
        rC = updateHBF(rC);

        return rC;
    }

    /**
     * Balance the node with proper rotation
     * @param currNode the node which we use to rotate
     * @return a AVLNode which is rotated
     */
    private AVLNode<T> rightRotation(AVLNode<T> currNode) {
        AVLNode<T> lC = currNode.getLeft();
        currNode.setLeft(lC.getRight());
        currNode = updateHBF(currNode);
        lC.setRight(currNode);
        lC = updateHBF(lC);

        return lC;
    }

    /**
     * Updates the Height and Balance of the given node.
     * @param currNode the node which we will update the Height and Balance
     * @return a AVLNode which is rotated
     */
    private AVLNode<T> updateHBF(AVLNode<T> currNode) {
        int lCH;
        int rCH;
        if (currNode.getRight() == null) {
            rCH = -1;
        } else {
            rCH = currNode.getRight().getHeight();
        }
        if (currNode.getLeft() == null) {
            lCH = -1;
        } else {
            lCH = currNode.getLeft().getHeight();
        }
        currNode.setHeight(Math.max(lCH, rCH) + 1);
        currNode.setBalanceFactor(lCH - rCH);
        return currNode;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null, real data is needed to be removed");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("Data is not presented in the tree");
        }
        size--;
        return dummy.getData();
    }

    /**
     * Recursive helper method for remove.
     * the starting root.
     * @param data The data to be removed
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the deleted data out
     * @return a BSTNode that data is removed from.
     */
    private AVLNode<T> removeHelper(AVLNode<T> currNode, T data, AVLNode<T> dummyNode) {
        if (currNode == null) {
            dummyNode.setData(null);
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(removeHelper(currNode.getRight(), data, dummyNode));
            currNode = balanceMaintainance(updateHBF(currNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(removeHelper(currNode.getLeft(), data, dummyNode));
            currNode = balanceMaintainance(updateHBF(currNode));
        } else {
            dummyNode.setData(currNode.getData());
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            } else if (currNode.getLeft() != null && currNode.getRight() == null) {
                return currNode.getLeft();
            } else if (currNode.getLeft() == null && currNode.getRight() != null) {
                return currNode.getRight();
            } else {
                AVLNode<T> dumDumNode = new AVLNode<>(null);
                currNode.setRight(removeSuccessor(currNode.getRight(), dumDumNode));
                currNode.setData(dumDumNode.getData());
                currNode = balanceMaintainance(updateHBF(currNode));
            }
        }
        return currNode;
    }

    /**
     * Recursive helper method to find the successor to replace the removed note
     * @param currNode The node to be replaced
     * @param dummyNode The node to cary the successor's data above.
     * @return a BSTNode that was added
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> currNode, AVLNode<T> dummyNode) {
        if (currNode.getLeft() == null) {
            dummyNode.setData(currNode.getData());
            return currNode.getRight();
        } else {
            currNode.setLeft(removeSuccessor(currNode.getLeft(), dummyNode));
            currNode = balanceMaintainance(updateHBF(currNode));
        }
        return currNode;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        AVLNode<T> dummyNode = new AVLNode<T>(null);
        root = getHelper(root, data, dummyNode);
        return dummyNode.getData();
    }


    /**
     * Recursive helper method for getting the node.
     * the starting root.
     * @param data The data to be found
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the data out
     * @return a BSTNode that has the data
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private AVLNode<T> getHelper(AVLNode<T> currNode, T data, AVLNode<T> dummyNode) {
        if (currNode == null) {
            throw new NoSuchElementException("Given data is not in the Tree");
        } else if (data.equals(currNode.getData())) {
            dummyNode.setData(currNode.getData());
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(getHelper(currNode.getRight(), data, dummyNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(getHelper(currNode.getLeft(), data, dummyNode));
        }
        return currNode;
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data could not be null");
        }
        if (root == null || size == 0) {
            return false;
        }
        if (root.getData().equals(data)) {
            return true;
        }
        AVLNode<T> dummyNode = new AVLNode<>(null);
        root = containsHelper(root, data, dummyNode);
        return dummyNode.getData() != null;
    }

    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param data The data to be searched
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the data or null value out
     * @return a BSTNode that has the data or null value
     */
    private AVLNode<T> containsHelper(AVLNode<T> currNode, T data, AVLNode<T> dummyNode) {
        if (currNode == null) {
            dummyNode.setData(null);
            return currNode;
        } else if (data.equals(currNode.getData())) {
            dummyNode.setData(currNode.getData());
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(containsHelper(currNode.getRight(), data, dummyNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(containsHelper(currNode.getLeft(), data, dummyNode));
        }
        return currNode;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the given data can not be null");
        }
        T result = null;
        if (size <= 1) {
            return result;
        }
        if (root.getData().compareTo(data) == 0) {
            result = lHPHelper(root.getLeft());
        } else {
            result = uHP(root, data, root.getData());
        }

        return result;
    }
    /**
     * helper method for searching the predecessor.
     *
     * @param data The data to be searched for
     * @param currNode The starting node usually the root
     * @param smallest the data of the lowest ancestor
     * @return the data of predecessor
     */
    private T uHP(AVLNode<T> currNode, T data, T smallest) {
        if (currNode == null) {
            throw new NoSuchElementException("Given data does not exist in the tree");
        }
        if (currNode.getData().equals(data)) {
            if (currNode.getLeft() == null) {
                if (smallest.compareTo(currNode.getData()) > 0) {
                    return null;
                }
                return smallest;
            } else {
                return lHPHelper(currNode.getLeft());
            }
        } else if (currNode.getData().compareTo(data) < 0) {
            return uHP(currNode.getRight(), data, currNode.getData());
        } else {
            return uHP(currNode.getLeft(), data, smallest);
        }
    }

    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode The starting node usually the root
     * @return the predecessor if existed.
     */
    private T lHPHelper(AVLNode<T> currNode) {
        if (currNode.getRight() == null) {
            return currNode.getData();
        } else {
            return lHPHelper(currNode.getRight());
        }
    }


    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Number of smallest element is out of bound.");
        }
        if (k == 0) {
            return new ArrayList<>();
        }
        return kHelper(root, k, new ArrayList<T>());
    }
    /**
     * Recursive helper method for traversal.
     *
     * @param k the number of smallest elements.
     * @param currNode the starting root.
     * @param elems the list container to hold the elements
     * @return a BSTNode that has the data or null value
     */
    private List<T> kHelper(AVLNode<T> currNode, int k, List<T> elems) {
        if (currNode == null || k <= 0) {
            return elems;
        }
        kHelper(currNode.getLeft(), k, elems);
        if (k > elems.size()) {
            elems.add(currNode.getData());
        }
        kHelper(currNode.getRight(), k, elems);
        return elems;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}