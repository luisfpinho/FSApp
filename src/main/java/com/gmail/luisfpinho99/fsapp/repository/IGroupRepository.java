package com.gmail.luisfpinho99.fsapp.repository;

import com.gmail.luisfpinho99.fsapp.domain.model.group.GroupName;
import com.gmail.luisfpinho99.fsapp.domain.model.group.UserGroup;

public interface IGroupRepository {

    UserGroup save(UserGroup group);

    UserGroup findById(GroupName name);
}
