package models.common;

import models.users.BaseUser;

import java.util.List;

/**
 * May the build success be with you.
 * With great problems, comes great help from @guilhermesteves
 */
public interface BaseStatistics {

    Long getInteractions();

    List<BaseUser> getParticipants();

    BaseUser getParticipant(String id);

    void setParticipant(BaseUser participant);
}
