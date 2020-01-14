package com.gmail.luisfpinho99.fsapp.repository.memory;

import com.gmail.luisfpinho99.fsapp.domain.model.group.GroupName;
import com.gmail.luisfpinho99.fsapp.domain.model.group.UserGroup;
import com.gmail.luisfpinho99.fsapp.repository.IGroupRepository;

public class GroupRepositoryMem extends MemRepository<UserGroup, GroupName> implements IGroupRepository {

    protected GroupName getId(UserGroup group) {
        return group.name();
    }
}
