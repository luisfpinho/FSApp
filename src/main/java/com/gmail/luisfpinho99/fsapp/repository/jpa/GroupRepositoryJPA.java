package com.gmail.luisfpinho99.fsapp.repository.jpa;

import com.gmail.luisfpinho99.fsapp.domain.model.group.GroupName;
import com.gmail.luisfpinho99.fsapp.domain.model.group.UserGroup;
import com.gmail.luisfpinho99.fsapp.repository.IGroupRepository;

public class GroupRepositoryJPA extends RepositoryJPA<UserGroup, GroupName> implements IGroupRepository {

    public GroupRepositoryJPA() {
        super(UserGroup.class);
    }
}
