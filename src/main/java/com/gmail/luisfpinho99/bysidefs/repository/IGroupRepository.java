package com.gmail.luisfpinho99.bysidefs.repository;

import com.gmail.luisfpinho99.bysidefs.domain.model.group.GroupName;
import com.gmail.luisfpinho99.bysidefs.domain.model.group.UserGroup;

public interface IGroupRepository {

    UserGroup save(UserGroup group);

    UserGroup findById(GroupName name);
}
