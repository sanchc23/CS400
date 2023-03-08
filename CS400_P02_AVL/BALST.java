import java.util.List;

/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black.
 * Note which tree you implement here and as a comment when you submit.
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

	private BSTNode<K, V> root;

	private int numKeys;

	public BALST() {
	}

	@Override
	public K getKeyAtRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<K> getInOrderTraversal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<K> getPreOrderTraversal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<K> getPostOrderTraversal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<K> getLevelOrderTraversal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numKeys() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub

	}
	
}
