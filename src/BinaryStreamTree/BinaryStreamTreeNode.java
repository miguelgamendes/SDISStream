package BinaryStreamTree;

/**
 * Created by danfergo on 27-05-2015.
 */
public abstract class BinaryStreamTreeNode {
    protected BinaryStreamTreeRemoteNode olderSon = null;
    protected BinaryStreamTreeRemoteNode youngerSon = null;


    public abstract void disconnect();

    public abstract void connectRequest();

}
