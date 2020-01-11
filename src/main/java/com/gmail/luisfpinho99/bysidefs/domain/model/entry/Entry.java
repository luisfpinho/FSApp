package com.gmail.luisfpinho99.bysidefs.domain.model.entry;

import com.gmail.luisfpinho99.bysidefs.Preconditions;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Entry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    protected EntryName name;

    @ManyToOne(fetch = FetchType.LAZY)
    protected Directory parent;

    @ManyToOne
    protected User owner;

    protected CreationTime creationTime;
    protected LastModificationTime lastModificationTime;

    protected Permissions permissions;

    protected int size;

    public Entry(EntryName name, Directory parent, User owner) {
        Preconditions.ensureNotNull(name, parent, owner);

        this.name = name;
        this.parent = parent;
        this.owner = owner;

        creationTime = new CreationTime();
        lastModificationTime = new LastModificationTime();

        // Permissions are immutable
        permissions = parent.permissions;

        size = 0;
    }

    public Entry(EntryName name, Directory parent) {
        this(name, parent, parent.owner);
    }

    /**
     * For ORM and creating root user only.
     */
    protected Entry() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherEntry = (Entry) other;

        // Unstored entities are unique
        if (this.id == null || otherEntry.id == null) {
            return false;
        }

        return Objects.equals(id, otherEntry.id);
    }

    @Override
    public int hashCode() {
        // Just to garantee that unstored entities are unique (not the best way)
        return id == null ? new Random().nextInt(3213213) : Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public EntryName getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    public User getOwner() {
        return owner;
    }

    public int getSize() {
        return size;
    }

    public CreationTime getCreationTime() {
        return creationTime;
    }

    public LastModificationTime getLastModificationTime() {
        return lastModificationTime;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public boolean isRoot() {
        return name.matches("");
    }

    public boolean isOwner(User user) {
        return owner.equals(user);
    }

    public void updatePermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public boolean nameMatches(String name) {
        return this.name.matches(name);
    }

    public String absolutePath() {
        if (isRoot()) {
            return String.valueOf(Directory.SEPARATOR);
        }

        StringBuilder pathBuilder = new StringBuilder();
        absolutePathRec(this, pathBuilder);
        return pathBuilder.toString();
    }

    public boolean canBeReadBy(User user) {
        if (owner.equals(user)) {
            return permissions.ownerRead;
        }

        if (user.getGroup().equals(owner.getGroup())) {
            return permissions.groupRead;
        }

        return permissions.everyoneRead;
    }

    public boolean canBeExecutedBy(User user) {
        if (owner.equals(user)) {
            return permissions.ownerExecute;
        }

        if (user.getGroup().equals(owner.getGroup())) {
            return permissions.groupExecute;
        }

        return permissions.everyoneExecute;
    }

    public boolean canBeWrittenBy(User user) {
        if (owner.equals(user)) {
            return permissions.ownerWrite;
        }

        if (user.getGroup().equals(owner.getGroup())) {
            return permissions.groupWrite;
        }

        return permissions.everyoneWrite;
    }

    public void updateName(EntryName newName) {
        Preconditions.ensureNotNull(newName);
        this.name = newName;
    }

    protected void updateLastModificationDate() {
        this.lastModificationTime = new LastModificationTime();
    }

    public abstract Entry copy(Directory newParent, EntryName newName, boolean keepPermissions, boolean keepOwner);

    protected abstract void updateSize();

    public static String nameFromPath(String path) {
        int indexOfSeparator = path.lastIndexOf(Directory.SEPARATOR);
        if (indexOfSeparator == -1) {
            return path;
        } else {
            return path.substring(indexOfSeparator + 1);
        }
    }

    private static void absolutePathRec(Entry current, StringBuilder pathBuilder) {
        if (current == null || current.parent == null) {
            return;
        }

        absolutePathRec(current.parent, pathBuilder);
        pathBuilder.append(Directory.SEPARATOR).append(current.name);
    }
}
