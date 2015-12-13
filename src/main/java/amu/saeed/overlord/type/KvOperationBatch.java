package amu.saeed.overlord.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KvOperationBatch implements Serializable {
    final public List<KvOperation> kvOperations;

    public KvOperationBatch() { this.kvOperations = new ArrayList<>(); }

    public KvOperationBatch(List<KvOperation> kvOperations) { this.kvOperations = kvOperations; }

}
