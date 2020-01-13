package com.gmail.luisfpinho99.fsapp.bootstrap;

import com.gmail.luisfpinho99.fsapp.domain.UserBuilder;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.Directory;
import com.gmail.luisfpinho99.fsapp.domain.model.entry.EntryName;
import com.gmail.luisfpinho99.fsapp.domain.model.group.UserGroup;
import com.gmail.luisfpinho99.fsapp.domain.model.group.GroupName;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;
import com.gmail.luisfpinho99.fsapp.domain.model.user.Username;
import com.gmail.luisfpinho99.fsapp.repository.IDirectoryRepository;
import com.gmail.luisfpinho99.fsapp.repository.IGroupRepository;
import com.gmail.luisfpinho99.fsapp.repository.IUserRepository;
import com.gmail.luisfpinho99.fsapp.repository.PersistenceContext;

public class Bootstrapper {

    private final IDirectoryRepository directoryRepository = PersistenceContext.repositories().directories();
    private final IUserRepository userRepository = PersistenceContext.repositories().users();
    private final IGroupRepository groupRepository = PersistenceContext.repositories().groups();

    public void bootstrap() {
        UserGroup rootGroup = createGroup(UserGroup.ROOT_GROUP_NAME);
        UserGroup normalGroup = createGroup(GroupName.from("normal"));

        User rootUser = createUser(User.ROOT_USERNAME, "root", rootGroup);
        User normalUser = createUser(Username.from("joe"), "qwerty", normalGroup);

        Directory rootDirectory = createRootDirectory(rootUser);
        createDirectory("myfolder", rootDirectory, normalUser);
        createDirectory("systemfolder", rootDirectory, rootUser);
    }

    private User createUser(Username username, String password, UserGroup group) {
        User rootUser = userRepository.findById(username);
        if (rootUser == null) {
            rootUser = new UserBuilder()
                    .withUsername(username)
                    .withPassword(password)
                    .withGroup(group)
                    .build();
            userRepository.save(rootUser);
        }

        return rootUser;
    }

    private UserGroup createGroup(GroupName name) {
        UserGroup group = groupRepository.findById(name);
        if (group == null) {
            group = new UserGroup(name);
            group = groupRepository.save(group);
        }

        return group;
    }

    private Directory createRootDirectory(User rootUser) {
        Directory rootDirectory = directoryRepository.findRoot();
        if (rootDirectory == null) {
            rootDirectory = Directory.createRoot(rootUser);
            rootDirectory = directoryRepository.save(rootDirectory);
        }

        return rootDirectory;
    }

    private void createDirectory(String name, Directory parent, User owner) {
        if (parent.findChild(name) != null) {
            return;
        }

        Directory directory = new Directory(EntryName.from(name), parent, owner);
        parent.addEntry(directory);
        directoryRepository.save(directory);
        directoryRepository.save(parent);
    }
}
