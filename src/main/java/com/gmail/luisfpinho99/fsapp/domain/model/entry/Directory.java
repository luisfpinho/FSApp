package com.gmail.luisfpinho99.fsapp.domain.model.entry;

import com.gmail.luisfpinho99.fsapp.Preconditions;
import com.gmail.luisfpinho99.fsapp.domain.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Directory extends Entry {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<Entry> children;

    public static final char SEPARATOR = '/';
    public static final String PARENT_DIRECTORY = "..";

    public Directory(EntryName name, Directory parent, User owner) {
        super(name, parent, owner);
        this.children = new HashSet<>();
    }

    public Directory(EntryName name, Directory parent) {
        this(name, parent, parent.owner);
    }

    /**
     * Only for ORM.
     */
    protected Directory() {
        // Initialize final fields
        children = new HashSet<>();
    }

    @Override
    public Entry copy(Directory newParent, EntryName newName, boolean keepPermissions, boolean keepOwner) {
        User owner = keepOwner ? this.owner : newParent.owner;
        Directory newDirectory = new Directory(newName, newParent, owner);
        for (Entry child : children) {
            newDirectory.addEntry(child.copy(this, child.name, keepPermissions, keepOwner));
        }

        return newDirectory;
    }

    public Entry findChild(String name) {
        for (Entry child : children) {
            if (child.nameMatches(name)) {
                return child;
            }
        }

        return null;
    }

    public String pathWithChild(String name) {
        return String.format("%s%s%s", absolutePath(), Directory.SEPARATOR, name);
    }

    public void addEntry(Entry entry) {
        addEntry(entry, true, true);
    }

    public void addEntry(Entry entry, boolean keepPermissions, boolean keepOwner) {
        Preconditions.ensure(
                String.format("The entry '%s' already exists in the directory '%s'.", entry.name, this.name),
                !contains(entry)
        );

        children.add(entry);
        entry.parent = this;
        size += entry.size;
        updateLastModificationDate();

        if (!keepPermissions) {
            entry.permissions = this.permissions;
        }

        if (!keepOwner) {
            entry.owner = this.owner;
        }
    }

    public void removeEntry(Entry entry) {
        children.remove(entry);
        size -= entry.size;
        updateLastModificationDate();
    }

    public Set<Entry> getChildren() {
        return children;
    }

    public static Directory createRoot(User rootUser) {
        Directory root = new Directory();
        root.name = EntryName.createRootName();
        root.parent = null;
        root.owner = rootUser;
        root.creationTime = new CreationTime();
        root.lastModificationTime = new LastModificationTime();
        root.permissions = new Permissions(true, true, true, true, true, true, true, false, true);
        return root;
    }

    @Override
    protected void updateSize() {
        int totalSize = 0;

        for (Entry child : children) {
            totalSize += child.size;
        }

        size = totalSize;
    }

    private boolean contains(Entry entry) {
        if (children.contains(entry)) {
            return true;
        }

        for (Entry child : children) {
            if (child.name.equals(entry.name)) {
                return true;
            }
        }

        return false;
    }
}
