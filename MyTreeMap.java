package TestMap;


import java.util.Random;

/**
 * @ClassName MyTreeMap
 * @Description TODO
 * @Auther danni
 * @Date 2019/10/13 9:46]
 * @Version 1.0
 **/

public class MyTreeMap<K extends Comparable<K>,V> {

    public static class Entry<K,V>{
        K key;
        V value;
        Entry left;
        Entry right;
        public Entry(K key,V value){
            this.key=key;
            this.value=value;
        }

        public String toString() {
            return String.format("{%s=%s}", key, value);
        }
    }
    private  Entry root=null;

    public V get(K key){
        if(root==null){
            return null;
        }
        Entry<K,V> cur=root;
        while(cur!=null){
            int r=key.compareTo((K)cur.key);
            if(r==0){
                return (V)cur.value;
            }else if(r<0){
                cur=cur.left;
            }else{
                cur=cur.right;
            }
        }
        return null;
    }
    public V put(K key,V value){
        if (root == null) {
            root = new Entry<>(key, value);
            return null;
        }
        Entry<K, V> parent = null;
        Entry<K, V> cur = root;
        while (cur != null) {
            int r = key.compareTo(cur.key);
            if (r == 0) {
                V oldValue = cur.value;
                cur.value = value;
                return oldValue;
            } else if (r < 0) {
                parent = cur;
                cur = cur.left;
            } else {
                parent = cur;
                cur = cur.right;
            }
        }
        Entry<K, V> entry = new Entry<>(key, value);
        if (key.compareTo(parent.key) < 0) {
            parent.left = entry;
        } else {
            parent.right = entry;
        }
        return null;
    }
    public boolean remove(K key){
        if(root==null){
            return false;
        }
        Entry<K,V> cur=root;
        Entry<K,V> parent=null;
        while(cur!=null){
            int r=key.compareTo(cur.key);
            if(r==0){
                removeEntry(parent,cur);
                return true;
            }else if(r<0){
                parent=cur;
                cur=cur.left;
            }else{
                parent=cur;
                cur=cur.right;
            }
        }
        return true;
    }

    private void removeEntry(Entry<K,V> parent, Entry<K,V> cur) {
        if(cur.left==null){
            if(cur==root){
                root=root.right;
            }else if(cur==parent.right){
                parent.right=cur.right;
            }else{
                parent.left=cur.right;
            }
        }
        if(cur.right==null){
            if(cur==root){
                root=root.left;
            }else if(cur==parent.left){
                parent.left=cur.left;
            }else{
                parent.right=cur.left;
            }
        }
        if(cur.right!=null&&cur.left!=null){
            Entry<K,V> temp=cur.right;
            Entry<K,V> head=null;
            while(temp!=null){
                head=temp;
                temp=temp.left;
            }
            cur.key=temp.key;
            cur.value=temp.value;

            if(temp==head.left){
                head.left=temp.right;
            }else if(temp==head.right){
                head.right=temp.right;
            }
        }
    }

    public static interface Function<T> {
        void apply(T entry);
    }
    public static <K, V> void preOrderTraversal(Entry<K, V> node, Function<Entry<K, V>> func) {
        if (node != null) {
            func.apply(node);
            preOrderTraversal(node.left, func);
            preOrderTraversal(node.right, func);
        }
    }
    public static <K, V> void inOrderTraversal(Entry<K, V> node, Function<Entry<K, V>>
            func) {
        if (node != null) {
            inOrderTraversal(node.left, func);
            func.apply(node);
            inOrderTraversal(node.right, func);
        }
    }
    public void print() {
        System.out.println("前序遍历: ");
        preOrderTraversal(root, (n) -> {
            System.out.print(n.key + " ");
        });
        System.out.println();
        System.out.println("中序遍历: ");
        inOrderTraversal(root, (n) -> {
            System.out.print(n.key + " ");
        });
        System.out.println();
    }

    public static void main(String[] args) {
        MyTreeMap<Integer, String> tree = new MyTreeMap<>();
        int count = 0;
        Random random = new Random(20190915);
        for (int i = 0; i < 20; i++) {
            int key = random.nextInt(200);
            String value = String.format("Value of %d", key);
            if (tree.put(key, value) == null) {
                count++;
            }
        }
        System.out.println("一共插入 " + count + " 个结点");
        tree.print();
    }
}
