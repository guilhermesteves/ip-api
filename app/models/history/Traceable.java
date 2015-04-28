package models.history;

import java.util.List;

/**
 * May the build success be with you
 * With great problems, comes great help from @guilhermesteves
 */
public interface Traceable<M> {

    public String getDetails();

    public List<String> getChanges(M model);
}
