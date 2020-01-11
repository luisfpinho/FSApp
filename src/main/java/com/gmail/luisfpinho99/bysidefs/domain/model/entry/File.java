package com.gmail.luisfpinho99.bysidefs.domain.model.entry;

import com.gmail.luisfpinho99.bysidefs.Preconditions;
import com.gmail.luisfpinho99.bysidefs.domain.model.user.User;

import javax.persistence.Entity;

@Entity
public class File extends Entry {

    private FileContent content;

    public File(EntryName name, Directory parent, User owner, FileContent content) {
        super(name, parent, owner);
        Preconditions.ensureNotNull(content);
        this.content = content;
        this.size = content.size();
    }

    public File(EntryName name, Directory parent, User owner) {
        this(name, parent, owner, new FileContent());
    }

    public File(EntryName name, Directory parent) {
        this(name, parent, parent.owner, new FileContent());
    }

    /**
     * Only for ORM.
     */
    protected File() {
    }

    @Override
    public Entry copy(Directory newParent, EntryName newName, boolean keepPermissions, boolean keepOwner) {
        User owner = keepOwner ? this.owner : newParent.owner;
        File newFile = new File(newName, newParent, owner, this.content);

        if (keepPermissions) {
            newFile.permissions = this.permissions;
        }

        return newFile;
    }

    @Override
    protected void updateSize() {
        size = content.size();
    }

    public FileContent getContent() {
        return content;
    }

    public void updateContent(FileContent newContent) {
        Preconditions.ensureNotNull(newContent);
        this.content = newContent;
        updateSize();
        updateLastModificationDate();
        parent.updateSize();
        parent.updateLastModificationDate();
    }
}
