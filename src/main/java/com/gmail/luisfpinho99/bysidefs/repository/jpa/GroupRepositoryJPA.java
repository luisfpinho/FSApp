package com.gmail.luisfpinho99.bysidefs.repository.jpa;

import com.gmail.luisfpinho99.bysidefs.domain.model.group.GroupName;
import com.gmail.luisfpinho99.bysidefs.domain.model.group.UserGroup;
import com.gmail.luisfpinho99.bysidefs.repository.IGroupRepository;

public class GroupRepositoryJPA extends RepositoryJPA<UserGroup, GroupName> implements IGroupRepository {

    public GroupRepositoryJPA() {
        super(UserGroup.class);
    }
}
